package com.example.ecommerce.model;

public class ResponseProductObject {

    private Product data;
    private boolean ok;

    public Product getData() {
        return data;
    }

    public void setData(Product data) {
        this.data = data;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }
}
