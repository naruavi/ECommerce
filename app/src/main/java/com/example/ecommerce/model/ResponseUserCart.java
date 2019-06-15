package com.example.ecommerce.model;

public class ResponseUserCart {

    private UserCart data;

    private boolean ok;

    public UserCart getData() {
        return data;
    }

    public void setData(UserCart data) {
        this.data = data;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }
}
