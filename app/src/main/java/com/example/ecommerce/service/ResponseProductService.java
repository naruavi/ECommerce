package com.example.ecommerce.service;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.ResponseObject;
import com.example.ecommerce.model.ResponseProductObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ResponseProductService {

    @GET("/getProduct")
    Call<ResponseProductObject> getProductById(@Query("productId") int productId);

}
