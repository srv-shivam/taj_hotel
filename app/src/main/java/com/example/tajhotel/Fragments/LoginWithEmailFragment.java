package com.example.tajhotel.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginWithEmailFragment extends Fragment {
    View view;
    EditText unametxt, passwdtxt;
    TextView signupbtn;
    Button loginbtn;
    boolean verified = false;
    FirebaseAuth firebaseAuth;
    String userId;
    FirebaseFirestore firebaseFirestore;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_login_with_email, container, false);
        unametxt = view.findViewById(R.id.unametxt);
        passwdtxt = view.findViewById(R.id.passwdtxt);
        loginbtn = view.findViewById(R.id.loginbtn);
        signupbtn = view.findViewById(R.id.signupbtn);

        firebaseAuth = FirebaseAuth.getInstance();
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
                    /*firebaseAuth.signInWithEmailAndPassword(usr_email,usr_password)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){

                                    }
                                    else{

                                    }
                                }
                            });*/
                    firebaseFirestore.collection("users").document("details")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot doc = task.getResult();
                                        String email = doc.getString("email");
                                        Log.e("email", email);
                                        String password = doc.getString("password");
                                        Log.e("password", password);
                                        String FName = doc.getString("Fame");
                                        String LName = doc.getString("Name");


                                        if (email.equalsIgnoreCase(usr_email) && password.equalsIgnoreCase(usr_password)) {
                                            verified = true;
                                            Intent intent = new Intent(getContext(), MainActivity.class);
                                            startActivity(intent);
                                            Toast.makeText(getContext(), "welcome " + FName + LName, Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();

                                        }
                                    } else {
                                        Toast.makeText(getContext(), "Wrong Details", Toast.LENGTH_SHORT).show();
                                        Log.w("Login", "Error getting documents.");
                                    }

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