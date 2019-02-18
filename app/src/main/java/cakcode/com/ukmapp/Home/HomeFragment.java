package cakcode.com.ukmapp.Home;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cakcode.com.ukmapp.Global.GridSpacingItemDecoration;
import cakcode.com.ukmapp.Global.SessionManager;
import cakcode.com.ukmapp.Helper.API;
import cakcode.com.ukmapp.Helper.MultipartJSONRequest;
import cakcode.com.ukmapp.Helper.MyRequest;
import cakcode.com.ukmapp.MainActivity;
import cakcode.com.ukmapp.Member.LoginActivity;
import cakcode.com.ukmapp.Model.Category;
import cakcode.com.ukmapp.Model.Member;
import cakcode.com.ukmapp.Model.Product;
import cakcode.com.ukmapp.Product.CategoryAdapter;
import cakcode.com.ukmapp.Product.ProductAdapter;
import cakcode.com.ukmapp.Product.ProductSmallAdapter;
import cakcode.com.ukmapp.R;
import spencerstudios.com.bungeelib.Bungee;

public class HomeFragment extends Fragment {
    private SwipeRefreshLayout swipeContainer;
    CarouselView carouselView;
    View view;
    private MultipartJSONRequest request;
    Gson gson = new Gson();
    private RecyclerView rvProduct,rvProductPopuler,rvCategory;
    private ProductAdapter productAdapter;
    private ProductSmallAdapter productSmallAdapter;
    private CategoryAdapter categoryAdapter;
    private List<Category> categoryList;
    private List<Product> productList;

    private SessionManager session;
//    int[] sampleImages = {R.drawable.gallery, R.drawable.camera, R.drawable.btn_gradient_rounded, R.drawable.red_button_background, R.drawable.cart02};
//        String[] sampleNetworkImageURLs = {};
    ArrayList<String> sampleNetworkImageURLs = new ArrayList<String>();

    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        // Inflate the layout for this fragment
        session = new SessionManager(getActivity());

        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        carouselView = (CarouselView) view.findViewById(R.id.carouselView);
        rvProduct = (RecyclerView) view.findViewById(R.id.rvProduct);
        rvProductPopuler = (RecyclerView) view.findViewById(R.id.rvProductPopuler);
        rvCategory = (RecyclerView) view.findViewById(R.id.rvCategory);

        productList = new ArrayList<>();
        categoryList = new ArrayList<>();

        productAdapter = new ProductAdapter(getActivity(), productList);
        productSmallAdapter = new ProductSmallAdapter(getActivity(), productList);
        categoryAdapter = new CategoryAdapter(getActivity(), categoryList);

        RecyclerView.LayoutManager mLayoutManager2 = new GridLayoutManager(getActivity(), 3);
        rvCategory.setLayoutManager(mLayoutManager2);
        rvCategory.addItemDecoration(new GridSpacingItemDecoration(3, dpToPx(5), true));
        rvCategory.setItemAnimator(new DefaultItemAnimator());
        rvCategory.setAdapter(categoryAdapter);


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        rvProduct.setLayoutManager(mLayoutManager);
        rvProduct.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(5), true));
        rvProduct.setItemAnimator(new DefaultItemAnimator());
        rvProduct.setAdapter(productAdapter);


        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvProductPopuler.setLayoutManager(layoutManager);
        rvProductPopuler.setAdapter(productSmallAdapter);

        loadSlide();
        loadProduct();
        loadCategory();

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sampleNetworkImageURLs.clear();
                productList.clear();
                productAdapter.clear();
                productSmallAdapter.clear();
                loadSlide();
                loadProduct();

            }
        });

        return view;
    }

    private int dpToPx(int dp) {
        Resources r = getContext().getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public void initSlide(){
        carouselView.setImageListener(imageListener);
        carouselView.setPageCount(sampleNetworkImageURLs.size());
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
//            Picasso.get().load(sampleNetworkImageURLs[position]).placeholder(sampleNetworkImageURLs[0]).error(Integer.parseInt(sampleNetworkImageURLs[3])).fit().centerCrop().into(imageView);
            Picasso.get()
                    .load(sampleNetworkImageURLs.get(position))
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(imageView);
        }
    };

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

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
                            Toast.makeText(getActivity(), "Gagal"+API.getResponseMessage(response), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Gagal"+error, Toast.LENGTH_SHORT).show();
            }
        }
        );
        swipeContainer.setRefreshing(false);
        request.addStringParam("ClientID", API.CLIENT_ID);
        request.setShouldCache(false);
        Log.d("getProduct", MyRequest.getDebugReqString(url, request));
        MyRequest.getInstance(getActivity()).addToRequestQueue(request);
    }

    public void loadProduct(){
        String url = API.PRODUCT_NEWER;
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
                                    Product product = gson.fromJson(data.toString(),Product.class);
                                    productList.add(product);
                                }
                                productAdapter.notifyDataSetChanged();
                                productSmallAdapter.notifyDataSetChanged();
                                Log.d("Ikko", "onResponse: "+sampleNetworkImageURLs);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        } else {
                            Toast.makeText(getActivity(), "Gagal"+API.getResponseMessage(response), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Gagal"+error, Toast.LENGTH_SHORT).show();
            }
        }
        );
        swipeContainer.setRefreshing(false);
        request.addStringParam("ClientID", API.CLIENT_ID);
        request.setShouldCache(false);
        Log.d("getProduct", MyRequest.getDebugReqString(url, request));
        MyRequest.getInstance(getActivity()).addToRequestQueue(request);
    }

    public void loadSlide(){
        String url = API.GLOBAL_SLIDE;
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
                                    JSONObject photo = data.getJSONObject("Photo");
                                    sampleNetworkImageURLs.add(photo.getString("Small"));
                                }
                                initSlide();
                                Log.d("Ikko", "onResponse: "+sampleNetworkImageURLs);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        } else {
                            Toast.makeText(getActivity(), "Gagal"+API.getResponseMessage(response), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Gagal"+error, Toast.LENGTH_SHORT).show();
            }
        }
        );

        request.addStringParam("ClientID", API.CLIENT_ID);

        request.setShouldCache(false);
        Log.d("savedata", MyRequest.getDebugReqString(url, request));
        MyRequest.getInstance(getActivity()).addToRequestQueue(request);
    }
}
