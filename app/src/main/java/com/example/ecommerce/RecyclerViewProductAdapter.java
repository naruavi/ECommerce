package com.example.ecommerce;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ecommerce.model.Product;

import java.util.List;

public class RecyclerViewProductAdapter extends RecyclerView.Adapter<RecyclerViewProductAdapter.RecyclerViewProductHolder> {


    private ImageView productImage;
    private TextView productName;
    private TextView merchantName;
    private TextView price;

    List<Product> listOfProducts;

    public RecyclerViewProductAdapter (List<Product> list){
        this.listOfProducts = list;
    }

    @NonNull
    @Override
    public RecyclerViewProductHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cardview_item_product,viewGroup, false);
        return new RecyclerViewProductHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewProductHolder recyclerViewProductHolder, int i) {
        recyclerViewProductHolder.bind(listOfProducts.get(i));
    }

    @Override
    public int getItemCount() {
        return listOfProducts.size();
    }


    public class RecyclerViewProductHolder extends RecyclerView.ViewHolder{

        public RecyclerViewProductHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            price = itemView.findViewById(R.id.price);
            productName = itemView.findViewById(R.id.productName);
        }

        public void bind(Product item){
            Log.d("recyclerView ", "binding data ");
            if(item != null){
                price.setText(String.valueOf(item.getPrice()));
                productName.setText(item.getProductName());
                //merchantName.setText(item.);
                Glide.with(itemView.getContext()).load(item.getProductIimages().get((0))).into(productImage);
                itemView.setContentDescription(String.valueOf(item.getProductId()));
            }
        }
    }

}
