package com.example.ecommerce.service;

import com.example.ecommerce.model.ResponseObjectBestPrice;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BestPriceService {

    @GET("/getBestMerchantPriceOfProduct")
    Call<ResponseObjectBestPrice> getBestPriceById(@Query("productId") int productId);

}
