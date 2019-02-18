package cakcode.com.ukmapp.Member;

import android.content.Intent;
import android.net.wifi.hotspot2.pps.HomeSp;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cakcode.com.ukmapp.MainActivity;
import cakcode.com.ukmapp.R;
import spencerstudios.com.bungeelib.Bungee;

public class LandingActivity extends AppCompatActivity {

    Button btnLogin,btnRegitration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        btnRegitration = (Button)findViewById(R.id.btnRegistration);
        btnLogin = (Button)findViewById(R.id.btnLogin);

        btnRegitration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LandingActivity.this,MainActivity.class));
                Bungee.slideLeft(LandingActivity.this);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LandingActivity.this,LoginActivity.class));
                Bungee.slideLeft(LandingActivity.this);
            }
        });
    }
}
