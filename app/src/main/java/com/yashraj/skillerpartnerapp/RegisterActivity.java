package com.yashraj.skillerpartnerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    String cities[] = {"Choose City", "Agra", "Mathura", "Delhi", "Varanasi", "Kanpur", "Other"};
    ArrayAdapter<String> adp;
    private boolean state = false;
    private EditText name;
    private EditText email;
    private EditText password;
    FirebaseAuth mAuth;
    private Button registerButton;
    private Spinner sp;
    DatabaseReference myRef;
    private EditText mobileNumber;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference();
        sp = findViewById(R.id.spinner);
        name = findViewById(R.id.reg_name);
        email = findViewById(R.id.reg_email);
        password = findViewById(R.id.reg_password);
        mobileNumber = findViewById(R.id.reg_mobileNumber);
        pb = findViewById(R.id.progressBar);
        registerButton = findViewById(R.id.btn_login);
        //Array adapter for spinner
        adp = new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_list_item_1, cities);
        sp.setAdapter(adp);
        // for user registration and authentication
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputName = name.getText().toString();
                String inputEmail = email.getText().toString();
                String inputPassword = password.getText().toString();
                String inputMobile = mobileNumber.getText().toString();
                if (inputName.isEmpty() || inputEmail.isEmpty() || inputPassword.isEmpty() || inputMobile.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Fill all the credentials", Toast.LENGTH_SHORT).show();
                } else if (inputPassword.length() < 6) {
                    password.setError("6 characters required");
                } else if (inputMobile.length() < 10) {
                    mobileNumber.setError("Invalid Number");
                } else {
                    registerUser(inputName, inputEmail, inputPassword, inputMobile);
                }
            }
        });
    }

    private void registerUser(final String name, final String email, String password, final String mobile) {
        pb.setVisibility(ProgressBar.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("Name", name);
                map.put("Email", email);
                map.put("Mobile", mobile);
                map.put("UserId", mAuth.getCurrentUser().getUid());
                myRef.child("Partners").child(mAuth.getCurrentUser().getEmail()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pb.setVisibility(ProgressBar.GONE);
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, DashBoardActivity.class);
                            intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pb.setVisibility(ProgressBar.GONE);
                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}