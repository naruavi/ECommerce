package com.example.ecommerce;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class CustomPageAdapter extends PagerAdapter {

    Context context;

    int images[];
    String imageCat[];
    LayoutInflater layoutInflater;



    public CustomPageAdapter(Context context, int images[], String category[]) {
        this.context = context;
        this.images = images;
        this.imageCat = category;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (o);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.slider_item, container, false);

        ImageView imageView = itemView.findViewById(R.id.sliderImage);
        imageView.setImageResource(images[position]);
        imageView.setContentDescription(imageCat[position]);
        container.addView(itemView);

        //listening to image click
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getByCategory(v);
                //Toast.makeText(context, "you clicked image " + (position + 1), Toast.LENGTH_SHORT).show();
            }
        });

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    @Override
    public int getCount() {
        return images.length;
    }

    public void getByCategory(View view){
        Intent intent = new Intent(context, SearchedItemActivity.class);
        intent.putExtra("Category", view.getContentDescription());
        context.startActivity(intent);
    }

}
