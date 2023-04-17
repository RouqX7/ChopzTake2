package com.example.chops.DBHandler;

import com.example.chops.Interfaces.ICallback;
import com.example.chops.models.Customer;
import com.example.chops.models.Food;
import com.example.chops.models.Order;
import com.example.chops.models.Restaurant;
import com.example.chops.views.Menu;

import java.util.ArrayList;

public interface IDatabase {
    public void createCustomer(Customer customer, ICallback callback);
    public void retrieveCustomer(String id, ICallback callback);
    public void createRestaurant(Restaurant restaurant, ICallback callback);
    public ArrayList<Restaurant> getAllRestaurants(ICallback callback);
    public Restaurant getSingleRestaurant(String id,ICallback callback);
    public void createFood(Food food,ICallback callback);
    public Food getFood(String id, ICallback callback);
    public void streamRestaurants(ICallback callback);
    public void streamFoodList(ICallback callback);
    public void getFoodList(ICallback callback);
    public void streamOrders(ICallback callback);
    public void createOrder(Order order, ICallback callback);
    public Menu getSingleOrder(String id, ICallback callback);
    public ArrayList<Order> getAllOrders(ICallback callback);




}
