package cakcode.com.ukmapp.Product;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.util.List;

import cakcode.com.ukmapp.Global.Cak;
import cakcode.com.ukmapp.Model.Product;
import cakcode.com.ukmapp.R;
import spencerstudios.com.bungeelib.Bungee;

public class ProductSmallAdapter extends RecyclerView.Adapter<ProductSmallAdapter.ProductViewHolder> {
    private Context mContext;
    private List<Product> productList;

    public ProductSmallAdapter(Context mContext, List<Product> albumList) {
        this.mContext = mContext;
        this.productList = albumList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_product_small, viewGroup, false);

        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder productViewHolder, int i) {
        final Product product = productList.get(i);
        productViewHolder.title.setText(product.getTitle());
        productViewHolder.description.setText(product.getDescription());
        if (product.getPhoto().size() > 0 && product.getPhoto().get(0) != null){
            productViewHolder.imgProduct.setImageURI(product.getPhoto().get(0).getPhoto().getSmall());
        }

        productViewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProductDetailActivity.class);
                Gson gson = new Gson();
                Log.d("Ikko", "onClick: "+product.toString());
                intent.putExtra("Product", gson.toJson(product));
                mContext.startActivity(intent);
                Bungee.card(mContext);
            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void clear() {
        productList.clear();
        notifyDataSetChanged();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        public TextView title,description;
        public SimpleDraweeView imgProduct;
        public View mView;


        public ProductViewHolder(final View view){
            super(view);
            mView = view;
            title = (TextView) view.findViewById(R.id.item_title);
            description = (TextView) view.findViewById(R.id.item_description);
            imgProduct = (SimpleDraweeView) view.findViewById(R.id.imgPhoto);
        }
    }


}
