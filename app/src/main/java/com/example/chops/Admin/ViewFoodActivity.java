package com.example.chops.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.chops.Interfaces.ICallback;
import com.example.chops.R;
import com.example.chops.controllers.DBController;
import com.example.chops.models.Food;
import com.example.chops.views.Adapters.FoodListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ViewFoodActivity extends AppCompatActivity {

    RecyclerView foodRecyclerView;
    FoodListAdapter foodListAdapter;
    Map<String, Food> selectedFoods = new HashMap<>();

    Button svaeFoodSelectedBtn;

    FloatingActionButton addFoodBtn;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_food);
        svaeFoodSelectedBtn = findViewById(R.id.svaeFoodSelectedBtn);
        addFoodBtn = findViewById(R.id.addFoodBtn);
        foodRecyclerView = findViewById(R.id.foodViewRecycler);
        foodRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        foodListAdapter = new FoodListAdapter(new ArrayList<>(), new ICallback() {
            @Override
            public void execute(Object... args) {
                if(args.length>1){
                    Food food = args[0] instanceof  Food ? (Food)args[0] : null;
                    if(food!=null){
                        selectedFoods.put(food.getId(),food);

                    }
                }
            }
        });
        addFoodBtn.setOnClickListener(v->{
            startActivity(new Intent(this, CreateFood.class));
        });
        
        foodRecyclerView.setAdapter(foodListAdapter);

        DBController.DATABASE.streamFoodList(new ICallback() {
            @Override
            public void execute(Object... args) {
                ArrayList<Food> foods = new ArrayList<>();
                if(args.length > 1){
                    foods = args[0] instanceof ArrayList ? (ArrayList) args[0] : new ArrayList<>();
                    boolean success = args[1] instanceof Boolean ? (Boolean) args[1] : false;
                    String errorMessages = args.length > 2 ? args[2] instanceof  String ? (String) args[2] : "":null;

                    if(success){
                        System.out.println("Food:::___>" + foods);
                        foodListAdapter.updatefoods(foods);
                    }
                }
            }
        });
        svaeFoodSelectedBtn.setOnClickListener(v->{
            Intent previousScreen = new Intent();

            ArrayList<Food> foods = new ArrayList<>(selectedFoods.values());
            previousScreen.putParcelableArrayListExtra("selectedFoods", foods);
            setResult(RESULT_OK);
            finish();
        });
    }
}