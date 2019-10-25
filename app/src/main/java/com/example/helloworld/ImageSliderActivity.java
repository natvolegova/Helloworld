package com.example.helloworld;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

public class ImageSliderActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private ArrayList<Integer> arrayOfImages;
    private AndroidImageAdapter imageAdapter;
    private CircleIndicator circleIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_slider);
        viewPager = findViewById(R.id.viewPager);
        circleIndicator = findViewById(R.id.circle);
        arrayOfImages = new ArrayList<>();
        initImageArray();
        imageAdapter = new AndroidImageAdapter(this, arrayOfImages);
        viewPager.setAdapter(imageAdapter);
        circleIndicator.setViewPager(viewPager);
    }

    private void initImageArray() {
        arrayOfImages.add(getRawResIdByName("img1"));
        arrayOfImages.add(getRawResIdByName("img2"));
        arrayOfImages.add(getRawResIdByName("img3"));
    }

    private int getRawResIdByName(String resName) {
        String pkgName = this.getPackageName();
        Integer resID = this.getResources().getIdentifier(resName, "drawable", pkgName);
        return resID;
    }
}
