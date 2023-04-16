package com.example.chops.models;
import java.util.ArrayList;
import java.util.Map;

public class Cart {
    ArrayList<String> cartItems;

    private String id;

    public Cart(ArrayList<String> cartItems, String id) {
        this.cartItems = cartItems;
        this.id = id;
    }

    public Cart() {
    }

    public ArrayList<String> getCartItems() {
        return cartItems;
    }

    public void setCartItems(ArrayList<String> cartItems) {
        this.cartItems = cartItems;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id='" + id + '\'' +
                '}';
    }

    public Cart(String id) {
        this.id = id;
    }
}
