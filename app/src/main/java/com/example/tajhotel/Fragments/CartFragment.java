package com.example.tajhotel.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tajhotel.Adapters.CartFoodItemsAdapter;
import com.example.tajhotel.CustomClasses.Recipe_Model;
import com.example.tajhotel.LocalDataBase.DataBaseHelper;
import com.example.tajhotel.Offers;
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
    CardView changeCardView;
    TextView mainPrice, tableNumber, cart_view_offers_txt_view;
    private int q, price;
    private RelativeLayout footer_payment_detail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_cart, container, false);

        int table = (int) (Math.random() * (50 - 1) + 1);


        payNowButton = rootView.findViewById(R.id.cart_pay_now_btn);
        recyclerView = rootView.findViewById(R.id.cart_food_list);
        gifImageView = rootView.findViewById(R.id.gif_animation);
        changeCardView = rootView.findViewById(R.id.change_address_card_view);
        mainPrice = rootView.findViewById(R.id.cart_fragment_total_price);
        tableNumber = rootView.findViewById(R.id.table_number);
        footer_payment_detail = rootView.findViewById(R.id.footer_payment_detail);
        cart_view_offers_txt_view = rootView.findViewById(R.id.cart_view_offers_txt_view);
        foodList = new ArrayList<>();

        tableNumber.setText(String.valueOf(table));

        cart_view_offers_txt_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Offers.class);
                startActivity(intent);
            }
        });


        displayFoodListFromDB();

        payNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                q = cartFoodItemsAdapter.returnTotalQuantity();
                price = cartFoodItemsAdapter.returnPrice();
                mainPrice.setText("₹" + String.valueOf(price));

                Bundle bundle = new Bundle();
                bundle.putInt("Total Items", q);
                bundle.putInt("Price", price);
                CartBottomSheetFragment cartBottom = new CartBottomSheetFragment();
                cartBottom.setArguments(bundle);
                cartBottom.show(getParentFragmentManager(), cartBottom.getTag());
            }
        });

        return rootView;
    }


    private void displayFoodListFromDB() {

        db = new DataBaseHelper(getContext());

        cursor = db.retrieveData();

        if (cursor.getCount() == 0) {
            payNowButton.setEnabled(Boolean.FALSE);
            return;
        }

        payNowButton.setEnabled(Boolean.TRUE);
        gifImageView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        changeCardView.setVisibility(View.VISIBLE);
        footer_payment_detail.setVisibility(View.VISIBLE);


        while (cursor.moveToNext()) {
            foodList.add(new Recipe_Model(cursor.getInt(2), cursor.getString(0),
                    cursor.getString(3), Boolean.TRUE, cursor.getString(1), cursor.getFloat(4)));
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
