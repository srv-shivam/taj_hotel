package com.example.tajhotel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Notifications extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

    }
}