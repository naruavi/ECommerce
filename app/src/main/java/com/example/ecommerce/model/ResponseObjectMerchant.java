package com.example.ecommerce.model;

public class ResponseObjectMerchant {

    private Merchant data;
    private boolean ok;

    public Merchant getData() {
        return data;
    }

    public void setData(Merchant data) {
        this.data = data;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }
}
