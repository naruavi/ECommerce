package com.example.ecommerce;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.ecommerce.model.Orders;
import com.example.ecommerce.model.ResponseObject;
import com.example.ecommerce.model.ResponseObject1;
import com.example.ecommerce.model.authenticationToken;
import com.example.ecommerce.service.ResponseObjectService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrderHistory extends AppCompatActivity {

    private TextView noOrderFound;
    private RecyclerView recyclerView;
    final List<Orders> listOfOrders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        noOrderFound = findViewById(R.id.noOrderFound);

        if(authenticationToken.getToken(this) == null){
            Log.d("not authenticated user","do not add");
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        recyclerView = findViewById(R.id.OrderRecyclerView);

        OrderHistoryAdapter orderHistoryAdapter = new OrderHistoryAdapter(listOfOrders);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(OrderHistory.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(orderHistoryAdapter);

        getOrderList();
    }

    public void getOrderList(){
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServerConfiguration.BASE_ORDER_SERVICE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(new OkHttpClient())
                .build();
        ResponseObjectService service = retrofit.create(ResponseObjectService.class);
        service.getOrders(authenticationToken.getToken(OrderHistory.this))
                .enqueue(new Callback<ResponseObject<Orders>>() {
                    @Override
                    public void onResponse(Call<ResponseObject<Orders>> call, Response<ResponseObject<Orders>> response) {
                        if(response.body()!=null && response.body().isOk()){
                            if(response.body().getData() != null){

                                noOrderFound.setVisibility(View.GONE);
                                Log.d("orderHistory", response.body().getData().toString());
                                listOfOrders.clear();
                                listOfOrders.addAll(response.body().getData());
                                recyclerView.getAdapter().notifyDataSetChanged();
                                Log.d("orderHistory list ", listOfOrders.toString());

                            }
                            else
                            {
                                noOrderFound.setVisibility(View.VISIBLE);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseObject<Orders>> call, Throwable t) {
                        Log.d("orderHistory", t.getMessage());

                    }
                });
    }
}