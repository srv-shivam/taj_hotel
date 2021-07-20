package com.example.tajhotel.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.tajhotel.CustomClasses.usr_data;
import com.example.tajhotel.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

public class SignupFragment extends Fragment {
    EditText fnametxt, lnametxt, emailtxt, passwdtxt, cpasswdtxt, mobtxt;
    TextView loginbtn, codtxt;
    boolean allFieldChecked = false;
    CheckBox term;
    RadioGroup genderradiogrop;
    RadioButton rb;
    Button signupbtn;
    ProgressDialog progressDialog;

    FirebaseFirestore db;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signup, container, false);

        fnametxt = view.findViewById(R.id.fnametxt);
        lnametxt = view.findViewById(R.id.lnametxt);
        mobtxt = view.findViewById(R.id.mobtxt);
        codtxt = view.findViewById(R.id.codetxt);
        emailtxt = view.findViewById(R.id.emailtxt);
        passwdtxt = view.findViewById(R.id.passtxt);
        cpasswdtxt = view.findViewById(R.id.cpasswdtxt);
        genderradiogrop = view.findViewById(R.id.genderradiogrp);
        term = view.findViewById(R.id.term_condition);
        signupbtn = view.findViewById(R.id.signupbtn);
        loginbtn = view.findViewById(R.id.loginbtn);

        db = FirebaseFirestore.getInstance();


        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("Please wait while we process");


        genderradiogrop.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                rb = (RadioButton) view.findViewById(checkedId);
            }
        });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.lsFragment, new LoginWithEmailFragment());
                fragmentTransaction.commit();
            }
        });
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                allFieldChecked = checkAllFields();

                if (allFieldChecked) {
                    progressDialog.show();
                    usr_data ud = new usr_data(
                            fnametxt.getText().toString(),
                            lnametxt.getText().toString(),
                            emailtxt.getText().toString(),
                            passwdtxt.getText().toString(),
                            codtxt.getText().toString(),
                            mobtxt.getText().toString(),
                            rb.getText().toString());
                    db.collection("users").document(emailtxt.getText().toString())
                            .set(ud).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                            fragmentTransaction.replace(R.id.lsFragment, new LoginWithEmailFragment());
                            fragmentTransaction.commit();
                            progressDialog.dismiss();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            Toast.makeText(getContext(), "Something went wrong please try again after some time" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            private boolean checkAllFields() {
                if (fnametxt.length() == 0) {
                    fnametxt.setError("This Field is required");
                    return false;
                } else if (lnametxt.length() == 0) {
                    lnametxt.setError("This Field is required");
                    return false;
                } else if (emailtxt.length() == 0) {
                    emailtxt.setError("This Field is required");
                    return false;
                } else if (mobtxt.length() == 0) {
                    mobtxt.setError("This Field is required");
                    return false;
                } else if (passwdtxt.length() == 0) {
                    fnametxt.setError("This Field is required");
                    return false;
                } else if (cpasswdtxt.length() == 0) {
                    cpasswdtxt.setError("This Field is required");
                    return false;
                } else if (!term.isChecked()) {
                    term.setError("This Field is required");
                    return false;
                }

                return true;
            }
        });

        return view;
    }
}