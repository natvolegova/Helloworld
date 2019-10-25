package com.example.helloworld;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class AndroidImageAdapter extends PagerAdapter {
    Context mContext;
    ArrayList<Integer> sliderImagesId;

    AndroidImageAdapter(Context context, ArrayList<Integer> sliderImagesId) {
        this.mContext = context;
        this.sliderImagesId = sliderImagesId;
    }
    @Override
    public int getCount() {
        return sliderImagesId.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((ImageView) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.pager_item, null);
        ImageView imageView = view.findViewById(R.id.imageView);
        imageView.setImageDrawable(mContext.getResources().getDrawable(sliderImagesId.get(position)));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
