package com.example.onlinefooddelivery;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.onlinefooddelivery.R;
import com.example.onlinefooddelivery.database.DbHelper;

public class RegisterActivity extends AppCompatActivity {

    EditText edtUsername, edtPassword;
    Button btnRegister;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnRegister = findViewById(R.id.btnRegister);
        dbHelper = new DbHelper(this);

        btnRegister.setOnClickListener(v -> {
            String user = edtUsername.getText().toString();
            String pass = edtPassword.getText().toString();
            if(user.isEmpty() ||pass.isEmpty()){
                Toast.makeText(this,"Fields are required!",Toast.LENGTH_SHORT).show();
           return;
            }

            boolean success = dbHelper.registerUser(user, pass);
            if (success) {
                Toast.makeText(this, "Registered successfully!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity.class));
                finish();

                SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("username", user); // username is the string you're saving
                editor.putBoolean("isLoggedIn", true);
                editor.apply();
            } else {
                Toast.makeText(this, "Registration failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}