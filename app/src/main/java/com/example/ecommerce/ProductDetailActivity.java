package com.example.ecommerce;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ecommerce.model.Merchant;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.ResponseObject;
import com.example.ecommerce.model.ResponseProductObject;
import com.example.ecommerce.service.ResponseObjectService;
import com.example.ecommerce.service.ResponseProductService;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductDetailActivity extends AppCompatActivity {

    private final String BASEURLPRODUCT = "http://172.16.20.64:8080";
    private final String BASEURLMERCHANT = "http://172.16.20.64:8081";

    private ImageView itemImage;
    private TextView merchantName;
    private TextView productDescription;
    private TextView price;
    private TextView productName;

    private String productId;
    private Product product;
    private List<Merchant> listOfMerchants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        itemImage = findViewById(R.id.productImage);
        merchantName = findViewById(R.id.merchantNameProductDetailPage);
        productDescription = findViewById(R.id.productDescription);
        price = findViewById(R.id.priceProductDetail);
        productName = findViewById(R.id.DetailProductName);

        productId = getIntent().getStringExtra("productId");

    }

    @Override
    protected void onStart() {
        super.onStart();

        getDetail();
        getBestPrice();
        getMerchants();
    }

    public void getDetail(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURLPRODUCT)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();
        ResponseProductService service = retrofit.create(ResponseProductService.class);
        service.getProductById(Integer.parseInt(productId)).enqueue(new Callback<ResponseProductObject>() {
            @Override
            public void onResponse(Call<ResponseProductObject> call, Response<ResponseProductObject> response) {
                if(response.body()!=null && response.body().isOk()){
                    Log.d("productdetail", response.body().getData() + "");
                    product = response.body().getData();
                    updateUI(); //add in last call
                }
                else {
                    Log.d("productdetail ", "not ok");
                }
            }

            @Override
            public void onFailure(Call<ResponseProductObject> call, Throwable t) {
                Log.d("productdetailthisis ", t.getMessage());
            }
        });

    }

    public void getBestPrice(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURLMERCHANT)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();
        ResponseObjectService service = retrofit.create(ResponseObjectService.class);
        service.getBestMerchantPrice(Integer.parseInt(productId)).enqueue(new Callback<ResponseObject<Merchant>>() {
            @Override
            public void onResponse(Call<ResponseObject<Merchant>> call, Response<ResponseObject<Merchant>> response) {
                if(response.body()!=null && response.body().isOk()){
                    Log.d("productdetail ", response.body().getBestPrice()
                            + " size " + response.body().getData().size());
                    String s[] = response.body().getBestPrice().split(":");
                    price.setText(s[0]);
                    merchantName.setText(s[1]);
                }
                else {
                    Log.d("productdetail ", "not ok");
                }
            }

            @Override
            public void onFailure(Call<ResponseObject<Merchant>> call, Throwable t) {
                Log.d("productdetailprice ", t.getMessage());
            }
        });
    }

    public void getMerchants(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURLMERCHANT)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();
        ResponseObjectService service = retrofit.create(ResponseObjectService.class);
        service.getMerchantById(Integer.parseInt(productId)).enqueue(new Callback<ResponseObject<Merchant>>() {
            @Override
            public void onResponse(Call<ResponseObject<Merchant>> call, Response<ResponseObject<Merchant>> response) {
                if(response.body()!=null && response.body().isOk()){
                    Log.d("merchantdetail ", response.body().getData().get(0)
                            + " size " + response.body().getData().size());
                    listOfMerchants = response.body().getData();
                }
                else {
                    Log.d("merchantdetail ", "not ok");
                }
            }

            @Override
            public void onFailure(Call<ResponseObject<Merchant>> call, Throwable t) {
                Log.d("merchantdetail ", t.getMessage());
            }
        });
    }

    public void updateUI(){
        Glide.with(this).load(product.getProductIimages().get(0)).into(itemImage);
        productName.setText(product.getProductName());
        productDescription.setText(product.getDescription());
    }

}
