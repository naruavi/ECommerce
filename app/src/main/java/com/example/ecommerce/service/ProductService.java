package com.example.ecommerce.service;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.ResponseObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProductService {

    @GET("/getProduct/?productId={productId}")
    Call<ResponseObject<Product>> getProductById(@Query("productId") int productId);
}
