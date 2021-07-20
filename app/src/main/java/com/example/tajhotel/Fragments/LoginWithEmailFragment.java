package com.example.tajhotel.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.tajhotel.MainActivity;
import com.example.tajhotel.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

public class LoginWithEmailFragment extends Fragment {
    View view;
    EditText unametxt, passwdtxt;
    TextView signupbtn;
    Button loginbtn;
    boolean verified = false;
    FirebaseFirestore firebaseFirestore;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_login_with_email, container, false);
        unametxt = view.findViewById(R.id.unametxt);
        passwdtxt = view.findViewById(R.id.passwdtxt);
        loginbtn = view.findViewById(R.id.loginbtn);
        signupbtn = view.findViewById(R.id.signupbtn);

        firebaseFirestore = FirebaseFirestore.getInstance();
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Taking you to your Account");
        progressDialog.setMessage("Please wait while we process");

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usr_email = unametxt.getText().toString().trim();
                String usr_password = passwdtxt.getText().toString().trim();

                if (TextUtils.isEmpty(usr_email) || TextUtils.isEmpty(usr_password)) {
                    Toast.makeText(getContext(), "Incomplete Details", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.show();
                    firebaseFirestore.collection("users").document(usr_email)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot doc = task.getResult();
                                        String email = doc.getString("email");
                                        String password = doc.getString("password");
                                        String FName = doc.getString("fname");
                                        String LName = doc.getString("lname");

                                        if (email.equalsIgnoreCase(usr_email) && password.equalsIgnoreCase(usr_password)) {
                                            verified = true;
                                            Intent intent = new Intent(getActivity(), MainActivity.class);
                                            intent.putExtra("FName", FName);
                                            intent.putExtra("LName", LName);
//                                            intent.putExtra("User Name", new String[]{FName, LName});
                                            startActivity(intent);
                                            getActivity().finish();
//                                            Toast.makeText(getContext(), "welcome " + FName + LName, Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();

                                        } else if (!email.equalsIgnoreCase(usr_email) || !password.equalsIgnoreCase((usr_password))) {
                                            Toast.makeText(getContext(), "Wrong Details", Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                        }
                                    }
                                }
                            })

                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull @NotNull Exception e) {
                                    Toast.makeText(getContext(), "Check your connection", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            });

                    signupbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getParentFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.lsFragment, new SignupFragment())
                                    .commit();
                        }
                    });

                }

            }
        });
        return view;
    }
}