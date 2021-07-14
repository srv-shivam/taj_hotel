package com.example.tajhotel.Fragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.tajhotel.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Random;

public class CartBottomSheetFragment extends BottomSheetDialogFragment {

    TextView totalItems, price, deliveryCharges, totalPrice, userName, mobileNumber;
    private int[] charges;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_cart_bottom_sheet, container, false);

        charges = new int[]{0, 20, 30, 0, 50, 0, 80, 0};
        int rnd = new Random().nextInt(charges.length);

        totalItems = rootView.findViewById(R.id.total_items_bottom_sheet);
        price = rootView.findViewById(R.id.price_bottom_sheet);
        deliveryCharges = rootView.findViewById(R.id.delivery_charges_bottom_sheet);
        totalPrice = rootView.findViewById(R.id.total_price_bottom_sheet);
        userName = rootView.findViewById(R.id.user_name_bottom_sheet);
        mobileNumber = rootView.findViewById(R.id.user_mobile_number_bottom_sheet);

        Bundle bundle = this.getArguments();
        int q = bundle.getInt("Total Items");
        int p = bundle.getInt("Price");

        totalItems.setText(String.valueOf(q));
        price.setText("₹" + String.valueOf(p));
        deliveryCharges.setText("₹" + String.valueOf(charges[rnd]));
        totalPrice.setText("₹" + String.valueOf(Math.abs(p - charges[rnd])));

        return rootView;
    }

}