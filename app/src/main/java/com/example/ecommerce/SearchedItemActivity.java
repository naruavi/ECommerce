package com.example.ecommerce;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

public class SearchedItemActivity extends AppCompatActivity {

    private String productCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched_item);

        productCategory = getIntent().getStringExtra("Category");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Fragment fragment = ProductListFragment.newProductListFragment(productCategory);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction()
                .add(R.id.productRecyclerViewHolder, fragment);
        fragmentTransaction.commit();
    }
}
