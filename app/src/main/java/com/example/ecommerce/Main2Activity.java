package com.example.ecommerce;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.GravityCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.ResponseObject;
import com.example.ecommerce.model.ResponseObject1;
import com.example.ecommerce.model.authenticationToken;
import com.example.ecommerce.service.ResponseObjectService;

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

    private int images[] = {R.drawable.book3, R.drawable.smartphone1,
            R.drawable.fashion, R.drawable.bags};
    private String imageCat[] = {"books","electronics","fashion","luggage"};

    private CustomPageAdapter pagerAdapter;

    private List<Product> listOfProducts;

    private ImageView[] TopProduct;

    private Intent navIntent;

    private TextView search;

//    private ImageView book;
//    private ImageView luggage;
//    private ImageView fashion;
//    private ImageView care;
//    private ImageView electronics;

    private MenuItem logOut;
    private MenuItem logIn;
    private MenuItem signUp;
    private MenuItem myOrder;
    private NavigationView navigationView;
    private Menu menuNav;
    private String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.HomeToolBar);
        //setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        menuNav = navigationView.getMenu();
        myOrder = menuNav.findItem(R.id.nav_my_orders);
        logOut = menuNav.findItem(R.id.nav_logout);
        logIn = menuNav.findItem(R.id.nav_login);
        signUp = menuNav.findItem(R.id.nav_signup);

        ImageView mainCartIcon = findViewById(R.id.mainCartIcon);
        mainCartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, Cart.class);
                intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        addViewPager();
        listOfProducts = new ArrayList<>();
//        TopProduct = new ImageView[4];
//        TopProduct[0] = findViewById(R.id.TopProduct1);
//        TopProduct[1] = findViewById(R.id.TopProduct2);
//        TopProduct[2] = findViewById(R.id.TopProduct3);
//        TopProduct[3] = findViewById(R.id.TopProduct4);

        SharedPreferences sharedPreferences = Main2Activity.this.getSharedPreferences(
                getString(R.string.preference_file), Context.MODE_PRIVATE);
        token = sharedPreferences.getString("TOKEN", null);
        //SharedPreferences.Editor editor =
        if(token == null)
            Log.d("SHARED PREFERENCES", "null");
        else
            Log.d("SHARED PREFERENCES ", token );

        if(token == null){
            logOut.setVisible(false);
            logIn.setVisible(true);
            signUp.setVisible(true);
            myOrder.setVisible(true);
        }
        else{
            logOut.setVisible(true);
            signUp.setVisible(false);
            logIn.setVisible(false);
        }

        Fragment fragment = ProductListFragment.newProductListFragment(null, null, true);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction()
                .replace(R.id.TopProductHolder, fragment);
        fragmentTransaction.commit();

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


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.nav_books) {
            Intent intent = new Intent(Main2Activity.this, SearchedItemActivity.class);
            intent.putExtra("Category", "books");
            startActivity(intent);
        } else if (id == R.id.nav_electronics) {
            Intent intent = new Intent(Main2Activity.this, SearchedItemActivity.class);
            intent.putExtra("Category", "electronics");
            startActivity(intent);

        } else if (id == R.id.nav_fashion) {
            Intent intent = new Intent(Main2Activity.this, SearchedItemActivity.class);
            intent.putExtra("Category", "fashion");
            startActivity(intent);

        }
        else if (id == R.id.nav_beauty) {
            Intent intent = new Intent(Main2Activity.this, SearchedItemActivity.class);
            intent.putExtra("Category", "beauty");
            startActivity(intent);
        }
        else if (id == R.id.nav_lug) {
            Intent intent = new Intent(Main2Activity.this, SearchedItemActivity.class);
            intent.putExtra("Category", "luggage");
            startActivity(intent);

        } else if (id == R.id.nav_my_orders) {

            navIntent = new Intent(this, OrderHistory.class);
            if(authenticationToken.getToken(this) == null)
                navIntent.setFlags(navIntent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(navIntent);

        }else if (id == R.id.nav_my_cart){
            navIntent = new Intent(this, Cart.class);
            if(authenticationToken.getToken(this) == null)
                navIntent.setFlags(navIntent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(navIntent);
        }
        else if (id == R.id.nav_login) {
            navIntent = new Intent(this, LoginActivity.class);
            navIntent.setFlags(navIntent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(navIntent);

        }
        else if (id == R.id.nav_signup) {
            navIntent = new Intent(this, SignupActivity.class);
            navIntent.setFlags(navIntent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(navIntent);
        }
        else if(id == R.id.nav_logout){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ServerConfiguration.BASE_USER_SERVICE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(new OkHttpClient())
                    .build();

            ResponseObjectService service = retrofit.create(ResponseObjectService.class);
            service.logout(token).enqueue(new Callback<ResponseObject1>() {
                @Override
                public void onResponse(Call<ResponseObject1> call, Response<ResponseObject1> response) {
                    if(response.body()!=null && response.body().isOk()){
                        Toast.makeText(Main2Activity.this, "Logout Successful",
                                Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedPreferences = Main2Activity.this.getSharedPreferences(
                                getString(R.string.preference_file), Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("TOKEN");
                        editor.apply();
                        logIn.setVisible(true);
                        signUp.setVisible(true);
                        myOrder.setVisible(true);
                        logOut.setVisible(false);
                    }
                    else {
                        SharedPreferences sharedPreferences = Main2Activity.this.getSharedPreferences(
                                getString(R.string.preference_file), Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("TOKEN");
                        editor.apply();
                        Toast.makeText(Main2Activity.this, "not valid token",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Main2Activity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<ResponseObject1> call, Throwable t) {

                }
            });
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void addViewPager(){
        viewPager = findViewById(R.id.homePageViewPager);
        pagerAdapter = new CustomPageAdapter(Main2Activity.this, images, imageCat);
        viewPager.setAdapter(pagerAdapter);

    }

    public void getTopProducts(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServerConfiguration.BASE_PRODUCT_SERVICE)
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

    public void taketosearch(View view){
        Intent intent = new Intent(Main2Activity.this, SearchAcitivity.class);
        startActivity(intent);
    }
}
