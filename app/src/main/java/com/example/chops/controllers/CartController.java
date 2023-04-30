package com.example.chops.controllers;

import com.example.chops.Interfaces.ICallback;
import com.example.chops.models.CartItem;

import java.util.ArrayList;

public class CartController {
    public static double calculateCart(ArrayList<CartItem> cartItems){
        System.out.println("CartItems: "+ cartItems);
        double price = 0.0;
        for(CartItem item : cartItems){
            price += item.getTotalPrice()*item.getQuantity();
        }
        System.out.println("Price "+ price);
        return price;
    }
}
