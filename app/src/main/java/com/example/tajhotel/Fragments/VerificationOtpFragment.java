package com.example.tajhotel.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.example.tajhotel.MainActivity;
import com.example.tajhotel.R;

import org.jetbrains.annotations.*;

import java.util.concurrent.TimeUnit;

public class VerificationOtpFragment extends Fragment {
    View view;
    TextView view_otp_num;
    Button ph_loginbtn;
    EditText otp_1, otp_2, otp_3, otp_5, otp_4, otp_6;
    String backEnd_Otp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_verification_otp, container, false);
        view_otp_num = view.findViewById(R.id.view_otp_num);
        otp_1 = view.findViewById(R.id.otp_1);
        otp_2 = view.findViewById(R.id.otp_2);
        otp_3 = view.findViewById(R.id.otp_3);
        otp_4 = view.findViewById(R.id.otp_4);
        otp_5 = view.findViewById(R.id.otp_5);
        otp_6 = view.findViewById(R.id.otp_6);
        ph_loginbtn = view.findViewById(R.id.ph_loginbtn);

        Bundle bundle = this.getArguments();
        String Number = bundle.getString("Number");
        String country_code = bundle.getString("Country_Code");
        view_otp_num.setText(country_code + Number);


        backEnd_Otp = bundle.getString("Otp");
        ProgressBar progressBar = view.findViewById(R.id.verify_otp);


        ph_loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!otp_1.getText().toString().trim().isEmpty() &&
                        !otp_2.getText().toString().trim().isEmpty() &&
                        !otp_3.getText().toString().trim().isEmpty() &&
                        !otp_4.getText().toString().trim().isEmpty() &&
                        !otp_5.getText().toString().trim().isEmpty() &&
                        !otp_6.getText().toString().trim().isEmpty()) {
                    String entered_otp = otp_1.getText().toString() + otp_2.getText().toString() +
                            otp_3.getText().toString() + otp_4.getText().toString() +
                            otp_5.getText().toString() + otp_6.getText().toString();

                    if (!(backEnd_Otp == null)) {
                        progressBar.setVisibility(View.VISIBLE);
                        ph_loginbtn.setVisibility(View.INVISIBLE);
                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(backEnd_Otp, entered_otp);
                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull  Task<AuthResult> task) {
                                progressBar.setVisibility(View.INVISIBLE);
                                ph_loginbtn.setVisibility(View.VISIBLE);

                            }
                        }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull  Exception e) {
                                Toast.makeText(getContext(), "Incorrect OTP", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                } else {
                    Toast.makeText(getContext(), "Please fill valid Otp", Toast.LENGTH_SHORT).show();
                }
            }
        });

        focusNumb();

        view.findViewById(R.id.resend_otp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + Number,
                        60, TimeUnit.SECONDS,
                        getActivity(),
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull @NotNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull @NotNull FirebaseException e) {
                                String er = e.getMessage();
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                Log.d("error", er);
                            }

                            @Override
                            public void onCodeSent(@NonNull @NotNull String newbackEnd_Otp, @NonNull @NotNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(backEnd_Otp, forceResendingToken);
                                backEnd_Otp = newbackEnd_Otp;
                                Toast.makeText(getContext(), "Otp send Sucess", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });


        return view;
    }

    private void focusNumb() {
        otp_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    otp_2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        otp_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    otp_3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        otp_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    otp_4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        otp_4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    otp_5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        otp_5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    otp_6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        otp_6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ph_loginbtn.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}