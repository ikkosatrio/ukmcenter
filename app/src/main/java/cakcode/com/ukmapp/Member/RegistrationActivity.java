package cakcode.com.ukmapp.Member;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.basgeekball.awesomevalidation.AwesomeValidation;

import org.json.JSONObject;

import cakcode.com.ukmapp.Global.Cak;
import cakcode.com.ukmapp.Helper.API;
import cakcode.com.ukmapp.Helper.MultipartJSONRequest;
import cakcode.com.ukmapp.Helper.MyRequest;
import cakcode.com.ukmapp.R;
import spencerstudios.com.bungeelib.Bungee;
import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;

public class RegistrationActivity extends AppCompatActivity {

    Button btnRegistration;
    EditText edName,edEmail,edPassword,edConfirmPassword;
    ProgressBar mProgressBar;
    private MultipartJSONRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        btnRegistration = (Button)findViewById(R.id.btnRegistration);
        edName = (EditText)findViewById(R.id.edName);
        edEmail = (EditText)findViewById(R.id.edEmail);
        edPassword = (EditText)findViewById(R.id.edPassword);
        edConfirmPassword = (EditText)findViewById(R.id.edConfirmPassword);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (formValidation()) {
                    sendData();
                }else{
                    Toast.makeText(RegistrationActivity.this, "Check Form", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean formValidation() {
        boolean check = true;

        if (edName.getText().toString().isEmpty()){
            check = false;
            edName.setError("insert name");
        }

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

        if (edConfirmPassword.getText().toString().isEmpty()){
            check = false;
            edConfirmPassword.setError("insert confirm password");
        }

        if (!edConfirmPassword.getText().toString().equals(edPassword.getText().toString())){
            check = false;
            edConfirmPassword.setError("password does'nt match");
        }

        return check;
    }

    public void sendData(){
        showProgressBar();
        String url = API.MEMBER_REGISTRATION;
        request = new MultipartJSONRequest(Request.Method.POST, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Kirim Data", response.toString());
                        if (API.isResponseSuccess(response)) {
                            Toast.makeText(RegistrationActivity.this, API.getResponseMessage(response), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegistrationActivity.this, "Gagal"+API.getResponseMessage(response), Toast.LENGTH_SHORT).show();
                            hideProgressBar();
                        }
                        hideProgressBar();
                    }
                }
                , new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideProgressBar();
                        Toast.makeText(RegistrationActivity.this, "Gagal"+error, Toast.LENGTH_SHORT).show();
                    }
                }
        );

        request.addStringParam("ClientID", API.CLIENT_ID);
        request.addStringParam("Name", edName.getText().toString());
        request.addStringParam("Email", edEmail.getText().toString());
        request.addStringParam("Password", edPassword.getText().toString());

        request.setShouldCache(false);
        Log.d("savedata", MyRequest.getDebugReqString(url, request));
        MyRequest.getInstance(this).addToRequestQueue(request);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        hideProgressBar();
        Bungee.slideRight(RegistrationActivity.this);
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
