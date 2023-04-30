package com.example.chops.controllers;

import android.content.Context;

import com.example.chops.Config.MockData;
import com.example.chops.Interfaces.ICallback;
import com.example.chops.models.Food;
import com.example.chops.models.Order;
import com.example.chops.views.Adapters.MenuAdapter;
import com.example.chops.views.Utility;

import java.util.ArrayList;

public class MenuListController {

    public static MenuAdapter getDefaultMenuAdapter(Context context, ICallback onListUpdated, String restaurantId){
       return  new MenuAdapter(new ArrayList<>(), new ICallback() {
            @Override
            public void execute(Object... args) {

                if(args.length > 2){
                    Food food = args[0] instanceof  Food ? (Food) args[0] : null;
                    boolean isAdd = args[1] instanceof Boolean ? (Boolean) args[1] : false;
                    int quantity = args[2] instanceof Integer ? (Integer) args[2] : 0;
                    if(food!=null){
                        if(isAdd){
                            DBController.DATABASE.addToCart(food, quantity , new ICallback() {
                                @Override
                                public void execute(Object... args) {
                                    if(args.length>1){
                                        boolean success = args[1] instanceof Boolean ? (Boolean) args[1] : false;
                                        Order order = args[0] instanceof Order ?(Order) args[0] : null;
                                        onListUpdated.execute(order);
                                        if(success){

                                            Utility.showToast(context,"Added to Cart");
                                        }
                                    }
                                }
                            }, CustomerController.GET_CURRENT_USER.getId(),restaurantId );

                        }else{
                            DBController.DATABASE.removeFromCart( CustomerController.GET_CURRENT_USER.getId(), food.getId(),quantity , new ICallback() {
                                @Override
                                public void execute(Object... args) {
                                    if(args.length>1){
                                        boolean success = args[1] instanceof Boolean ? (Boolean) args[1] : false;
                                        Order order = args[0] instanceof Order ?(Order) args[0] : null;
                                        onListUpdated.execute(order);
                                        if(success){
                                            Utility.showToast(context,"Removed from Cart");
                                        }
                                    }
                                }
                            });
                        }
                    }
                }


            }
        });

    }
    public static MenuAdapter getModifierMenuAdapter(Context context, ICallback onListUpdated, String restaurantId, boolean hideModifier){
       return  new MenuAdapter(new ArrayList<>(), new ICallback() {
            @Override
            public void execute(Object... args) {

                if(args.length > 2){
                    Food food = args[0] instanceof  Food ? (Food) args[0] : null;
                    boolean isAdd = args[1] instanceof Boolean ? (Boolean) args[1] : false;
                    int quantity = args[2] instanceof Integer ? (Integer) args[2] : 0;
                    if(food!=null){
                        if(isAdd){
                            DBController.DATABASE.addToCart(food, quantity , new ICallback() {
                                @Override
                                public void execute(Object... args) {
                                    if(args.length>1){
                                        boolean success = args[1] instanceof Boolean ? (Boolean) args[1] : false;
                                        Order order = args[0] instanceof Order ?(Order) args[0] : null;
                                        onListUpdated.execute(order);
                                        if(success){

                                            Utility.showToast(context,"Added to Cart");
                                        }
                                    }
                                }
                            }, CustomerController.GET_CURRENT_USER.getId(),restaurantId );

                        }else{
                            DBController.DATABASE.removeFromCart( CustomerController.GET_CURRENT_USER.getId(), food.getId(),quantity , new ICallback() {
                                @Override
                                public void execute(Object... args) {
                                    if(args.length>1){
                                        boolean success = args[1] instanceof Boolean ? (Boolean) args[1] : false;
                                        Order order = args[0] instanceof Order ?(Order) args[0] : null;
                                        onListUpdated.execute(order);
                                        if(success){
                                            Utility.showToast(context,"Removed from Cart");
                                        }
                                    }
                                }
                            });
                        }
                    }
                }


            }
        }, hideModifier);

    }
}
