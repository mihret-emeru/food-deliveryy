package com.example.onlinefooddelivery;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinefooddelivery.R;
import com.example.onlinefooddelivery.adapters.CartAdapter;
import com.example.onlinefooddelivery.database.DbHelper;
import com.example.onlinefooddelivery.models.CartItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    DbHelper dbHelper;
    RecyclerView cartRecyclerView;
    TextView txtTotal;
    Button btnPlaceOrder;
    List<CartItem> cartItems;
    double total = 0.0;
    CartAdapter cartAdapter;
    Button btnGoToHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartRecyclerView = findViewById(R.id.cartRecyclerView);
        txtTotal = findViewById(R.id.txtTotal);
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);
        dbHelper = new DbHelper(this);
         btnGoToHistory = findViewById(R.id.btnGoToHistory);
        btnGoToHistory.setOnClickListener(v -> {
            startActivity(new Intent(CartActivity.this, OrderHistoryActivity.class));
        });

        loadCartItems();

        btnPlaceOrder.setOnClickListener(v -> {
            if (cartItems.isEmpty()) {
                Toast.makeText(this, "Cart is empty!", Toast.LENGTH_SHORT).show();
                return;
            }

            StringBuilder itemsStr = new StringBuilder();
            for (CartItem item : cartItems) {
                itemsStr.append(item.getName()).append(" x").append(item.getQuantity()).append(", ");
            }

            String orderDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
            boolean placed = dbHelper.placeOrder(itemsStr.toString(), total, orderDate);

            if (placed) {
                Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_SHORT).show();
                loadCartItems();
            } else {
                Toast.makeText(this, "Failed to place order.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loadCartItems() {
        cartItems = new ArrayList<>();
        Cursor cursor = dbHelper.getCartItems();
        total = 0.0;

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));

                double itemTotal = price * quantity;
                total += itemTotal;

                cartItems.add(new CartItem(id, name, price, quantity));
            } while (cursor.moveToNext());
        }

        txtTotal.setText("Total: ETB " + total);
        cartAdapter = new CartAdapter(this, cartItems);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartRecyclerView.setAdapter(cartAdapter);
    }
}