package com.example.chops.models;

import java.util.ArrayList;

public class Order {
    private String id;
    private String userId;
    private String notes;
    private double price;
    private boolean isPaid;
    private boolean isDelivered;

    private ArrayList<CartItem> meals;



    public Order(String id) {
        this.id = id;
    }

    public Order(String id, String userId, String notes, double price, boolean isPaid, boolean isDelivered, ArrayList<CartItem> meals) {
        this.id = id;
        this.userId = userId;
        this.notes = notes;
        this.price = price;
        this.isPaid = isPaid;
        this.isDelivered = isDelivered;
        this.meals = meals;
    }

    public ArrayList<CartItem> getMeals() {
        return meals;
    }

    public void setMeals(ArrayList<CartItem> meals) {
        this.meals = meals;
    }

    public Order() {
        notes = "";
        price = 10.00;
        isPaid = true;
        isDelivered= true;
        meals = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public boolean isDelivered() {
        return isDelivered;
    }

    public void setDelivered(boolean delivered) {
        isDelivered = delivered;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                '}';
    }


}
