package com.example.onlinefooddelivery.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

public class DbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "FoodDelivery.db";
    public static final int DB_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Users Table
        db.execSQL("CREATE TABLE users(id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT)");

        // Food Menu Table
        db.execSQL("CREATE TABLE food(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, price REAL, image TEXT)");

        // Cart Table
        db.execSQL("CREATE TABLE cart(id INTEGER PRIMARY KEY AUTOINCREMENT, food_id INTEGER, quantity INTEGER)");

        // Orders Table
        db.execSQL("CREATE TABLE orders(id INTEGER PRIMARY KEY AUTOINCREMENT, items TEXT, total REAL, date TEXT)");



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Users");
        db.execSQL("DROP TABLE IF EXISTS food");
        db.execSQL("DROP TABLE IF EXISTS cart");
        db.execSQL("DROP TABLE IF EXISTS orders");
        onCreate(db);
    }

    // ========== USER AUTHENTICATION ==========


    public boolean loginUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username=? AND password=?", new String[]{username, password});
        return cursor.getCount() > 0;
    }

    public boolean registerUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        long result = db.insert("users", null, values);
        return result != -1;
    }

    // ========== FOOD MENU ==========
    public void insertSampleFood() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM food");
        db.execSQL("INSERT INTO food(name, price, image) VALUES('Pizza', 480.0, 'pizza')");
        db.execSQL("INSERT INTO food(name, price, image) VALUES('Burger',525.0,'burger')");
        db.execSQL("INSERT INTO food(name, price, image) VALUES('Pasta', 275.0, 'pasta')");
        db.execSQL("INSERT INTO food(name, price, image) VALUES('Chicken Fries', 750.0, 'chicken')");
        db.execSQL("INSERT INTO food(name, price, image) VALUES('Salad', 350.0, 'salad')");
        db.execSQL("INSERT INTO food(name, price, image) VALUES('Slice Cake', 125.0, 'cake')");
        db.execSQL("INSERT INTO food(name, price, image) VALUES('Sandwich', 275.0, 'sandwich')");
        db.execSQL("INSERT INTO food(name, price, image) VALUES('Softdrinks', 80.0, 'softdrinks')");
        db.execSQL("INSERT INTO food(name, price, image) VALUES('Coffee', 50.0, 'coffee')");
        db.execSQL("INSERT INTO food(name, price, image) VALUES('Bottle of Water(500ml)', 40.0, 'water')");
        db.execSQL("INSERT INTO food(name, price, image) VALUES('MilkShake', 260.0, 'milkshake')");

    }

    public Cursor getAllFoodItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM food", null);
    }

    // ========== CART ==========
    public boolean addToCart(int foodId, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM cart WHERE food_id=?", new String[]{String.valueOf(foodId)});
        if (cursor.moveToFirst()) {
            int existingQty = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));
            ContentValues values = new ContentValues();
            values.put("quantity", existingQty + quantity);
            db.update("cart", values, "food_id=?", new String[]{String.valueOf(foodId)});
        } else {
            ContentValues values = new ContentValues();
            values.put("food_id", foodId);
            values.put("quantity", quantity);
            db.insert("cart", null, values);
        }
        return true;
    }

    public Cursor getCartItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT c.id, f.name, f.price, f.image, c.quantity FROM cart c JOIN food f ON c.food_id = f.id", null);
    }

    public void clearCart() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM cart");
    }

    // ========== ORDERS ==========
    public boolean placeOrder(String items, double total, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("items", items);
        values.put("total", total);
        values.put("date", date);
        long result = db.insert("orders", null, values);
        clearCart();
        return result != -1;
    }

    public Cursor getOrderHistory() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM orders ORDER BY id DESC", null);
    }





    }
