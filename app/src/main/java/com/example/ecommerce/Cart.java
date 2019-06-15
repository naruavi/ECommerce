package com.example.ecommerce;

import android.content.Intent;
import android.support.design.button.MaterialButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerce.model.CartDisplay;
import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.model.Merchant;
import com.example.ecommerce.model.ResponseObject;
import com.example.ecommerce.model.ResponseObject1;
import com.example.ecommerce.model.ResponseObjectMerchant;
import com.example.ecommerce.model.ResponseProductObject;
import com.example.ecommerce.model.ResponseUserCart;
import com.example.ecommerce.model.UserCart;
import com.example.ecommerce.model.authenticationToken;
import com.example.ecommerce.service.ResponseObjectService;
import com.example.ecommerce.service.ResponseProductService;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Cart extends AppCompatActivity {

    private String productId;
    private int merchantId;
    List<CartItem> listCartItem;
    List<CartDisplay> listOfCartDisplayItem;
    private UserCart userCart;
    private RecyclerView recyclerView;
    private MaterialButton BuyNow;


//    TODO delete cart DONE
//    TODO update qty DONE
//    TODO RECYCLER VIEW MIGHT NOT WORK

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        if(authenticationToken.getToken(this) == null){
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }

        listOfCartDisplayItem = new ArrayList<>();

        BuyNow = findViewById(R.id.buyNow);
        BuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("buy now ", "clicked");
                Fragment fragment = new PlaceOrder();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction()
                        .add(R.id.placeOrderHolder, fragment);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        recyclerView = findViewById(R.id.CartRecyclerView);

        RecyclerViewCartAdapter recyclerViewCartAdapter = new RecyclerViewCartAdapter(Cart.this
                , listOfCartDisplayItem, new ClickListener() {
            @Override
            public void onClickRemove(View view, int position) {
                View foundedView = recyclerView.getLayoutManager().findViewByPosition(position);
                removeItem(foundedView);
                listOfCartDisplayItem.remove(position);
                recyclerView.getAdapter().notifyItemRemoved(position);
                recyclerView.getAdapter().notifyItemRangeChanged(position, listOfCartDisplayItem.size());
            }

            @Override
            public void onClickIncrease(View view, int position) {
                View foundedView = recyclerView.getLayoutManager().findViewByPosition(position);
                TextView qty = foundedView.findViewById(R.id.Qty);
                TextView saveChange = foundedView.findViewById(R.id.saveChange);
                int previousQty = Integer.valueOf(qty.getText().toString());
                int currentQty = previousQty + 1;
                qty.setText(String.valueOf(currentQty));
                saveChange.setVisibility(View.VISIBLE);
                Log.d("works increase", currentQty + "");
            }

            @Override
            public void onClickDecrease(View view, int position) {
                View foundedView = recyclerView.getLayoutManager().findViewByPosition(position);
                TextView qty = foundedView.findViewById(R.id.Qty);
                TextView saveChange = foundedView.findViewById(R.id.saveChange);
                int previousQty = Integer.valueOf(qty.getText().toString());
                if(previousQty >= 2){
                    int currentQty = previousQty - 1;
                    qty.setText(String.valueOf(currentQty));
                    saveChange.setVisibility(View.VISIBLE);
                    Log.d("works increase", currentQty + "");
               }
            }

            @Override
            public void onSaveChange(View view, int position) {
                View foundedView = recyclerView.getLayoutManager().findViewByPosition(position);
                saveChanges(foundedView);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Cart.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerViewCartAdapter);

        getCartItems();

    }

    public void getCartItems(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServerConfiguration.BASE_CART_SERVICE)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();
        ResponseObjectService service = retrofit.create(ResponseObjectService.class);
        service.getUserCart(authenticationToken.getToken(Cart.this)).enqueue(new Callback<ResponseUserCart>() {
            @Override
            public void onResponse(Call<ResponseUserCart> call, Response<ResponseUserCart> response) {
                if(response.body()!=null && response.body().isOk()){
                    //Log.d("cart", response.body().getData().getListOfCartItem().toString());
                    //Log.d("cart 2", response.body().getData().getListOfCartItem().get(1).getProductId() + "");
                    //userCart = response.body().getData();
//                    listCartItem = response.body().getData().getListOfCartItem();
//                  TODO add something for empty page
                    if(response.body().getData() == null){
                        TextView noItemFound = findViewById(R.id.noItemFound);
                        noItemFound.setVisibility(View.VISIBLE);
                    }
                    else{
                        Log.d("cart", response.body().getData().getListOfCartItem().toString());
                        listCartItem = response.body().getData().getListOfCartItem();
                        getListOfDisplayItem(); // updates of listOfDisplayItem

                    }
                }
                else {
                    Log.d("cart", response.body().getData().toString());
                    Toast.makeText(Cart.this, response.body().getData().toString(), Toast.LENGTH_LONG ).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseUserCart> call, Throwable t) {
                Log.d("cart", t.getMessage());
            }
        });
    }

    public void getListOfDisplayItem(){
        for(final CartItem item : listCartItem){
            final CartDisplay cartDisplay = new CartDisplay();

            Retrofit retrofit1 = new Retrofit.Builder()
                    .baseUrl(ServerConfiguration.BASE_PRODUCT_SERVICE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(new OkHttpClient())
                    .build();
            ResponseProductService service1 = retrofit1.create(ResponseProductService.class);
            service1.getProductById(item.getProductId()).enqueue(new Callback<ResponseProductObject>() {
                @Override
                public void onResponse(Call<ResponseProductObject> call, Response<ResponseProductObject> response) {
                    if(response.body()!=null && response.body().isOk()){
                        Log.d("cart", response.body().getData().toString());
                        cartDisplay.setProductName( response.body().getData().getProductName());
                        cartDisplay.setProductId(item.getProductId());
                        cartDisplay.setProductImage(response.body().getData().getProductIimages().get(0));
                        cartDisplay.setQty(item.getQuantity());


                        Retrofit retrofit2 = new Retrofit.Builder()
                                .baseUrl(ServerConfiguration.BASE_MERCHANT_SERVICE)
                                .addConverterFactory(GsonConverterFactory.create())
                                .client(new OkHttpClient())
                                .build();
                        ResponseObjectService service2 = retrofit2.create(ResponseObjectService.class);
                        service2.getMerchant(item.getMerchantId()).enqueue(new Callback<ResponseObjectMerchant>() {
                            @Override
                            public void onResponse(Call<ResponseObjectMerchant> call, Response<ResponseObjectMerchant> response) {
                                if(response.body()!=null && response.body().isOk()){
                                    Log.d("cart", response.body().getData().toString());
                                    //Merchant merchant =  (Merchant) response.body().getData();
                                    cartDisplay.setMerchantName(response.body().getData().getMerchantName());
                                    cartDisplay.setMerchantId(item.getMerchantId());



                                    Retrofit retrofit3 = new Retrofit.Builder()
                                            .baseUrl(ServerConfiguration.BASE_MERCHANT_SERVICE)
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .client(new OkHttpClient())
                                            .build();
                                    ResponseObjectService service3 = retrofit3.create(ResponseObjectService.class);
                                    service3.getPriceOfProduct(item.getProductId(),item.getMerchantId())
                                            .enqueue(new Callback<ResponseObject1>() {
                                                @Override
                                                public void onResponse(Call<ResponseObject1> call, Response<ResponseObject1> response) {
                                                    if(response.body()!=null && response.body().isOk()){
                                                        Log.d("cart", response.body().getData().toString());
                                                        Double price = (Double) response.body().getData();
                                                        cartDisplay.setPrice(price);
                                                        listOfCartDisplayItem.add(cartDisplay);
                                                        recyclerView.getAdapter().notifyDataSetChanged();
                                                    }
                                                    else {
                                                        Log.d("cart", response.body().getData().toString());
                                                        Toast.makeText(Cart.this, response.body().getData().toString(),
                                                                Toast.LENGTH_LONG ).show();
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<ResponseObject1> call, Throwable t) {
                                                    Log.d("cart", t.getMessage());
                                                }
                                            });



                                }
                                else {
                                    Log.d("cart", response.body().isOk() + "");
                                    Toast.makeText(Cart.this, response.body().getData().toString(), Toast.LENGTH_LONG ).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseObjectMerchant> call, Throwable t) {
                                Log.d("cart", t.getMessage());
                            }
                        });




                    }
                    else {
                        Log.d("cart", response.body().getData().toString());
                        Toast.makeText(Cart.this, response.body().getData().toString(), Toast.LENGTH_LONG ).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseProductObject> call, Throwable t) {
                    Log.d("cart", t.getMessage());
                }
            });

        }
    }


    public void removeItem(View view){
            TextView productIdtextView = view.findViewById(R.id.productId);
            TextView merchantIdtextView = view.findViewById(R.id.merchantId);
            Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServerConfiguration.BASE_CART_SERVICE)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();
        ResponseObjectService service = retrofit.create(ResponseObjectService.class);
        service.deleteCartItem(authenticationToken.getToken(Cart.this),
                Integer.valueOf(productIdtextView.getText().toString()),
                Integer.valueOf(merchantIdtextView.getText().toString())).enqueue(new Callback<ResponseObject1>() {
            @Override
            public void onResponse(Call<ResponseObject1> call, Response<ResponseObject1> response) {
                if(response.body()!=null && response.body().isOk()){
                    Log.d("remove item", response.body().isOk() + "");
                    Toast.makeText(Cart.this, "Deleted Successfully", Toast.LENGTH_LONG).show();
                }
                else {
                    Log.d("remove item", response.body().isOk() + "");
                    Toast.makeText(Cart.this, response.body().getData().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseObject1> call, Throwable t) {
                Log.d("remove cart ", t.getMessage());
            }
        });
    }

    public void saveChanges(final View view){
        TextView productIdtextView = view.findViewById(R.id.productId);
        TextView merchantIdtextView = view.findViewById(R.id.merchantId);
        TextView qty = view.findViewById(R.id.Qty);
        Log.d("QTYTOADDCART",qty.getText().toString());
            Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServerConfiguration.BASE_CART_SERVICE)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();
        ResponseObjectService service = retrofit.create(ResponseObjectService.class);
        service.inserToCart(authenticationToken.getToken(Cart.this),
                Integer.valueOf(productIdtextView.getText().toString()),
                Integer.valueOf(merchantIdtextView.getText().toString()),
                Integer.valueOf(qty.getText().toString())).enqueue(new Callback<ResponseObject1>() {
            @Override
            public void onResponse(Call<ResponseObject1> call, Response<ResponseObject1> response) {
                if(response.body()!=null && response.body().isOk()){
                    Log.d("cart insert", response.body().getData().toString());
                    Toast.makeText(Cart.this, "Quantity Updated"
                            ,Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(Cart.this, response.body().getData().toString()
                            ,Toast.LENGTH_LONG).show();
                    Log.d("cart insert", response.body().getData().toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseObject1> call, Throwable t) {
                Log.d("cart insert", t.getMessage());
            }
        });
    }

}
