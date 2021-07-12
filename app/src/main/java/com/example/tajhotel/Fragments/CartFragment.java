package com.example.tajhotel.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tajhotel.Adapters.CartFoodItemsAdapter;
import com.example.tajhotel.CustomClasses.Recipe_Model;
import com.example.tajhotel.LocalDataBase.DataBaseHelper;
import com.example.tajhotel.R;
import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class CartFragment extends Fragment {

    Button payNowButton;
    RecyclerView recyclerView;
    CartFoodItemsAdapter cartFoodItemsAdapter;
    DataBaseHelper db;
    Cursor cursor;
    ArrayList<Recipe_Model> foodList;
    GifImageView gifImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_cart, container, false);

        payNowButton = rootView.findViewById(R.id.cart_pay_now_btn);
        recyclerView = rootView.findViewById(R.id.cart_food_list);
        gifImageView = rootView.findViewById(R.id.gif_animation);
        foodList = new ArrayList<>();


        displayFoodListFromDB();

        payNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartBottomSheetFragment cartBottomSheetFragment = new CartBottomSheetFragment();
                cartBottomSheetFragment.show(getParentFragmentManager(), cartBottomSheetFragment.getTag());
            }
        });
        return rootView;
    }

    private void displayFoodListFromDB() {

        db = new DataBaseHelper(getContext());

        cursor = db.retrieveData();

        if (cursor.getCount() == 0) {
            payNowButton.setEnabled(Boolean.FALSE);
            Toast.makeText(getContext(), "No item in cart", Toast.LENGTH_SHORT).show();
            return;
        }

        payNowButton.setEnabled(Boolean.TRUE);
        gifImageView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);

        while (cursor.moveToNext()) {
            foodList.add(new Recipe_Model(cursor.getInt(2), cursor.getString(0),
                    cursor.getString(3), Boolean.TRUE, "₹"+cursor.getString(1), cursor.getFloat(4)));
        }

        cartFoodItemsAdapter = new CartFoodItemsAdapter(getContext(), foodList);
        cartFoodItemsAdapter.notifyDataSetChanged();
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(cartFoodItemsAdapter);
    }

}
