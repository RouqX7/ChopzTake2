package com.example.chops.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.chops.R;

public class AdminActivity extends AppCompatActivity {
    Button manageRestaurants, manageFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        manageRestaurants = findViewById(R.id.mangerest);
        manageFood = findViewById(R.id.managefood);

        manageRestaurants.setOnClickListener(v -> {
            Intent intent = new Intent(this,ManageRestaurantActivity.class);
            startActivity(intent);
        });
        manageFood.setOnClickListener(v -> {
            Intent intent = new Intent(this,ViewFoodActivity.class);
            startActivity(intent);
        });
    }

}