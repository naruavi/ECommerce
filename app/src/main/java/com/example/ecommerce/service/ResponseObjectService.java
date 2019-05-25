package com.example.ecommerce.service;

import com.example.ecommerce.model.Merchant;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.ResponseObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ResponseObjectService{


    @GET("/getTopProducts")
    Call<ResponseObject<Product>> getTopProducts();

    @GET("/products/{category}")
    Call<ResponseObject<Product>> getProductByCategory(@Path("category") String category);

    @GET("/merchants/")
    Call<ResponseObject<Merchant>> getMerchantById(@Query("productId") int productId);

    @GET("/getBestMerchantPriceOfProduct")
    Call<ResponseObject<Merchant>> getBestMerchantPrice(@Query("productId") int productId);

}
