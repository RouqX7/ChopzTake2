package com.example.chops.models;

import android.os.Parcel;
import android.os.Parcelable;

public class CartItem implements Parcelable {
    private String id;
    private long time;
    private String foodId;
    private int quantity = 0;
    private double totalPrice;

    public CartItem(String id, String foodId, int quantity, double totalPrice) {
        this.id = id;
        this.time = 0;
        this.foodId = foodId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public CartItem(String id) {
        this.id = id;
    }

    public CartItem() {
    }

    protected CartItem(Parcel in) {
        id = in.readString();
        time = in.readLong();
        foodId = in.readString();
        quantity = in.readInt();
        totalPrice = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeLong(time);
        dest.writeString(foodId);
        dest.writeInt(quantity);
        dest.writeDouble(totalPrice);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CartItem> CREATOR = new Creator<CartItem>() {
        @Override
        public CartItem createFromParcel(Parcel in) {
            return new CartItem(in);
        }

        @Override
        public CartItem[] newArray(int size) {
            return new CartItem[size];
        }
    };

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
        return quantity ;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice){
        this.totalPrice=totalPrice;
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
