package com.example.ecommerce;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.GravityCompat;
import android.util.Log;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.ResponseObject;
import com.example.ecommerce.service.ResponseObjectService;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ViewPager viewPager;
    private final String BASEURL = "http://172.16.20.64:8080";

    private int images[] = {R.drawable.ic_baseline_library_books_24px, R.drawable.ic_baseline_mobile_screen_share_24px,
            R.drawable.ic_baseline_tv_24px, R.drawable.ic_storage};

    private CustomPageAdapter pagerAdapter;

    private List<Product> listOfProducts;

    private ImageView[] TopProduct;

    private ImageView TopPrdduct1;
    private ImageView TopPrdduct2;
    private ImageView TopPrdduct3;
    private ImageView TopPrdduct4;

    private ImageView book;
    private ImageView luggage;
    private ImageView fashion;
    private ImageView care;
    private ImageView electronics;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.HomeToolBar);
        //setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        addViewPager();
        listOfProducts = new ArrayList<>();
        TopProduct = new ImageView[4];
        TopProduct[0] = findViewById(R.id.TopProduct1);
        TopProduct[1] = findViewById(R.id.TopProduct2);
        TopProduct[2] = findViewById(R.id.TopProduct3);
        TopProduct[3] = findViewById(R.id.TopProduct4);

        book = findViewById(R.id.book);
        luggage = findViewById(R.id.storage);
        fashion = findViewById(R.id.fashion);
        care = findViewById(R.id.careProducts);
        electronics = findViewById(R.id.electronics);

        getTopProducts();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_books) {
            // Handle the camera action
        } else if (id == R.id.nav_electronics) {

        } else if (id == R.id.nav_fashion) {

        } else if (id == R.id.nav_my_account) {

        } else if (id == R.id.nav_lug) {

        } else if (id == R.id.nav_my_orders) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void addViewPager(){
        viewPager = findViewById(R.id.homePageViewPager);
        pagerAdapter = new CustomPageAdapter(Main2Activity.this, images);
        viewPager.setAdapter(pagerAdapter);

    }

    public void getTopProducts(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();

        ResponseObjectService service = retrofit.create(ResponseObjectService.class);
        service.getTopProducts().enqueue(new Callback<ResponseObject<Product>>() {
            @Override
            public void onResponse(Call<ResponseObject<Product>> call, Response<ResponseObject<Product>> response) {
                if(response.body()!=null && response.body().isOk()){
                    Log.d("product ", response.body().getData().get(2).getProductIimages()
                            + " size " + response.body().getData().size());
                    listOfProducts.clear();
                    listOfProducts = response.body().getData();
                    addImages();
                }
                else {
                    Log.d("product ", "not ok");
                }

            }

            @Override
            public void onFailure(Call<ResponseObject<Product>> call, Throwable t) {
                Log.d("product", t.getMessage());
            }
        });

    }

    public void addImages(){
        for(int i=0;i<4;i++){
            Glide.with(Main2Activity.this).load(listOfProducts.get(i).getProductIimages().get(0)).into(TopProduct[i]);
            TopProduct[i].setContentDescription(String.valueOf(listOfProducts.get(i).getProductId()));
        }
    }

    public void getByProductId(View view){
        Intent intent = new Intent(Main2Activity.this, ProductDetailActivity.class);
        intent.putExtra("productId", view.getContentDescription());
        startActivity(intent);
    }

    public void getByCategory(View view){
        Intent intent = new Intent(Main2Activity.this, SearchedItemActivity.class);
        intent.putExtra("Category", view.getContentDescription());
        startActivity(intent);
    }

}
