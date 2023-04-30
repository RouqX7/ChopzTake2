package com.example.chops.Admin;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.ImageFilterButton;

import android.annotation.SuppressLint;
import android.app.Activity;
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

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class CreateRestaurant extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    EditText nameField,avgTime,location;
    Spinner categorySpinner;
    ChipGroup selectDishes;
    
    SeekBar rating;
    
    TextView ratingLabel;
    Button selectedDishesBtn, saveButton;
    ArrayList<String> selectedDishes = new ArrayList<>();

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
            rating.setProgress((int)newRestaurant.getRating()*10);
            ratingLabel.setText((newRestaurant.getRating()*10)+"");
            avgTime.setText(newRestaurant.getAvgTime());
            location.setText(newRestaurant.getLocation());
            categoriestoIgnore.addAll(newRestaurant.getCategory());
            System.out.println("Cat to Ignore: "+ categoriestoIgnore);
            if(newRestaurant.getDishes()!=null) {
                selectedDishes.addAll(newRestaurant.getDishes());
            }
            populateChipGroup(selectedCategory, categoriestoIgnore, new ICallback() {
                @Override
                public void execute(Object... args) {
                    if(args.length > 0){
                        String s = args[0] instanceof String ? (String)args[0] : "";
                        categoriestoIgnore.remove(s);
                        newRestaurant.setCategory(categoriestoIgnore);
                    }
                }
            });
            DBController.DATABASE.retrieveFoodListFromIds(selectedDishes, new ICallback() {
                @Override
                public void execute(Object... args) {
                    if(args.length > 0) {

                        ArrayList<Food> foods = args[0] instanceof ArrayList ? (ArrayList<Food>) args[0] : new ArrayList<>();
                        populateChipGroup(selectDishes, new ArrayList<>(foods.stream().map(food->food.getName()).collect(Collectors.toList())), new ICallback() {
                            @Override
                            public void execute(Object... argz) {
                                if(argz.length > 0){
                                    String s = argz[0] instanceof String ? (String)argz[0] : "";
                                    System.out.println("REMOVEID -> "+s);
                                    selectedDishes = new ArrayList<>(foods.stream().filter(food->!food.getName().equals(s)).map(f->f.getId()).collect(Collectors.toList()));
                                    newRestaurant.setDishes(selectedDishes);


                                }
                            }
                        });
                    }
                }
            }, new ArrayList<>());





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
                if(s.equals(newRestaurant.getImage())){
                    onSelectedImage(getResources().getResourceName(id), restaurantImage, textView);
                }
                restaurantImage.setOnClickListener(v->onSelectedImage(getResources().getResourceName(id) , v, textView));
                i++;
            }
           
        }

        populateCategories();
        categorySpinner.setOnItemSelectedListener(this);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);
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
        selectedDishesBtn.setOnClickListener(v->{
            Intent nextPage = new Intent(CreateRestaurant.this, ViewFoodActivity.class);
            nextPage.putStringArrayListExtra("currentlySelectedDishes", newRestaurant.getDishes()!=null ? newRestaurant.getDishes() : new ArrayList<>());
            nextPageLauncher.launch(nextPage);
        });
        saveButton.setOnClickListener(v->{saveNewRestaurant();});
    }
    
    private void saveNewRestaurant(){
        addRestaurantLoader.setVisibility(View.VISIBLE);
        saveButton.setVisibility(View.GONE);
        if(newRestaurant != null){
            System.out.println(newRestaurant);
            System.out.println(newRestaurant.getDishes());
            System.out.println(newRestaurant.getCategory());
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
        categories.add("Select Food Category");
        for(CategoryDomain cat : Defaults.defaultFoodCatagories){
            if(!cat.getTitle().equals("all"))
            categories.add(cat.getTitle());
        }
        if(categoryAdapter!=null){
            categoryAdapter.clear();
            categoryAdapter.addAll(categories);

        }else{
            categoryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,categories);
        }
        categoryAdapter.notifyDataSetChanged();
    }
    private void populateChipGroup(ChipGroup chips, ArrayList<String> titles,ICallback onClosed){
        System.out.println("Chips to Create: "+titles);
        chips.removeAllViews();
        for(String s : titles){
            Chip chip = new Chip(this);
            chip.setText(s);
            chips.addView(chip);
            chip.setCloseIconVisible(true);
            chip.setOnCloseIconClickListener(v->{
                chips.removeView(v);
                onClosed.execute(s);

            });

        }
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        System.out.println("ItemSelected!!!!!");
        String item = parent.getItemAtPosition(position).toString();
        if(position == 0){

        }else{
            categoriestoIgnore.add(item);
            Chip chip = new Chip(this);
            chip.setText(item);
            selectedCategory.addView(chip);
            chip.setCloseIconVisible(true);
            populateCategories();
            newRestaurant.setCategory(categoriestoIgnore);
            chip.setOnCloseIconClickListener(v->{
                selectedCategory.removeView(v);
                categoriestoIgnore.remove(item);
                newRestaurant.setCategory(categoriestoIgnore);
                populateCategories();

            });
        }


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
    protected void onPostResume() {
        super.onPostResume();
    }


    protected void onActivityResultCall(int resultCode, @Nullable Intent data) {

        System.out.println("Activity->>>>>>");
        if(newRestaurant.getDishes() == null){
            newRestaurant.setDishes(new ArrayList<>());
        }
        if(resultCode==RESULT_OK){
                ArrayList<Food> result = data.getExtras().getParcelableArrayList("selectedFoods", Food.class);
              selectDishes.removeAllViews();
              newRestaurant.getDishes().clear();
            System.out.println(result);
                for(Food food : result){
                    System.out.println(food.getId());
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