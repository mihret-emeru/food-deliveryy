package com.example.onlinefooddelivery.models;

public class Order {
    private int id;
    private String items;
    private double total;
    private String date;

    public Order(int id, String items, double total, String date) {
        this.id = id;
        this.items = items;
        this.total = total;
        this.date = date;
    }

    public int getId() { return id; }
    public String getItems() { return items; }
    public double getTotal() { return total; }
    public String getDate() { return date; }
}