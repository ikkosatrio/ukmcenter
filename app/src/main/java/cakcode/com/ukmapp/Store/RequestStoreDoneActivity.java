package cakcode.com.ukmapp.Store;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cakcode.com.ukmapp.R;
import spencerstudios.com.bungeelib.Bungee;

public class RequestStoreDoneActivity extends AppCompatActivity {
    Button btnLengkapi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_store_done);

        btnLengkapi = (Button)findViewById(R.id.btnLengkapi);
        btnLengkapi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RequestStoreDoneActivity.this,StoreData1Activity.class));
                Bungee.slideLeft(RequestStoreDoneActivity.this);
            }
        });
    }
}
