package com.example.onlinefooddelivery;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.onlinefooddelivery.R;
import com.example.onlinefooddelivery.utils.NetworkUtil;

public class WelcomeActivity extends AppCompatActivity {

    Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        Button btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(v -> {
            if (!NetworkUtil.isConnected(WelcomeActivity.this)) {
                Toast.makeText(this, "Check your network connection", Toast.LENGTH_SHORT).show();
            } else {
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            }
        });
    }
}