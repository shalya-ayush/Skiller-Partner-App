package com.yashraj.skillerpartnerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private Button login;
    private TextView newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = findViewById(R.id.log_email);
        password = findViewById(R.id.log_password);
        login = findViewById(R.id.btn_login);
        newUser = findViewById(R.id.text_new_user);
        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getEmail = email.getText().toString();
                String getPassword = password.getText().toString();
                if (getEmail.isEmpty()) {
                    email.setError("Invalid Credentials");
                }
                if (getPassword.length() < 6) {
                    password.setError("Atleast 6 characters rewuired");
                    Toast.makeText(MainActivity.this, "Password too short", Toast.LENGTH_SHORT).show();
                } else {
                    loginUser(getEmail, getPassword);
                }
            }
        });


    }

    private void loginUser(String email, String password) {

    }
}