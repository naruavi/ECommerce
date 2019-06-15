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
import com.example.ecommerce.model.Orders;

import java.sql.DatabaseMetaData;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryAdapterHolder> {

    private List<Orders> listOfOrder;
    private TextView orderNumber;
    private TextView productName;
    private TextView merchantName;
    private TextView totalPrice;
    private TextView qty;
    private ImageView imageView;
    private TextView orderDate;

    public OrderHistoryAdapter(List<Orders> listOfOrder){
        this.listOfOrder  = listOfOrder;
    }


    @NonNull
    @Override
    public OrderHistoryAdapterHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cardview_item_order,viewGroup, false);

        return new OrderHistoryAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryAdapterHolder orderHistoryAdapterHolder, int i) {
        orderHistoryAdapterHolder.bind(listOfOrder.get(i));
    }

    @Override
    public int getItemCount() {
        return listOfOrder.size();
    }

    public class OrderHistoryAdapterHolder extends RecyclerView.ViewHolder{

        public OrderHistoryAdapterHolder(View view){
            super(view);
            orderNumber = view.findViewById(R.id.orderNumber);
            productName = view.findViewById(R.id.ProductCartName);
            merchantName = view.findViewById(R.id.merchantCartName);
            totalPrice = view.findViewById(R.id.price);
            qty = view.findViewById(R.id.Qty);
            imageView = view.findViewById(R.id.cartProductImage);
            orderDate = view.findViewById(R.id.OrderDate);
        }

        public void bind(Orders item){
            Log.d("order", "binding order");
            orderNumber.setText(item.getOrderId());
            productName.setText(item.getProductDTO().getProductName());
            merchantName.setText(item.getMerchantDTO().getMerchantName());
            totalPrice.setText(String.valueOf(item.getTotalPrice()));
            qty.setText("Qty : " + item.getQuantity());
            String str = item.getOrderDate();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
            long l = Long.valueOf(str);
            Date date = new Date(l);
            //Log.d("time", date.toString());
            orderDate.setText(new SimpleDateFormat("dd-MM-yyyy").format(date));
            Glide.with(itemView.getContext()).load(item.getProductDTO().getProductIimages().get(0)).into(imageView);
        }

    }
}
