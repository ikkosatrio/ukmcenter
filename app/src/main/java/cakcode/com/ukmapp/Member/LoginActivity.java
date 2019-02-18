package cakcode.com.ukmapp.Member;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import cakcode.com.ukmapp.Global.Cak;
import cakcode.com.ukmapp.Global.SessionManager;
import cakcode.com.ukmapp.Helper.API;
import cakcode.com.ukmapp.Helper.MultipartJSONRequest;
import cakcode.com.ukmapp.Helper.MyRequest;
import cakcode.com.ukmapp.MainActivity;
import cakcode.com.ukmapp.Model.Member;
import cakcode.com.ukmapp.R;
import spencerstudios.com.bungeelib.Bungee;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText edEmail,edPassword;
    TextView tvForgotPassword,tvRegistration;

    ProgressBar mProgressBar;
    private MultipartJSONRequest request;
    Gson gson = new Gson();
    private SessionManager session;
    Member mMember;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        session = new SessionManager(LoginActivity.this);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        edEmail = (EditText)findViewById(R.id.edEmail);
        edPassword = (EditText)findViewById(R.id.edPassword);
        tvForgotPassword = (TextView)findViewById(R.id.tvForgotPassword);
        tvRegistration = (TextView)findViewById(R.id.tvRegistration);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        tvRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));
                Bungee.slideLeft(LoginActivity.this);
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (formValidation()) {
                    sendData();

                }else{
                    Toast.makeText(LoginActivity.this, "Check Form", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Bungee.slideRight(LoginActivity.this);
    }

    private boolean formValidation() {
        boolean check = true;
        if (edEmail.getText().toString().isEmpty()){
            check = false;
            edEmail.setError("insert email");
        }

        if (!Cak.isEmailValid(edEmail.getText().toString())){
            check = false;
            edEmail.setError("format email wrong");
        }

        if (edPassword.getText().toString().isEmpty()){
            check = false;
            edPassword.setError("insert password");
        }

        return check;
    }

    public void sendData(){
        showProgressBar();
        String url = API.MEMBER_LOGIN;
        request = new MultipartJSONRequest(Request.Method.POST, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Kirim Data", response.toString());
                        if (API.isResponseSuccess(response)) {
                            try {
                                JSONObject jsonResult = response.getJSONObject(API.JSON_DATA);
                                mMember = gson.fromJson(jsonResult.toString(),Member.class);
                                session.setLogin(true);
                                session.setCurrentMember(jsonResult.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            Toast.makeText(LoginActivity.this, API.getResponseMessage(response), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            Bungee.slideUp(LoginActivity.this);
                        } else {
                            Toast.makeText(LoginActivity.this, "Gagal"+API.getResponseMessage(response), Toast.LENGTH_SHORT).show();
                            hideProgressBar();
                        }
                        hideProgressBar();
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgressBar();
                error.printStackTrace();
//                Log.d("OKKI", error.toString());
                Toast.makeText(LoginActivity.this, "Gagal"+error, Toast.LENGTH_SHORT).show();
            }
        }
        );

        request.addStringParam("Email", edEmail.getText().toString());
        request.addStringParam("Password", edPassword.getText().toString());
        request.addStringParam("ClientID", API.CLIENT_ID);

        request.setShouldCache(false);
        Log.d("savedata", MyRequest.getDebugReqString(url, request));
        MyRequest.getInstance(this).addToRequestQueue(request);
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
