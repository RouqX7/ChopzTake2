package com.example.chops.models;

public class Order {
    private String id;
    private String userId;
    private String notes;
    private double price;
    private boolean isPaid;
    private boolean isDelivered;

    public Order(String id, String userId, String notes, double price, boolean isPaid, boolean isDelivered) {
        this.id = id;
        this.userId = userId;
        this.notes = notes;
        this.price = price;
        this.isPaid = isPaid;
        this.isDelivered = isDelivered;
    }

    public Order(String id) {
        this.id = id;
    }

    public Order() {
        notes = "";
        price = 10.00;
        isPaid = true;
        isDelivered= true;
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
