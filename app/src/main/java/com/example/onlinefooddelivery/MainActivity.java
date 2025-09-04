package com.example.onlinefooddelivery;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinefooddelivery.R;
import com.example.onlinefooddelivery.adapters.FoodAdapter;
import com.example.onlinefooddelivery.database.DbHelper;
import com.example.onlinefooddelivery.models.FoodItem;
import com.example.onlinefooddelivery.database.DbHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FoodAdapter adapter;
    DbHelper dbHelper;
    List<FoodItem> foodList;
    Button btnGoToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        dbHelper = new DbHelper(this);
        dbHelper.insertSampleFood();
        btnGoToCart=findViewById(R.id.btnGoToCart);
        btnGoToCart.setOnClickListener(v-> {
            Intent intent=new Intent(this,CartActivity.class);
            startActivity(intent);
        });


        // Insert sample food items if table is empty
        Cursor check = dbHelper.getAllFoodItems();
        if (check.getCount() == 0) {
            dbHelper.insertSampleFood();
        }

        foodList = loadFoodFromDB();
        adapter = new FoodAdapter(this, foodList, dbHelper);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
    private List<FoodItem> loadFoodFromDB() {
        List<FoodItem> list = new ArrayList<>();
        Cursor cursor = dbHelper.getAllFoodItems();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
                String image = cursor.getString(cursor.getColumnIndexOrThrow("image"));
                list.add(new FoodItem(id, name, price, image));

            } while (cursor.moveToNext());
        } else {
            Toast.makeText(this, "No food available", Toast.LENGTH_SHORT).show();
        }
        return list;
    }
}