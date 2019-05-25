package com.example.ecommerce;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecommerce.model.Merchant;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.ResponseObject;
import com.example.ecommerce.service.ResponseObjectService;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ProductListFragment extends Fragment {

    private final String BASEURL = "http://172.16.20.64:8080";

    private static String productCategory;

    final List<Product> listOfProducts = new ArrayList<>();

    public static ProductListFragment newProductListFragment(String pc){
        productCategory = pc;
        return new ProductListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        final RecyclerView recyclerView = view.findViewById(R.id.productRecyclerView);

        RecyclerViewProductAdapter recyclerViewAdapter = new RecyclerViewProductAdapter(listOfProducts);

        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mGridLayoutManager);

        recyclerView.setAdapter(recyclerViewAdapter);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();

        ResponseObjectService service = retrofit.create(ResponseObjectService.class);
        service.getProductByCategory(productCategory).enqueue(new Callback<ResponseObject<Product>>() {
            @Override
            public void onResponse(Call<ResponseObject<Product>> call, Response<ResponseObject<Product>> response) {
                if(response.body()!=null && response.body().isOk()){
                    Log.d("product ", response.body().getData()
                            + " size " + response.body().getData().size());
                    listOfProducts.clear();
                    listOfProducts.addAll(response.body().getData());
                    recyclerView.getAdapter().notifyDataSetChanged();
                    Log.d("asdfjd", listOfProducts.size() + "");
                }
                else {
                    Log.d("product ", "not ok");
                }

            }

            @Override
            public void onFailure(Call<ResponseObject<Product>> call, Throwable t) {
                Log.d("product", t.getMessage());
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    public void getMerchants(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();
        ResponseObjectService service = retrofit.create(ResponseObjectService.class);
        for (Product product : listOfProducts){
            for(Integer id : product.getListOfMerchants()){
                service.getMerchantById(id).enqueue(new Callback<ResponseObject<Merchant>>() {
                    @Override
                    public void onResponse(Call<ResponseObject<Merchant>> call, Response<ResponseObject<Merchant>> response) {

                    }

                    @Override
                    public void onFailure(Call<ResponseObject<Merchant>> call, Throwable t) {

                    }
                });
            }
        }
    }
}
