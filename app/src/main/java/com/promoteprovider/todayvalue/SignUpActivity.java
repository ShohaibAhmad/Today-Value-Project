package com.promoteprovider.todayvalue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;
import com.promoteprovider.todayvalue.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;
    FirebaseAuth auth;
    FirebaseFirestore database;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        dialog = new ProgressDialog(this);
        dialog.setMessage("WE ARE CREATING NEW ACCOUNT....");


        binding.createNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, pass, name, referCode;
                email = binding.emailBox.getText().toString();
                pass = binding.passwordBox.getText().toString();
                name = binding.nameBox.getText().toString();
                referCode = binding.referBox.getText().toString();
               final User_data usr = new User_data(email,pass,name,referCode);

                dialog.show();
                auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            String uid = task.getResult().getUser().getUid();
                            database
                                    .collection("user")
                                    .document(uid)
                                    .set(usr).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        dialog.dismiss();
                                        startActivity(new Intent(SignUpActivity.this,MainActivity.class));
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(SignUpActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                            Toast.makeText(SignUpActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            dialog.dismiss();
                            Toast.makeText(SignUpActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
            }
        });
    }
}