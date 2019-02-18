package cakcode.com.ukmapp.Product;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import cakcode.com.ukmapp.Model.Category;
import cakcode.com.ukmapp.Model.Product;
import cakcode.com.ukmapp.R;
import spencerstudios.com.bungeelib.Bungee;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private Context mContext;
    private List<Category> categoryList;

    public CategoryAdapter(Context mContext, List<Category> albumList) {
        this.mContext = mContext;
        this.categoryList = albumList;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_category, viewGroup, false);

        return new CategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder categoryViewHolder, int i) {
        Category category = categoryList.get(i);
        categoryViewHolder.title.setText(category.getTitle());
        if (category.getPhoto() != null){
            categoryViewHolder.imgProduct.setImageURI(category.getPhoto().getSmall());
        }

        categoryViewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Ini Category", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public void clear() {
        categoryList.clear();
        notifyDataSetChanged();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public SimpleDraweeView imgProduct;
        public View mView;

        public CategoryViewHolder(final View view){
            super(view);
            mView = view;
            title = (TextView) view.findViewById(R.id.tvTitle);
            imgProduct = (SimpleDraweeView) view.findViewById(R.id.imgPhoto);
        }
    }


}
