package com.example.ecommerce.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.ecommerce.Main2Activity;
import com.example.ecommerce.R;

public class authenticationToken {

    public static String authToken;

    public static String getToken(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.preference_file), Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("TOKEN", null);
        return token;
    }

}
