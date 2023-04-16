package com.example.chops.models;

public class CartItem {
    private String id;
    private long time;
    private String foodId;
    private int quantity;
    private double totalPrice;

    public CartItem(String id, long time, String foodId, int quantity, double totalPrice) {
        this.id = id;
        this.time = time;
        this.foodId = foodId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public CartItem(String id) {
        this.id = id;
    }

    public CartItem() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice){

    }

    @Override
    public String toString() {
        return "CartItem{" +
                "id='" + id + '\'' +
                ", time=" + time +
                ", foodId='" + foodId + '\'' +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
