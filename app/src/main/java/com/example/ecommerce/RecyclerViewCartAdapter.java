package com.example.ecommerce;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ecommerce.model.CartDisplay;
import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.model.ResponseObject;
import com.example.ecommerce.model.ResponseObject1;
import com.example.ecommerce.model.authenticationToken;
import com.example.ecommerce.service.ResponseObjectService;

import java.lang.ref.WeakReference;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecyclerViewCartAdapter extends RecyclerView.Adapter<RecyclerViewCartAdapter.RecyclerViewCartHolder> {

    private List<CartDisplay> listOfCartProducts;
    private ClickListener listener;

    private TextView productName;
    private TextView merchantName;
    private TextView productId;
    private TextView merchantId;
    private TextView price;
    private ImageView productImage;
    private TextView qty;
    private Button remove;
    private Button increaseQty;
    private Button decreseQty;
    private Context context;
    private Button saveChange;

    public RecyclerViewCartAdapter(Context context, List<CartDisplay> list, ClickListener listener) {
        this.context = context;
        this.listOfCartProducts = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerViewCartHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cardview_item_cart, viewGroup, false);

        return new RecyclerViewCartHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewCartHolder recyclerViewCartHolder, int i) {
        recyclerViewCartHolder.bind(listOfCartProducts.get(i));
    }


    @Override
    public int getItemCount() {
        return listOfCartProducts.size();
    }


    public class RecyclerViewCartHolder extends RecyclerView.ViewHolder {

        private ClickListener mListener;

        public RecyclerViewCartHolder(@NonNull View view, final ClickListener listener) {
            super(view);

            productImage = view.findViewById(R.id.cartProductImage);
            productName = view.findViewById(R.id.ProductCartName);
            merchantName = view.findViewById(R.id.merchantCartName);
            productId = view.findViewById(R.id.productId);
            merchantId = view.findViewById(R.id.merchantId);
            price = view.findViewById(R.id.price);
            qty = view.findViewById(R.id.Qty);
            remove = view.findViewById(R.id.remove);
            increaseQty = view.findViewById(R.id.increaseQty);
            decreseQty = view.findViewById(R.id.decreaseQty);
            saveChange = view.findViewById(R.id.saveChange);

            mListener = listener;

            //view.setOnClickListener(this);

            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onClickRemove(v, position);
                        }
                    }
                }
            });
            increaseQty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onClickIncrease(v, position);
                        }
                    }
                }
            });
            decreseQty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onClickDecrease(v, position);
                        }
                    }
                }
            });
            saveChange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onSaveChange(v, position);
                        }
                    }
                }
            });
        }

        public void bind(CartDisplay item) {
            productName.setText(item.getProductName());
            productId.setText(String.valueOf(item.getProductId()));
            merchantName.setText(item.getMerchantName());
            merchantId.setText(String.valueOf(item.getMerchantId()));
            price.setText(String.valueOf(item.getPrice()));
            qty.setText(String.valueOf(item.getQty()));
            Glide.with(itemView.getContext()).load(item.getProductImage()).into(productImage);
        }

//        @Override
//        public void onClick(View v) {
//
//            mListener.onClickListen(v, getAdapterPosition());
//            if(v.getId() == remove.getId()){
//                removeItem();
//                listOfCartProducts.remove(getAdapterPosition());
//                notifyItemRemoved(getAdapterPosition());
//                notifyItemRangeChanged(getAdapterPosition(), getItemCount());
//            }
//
//            if(v.getId() == increaseQty.getId()){
//
//                int previousQty = Integer.valueOf(qty.getText().toString());
//                int currentQty = previousQty + 1;
//                qty.setText(String.valueOf(currentQty));
//                saveChange.setVisibility(View.VISIBLE);
//                Log.d("works increase", currentQty + "");
//            }
//
//            if(v.getId() == decreseQty.getId()){
//                int previousQty = Integer.valueOf(qty.getText().toString());
//                if(previousQty >= 2){
//                    int currentQty = previousQty - 1;
//                    qty.setText(String.valueOf(currentQty));
//                    saveChange.setVisibility(View.VISIBLE);
//                    Log.d("works increase", currentQty + "");
//                }
//            }
//
//            if(v.getId() == saveChange.getId()){
//                saveChanges();
//            }
//
//            listenerRef.get().onClickListen(getAdapterPosition());
//        }
//    }

        public void removeItem() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ServerConfiguration.BASE_CART_SERVICE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(new OkHttpClient())
                    .build();
            ResponseObjectService service = retrofit.create(ResponseObjectService.class);
            service.deleteCartItem(authenticationToken.getToken(context),
                    Integer.valueOf(productId.getText().toString()),
                    Integer.valueOf(merchantId.getText().toString())).enqueue(new Callback<ResponseObject1>() {
                @Override
                public void onResponse(Call<ResponseObject1> call, Response<ResponseObject1> response) {
                    if (response.body() != null && response.body().isOk()) {
                        Log.d("remove item", response.body().isOk() + "");
                        Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_LONG).show();
                    } else {
                        Log.d("remove item", response.body().isOk() + "");
                        Toast.makeText(context, response.body().getData().toString(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseObject1> call, Throwable t) {
                    Log.d("remove cart ", t.getMessage());
                }
            });
        }

        public void saveChanges() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ServerConfiguration.BASE_CART_SERVICE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(new OkHttpClient())
                    .build();
            ResponseObjectService service = retrofit.create(ResponseObjectService.class);
            service.inserToCart(authenticationToken.getToken(context),
                    Integer.valueOf(productId.getText().toString()),
                    Integer.valueOf(merchantId.getText().toString()),
                    Integer.valueOf(qty.getText().toString())).enqueue(new Callback<ResponseObject1>() {
                @Override
                public void onResponse(Call<ResponseObject1> call, Response<ResponseObject1> response) {
                    if (response.body() != null && response.body().isOk()) {
                        Log.d("cart insert", response.body().getData().toString());
                        Toast.makeText(context, "Quantity Updated"
                                , Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context, response.body().getData().toString()
                                , Toast.LENGTH_LONG).show();
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
}
