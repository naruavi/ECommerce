package com.example.ecommerce.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseObject <T> {

    private List<T> data;
    private boolean ok;
    private String bestPrice;

    public String getBestPrice() {
        return bestPrice;
    }

    public void setBestPrice(String bestPrice) {
        this.bestPrice = bestPrice;
    }

    public List<T> getData() {
        return data;
    }

    public boolean isOk() {
        return ok;
    }

}
