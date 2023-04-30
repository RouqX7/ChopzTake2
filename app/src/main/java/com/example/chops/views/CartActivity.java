package com.example.chops.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.chops.Config.MockData;
import com.example.chops.Interfaces.ICallback;
import com.example.chops.R;
import com.example.chops.controllers.CartController;
import com.example.chops.controllers.CustomerController;
import com.example.chops.controllers.DBController;
import com.example.chops.controllers.MenuListController;
import com.example.chops.models.CartItem;
import com.example.chops.models.Food;
import com.example.chops.models.Order;
import com.example.chops.models.Restaurant;
import com.example.chops.views.Adapters.MenuAdapter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartActivity extends AppCompatActivity {
    private MenuAdapter cartAdapter;
    private RecyclerView cartRecyclerViewList;
    TextView totalDeliveryFeeText, checkOutText,totalFeeText, deliveryFeeText, itemsTotalText, itemsTotalFeeText, deliveryChargeText, emptyTxt;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        initView();
        initList();



    }
    private void initView(){
        cartRecyclerViewList = findViewById(R.id.cartView);
        totalFeeText = findViewById(R.id.totalFeeFullText);
        deliveryFeeText = findViewById(R.id.deliveryFeeText);
        deliveryChargeText = findViewById(R.id.deliveryChargeText);
        itemsTotalText = findViewById(R.id.itemsTotalText);
        itemsTotalFeeText = findViewById(R.id.itemsTotalFee);
        totalDeliveryFeeText = findViewById(R.id.totalDeliveryFeeText);
        checkOutText = findViewById(R.id.checkOutText);
        emptyTxt = findViewById(R.id.emptyTxt);


    }
    private void initList(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        cartRecyclerViewList.setLayoutManager(linearLayoutManager);
        cartAdapter = MenuListController.getModifierMenuAdapter(this, new ICallback() {
            @Override
            public void execute(Object... args) {
                if(args.length > 0){

                }
            }
        },null,true);
        cartRecyclerViewList.setAdapter(cartAdapter);
        DBController.DATABASE.retrieveCart(CustomerController.GET_CURRENT_USER.getId(), new ICallback() {
            @Override
            public void execute(Object... args) {
                if (args.length > 0) {
                    Order order = args[0] instanceof Order ? (Order) args[0] : null;

                    Map<String, Integer> foodQuantities = new HashMap<>();
                    if (order != null) {
                        DBController.DATABASE.getSingleRestaurant(order.getRestaurantId(), (args0) -> {
                            if (args0.length > 0) {
                                Restaurant restaurant = args0[0] instanceof Restaurant ? (Restaurant) args0[0] : null;
                                if (restaurant != null) {
                                    deliveryFeeText.setText(("€" + DecimalFormat.getInstance().format(restaurant.getDeliveryCharge())));

                                    double totalFoodCost = CartController.calculateCart(order.getMeals());
                                    itemsTotalFeeText.setText("€" + DecimalFormat.getInstance().format(totalFoodCost));
                                    totalDeliveryFeeText.setText("€" + DecimalFormat.getInstance().format(totalFoodCost + restaurant.getDeliveryCharge()));


                                    for (CartItem item : order.getMeals()) {
                                        foodQuantities.put(item.getFoodId(), item.getQuantity());
                                    }
                                        Order orderCopy = new Order(order);
                                        ArrayList<CartItem> cartItems =new ArrayList<>(order.getMeals());
                                        System.out.println("ORDERCOPYYY 0___"+orderCopy.getMeals());
                                        DBController.DATABASE.retrieveFoodListFromCartItems(cartItems, new ICallback() {
                                            @Override
                                            public void execute(Object... args) {
                                                if (args.length > 0) {
                                                    ArrayList<Food> foods = args[0] instanceof ArrayList ? (ArrayList<Food>) args[0] : new ArrayList<>();

                                                    if (!foods.isEmpty()) {
                                                        System.out.println("Successful: " + true);
                                                        System.out.println(orderCopy.getMeals());
                                                        cartAdapter.updateFoods(foods, foodQuantities);
                                                        checkOutText.setOnClickListener(e->{
                                                            Intent summaryPage = new Intent(CartActivity.this, CheckOutActivity.class);
                                                            summaryPage.putParcelableArrayListExtra("currentOrderedFood",foods);
                                                            summaryPage.putExtra("currentOrder",orderCopy);
                                                            summaryPage.putExtra("currentOrderTotal",totalFoodCost + restaurant.getDeliveryCharge());
                                                            summaryPage.putExtra("currentOrderedRestaurant",restaurant);
                                                            startActivity(summaryPage);
                                                            finish();
                                                        });
                                                    } else {
                                                        System.out.println("Something went really wrong!!");
                                                    }
                                                }
                                            }
                                        }, new ArrayList<>());
                                    }
                                }

                        });
                    }
                }

            }
        });
    }
}