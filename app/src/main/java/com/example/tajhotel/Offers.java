package com.example.tajhotel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;

public class Offers extends AppCompatActivity {

    ImageSlider imageSlider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);

        imageSlider = findViewById(R.id.offers_banners);
        ArrayList<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel("https://images.template.net/wp-content/uploads/2017/01/18032640/Burgers-Offer-Food-Menu-Flyer.jpg"));
        slideModels.add(new SlideModel("https://images.template.net/wp-content/uploads/2017/01/18032640/Burgers-Offer-Food-Menu-Flyer.jpg"));
        slideModels.add(new SlideModel("https://images.template.net/wp-content/uploads/2017/01/18032640/Burgers-Offer-Food-Menu-Flyer.jpg"));
        slideModels.add(new SlideModel("https://images.template.net/wp-content/uploads/2017/01/18032640/Burgers-Offer-Food-Menu-Flyer.jpg"));
        slideModels.add(new SlideModel("https://images.template.net/wp-content/uploads/2017/01/18032640/Burgers-Offer-Food-Menu-Flyer.jpg"));
        slideModels.add(new SlideModel("https://images.template.net/wp-content/uploads/2017/01/18032640/Burgers-Offer-Food-Menu-Flyer.jpg"));
        imageSlider.setImageList(slideModels, false);

    }
}