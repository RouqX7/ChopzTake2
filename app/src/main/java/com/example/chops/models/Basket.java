package com.example.chops.models;
import java.util.ArrayList;

public class Basket {
    ArrayList<Food> foods = new ArrayList<>();

    public void addFood(Food foodFromUser){
        foods.add(foodFromUser);
    }
    public void removeFood(Food foodfromUser) {
        foods.remove(foodfromUser);
    }

}
