package com.example.chops.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chops.Interfaces.ICallback;
import com.example.chops.R;
import com.example.chops.controllers.DBController;
import com.example.chops.models.Food;
import com.example.chops.models.Restaurant;
import com.example.chops.views.Adapters.MenuAdapter;

import java.util.ArrayList;

public class Menu extends AppCompatActivity {
    Restaurant restaurant = new Restaurant();
    TextView menuViewRestaurantName;
    ImageFilterView menuViewRestaurantImage;
    EditText searchMenuInput;
    RecyclerView menuListView;
    MenuAdapter menuAdapter;
    ProgressBar progressFoodBar;
    LinearLayout menuListViewLayout;
    boolean inProgress = false;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        menuViewRestaurantName = findViewById(R.id.menuViewRestaurantName);
        menuViewRestaurantImage = findViewById(R.id.menuViewRestaurantImage);
        searchMenuInput = findViewById(R.id.searchMenuInput);
        menuListView = findViewById(R.id.menuListView);
        progressFoodBar = findViewById(R.id.progressFoodBar);
        menuListViewLayout = findViewById(R.id.menuResultView);

        menuListView.setLayoutManager(new LinearLayoutManager(this));
        menuAdapter = new MenuAdapter(new ArrayList<>());
        menuListView.setAdapter(menuAdapter);
        retrieveDataFromBundle();




    }
    public void changeInProgress() {

        if (inProgress) {
            progressFoodBar.setVisibility(View.GONE);
            menuListViewLayout.setVisibility(View.VISIBLE);
            inProgress = false;
        } else {
            progressFoodBar.setVisibility(View.VISIBLE);
            menuListViewLayout.setVisibility(View.GONE);
            inProgress = true;
        }
    }

    protected void retrieveDataFromBundle() {
        System.out.println("____-_____________");
        Bundle data = getIntent().getExtras();
        if(data != null){
            restaurant = data.getParcelable("currentRestaurant",Restaurant.class);
            System.out.println(restaurant);
            menuViewRestaurantName.setText(restaurant.getName());
            if(restaurant.getImage() != null){
                int drawableResourceId = getResources().getIdentifier(restaurant.getImage(),"drawable",getPackageName());
                Glide.with(this)
                        .load(drawableResourceId)
                        .into(menuViewRestaurantImage);
            }


            changeInProgress();
            DBController.DATABASE.getFoodList(new ICallback() {
                @Override
                public void execute(Object... args) {
                    ArrayList<Food> foods = new ArrayList<>();
                    if(args.length > 1){
                        foods = args[0] instanceof  ArrayList ? (ArrayList) args[0] : new ArrayList<>();
                        boolean success = args[1] instanceof Boolean ? (Boolean) args[1] : false;
                        String errorMessage = args.length> 2 ? args[2] instanceof String ? (String) args[2] :  "": null;

                        if (success){
                            System.out.println("FOOD:::__>" + foods);
                            menuAdapter.updateFoods(foods);
                        }
                        changeInProgress();

                    }
                }
            });

        }
    }
}