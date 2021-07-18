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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.tajhotel.AvatarPractice;
import com.example.tajhotel.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
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
    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;
    DatabaseId databaseId;
    ProgressDialog progressDialog;
    private String userID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_login_with_email, container, false);
        unametxt = view.findViewById(R.id.unametxt);
        passwdtxt = view.findViewById(R.id.passwdtxt);
        loginbtn = view.findViewById(R.id.loginbtn);
        signupbtn = view.findViewById(R.id.signupbtn);

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        /**** Getting User ID ****/
        userID = firebaseAuth.getCurrentUser().getUid();

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Taking you to your Account");
        progressDialog.setMessage("Please wait while we process");

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (unametxt.getText().toString().isEmpty() || passwdtxt.getText().toString().isEmpty()) {

                    Toast.makeText(getContext(), "Incomplete Details", Toast.LENGTH_SHORT).show();
                }
                else {
                    progressDialog.show();

//                    db.collection("users").get()
//                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                                @Override
//                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//
//                                }
//                            })
//                            .addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull @NotNull Exception e) {
//                                }
//                            });

//                    DocumentReference documentReference = db.collection("users").document(userID);
//
//                    documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
//                        @Override
//                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                            String userEmail = value.get("email").toString();
//                            String userPassword = value.get("password").toString();
//
//                            String enteredEmail = unametxt.getText().toString().trim();
//                            String enteredPass = passwdtxt.getText().toString().trim();
//
//                            if (userEmail.equalsIgnoreCase(enteredEmail) && userPassword.equalsIgnoreCase(enteredPass)) {
//                                verified = true;
//                                Intent intent = new Intent(getContext(), AvatarPractice.class);
//                                startActivity(intent);
//                                progressDialog.dismiss();
//                            }
//                            else {
//                                Toast.makeText(getContext(), "Wrong Details Entered", Toast.LENGTH_SHORT).show();
//                            }
//
//                        }
//                    });


                    db.collection("users")
                            .get()
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            String email = document.getString("email");
                                            String pasword = document.getString("password");

                                            if (email.isEmpty() || pasword.isEmpty()) {
                                                Log.e("Email", "strings are null");
                                            }
                                            String FName = document.getString("fame");
                                            String LName = document.getString("name");
                                            Log.d("TAG", document.getId() + " => " + document.getData());


                                            String usr_email = unametxt.getText().toString().trim();
                                            String usr_password = passwdtxt.getText().toString().trim();

                                            if (email.equalsIgnoreCase(usr_email) && pasword.equalsIgnoreCase(usr_password)) {
                                                verified = true;
                                                Intent intent = new Intent(getContext(), AvatarPractice.class);
                                                intent.putExtra("FName", FName);
                                                intent.putExtra("LName", LName);
                                                startActivity(intent);
//                                                Toast.makeText(getContext(), "welcome " + FName + LName, Toast.LENGTH_SHORT).show();
                                                progressDialog.dismiss();

                                            }
                                        }

                                    } else {
                                        Toast.makeText(getContext(), "Wrong Details", Toast.LENGTH_SHORT).show();
                                        Log.w("Login", "Error getting documents.", task.getException());
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            Log.d("Email", e.getMessage());
                        }
                    });


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
//                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.lsFragment, new com.example.tajhotel.Fragments.SignupFragment());
//                fragmentTransaction.commit();
            }
        });
        return view;

    }

}