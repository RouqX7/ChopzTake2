package com.example.chops.DBHandler;

import com.example.chops.Config.MockData;
import com.example.chops.Interfaces.ICallback;
import com.example.chops.models.CartItem;
import com.example.chops.models.Customer;
import com.example.chops.models.Food;
import com.example.chops.models.Order;
import com.example.chops.models.Restaurant;

import java.util.ArrayList;
import java.util.UUID;

public class MockDB implements IDatabase{
    @Override
    public void createCustomer(Customer customer, ICallback callback) {

    }

    @Override
    public void retrieveCustomer(String id, ICallback callback) {

    }

    @Override
    public void createRestaurant(Restaurant restaurant, ICallback callback) {

    }

    @Override
    public void getAllRestaurants(ICallback callback) {
        System.out.println("MOCK DATA-->>");
        System.out.println();
        callback.execute(new ArrayList<Restaurant>(MockData.MOCKRESTAURANTS.values()), true);
    }

    @Override
    public void getSingleRestaurant(String id, ICallback callback) {
        callback.execute(MockData.MOCKRESTAURANTS.get(id));
    }

    @Override
    public void createFood(Food food, ICallback callback) {

    }

    @Override
    public void getFood(String id, ICallback callback) {
        callback.execute(MockData.MOCKFOODS.get(id));
    }

    @Override
    public void streamRestaurants(ICallback callback) {
       getAllRestaurants(callback);
    }

    @Override
    public void streamFoodList(ICallback callback) {
        getFoodList(callback);
    }

    @Override
    public void getFoodList(ICallback callback) {
        callback.execute(MockData.MOCKFOODS, true);

    }

    @Override
    public void retrieveFoodListFromIds(ArrayList<String> ids, ICallback callback,ArrayList<Food> foodList) {
        if(ids.size() <= 0){
            callback.execute(foodList, true);
            return;
        }
        String current = ids.remove(ids.size()-1);
        if(MockData.MOCKFOODS.containsKey(current)){
            foodList.add(MockData.MOCKFOODS.get(current));
        }
        retrieveFoodListFromIds(ids,callback,foodList);
    }

    @Override
    public void retrieveFoodListFromCartItems(ArrayList<CartItem> cartItems, ICallback callback, ArrayList<Food> foodList) {
        if(cartItems.size() <= 0){
            callback.execute(foodList, true);
            return;
        }
        CartItem current = cartItems.remove(cartItems.size()-1);
        if(MockData.MOCKFOODS.containsKey(current.getFoodId())){
            foodList.add(MockData.MOCKFOODS.get(current.getFoodId()));
        }
        retrieveFoodListFromCartItems(cartItems,callback,foodList);
    }

    @Override
    public void getUserOrders(ArrayList<String> orderIDs, ICallback callback, ArrayList<Order> orders) {

    }

    @Override
    public void createOrder(Order order, ICallback callback) {

    }

    @Override
    public void getUserCurrentOrder(String id, ICallback callback) {
        callback.execute(MockData.MOCKCURRENTORDERS.get(id));
    }

    @Override
    public void getAllOrders(ICallback callback) {

    }

    @Override
    public void addToCart(Food food, int quantity, ICallback callback, String uid, String restaurantId) {
        Order order;
        if(MockData.MOCKCURRENTORDERS.containsKey(uid)){
           ArrayList<CartItem> meals =  new ArrayList<>(MockData.MOCKCURRENTORDERS.get(uid).getMeals());
           boolean updated = false;

           for(CartItem c : meals){
               if(c.getFoodId().equals(food.getId())){
                   c.setQuantity(quantity);
                   updated = true;
               }
           }
           if(!updated){
               CartItem newItem = new CartItem(UUID.randomUUID().toString(),food.getId(),quantity,food.getPrice());
               meals.add(newItem);
           }
            System.out.println("MEALS->>"+meals);
           MockData.MOCKCURRENTORDERS.get(uid).setMeals(meals);
           order = MockData.MOCKCURRENTORDERS.get(uid);
        }else{
             order = new Order(UUID.randomUUID().toString(),uid,"",0,false,false,restaurantId,new ArrayList<>());

            CartItem newItem = new CartItem(UUID.randomUUID().toString(),food.getId(),1,food.getPrice());
            order.getMeals().add(newItem);
            System.out.println("New MEALS->>"+order.getMeals());
            MockData.MOCKCURRENTORDERS.put(uid,order);

        }
        callback.execute(order, true);
    }

    @Override
    public void removeFromCart(String uid, String foodId, int quantity, ICallback callback) {
        Order order = new Order();
        if(MockData.MOCKCURRENTORDERS.containsKey(uid)){
            ArrayList<CartItem> meals =  new ArrayList<>(MockData.MOCKCURRENTORDERS.get(uid).getMeals());
            ArrayList<CartItem> newMeals = new ArrayList<>();
            boolean updated = false;
            for(CartItem c : meals){
                if(c.getFoodId().equals(foodId)){
                    System.out.println("Food to remove: "+foodId+" ~ Current Quantity: "+c.getQuantity());
                        c.setQuantity(quantity);
                    System.out.println("Quantity: "+c.getQuantity());
                        newMeals.add(c);
                }else{
                    newMeals.add(c);
                }
            }
            if(newMeals.size() > 0)
                MockData.MOCKCURRENTORDERS.get(uid).setMeals(newMeals);
            order = MockData.MOCKCURRENTORDERS.get(uid);
            MockData.MOCKCURRENTORDERS.put(uid,order);
        }

        callback.execute(order,true);
    }

    @Override
    public void updateCustomer(Customer customer, ICallback callback) {

    }

    @Override
    public void retrieveCart(String uid, ICallback callback) {
        Order order  = new Order();
        if(MockData.MOCKCURRENTORDERS.get(uid)!=null){
            order = MockData.MOCKCURRENTORDERS.get(uid);
        }
        callback.execute(order, true);
    }

    @Override
    public void payForOrder(Order order, ICallback o) {

    }
}
