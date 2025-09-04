package com.example.onlinefooddelivery.models;

public class CartItem {
    private int foodId;
    private String name;
    private double price;
    private int quantity;

    public CartItem( int foodId,String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.foodId = foodId;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }

    public int getFoodId() {
        return foodId;
    }
}
