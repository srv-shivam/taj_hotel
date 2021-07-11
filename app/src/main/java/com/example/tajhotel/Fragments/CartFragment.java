package com.example.tajhotel.Fragments;

import android.database.Cursor;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.tajhotel.Adapters.CartFoodItemsAdapter;
import com.example.tajhotel.CustomClasses.OrderedFood;
import com.example.tajhotel.CustomClasses.Recipe_Model;
import com.example.tajhotel.LocalDataBase.DataBaseHelper;
import com.example.tajhotel.R;

import java.math.BigDecimal;
import java.util.ArrayList;

public class CartFragment extends Fragment {

    Button payNowButton;
//    ArrayList<String> foodName, foodPrice, foodDescription;
//    ArrayList<Integer> foodImage;
//    float[] foodRatings;
//    ArrayList<Recipe_Model> food;
    RecyclerView recyclerView;
    CartFoodItemsAdapter cartFoodItemsAdapter;
    DataBaseHelper db;
    Cursor cursor;
    ArrayList<Recipe_Model> foodList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_cart, container, false);

        payNowButton = rootView.findViewById(R.id.cart_pay_now_btn);

        recyclerView = rootView.findViewById(R.id.cart_food_list);

        foodList = new ArrayList<>();

        /***********************************************************************************/
        displayFoodListFromDB();

//        foodName = new ArrayList<>();
//        foodDescription = new ArrayList<>();
//        foodPrice = new ArrayList<>();
//        foodImage = new ArrayList<>();
//
//        food = new ArrayList<>();

        payNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartBottomSheetFragment cartBottomSheetFragment = new CartBottomSheetFragment();
                cartBottomSheetFragment.show(getParentFragmentManager(), cartBottomSheetFragment.getTag());
            }
        });
/*********************************************************************************
//        Bundle bundle = getArguments();
////        foodName = bundle.getStringArrayList("Food Name");
//        foodPrice = bundle.getStringArrayList("Food Price");
//        foodDescription = bundle.getStringArrayList("Food Description");
//        foodImage = bundle.getIntegerArrayList("Food Image");
//
//        foodRatings = new float[foodImage.size()];
//
//        foodRatings = bundle.getFloatArray("Food Ratings");
//
//        for(int i=0; i<foodName.size(); i++) {
//            food.add(new Recipe_Model(foodImage.get(i), foodName.get(i), foodDescription.get(i),
//                    Boolean.TRUE, foodPrice.get(i), foodRatings[i]));
//        }
//
//        CartFoodItemsAdapter cartFoodItemsAdapter = new CartFoodItemsAdapter(getContext(), food);
//        cartFoodItemsAdapter.notifyDataSetChanged();
//
//        LinearLayoutManager linearLayoutManager =
//                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
//
//        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setAdapter(cartFoodItemsAdapter);
***************************************************************************************/

        return rootView;
    }

    private void displayFoodListFromDB() {

        db = new DataBaseHelper(getContext());

        cursor = db.retrieveData();

        if (cursor.getCount() == 0) {
            Toast.makeText(getContext(), "No item in cart", Toast.LENGTH_SHORT).show();
            return;
        }

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
