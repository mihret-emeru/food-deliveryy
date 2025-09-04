package com.example.onlinefooddelivery;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinefooddelivery.R;
import com.example.onlinefooddelivery.adapters.OrderAdapter;
import com.example.onlinefooddelivery.database.DbHelper;
import com.example.onlinefooddelivery.models.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    OrderAdapter orderAdapter;
    List<Order> orderList;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        recyclerView = findViewById(R.id.recyclerViewOrders);
        dbHelper = new DbHelper(this);
        orderList = new ArrayList<>();

        Cursor cursor = dbHelper.getOrderHistory();
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String items = cursor.getString(cursor.getColumnIndexOrThrow("items"));
                double total = cursor.getDouble(cursor.getColumnIndexOrThrow("total"));
                String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));

                orderList.add(new Order(id, items, total, date));
            } while (cursor.moveToNext());
        } else {
            Toast.makeText(this, "No orders found.", Toast.LENGTH_SHORT).show();
        }

        orderAdapter = new OrderAdapter(this, orderList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(orderAdapter);
    }
}