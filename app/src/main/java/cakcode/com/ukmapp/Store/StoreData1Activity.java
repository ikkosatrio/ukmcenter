package cakcode.com.ukmapp.Store;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.google.gson.Gson;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import org.json.JSONException;
import org.json.JSONObject;

import cakcode.com.ukmapp.Global.SessionManager;
import cakcode.com.ukmapp.Helper.API;
import cakcode.com.ukmapp.Helper.MultipartJSONRequest;
import cakcode.com.ukmapp.Helper.MyRequest;
import cakcode.com.ukmapp.MainActivity;
import cakcode.com.ukmapp.Model.Member;
import cakcode.com.ukmapp.R;
import spencerstudios.com.bungeelib.Bungee;

public class StoreData1Activity extends AppCompatActivity {
    Button btnNext;
    Toolbar toolbar;
    EditText edSlogan, edDeskripsi;
    ImageView imgPicker;
    ProgressBar mProgressBar;
    private MultipartJSONRequest request;
    Gson gson = new Gson();
    private SessionManager session;
    Member mMember;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_data1);

        edSlogan = (EditText)findViewById(R.id.edSlogan);
        edDeskripsi = (EditText)findViewById(R.id.edDeskripsi);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        btnNext = (Button) findViewById(R.id.btnNext);
        imgPicker = (ImageView)findViewById(R.id.imgPicker);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);


        session = new SessionManager(StoreData1Activity.this);
        mMember = session.getCurrentMember();

        setSupportActionBar(toolbar);
        toolbar.setTitle("Data 1 / 3");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StoreData1Activity.this,StoreData2Activity.class));
                Bungee.slideLeft(StoreData1Activity.this);
            }
        });

        imgPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickImageDialog.build(new PickSetup())
                        .setOnPickResult(new IPickResult() {
                            @Override
                            public void onPickResult(PickResult r) {
                                Log.d("", "onPickResult: "+r.toString());
                                imgPicker.setImageURI(r.getUri());
                            }
                        })
                        .setOnPickCancel(new IPickCancel() {
                            @Override
                            public void onCancelClick() {
                                //TODO: do what you have to if user clicked cancel

                            }
                        }).show(StoreData1Activity.this.getSupportFragmentManager());
            }
        });
    }

    public void sendData(){
        showProgressBar();
        String url = API.STORE_ADD_DETAIL2;
        request = new MultipartJSONRequest(Request.Method.POST, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideProgressBar();
                        if (API.isResponseSuccess(response)) {
                            try {
                                JSONObject jsonResult = response.getJSONObject(API.JSON_DATA);
                                startActivity(new Intent(StoreData1Activity.this,StoreData2Activity.class));
                                Bungee.slideLeft(StoreData1Activity.this);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } else {
                            startActivity(new Intent(StoreData1Activity.this, MainActivity.class));
                            Bungee.slideDown(StoreData1Activity.this);
                            Toast.makeText(StoreData1Activity.this, "Gagal"+API.getResponseMessage(response), Toast.LENGTH_SHORT).show();
                        }
                        hideProgressBar();
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgressBar();
                Toast.makeText(StoreData1Activity.this    , "Failed "+error, Toast.LENGTH_SHORT).show();
            }
        }
        );

        request.addStringParam("ClientID", API.CLIENT_ID);
        request.addStringParam("IDMember", String.valueOf(mMember.getId()));
        request.addStringParam("Slogan", String.valueOf(edSlogan.getText()));
        request.addStringParam("Description", String.valueOf(edDeskripsi.getText()));

        request.setShouldCache(false);
        Log.d("savedata", MyRequest.getDebugReqString(url, request));
        MyRequest.getInstance(StoreData1Activity.this).addToRequestQueue(request);
    }

    public void showProgressBar(){
        mProgressBar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public void hideProgressBar(){
        mProgressBar.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
}
