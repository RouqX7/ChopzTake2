package com.example.chops.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.chops.Interfaces.ICallback;
import com.example.chops.R;
import com.example.chops.controllers.DBController;
import com.example.chops.models.Restaurant;
import com.example.chops.views.Adapters.RestaurantAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ManageRestaurantActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RestaurantAdapter restAdapter;
    FloatingActionButton fab;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_restaurant);
        recyclerView = findViewById(R.id.recycleVRest);
        fab = findViewById(R.id.addRestaurants);
        fab.setOnClickListener(v->{
            Intent nextPage = new Intent(this, CreateRestaurant.class);
            startActivity(nextPage);
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        restAdapter = new RestaurantAdapter(new ArrayList<>());
        recyclerView.setAdapter(restAdapter);
        DBController.DATABASE.streamRestaurants(new ICallback() {
            @Override
            public void execute(Object... args) {
                ArrayList<Restaurant> restaurants = new ArrayList<>();
                if(args.length > 1){
                    restaurants = args[0] instanceof ArrayList ? (ArrayList) args[0] : new ArrayList<>();
                    boolean success = args[1] instanceof Boolean ? (Boolean) args[1] : false;
                    String errorMessages = args.length > 2 ? args[2] instanceof  String ? (String) args[2] : "":null;

                    if(success){
                        System.out.println("RESTAURANT:::___>" + restaurants);
                        restAdapter.updateRestaurants(restaurants);
                    }
                }
            }
        });

    }
}