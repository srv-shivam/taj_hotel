package com.example.tajhotel.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import androidx.fragment.app.FragmentTransaction;

import com.example.tajhotel.MainActivity;
import com.example.tajhotel.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.model.DatabaseId;

import org.jetbrains.annotations.NotNull;

public class LoginWithEmailFragment extends Fragment {
    View view;
    EditText unametxt, passwdtxt;
    TextView signupbtn;
    Button loginbtn;
    boolean verified = false;

    FirebaseFirestore db;
    DatabaseId databaseId;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_login_with_email, container, false);
        unametxt = view.findViewById(R.id.unametxt);
        passwdtxt = view.findViewById(R.id.passwdtxt);
        loginbtn = view.findViewById(R.id.loginbtn);


        signupbtn = view.findViewById(R.id.signupbtn);
        db = FirebaseFirestore.getInstance();

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Taking you to your Account");
        progressDialog.setTitle("Please wait while we process");

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();

                if (unametxt.getText().toString().isEmpty() || passwdtxt.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Fill it up", Toast.LENGTH_SHORT).show();
                } else {
                    db.collection("users")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            String email = document.getString("email");
                                            String pasword = document.getString("password");
                                            String FName = document.getString("fame");
                                            String LName = document.getString("name");
                                            Log.d("TAG", document.getId() + " => " + document.getData());


                                            String usr_email = unametxt.getText().toString().trim();
                                            String usr_password = passwdtxt.getText().toString().trim();

                                            if (email.equalsIgnoreCase(usr_email) & pasword.equalsIgnoreCase(usr_password)) {
                                                boolean verified = true;
                                                Intent intent = new Intent(getContext(), MainActivity.class);
                                                intent.putExtra("FName", FName);
                                                intent.putExtra("LName", LName);
                                                startActivity(intent);
                                                Toast.makeText(getContext(), "welcome " + FName + LName, Toast.LENGTH_SHORT).show();
                                                progressDialog.dismiss();

                                            }
                                        }

                                    } else {
                                        Log.w("Login", "Error getting documents.", task.getException());
                                    }
                                }
                            });


                }

            }
        });
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.lsFragment, new com.example.tajhotel.Fragments.SignupFragment());
                fragmentTransaction.commit();
            }
        });
        return view;

    }

}