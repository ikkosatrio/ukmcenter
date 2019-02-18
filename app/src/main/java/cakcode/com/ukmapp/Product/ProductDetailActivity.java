package cakcode.com.ukmapp.Product;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;

import cakcode.com.ukmapp.Global.Cak;
import cakcode.com.ukmapp.Model.Product;
import cakcode.com.ukmapp.R;

public class ProductDetailActivity extends AppCompatActivity {
    TextView tvTitle,tvPrice,tvPriceDiscount,tvDescription;
    CarouselView carouselView;
    Product mProduct;
    Gson gson;
    Toolbar toolbar;

    ArrayList<String> sampleNetworkImageURLs = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        gson = new Gson();
        tvTitle = (TextView)findViewById(R.id.tvtTitle);
        tvPrice = (TextView)findViewById(R.id.tvPrice);
        tvPriceDiscount = (TextView)findViewById(R.id.tvPriceDiscount);
        tvDescription = (TextView)findViewById(R.id.tvDescription);
        carouselView = (CarouselView)findViewById(R.id.carouselView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        String strObj = getIntent().getExtras().getString("Search");
        if(!strObj.isEmpty()){
            mProduct = gson.fromJson(strObj, Product.class);
            initView();
        }

        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(mProduct.getTitle());
                    isShow = true;
                } else if(isShow) {
                    collapsingToolbarLayout.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_36dp);

        upArrow.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

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

    public void initView(){
        if(mProduct != null){
            getSupportActionBar().setTitle(mProduct.getTitle());
            tvTitle.setText(mProduct.getTitle());
            tvPrice.setText("Rp. "+Cak.myCurrencyFormat(String.valueOf(mProduct.getPrice())));
            tvPriceDiscount.setText("Rp. "+Cak.myCurrencyFormat(String.valueOf(mProduct.getPriceDiscount())));
            if (mProduct.getPriceDiscount() != 0){
                tvPrice.setPaintFlags(tvPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                tvPriceDiscount.setText("Rp. "+ Cak.myCurrencyFormat(String.valueOf(mProduct.getPriceDiscount())));
            }
            tvDescription.setText(mProduct.getDescription());
        }

        for (int i =0;i<mProduct.getPhoto().size();i++){
            sampleNetworkImageURLs.add(mProduct.getPhoto().get(i).getPhoto().getLarge());
        }
        initSlide();
    }
}
