package com.example.tajhotel.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.tajhotel.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class CartBottomSheetFragment extends BottomSheetDialogFragment {

    EditText address;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cart_bottom_sheet, container, false);

        address = view.findViewById(R.id.address_bottom_sheet);

        return view;
    }

}