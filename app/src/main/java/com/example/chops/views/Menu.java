package com.example.chops.views;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.example.chops.controllers.CustomerController;
import com.example.chops.controllers.DBController;
import com.example.chops.controllers.MenuListController;
import com.example.chops.models.CartItem;
import com.example.chops.models.Food;
import com.example.chops.models.Order;
import com.example.chops.models.Restaurant;
import com.example.chops.views.Adapters.MenuAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.model.FieldIndex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

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
        ActivityResultLauncher<Intent> nextPageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == RESULT_OK){
                            assert result.getData() != null;
                            System.out.println(result.getData().getExtras().getString("tester")+"<<<<<<");
                            onActivityResultCall(result.getResultCode(),result.getData());
                        }

                    }
                });
        cartFloatBtn.setOnClickListener(v->{
            Intent nextPage = new Intent(Menu.this,CartActivity.class);
            nextPageLauncher.launch(nextPage);
        });
        menuListView.setLayoutManager(new LinearLayoutManager(this));

        retrieveDataFromBundle();

    }

    private void calculateCart(Order order) {
        int counter = 0;
        for(CartItem item : order.getMeals()){
            counter+=item.getQuantity();
        }
        menuViewCartCount.setText(String.valueOf(counter));

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


    protected void onActivityResultCall(int resultCode, @Nullable Intent data) {

            retrieveDataFromBundle();

    }
    protected void retrieveDataFromBundle() {
        System.out.println("____-_____________");
        Bundle data = getIntent().getExtras();
        if(data != null) {
            restaurant = data.getParcelable("currentRestaurant", Restaurant.class);
            menuAdapter = MenuListController.getDefaultMenuAdapter(this, new ICallback() {
                @Override
                public void execute(Object... args) {
                    if (args.length > 0) {
                        Order order = args[0] instanceof Order ? (Order) args[0] : null;
                        if (order != null)
                            calculateCart(order);
                    }
                }
            }, restaurant.getId());
            menuListView.setAdapter(menuAdapter);

            System.out.println(restaurant);
            menuViewRestaurantName.setText(restaurant.getName());
            if (restaurant.getImage() != null) {
                int drawableResourceId = getResources().getIdentifier(restaurant.getImage(), "drawable", getPackageName());
                Glide.with(this)
                        .load(drawableResourceId)
                        .into(menuViewRestaurantImage);
            }

            changeInProgress();
            DBController.DATABASE.retrieveCart(CustomerController.GET_CURRENT_USER.getId(), new ICallback() {
                @Override
                public void execute(Object... args) {
                    if (args.length > 0) {
                        Order order = args[0] instanceof Order ? (Order) args[0] : new Order();

                        Map<String, Integer> foodQuantities = new HashMap<>();
                        if (order != null) {
                            System.out.println(restaurant.getDishes());
                            for (CartItem item : order.getMeals()) {
                                foodQuantities.put(item.getFoodId(), item.getQuantity());
                            }
                            ArrayList<String> cartItems =new ArrayList<>(restaurant.getDishes());
                            DBController.DATABASE.retrieveFoodListFromIds(cartItems, new ICallback() {
                                @Override
                                public void execute(Object... args) {
                                    if (args.length > 0) {
                                        ArrayList<Food> foods = args[0] instanceof ArrayList ? (ArrayList<Food>) args[0] : new ArrayList<>();

                                        if (!foods.isEmpty()) {
                                            System.out.println("Successful: " + foods);
                                            menuAdapter.updateFoods(foods, foodQuantities);
                                            changeInProgress();
                                            calculateCart(order);
                                        } else {
                                            System.out.println("Something went really wrong!!");
                                        }
                                    }
                                }
                            }, new ArrayList<>());
                        }
                    }
                }});

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