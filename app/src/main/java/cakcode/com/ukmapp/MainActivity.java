package cakcode.com.ukmapp;

import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;

import cakcode.com.ukmapp.Global.Cak;
import cakcode.com.ukmapp.Global.SessionManager;
import cakcode.com.ukmapp.Helper.API;
import cakcode.com.ukmapp.Home.AccountFragment;
import cakcode.com.ukmapp.Home.CartFragment;
import cakcode.com.ukmapp.Home.ControlStoreFragment;
import cakcode.com.ukmapp.Home.HomeFragment;
import cakcode.com.ukmapp.Home.StoreFragment;
import cakcode.com.ukmapp.Home.WishlishFragment;
import cakcode.com.ukmapp.Member.LandingActivity;
import cakcode.com.ukmapp.Member.LoginActivity;
import cakcode.com.ukmapp.Model.Member;
import cakcode.com.ukmapp.Product.ProductActivity;
import cakcode.com.ukmapp.Product.ProductDetailActivity;
import spencerstudios.com.bungeelib.Bungee;

public class MainActivity extends AppCompatActivity {

//    private ActionBar toolbar;

    TextView tvTitle;
    private SessionManager session;
    Member mCurrenMember;
    EditText edSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);
        session = new SessionManager(MainActivity.this);
        initActivity();

        tvTitle = (TextView)findViewById(R.id.tvTitle);
        edSearch = (EditText)findViewById(R.id.edSearch);
        tvTitle.setText(Cak.getApplicationName(MainActivity.this));
        Toolbar toolbarMain = (Toolbar) findViewById(R.id.tbMain);
        setSupportActionBar(toolbarMain);
//        toolbar = getSupportActionBar();

        edSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    Intent intent = new Intent(MainActivity.this, ProductActivity.class);
                    intent.putExtra("Search",edSearch.getText().toString());
                    startActivity(intent);
                    Bungee.fade(MainActivity.this);
                    return true;
                }
                return false;
            }
        });


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

//        toolbar.setTitle("Home");
        loadFragment(new HomeFragment());
    }

    public void initActivity() {
        if(session.isLoggedIn() == false){
            Toast.makeText(MainActivity.this, "Anda belum login", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this,LandingActivity.class));
            Bungee.slideDown(MainActivity.this);
        }else {
            mCurrenMember = session.getCurrentMember();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:
//                    toolbar.setTitle("Home");
                    getSupportActionBar().show();
                    fragment = new HomeFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_wishlist:
                    getSupportActionBar().hide();
//                    toolbar.setTitle("Wishlist");
                    fragment = new WishlishFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_shop:
                    getSupportActionBar().hide();

                    fragment = new StoreFragment();

                    if(mCurrenMember.getIdStore() != 0){
//                        fragment = new ControlStoreFragment();
                    }

                    loadFragment(fragment);
                    return true;
                case R.id.navigation_cart:
                    getSupportActionBar().hide();
//                    toolbar.setTitle("Cart");
                    fragment = new CartFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_profile:
                    getSupportActionBar().hide();
//                    toolbar.setTitle("Profile");
                    fragment = new AccountFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
