package com.example.chops.models;

import java.util.ArrayList;

public class Restaurant {
    private String id;
    private  String location;
    private double rating;
    private String image;
    private ArrayList<String> category;
    private String avgTime;
    private ArrayList<String> dishes;
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

    public Restaurant(String id, String location, double rating, String image, ArrayList<String> category, String avgTime, ArrayList<String> dishes, String name) {
        this.id = id;
        this.location = location;
        this.rating = rating;
        this.image = image;
        this.category = category;
        this.avgTime = avgTime;
        this.dishes = dishes;
        this.name = name;
    }

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
