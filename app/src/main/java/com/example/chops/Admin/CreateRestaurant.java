package com.example.chops.Admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.ImageFilterButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chops.Config.Defaults;
import com.example.chops.Domain.CategoryDomain;
import com.example.chops.Interfaces.ICallback;
import com.example.chops.R;
import com.example.chops.controllers.DBController;
import com.example.chops.models.Food;
import com.example.chops.models.Restaurant;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.UUID;

public class CreateRestaurant extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    EditText nameField,avgTime,location;
    Spinner categorySpinner;
    ChipGroup selectDishes;
    
    SeekBar rating;
    
    TextView ratingLabel;
    Button selectedDishesBtn, saveButton;

    ChipGroup selectedCategory;
    ArrayList<String> categoriestoIgnore = new ArrayList<>();
    ArrayAdapter<String> categoryAdapter;

    ImageFilterButton[] restaurantImageBtn = new ImageFilterButton[Defaults.defaultResturantImages.size()];
    ProgressBar addRestaurantLoader;
    
    Restaurant newRestaurant = new Restaurant(UUID.randomUUID().toString());
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_restaurant);
        nameField = findViewById(R.id.name);
        rating = findViewById(R.id.rating);
        ratingLabel = findViewById(R.id.ratingLabel);
        avgTime = findViewById(R.id.avgtime);
        location = findViewById(R.id.location);
        categorySpinner = findViewById(R.id.foodcategoryspinner);
        selectedCategory = findViewById(R.id.selectedFoodCatagories);
        selectDishes = findViewById(R.id.selecteddishes);
        selectedDishesBtn = findViewById(R.id.select_dishes_btn);
        saveButton = findViewById(R.id.savebtn);
        addRestaurantLoader = findViewById(R.id.addRestaurantLoader);

        Bundle bundleData = getIntent().getExtras();
        if(bundleData.containsKey("currentRestaurant")){
            newRestaurant = bundleData.getParcelable("currentRestaurant", Restaurant.class);
            nameField.setText(newRestaurant.getName());
            rating.setProgress((int)newRestaurant.getRating());
            ratingLabel.setText(newRestaurant.getRating()+"");
            avgTime.setText(newRestaurant.getAvgTime());
            location.setText(newRestaurant.getLocation());


        }
        nameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                newRestaurant.setName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        avgTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                newRestaurant.setAvgTime(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        location.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                newRestaurant.setLocation(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        rating.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // 0-1
                int actualProgress = progress/10;
                newRestaurant.setRating(actualProgress*1.0);
                ratingLabel.setText("Rating ("+actualProgress+"/10)");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        int i = 0;

        for(String s : Defaults.defaultResturantImages.keySet()){
            int id = Defaults.defaultResturantImages.get(s);
            if(Defaults.defaultResturantImages.containsKey(s)){
                ImageFilterButton restaurantImage = restaurantImageBtn[i];
                restaurantImage = findViewById(id);
                TextView textView = findViewById(Defaults.defaultResturantImageTexts.get(s));
                restaurantImage.setOnClickListener(v->onSelectedImage(getResources().getResourceName(id) , v, textView));
                i++;
            }
           
        }
        
        populateCategories();
        categorySpinner.setOnItemSelectedListener(this);

        categorySpinner.setOnItemSelectedListener(this);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);
        selectedDishesBtn.setOnClickListener(v->{
            Intent nextPage = new Intent(CreateRestaurant.this, ViewFoodActivity.class);
            startActivity(nextPage);
        });
        saveButton.setOnClickListener(v->{saveNewRestaurant();});
    }
    
    private void saveNewRestaurant(){
        addRestaurantLoader.setVisibility(View.VISIBLE);
        saveButton.setVisibility(View.GONE);
        if(newRestaurant != null){
            System.out.println(newRestaurant);
            DBController.DATABASE.createRestaurant(newRestaurant, new ICallback() {
                @Override
                public void execute(Object... args) {
                    addRestaurantLoader.setVisibility(View.GONE);
                    saveButton.setVisibility(View.VISIBLE);
                    if(args.length > 1){
                        String s  = (args[0]).toString();
                        boolean success = args[1] instanceof  Boolean ? (Boolean)args[1] : false;
                        if(success){
                            Toast.makeText(CreateRestaurant.this, "Successfully Added", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(CreateRestaurant.this, "Failed to Add Restaurant", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }
    private void populateCategories(){
        ArrayList<String> categories = new ArrayList<String>();
        for(CategoryDomain cat : Defaults.defaultFoodCatagories){
            categories.add(cat.getTitle());
        }
        if(categoryAdapter!=null){
            categoryAdapter.clear();
            categoryAdapter.addAll(categories);
            categoryAdapter.notifyDataSetChanged();
        }else{
            categoryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,categories);
        }
        newRestaurant.setCategory(categories);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        categoriestoIgnore.add(item);
        Chip chip = new Chip(this);
        chip.setText(item);
        selectedCategory.addView(chip);
        chip.setCloseIconVisible(true);
        populateCategories();
        chip.setOnCloseIconClickListener(v->{
            selectedCategory.removeView(v);
            categoriestoIgnore.remove(item);
            populateCategories();

        });

    }
    // [..] [] []
    public void onSelectedImage(String drawableKey, View v, TextView textView){
        textView.setVisibility(View.VISIBLE);
        newRestaurant.setImage(drawableKey);
        ImageFilterButton selectedImage = v instanceof ImageFilterButton ? (ImageFilterButton) v : null;
        if(selectedImage!=null){
            selectedImage.setBrightness(0.65f);
        }
        for(String s : Defaults.defaultResturantImageTexts.keySet()){
            int id = Defaults.defaultResturantImageTexts.get(s);
            if(textView.getId() != id) {
                TextView otherImageTexts = findViewById(id);
                ImageFilterButton otherImageBtns = findViewById(Defaults.defaultResturantImages.get(s));
                otherImageTexts.setVisibility(View.GONE);
                otherImageBtns.setBrightness(1);
            }
        }
        
        
        
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
                ArrayList<Food> result = data.getParcelableArrayListExtra("selectedFoods", Food.class);
                for(Food food : result){
                    Chip chip = new Chip(this);
                    chip.setText(food.getName());
                    chip.setCloseIconVisible(true);
                    chip.setOnCloseIconClickListener(v->{
                        selectDishes.removeView(v);
                        newRestaurant.getDishes().remove(food.getId());
                    });
                    selectDishes.addView(chip);
                    newRestaurant.getDishes().add(food.getId());

                }
        }
    }
}