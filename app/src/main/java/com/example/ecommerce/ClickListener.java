package com.example.ecommerce;

import android.view.View;

public interface ClickListener {

    void onClickRemove(View view, int position);
    void onClickIncrease(View view, int position);
    void onClickDecrease(View view, int position);
    void onSaveChange(View view, int position);

}
