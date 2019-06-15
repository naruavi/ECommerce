package com.example.ecommerce;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecommerce.model.ResponseObject;
import com.example.ecommerce.model.ResponseObject1;
import com.example.ecommerce.model.authenticationToken;
import com.example.ecommerce.service.ResponseObjectService;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class PlaceOrder extends Fragment {

    private EditText address;
    private MaterialButton placeOrderBtn;

    public PlaceOrder() {
        // Required empty public constructor
    }

    public static PlaceOrder newInstance(String param1, String param2) {
        PlaceOrder fragment = new PlaceOrder();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_place_order, container, false);
        Log.d("view inflate", "inflated");
        address = view.findViewById(R.id.address);
        placeOrderBtn = view.findViewById(R.id.placeOrder);
        //final boolean success = false;
        placeOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog progressDialog = new ProgressDialog(getContext(),
                        R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Placing Order...");
                progressDialog.show();
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                final ResponseObject1 responseObject1 = new ResponseObject1();
                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(ServerConfiguration.BASE_ORDER_SERVICE)
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .client(new OkHttpClient())
                                        .build();
                                ResponseObjectService service = retrofit.create(ResponseObjectService.class);
                                service.placeOrder(authenticationToken.getToken(getContext()), address.getText().toString())
                                        .enqueue(new Callback<ResponseObject1>() {
                                            @Override
                                            public void onResponse(Call<ResponseObject1> call, Response<ResponseObject1> response) {
                                                if(response.body()!=null && response.body().isOk()){
                                                    responseObject1.setData(response.body().getData());
                                                    responseObject1.setOk(response.body().isOk());
                                                    Log.d("order placed",response.body().getData().toString());
                                                    //success = true;
                                                    Toast.makeText(getContext(), "Order Successfully placed", Toast.LENGTH_SHORT).show();
                                                    Log.d("direct", "directed");
                                                    Intent intent = new Intent(getContext(), OrderHistory.class);
                                                    startActivity(intent);
                                                }
                                                else {
                                                    Toast.makeText(getContext(), response.body().getData().toString(), Toast.LENGTH_SHORT).show();
                                                    Log.d("placeOrder", response.body().getData().toString());
                                                    Log.d("direct", "directed");
                                                    Intent intent = new Intent(getContext(), Cart.class);
                                                    startActivity(intent);
                                                }
                                            }
                                            @Override
                                            public void onFailure(Call<ResponseObject1> call, Throwable t) {
                                                Log.d("placeOrder", t.getMessage());
                                            }
                                        });
                                progressDialog.dismiss();
                                //Toast.makeText(getContext(), "Order Successfully placed", Toast.LENGTH_SHORT).show();

                            }
                        }, 3000);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


    }
}
