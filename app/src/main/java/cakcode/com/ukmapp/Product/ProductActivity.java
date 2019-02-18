package cakcode.com.ukmapp.Product;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cakcode.com.ukmapp.Global.GridSpacingItemDecoration;
import cakcode.com.ukmapp.Helper.API;
import cakcode.com.ukmapp.Helper.MultipartJSONRequest;
import cakcode.com.ukmapp.Helper.MyRequest;
import cakcode.com.ukmapp.MainActivity;
import cakcode.com.ukmapp.Model.Category;
import cakcode.com.ukmapp.Model.Product;
import cakcode.com.ukmapp.R;
import spencerstudios.com.bungeelib.Bungee;

public class ProductActivity extends AppCompatActivity {
    private MultipartJSONRequest request;
    Toolbar toolbar;
    ProgressBar mProgressBar;
    EditText edSearch;
    Gson gson = new Gson();
    private RecyclerView rvProduct,rvCategory;
    private ProductAdapter productAdapter;
    private CategoryAdapter categoryAdapter;
    private List<Product> productList;
    private List<Category> categoryList;
    public int Start = 0;
    public int Count = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        rvProduct = (RecyclerView) findViewById(R.id.rvProduct);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        rvCategory = (RecyclerView)findViewById(R.id.rvCategory);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        edSearch = (EditText)findViewById(R.id.edSearch);

        String strSearch = getIntent().getStringExtra("Search");
        Log.d("OKKI", "onCreate: "+strSearch);
        if(strSearch != null){
            edSearch.setText(strSearch);
        }
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        productList = new ArrayList<>();
        categoryList = new ArrayList<>();
        productAdapter = new ProductAdapter(ProductActivity.this, productList);
        categoryAdapter = new CategoryAdapter(ProductActivity.this, categoryList);


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(ProductActivity.this, 2);
        rvProduct.setLayoutManager(mLayoutManager);
        rvProduct.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(5), true));
        rvProduct.setItemAnimator(new DefaultItemAnimator());
        rvProduct.setAdapter(productAdapter);

        rvProduct.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                Log.d("TROSS", "onScrolled: ");
//                if(dy > 0) //check for scroll down
//                {
//                    Log.d("BAWAH", "onScrolled: ");
//                }
                if (!recyclerView.canScrollVertically(1)) {
                    loadProduct();
                    Toast.makeText(ProductActivity.this, "Last", Toast.LENGTH_LONG).show();
                }
            }
        });


        LinearLayoutManager layoutManager
                = new LinearLayoutManager(ProductActivity.this, LinearLayoutManager.HORIZONTAL, false);
        rvCategory.setLayoutManager(layoutManager);
        rvCategory.setAdapter(categoryAdapter);

        edSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    Start = 0;
                    productList.clear();
                    loadProduct();
                    return true;
                }
                return false;
            }
        });

        loadProduct();
        loadCategory();
    }

    public void loadCategory(){
        String url = API.CATEGORY;
        request = new MultipartJSONRequest(Request.Method.POST, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Kirim Data", response.toString());
                        if (API.isResponseSuccess(response)) {
                            try {
                                JSONArray jsonResult = response.getJSONArray(API.JSON_DATA);

                                for (int i=0; i<jsonResult.length();i++){
                                    JSONObject data = jsonResult.getJSONObject(i);
                                    Category category = gson.fromJson(data.toString(),Category.class);
                                    categoryList.add(category);
                                }
                                categoryAdapter.notifyDataSetChanged();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        } else {
                            Toast.makeText(ProductActivity.this, "Gagal"+API.getResponseMessage(response), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProductActivity.this, "Gagal"+error, Toast.LENGTH_SHORT).show();
            }
        }
        );
        request.addStringParam("ClientID", API.CLIENT_ID);
        request.setShouldCache(false);
        Log.d("getProduct", MyRequest.getDebugReqString(url, request));
        MyRequest.getInstance(ProductActivity.this).addToRequestQueue(request);
    }

    @Override
    public void onBackPressed() {
        Log.d("Okki", "onBackPressed: ");
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d("OKKI", "onKeyDown: ");
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public void loadProduct(){
        showProgressBar();
        String url = API.PRODUCT_NEWER;
        request = new MultipartJSONRequest(Request.Method.POST, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Kirim Data", response.toString());
                        if (API.isResponseSuccess(response)) {
                            try {
                                JSONArray jsonResult = response.getJSONArray(API.JSON_DATA);
                                Start = response.getInt("Start");
                                for (int i=0; i<jsonResult.length();i++){
                                    JSONObject data = jsonResult.getJSONObject(i);
                                    Product product = gson.fromJson(data.toString(),Product.class);
                                    productList.add(product);
                                }
                                productAdapter.notifyDataSetChanged();
                                hideProgressBar();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        } else {
                            hideProgressBar();
                            Toast.makeText(ProductActivity.this, "Gagal"+API.getResponseMessage(response), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgressBar();
                Toast.makeText(ProductActivity.this, "Gagal"+error, Toast.LENGTH_SHORT).show();
            }
        }
        );

        request.addStringParam("ClientID", API.CLIENT_ID);
        request.addStringParam("Keyword", edSearch.getText().toString());
        request.addStringParam("Start", String.valueOf(Start));
        request.addStringParam("Count", String.valueOf(Count));

        request.setShouldCache(false);
        Log.d("getProduct", MyRequest.getDebugReqString(url, request));
        MyRequest.getInstance(ProductActivity.this).addToRequestQueue(request);
    }

    private int dpToPx(int dp) {
        Resources r = ProductActivity.this.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
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
