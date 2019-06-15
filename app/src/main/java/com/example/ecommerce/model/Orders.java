package com.example.ecommerce.model;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Orders {

    //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private String orderId;

    @SerializedName("orderDate")
    private String orderDate;
    private String orderItemId;
    private int userId;
    private User userDTO;
    private String orderAddress;
    private int productId;
    private Product productDTO;
    private double totalPrice;
    private double productPrice;
    private int merchantId;
    private Merchant merchantDTO;
    private int quantity;

    @Override
    public String toString() {
        return "Orders{" +
                "orderId='" + orderId + '\'' +
                ", orderDate=" + orderDate +
                ", orderItemId='" + orderItemId + '\'' +
                ", userId=" + userId +
                ", userDTO=" + userDTO +
                ", orderAddress='" + orderAddress + '\'' +
                ", productId=" + productId +
                ", productDTO=" + productDTO +
                ", totalPrice=" + totalPrice +
                ", productPrice=" + productPrice +
                ", merchantId=" + merchantId +
                ", merchantDTO=" + merchantDTO +
                ", quantity=" + quantity +
                '}';
    }

    public String getOrderId() {
        return orderId;
    }


    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }


    public String getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public User getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(User userDTO) {
        this.userDTO = userDTO;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public Product getProductDTO() {
        return productDTO;
    }

    public void setProductDTO(Product productDTO) {
        this.productDTO = productDTO;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
    }

    public Merchant getMerchantDTO() {
        return merchantDTO;
    }

    public void setMerchantDTO(Merchant merchantDTO) {
        this.merchantDTO = merchantDTO;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
