package com.example.chops.Config;

import com.example.chops.models.Address;
import com.example.chops.models.Customer;
import com.example.chops.models.Food;
import com.example.chops.models.Order;
import com.example.chops.models.Restaurant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MockData {
    public static Customer MOCKCUSTOMER = new Customer("c1","cartNo-00",new Address("123 CresentView Limerick","",""),"","Guest","Doe","",0.0);
    public static Map<String, Order> MOCKCURRENTORDERS = new HashMap<>();

    public static Map<String,Food> MOCKFOODS = new HashMap<>();
    static {
        MOCKFOODS.put("food1", new Food(10,"Pizza", new ArrayList<>(Collections.singletonList("Pizza")),"cat_1","food1"));
        MOCKFOODS.put("food2", new Food(7,"Burgers",new ArrayList<>(Collections.singletonList("Burgers")),"cat_2","food2"));
        MOCKFOODS.put("food3", new Food(10,"Nuggets",new ArrayList<>(Collections.singletonList("Nuggets")),"cat_3","food3"));
        MOCKFOODS.put("food4", new Food(10,"Pizza",new ArrayList<>(Collections.singletonList("Pizza")),"cat_4","food4"));
        MOCKFOODS.put("food5", new Food(10,"Pizza",new ArrayList<>(Collections.singletonList("Pizza")),"cat_1","food5"));
        MOCKFOODS.put("food6", new Food(10,"Pizza",new ArrayList<>(Collections.singletonList("Pizza")),"cat_2","food6"));
        MOCKFOODS.put("food7", new Food(10,"Pizza",new ArrayList<>(Collections.singletonList("Pizza")),"cat_3","food7"));
        MOCKFOODS.put("food8", new Food(10,"Pizza",new ArrayList<>(Collections.singletonList("Pizza")),"cat_4","food8"));
    }
    public static Map<String,Restaurant> MOCKRESTAURANTS = new HashMap<>();
    static {
        String[] foodIds = MOCKFOODS.keySet().toArray(new String[4]);
        MOCKRESTAURANTS.put("rest1",new Restaurant("rest1","Limerick",9.00,"airrest",
                new ArrayList<>(Arrays.asList(new String[]{"Pizza,Burger"})),"30",new ArrayList<String>(Arrays.asList(foodIds)),"Apache",3.00));
    }
}
