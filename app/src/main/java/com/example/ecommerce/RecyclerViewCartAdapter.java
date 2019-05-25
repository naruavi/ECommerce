package com.example.ecommerce;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class RecyclerViewCartAdapter extends RecyclerView.Adapter<RecyclerViewCartAdapter.RecyclerViewCartHolder> {

    private List<String> listOfCartProducts;

    public RecyclerViewCartAdapter(List<String > list){
        this.listOfCartProducts = list;
    }

    @NonNull
    @Override
    public RecyclerViewCartHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cardview_item_cart,viewGroup, false);

        return new RecyclerViewCartHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewCartHolder recyclerViewCartHolder, int i) {
        recyclerViewCartHolder.bind(listOfCartProducts.get(i));
    }


    @Override
    public int getItemCount() {
        return listOfCartProducts.size();
    }


    public class RecyclerViewCartHolder extends RecyclerView.ViewHolder{

        public RecyclerViewCartHolder(View view){
            super(view);
        }

        public void bind(String  item){
        }
    }

}
