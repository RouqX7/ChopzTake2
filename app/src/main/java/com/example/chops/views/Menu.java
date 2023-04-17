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
import com.example.chops.Config.MockData;
import com.example.chops.Interfaces.ICallback;
import com.example.chops.R;
import com.example.chops.controllers.DBController;
import com.example.chops.models.CartItem;
import com.example.chops.models.Food;
import com.example.chops.models.Order;
import com.example.chops.models.Restaurant;
import com.example.chops.views.Adapters.MenuAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.model.FieldIndex;

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
    FloatingActionButton cartFloatBtn;
    TextView menuViewCartCount;

    Order order;



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
        cartFloatBtn = findViewById(R.id.cartFloatBtn);
        menuViewCartCount = findViewById(R.id.menuViewCartCount);

        menuListView.setLayoutManager(new LinearLayoutManager(this));
        menuAdapter = new MenuAdapter(new ArrayList<>(), new ICallback() {
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
                                        order = args[0] instanceof Order ?(Order) args[0] : null;

                                       if(success){
                                           if(order!=null) {
                                               calculateCart(order);
                                           }
                                           Utility.showToast(Menu.this,"Added to Cart");
                                       }
                                   }
                               }
                           }, MockData.MOCKCUSTOMER.getId());

                       }else{
                           DBController.DATABASE.removeFromCart( MockData.MOCKCUSTOMER.getId(), food.getId(),quantity , new ICallback() {
                               @Override
                               public void execute(Object... args) {
                                   if(args.length>1){
                                       boolean success = args[1] instanceof Boolean ? (Boolean) args[1] : false;
                                        order = args[0] instanceof Order ?(Order) args[0] : null;

                                       ;
                                       if(success){
                                           if(order!=null) {
                                               calculateCart(order);
                                           }
                                           Utility.showToast(Menu.this,"Removed from Cart");
                                       }
                                   }
                               }
                           });
                       }
                       }
               }


            }
        });
        menuListView.setAdapter(menuAdapter);
        retrieveDataFromBundle();




    }

    private void calculateCart(Order order) {
        int counter = 0;
        System.out.println(counter);
        for(CartItem item : order.getMeals()){
            counter++;
        }
        menuViewCartCount.setText(counter + "");
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


            System.out.println(restaurant.getDishes());
            for(String foodId : restaurant.getDishes()){
                changeInProgress();
                DBController.DATABASE.getFood(foodId, new ICallback() {
                    @Override
                    public void execute(Object... args) {
                        if(args.length > 0){
                            Food food = args[0] instanceof  Food ? (Food)args[0] : null;
                            System.out.println("FOOD ==> "+food);
                            if(food !=null){
                                menuAdapter.addFood(food);
                            }

                        }
                        changeInProgress();
                    }
                });
            }
//            DBController.DATABASE.getFoodList(new ICallback() {
//                @Override
//                public void execute(Object... args) {
//                    ArrayList<Food> foods = new ArrayList<>();
//                    if(args.length > 1){
//                        foods = args[0] instanceof  ArrayList ? (ArrayList) args[0] : new ArrayList<>();
//                        boolean success = args[1] instanceof Boolean ? (Boolean) args[1] : false;
//                        String errorMessage = args.length> 2 ? args[2] instanceof String ? (String) args[2] :  "": null;
//
//                        if (success){
//                            System.out.println("FOOD:::__>" + foods);
//                            menuAdapter.updateFoods(foods);
//                        }
//                        changeInProgress();
//
//                    }
//                }
//            });

        }
    }
}