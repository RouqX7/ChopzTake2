package com.example.chops.views;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.chops.Interfaces.ICallback;
import com.example.chops.controllers.CartController;
import com.example.chops.controllers.MenuListController;
import com.example.chops.models.Food;
import com.example.chops.models.Order;
import com.example.chops.models.Restaurant;
import com.example.chops.views.Adapters.MenuAdapter;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.chops.R;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class OrderCompletedScreen extends AppCompatActivity {

    TextView orderETA, summaryFinalPrice, goHomeBtn;
    RecyclerView orderSummary;
    MenuAdapter menuAdapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_completed_screen);
        orderSummary = findViewById(R.id.order_summary_list);
        summaryFinalPrice = findViewById(R.id.summary_final_price);
        orderETA = findViewById(R.id.order_ETA);
        goHomeBtn =findViewById(R.id.goHome);
        goHomeBtn.setOnClickListener(e->{
            finish();
        });
        orderSummary.setLayoutManager(new LinearLayoutManager(this));
        menuAdapter = MenuListController.getModifierMenuAdapter(this, new ICallback() {
            @Override
            public void execute(Object... args) {

            }
        }, "", true);
        parseBundle();
        orderSummary.setAdapter(menuAdapter);
    }

    private void parseBundle() {
        Bundle data = getIntent().getExtras();
        if (data != null) {
            Restaurant restaurant = data.getParcelable("currentOrderedRestaurant", Restaurant.class);
            Order order = data.getParcelable("currentOrder", Order.class);
            summaryFinalPrice.setText("€"+ data.getDouble("currentOrderTotal"));
            ArrayList<Food> foodOrdered = data.getParcelableArrayList("currentOrderedFood", Food.class);
            System.out.println("Rest: "+restaurant);
            System.out.println("Order: "+order);
            System.out.println(foodOrdered);
            menuAdapter.updateFoods(foodOrdered);
            LocalDateTime date = LocalDateTime.ofEpochSecond(order.getOrderedTime(), 0, ZoneOffset.UTC).plusMinutes(Long.parseLong(restaurant.getAvgTime()));
            orderETA.setText(DateTimeFormatter.ofPattern("HH:mm").format(date));
        }
    }

}