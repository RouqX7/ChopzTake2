package com.example.chops.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Food implements Parcelable {
    private double price;
    private String name;
    private ArrayList<String> category;
    private String image;
    private String id;

    public Food(double price, String name, ArrayList<String> category, String image, String id) {
        this.price = price;
        this.name = name;
        this.category = category;
        this.image = image;
        this.id = id;
    }

    public Food(String id) {
        this.id = id;
    }

    public Food() {
    }

    public ArrayList<String> getCategory() {
        return category;
    }

    public void setCategory(ArrayList<String> category) {
        this.category = category;
    }

    protected Food(Parcel in) {
        price = in.readDouble();
        name = in.readString();
        category = in.createStringArrayList();
        image = in.readString();
        id = in.readString();
    }

    public static final Creator<Food> CREATOR = new Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel in) {
            return new Food(in);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Food{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeDouble(price);
        dest.writeString(name);
        dest.writeStringList(category);
        dest.writeString(image);
        dest.writeString(id);
    }
}
