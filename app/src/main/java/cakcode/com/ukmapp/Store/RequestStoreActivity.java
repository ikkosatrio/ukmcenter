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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cakcode.com.ukmapp.Global.Cak;
import cakcode.com.ukmapp.Global.SessionManager;
import cakcode.com.ukmapp.Helper.API;
import cakcode.com.ukmapp.Helper.MultipartJSONRequest;
import cakcode.com.ukmapp.Helper.MyRequest;
import cakcode.com.ukmapp.MainActivity;
import cakcode.com.ukmapp.Member.LandingActivity;
import cakcode.com.ukmapp.Model.Member;
import cakcode.com.ukmapp.R;
import spencerstudios.com.bungeelib.Bungee;

public class RequestStoreActivity extends AppCompatActivity {
    Toolbar toolbar;
    Button btnBuat;
    EditText edNamaUKM,edDomainUKM;
    ProgressBar mProgressBar;
    private MultipartJSONRequest request;
    Gson gson = new Gson();
    private SessionManager session;
    Member mMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_store);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        edNamaUKM = (EditText) findViewById(R.id.edNamaUKM);
        edDomainUKM = (EditText) findViewById(R.id.edDomainUKM);
        btnBuat = (Button) findViewById(R.id.btnBuat);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        session = new SessionManager(RequestStoreActivity.this);
        mMember = session.getCurrentMember();

        setSupportActionBar(toolbar);
        toolbar.setTitle("BUKA UKM");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Bungee.slideDown(RequestStoreActivity.this);
            }
        });

        btnBuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(formValidation()){
//                    sendData();
                    startActivity(new Intent(RequestStoreActivity.this,RequestStoreDoneActivity.class));
                    Bungee.slideLeft(RequestStoreActivity.this);
                }

            }
        });
    }

    private boolean formValidation() {
        boolean check = true;

        if (edNamaUKM.getText().toString().isEmpty()){
            check = false;
            edNamaUKM.setError("insert name");
        }

        if (edDomainUKM.getText().toString().isEmpty()){
            check = false;
            edNamaUKM.setError("insert domain");
        }

        return check;
    }

    public void sendData(){
        showProgressBar();
        String url = API.STORE_ADD;
        request = new MultipartJSONRequest(Request.Method.POST, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideProgressBar();
                        if (API.isResponseSuccess(response)) {
                            try {
                                JSONObject jsonResult = response.getJSONObject(API.JSON_DATA
                                );
                                startActivity(new Intent(RequestStoreActivity.this,RequestStoreDoneActivity.class));
                                Bungee.slideLeft(RequestStoreActivity.this);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } else {
                            startActivity(new Intent(RequestStoreActivity.this,MainActivity.class));
                            Bungee.slideDown(RequestStoreActivity.this);
                            Toast.makeText(RequestStoreActivity.this, "Gagal"+API.getResponseMessage(response), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgressBar();
                Toast.makeText(RequestStoreActivity.this    , "Failed "+error, Toast.LENGTH_SHORT).show();
            }
        }
        );

        request.addStringParam("ClientID", API.CLIENT_ID);
        request.addStringParam("IDMember", String.valueOf(mMember.getId()));
        request.addStringParam("Nama", String.valueOf(edNamaUKM.getText()));

        String path = edDomainUKM.getText().toString();
        String segments[] = path.split("/");
        String document = "";
        if(segments.length > 0){
            document = segments[1];
        }

        request.addStringParam("URl", String.valueOf(document));

        request.setShouldCache(false);
        Log.d("savedata", MyRequest.getDebugReqString(url, request));
        MyRequest.getInstance(RequestStoreActivity.this).addToRequestQueue(request);
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
