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

public class StoreData2Activity extends AppCompatActivity {
    Button btnNext;
    Toolbar toolbar;
    EditText edProvince, edCity, edDistrict,edAddress;
    ProgressBar mProgressBar;
    private MultipartJSONRequest request;
    Gson gson = new Gson();
    private SessionManager session;
    Member mMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_data2);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        btnNext = (Button) findViewById(R.id.btnNext);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        edProvince = (EditText)findViewById(R.id.edProvince);
        edCity = (EditText)findViewById(R.id.edCity);
        edDistrict = (EditText)findViewById(R.id.edDistrict);
        edAddress = (EditText)findViewById(R.id.edAddress);

        session = new SessionManager(StoreData2Activity.this);
        mMember = session.getCurrentMember();

        setSupportActionBar(toolbar);
        toolbar.setTitle("Data 2 / 3");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(formValidation()){
                    startActivity(new Intent(StoreData2Activity.this, StoreData3Activity.class));
                    Bungee.slideLeft(StoreData2Activity.this);
                }
            }
        });
    }

    private boolean formValidation() {
        boolean check = true;

        if (edProvince.getText().toString().isEmpty()){
            check = false;
            edProvince.setError("insert provinsi");
        }

        if (edCity.getText().toString().isEmpty()){
            check = false;
            edCity.setError("insert city");
        }

        if (edDistrict.getText().toString().isEmpty()){
            check = false;
            edDistrict.setError("insert district");
        }

        if (edAddress.getText().toString().isEmpty()){
            check = false;
            edAddress.setError("insert address");
        }

        return check;
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
                                startActivity(new Intent(StoreData2Activity.this,StoreData3Activity.class));
                                Bungee.slideLeft(StoreData2Activity.this);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } else {
                            startActivity(new Intent(StoreData2Activity.this, MainActivity.class));
                            Bungee.slideDown(StoreData2Activity.this);
                            Toast.makeText(StoreData2Activity.this, "Gagal"+API.getResponseMessage(response), Toast.LENGTH_SHORT).show();
                        }
                        hideProgressBar();
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgressBar();
                Toast.makeText(StoreData2Activity.this    , "Failed "+error, Toast.LENGTH_SHORT).show();
            }
        }
        );

        request.addStringParam("ClientID", API.CLIENT_ID);
        request.addStringParam("IDMember", String.valueOf(mMember.getId()));
        request.addStringParam("Province", String.valueOf(edProvince.getText()));
        request.addStringParam("City", String.valueOf(edCity.getText()));
        request.addStringParam("District", String.valueOf(edDistrict.getText()));
        request.addStringParam("Address", String.valueOf(edAddress.getText()));

        request.setShouldCache(false);
        Log.d("savedata", MyRequest.getDebugReqString(url, request));
        MyRequest.getInstance(StoreData2Activity.this).addToRequestQueue(request);
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
