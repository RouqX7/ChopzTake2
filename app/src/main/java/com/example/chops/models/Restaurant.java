package com.example.chops.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Restaurant implements Parcelable {
    private String id;
    private  String location;
    private double rating;
    private double deliveryCharge;
    private String image;
    private ArrayList<String> category;
    private String avgTime;
    private ArrayList<String> dishes = new ArrayList<>();
    private String name;

    public Restaurant(String id) {
        this.id = id;
    }

    public Restaurant() {
        category = new ArrayList<>();
        image = "";
        name = "Unknown Resturant";
        avgTime = "30";
        rating = 8;
        dishes = new ArrayList<>();
        location = "";
    }

    public Restaurant(String id, String location, double rating, String image, ArrayList<String> category, String avgTime, ArrayList<String> dishes, String name, double deliveryCharge) {
        this.id = id;
        this.location = location;
        this.rating = rating;
        this.image = image;
        this.category = category;
        this.avgTime = avgTime;
        this.dishes = dishes;
        this.name = name;
        this.deliveryCharge = deliveryCharge;
    }

    public double getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(double deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    protected Restaurant(Parcel in) {
        id = in.readString();
        location = in.readString();
        rating = in.readDouble();
        image = in.readString();
        category = in.createStringArrayList();
        avgTime = in.readString();
        dishes = in.createStringArrayList();
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(location);
        dest.writeDouble(rating);
        dest.writeString(image);
        dest.writeStringList(category);
        dest.writeString(avgTime);
        dest.writeStringList(dishes);
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<String> getCategory() {
        return category;
    }

    public void setCategory(ArrayList<String> category) {
        this.category = category;
    }

    public String getAvgTime() {
        return avgTime;
    }

    public void setAvgTime(String avgTime) {
        this.avgTime = avgTime;
    }

    public ArrayList<String> getDishes() {
        return dishes;
    }

    public void setDishes(ArrayList<String> dishes) {
        this.dishes = dishes;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
