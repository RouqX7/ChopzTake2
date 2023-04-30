package com.example.chops.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Order implements Parcelable {
    private String id;
    private String userId;
    private String notes;
    private double price;
    private boolean isPaid;
    private boolean isDelivered;

    private String restaurantId;
    private ArrayList<CartItem> meals;

    private long orderedTime = 0;



    public Order(String id) {
        this.id = id;
    }

    public Order (Order order){
        this(order.getId(),order.getUserId(),order.getNotes(),order.getPrice(), order.isPaid(), order.isDelivered,order.getRestaurantId(),order.getMeals());
        this.orderedTime = order.getOrderedTime();
    }

    public Order(String id, String userId, String notes, double price, boolean isPaid, boolean isDelivered, String restaurantId, ArrayList<CartItem> meals) {
        this.id = id;
        this.userId = userId;
        this.notes = notes;
        this.price = price;
        this.isPaid = isPaid;
        this.isDelivered = isDelivered;
        this.restaurantId = restaurantId;
        this.meals = meals;
    }

    protected Order(Parcel in) {
        id = in.readString();
        userId = in.readString();
        notes = in.readString();
        price = in.readDouble();
        isPaid = in.readByte() != 0;
        isDelivered = in.readByte() != 0;
        restaurantId = in.readString();
        meals = in.createTypedArrayList(CartItem.CREATOR);
        orderedTime = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(userId);
        dest.writeString(notes);
        dest.writeDouble(price);
        dest.writeByte((byte) (isPaid ? 1 : 0));
        dest.writeByte((byte) (isDelivered ? 1 : 0));
        dest.writeString(restaurantId);
        dest.writeTypedList(meals);
        dest.writeLong(orderedTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    public long getOrderedTime() {
        return orderedTime;
    }

    public void setOrderedTime(long orderedTime) {
        this.orderedTime = orderedTime;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
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
