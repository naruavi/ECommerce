package com.example.ecommerce.model;

import java.util.List;

public class UserCart {

    private int userId;
    private List<CartItem> listOfCartItem;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<CartItem> getListOfCartItem() {
        return listOfCartItem;
    }

    public void setListOfCartItem(List<CartItem> listOfCartItem) {
        this.listOfCartItem = listOfCartItem;
    }
}
