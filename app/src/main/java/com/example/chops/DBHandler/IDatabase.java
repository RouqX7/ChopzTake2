package com.example.chops.DBHandler;

import com.example.chops.Interfaces.ICallback;
import com.example.chops.models.CartItem;
import com.example.chops.models.Customer;
import com.example.chops.models.Food;
import com.example.chops.models.Order;
import com.example.chops.models.Restaurant;

import java.util.ArrayList;

public interface IDatabase {
    public void createCustomer(Customer customer, ICallback callback);
    public void retrieveCustomer(String id, ICallback callback);
    public void createRestaurant(Restaurant restaurant, ICallback callback);
    public void getAllRestaurants(ICallback callback);
    public void getSingleRestaurant(String id,ICallback callback);
    public void createFood(Food food,ICallback callback);
    public void getFood(String id, ICallback callback);
    public void streamRestaurants(ICallback callback);
    public void streamFoodList(ICallback callback);
    public void getFoodList(ICallback callback);

    public void retrieveFoodListFromCartItems(ArrayList<CartItem> cartItems, ICallback callback, ArrayList<Food> foodList);
    public void streamOrders(ICallback callback);
    public void createOrder(Order order, ICallback callback);
    public void getUserCurrentOrder(String id, ICallback callback);
    public void getAllOrders(ICallback callback);
    public void addToCart(Food food, int quantity, ICallback callback, String uid, String restaurantId);
    public void removeFromCart(String uid, String foodId, int quantity, ICallback callback);

    public void retrieveCart(String uid, ICallback callback);


}
