package com.example.ecommerce.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product {

    @SerializedName("productId")
    private int productId;

    @SerializedName("productName")
    private String productName;

    @SerializedName("productImages")
    private List<String> productImages;

    @SerializedName("listOfMerchants")
    private List<Integer> listOfMerchants;

    @SerializedName("price")
    private double price;

    @SerializedName("description")
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public List<String> getProductIimages() {
        return productImages;
    }

    public void setProductIimages(String productIimageUrl) {
        this.productImages = productImages;
    }

    public List<Integer> getListOfMerchants() {
        return listOfMerchants;
    }

    public void setListOfMerchants(List<Integer> listOfMerchants) {
        this.listOfMerchants = listOfMerchants;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
