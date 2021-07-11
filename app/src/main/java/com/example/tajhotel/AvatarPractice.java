package com.example.tajhotel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tajhotel.Adapters.AvatarProfileAdapter;

import java.util.ArrayList;

public class AvatarPractice extends AppCompatActivity {

    RecyclerView profileRecyclerView;
    TextView upTxt, uploadImageText;
    Button btn;
    ArrayList<Integer> arrayList;
    AvatarProfileAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar_practice);

        SpannableString s = new SpannableString("Taj Hotel");
        s.setSpan(new ForegroundColorSpan(Color.WHITE), 0, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(s);

        uploadImageText = findViewById(R.id.textView3);
        profileRecyclerView = findViewById(R.id.recyclerView);
        upTxt = findViewById(R.id.textView3);
        btn = findViewById(R.id.avatar_btn);
        arrayList = new ArrayList<>();


        arrayList.add(R.drawable.boy1);
        arrayList.add(R.drawable.girl1);
        arrayList.add(R.drawable.boy2);
        arrayList.add(R.drawable.girl2);

        btn.setEnabled(false);

        adapter = new AvatarProfileAdapter(AvatarPractice.this, arrayList, btn);
        profileRecyclerView.setHasFixedSize(true);
        adapter.notifyDataSetChanged();

        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        profileRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        profileRecyclerView.setAdapter(adapter);


        uploadImageText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AvatarPractice.this, "App Under Construction", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AvatarPractice.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}