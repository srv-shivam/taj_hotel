package com.example.tajhotel.Fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import androidx.fragment.app.FragmentTransaction;

import com.example.tajhotel.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class LoginWithPhoneFragment extends Fragment {
    View v;
    TextView login_using_email, signup_btn;
    EditText ph_num, ph_country_code;
    Button ph_loginbtn;
    FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_login_with_phone, container, false);
        ph_num = v.findViewById(R.id.ph_num);
        ph_country_code = v.findViewById(R.id.ph_country_code);
        ProgressBar progressBar = v.findViewById(R.id.send_otp);

        ph_loginbtn = v.findViewById(R.id.ph_loginbtn);
        login_using_email = v.findViewById(R.id.login_using_email);
        signup_btn = v.findViewById(R.id.signup_btn);

        login_using_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.lsFragment, new LoginWithEmailFragment());
                fragmentTransaction.commit();
            }
        });
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.lsFragment, new SignupFragment());
                fragmentTransaction.commit();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        ph_loginbtn.setEnabled(false);

        ph_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ph_loginbtn.setEnabled(true);

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (ph_num.getText().toString().length() == 0) {
                    ph_loginbtn.setEnabled(false);
                }
            }
        });

        ph_loginbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                ph_loginbtn.setVisibility(View.INVISIBLE);

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + ph_num.getText().toString(),
                        60, TimeUnit.SECONDS,
                        getActivity(),
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull @NotNull PhoneAuthCredential phoneAuthCredential) {
                                progressBar.setVisibility(View.INVISIBLE);
                                ph_loginbtn.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull @NotNull FirebaseException e) {
                                progressBar.setVisibility(View.INVISIBLE);
                                ph_loginbtn.setVisibility(View.VISIBLE);

                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                                if (ph_num.getText().toString().length() < 10) {
                                    Toast.makeText(getContext(), "Number should be 10 digit", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCodeSent(@NonNull @NotNull String backEnd_Otp, @NonNull @NotNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(backEnd_Otp, forceResendingToken);
                                progressBar.setVisibility(View.INVISIBLE);
                                ph_loginbtn.setVisibility(View.VISIBLE);
                                if (!ph_num.getText().toString().isEmpty()) {

                                    if (ph_num.getText().toString().length() == 10) {
                                        VerificationOtpFragment verificationOtpFragment = new VerificationOtpFragment();
                                        Bundle bundle = new Bundle();
                                        String num = ph_num.getText().toString();
                                        String country_code = ph_country_code.getText().toString();
                                        bundle.putString("Number", num);
                                        bundle.putString("Country_Code", country_code);
                                        bundle.putString("Otp", backEnd_Otp);
                                        verificationOtpFragment.setArguments(bundle);
                                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.lsFragment, verificationOtpFragment).commit();
                                    } else {
                                        Toast.makeText(getContext(), "Number should be 10 digit", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getContext(), "Please enter Mob Number ", Toast.LENGTH_SHORT).show();
                                }
                            }

                        });

            }
        });
        return v;
    }
}