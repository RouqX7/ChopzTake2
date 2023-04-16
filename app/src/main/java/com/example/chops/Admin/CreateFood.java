package com.example.chops.Admin;

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
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chops.Config.Defaults;
import com.example.chops.Domain.CategoryDomain;
import com.example.chops.Interfaces.ICallback;
import com.example.chops.R;
import com.example.chops.controllers.DBController;
import com.example.chops.models.Food;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.UUID;

public class CreateFood extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText name, price;
    ImageButton foodImage;
    Spinner categorySpinner;
    ChipGroup selectedCategory;
    ArrayAdapter<String> categoryAdapter;
    ImageFilterButton[] foodImageBtn = new ImageFilterButton[Defaults.defaultFoodImages.size()];
    ProgressBar addFoodLoader;
    ArrayList<String>categoriestoIgnore = new ArrayList<>();
    
    Food newFood = new Food(UUID.randomUUID().toString());
    
    Button saveButton;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_food);

        name = findViewById(R.id.name);
        price = findViewById(R.id.price);
        categorySpinner = findViewById(R.id.foodcategoryspinner);
        selectedCategory = findViewById(R.id.selectedFoodCatagories);
        saveButton = findViewById(R.id.addNewFood);
        addFoodLoader = findViewById(R.id.addFoodLoader);

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                newFood.setName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().matches("[0-9]+(.)?[0-9]+"))
                newFood.setPrice(Double.parseDouble(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        int i = 0;
        for(String s : Defaults.defaultFoodImages.keySet()){
            int id = Defaults.defaultFoodImages.get(s);
            if(Defaults.defaultFoodImages.containsKey(s)){
                ImageFilterButton foodImage = foodImageBtn[i];
                foodImage = findViewById(id);
                TextView textView = findViewById(Defaults.defaultFoodImageTexts.get(s));
                foodImage.setOnClickListener(v->onSelectedImage(getResources().getResourceName(id) , v, textView));
                i++;
            }

        }
        populateCategories();
        categorySpinner.setOnItemSelectedListener(this);

        categorySpinner.setOnItemSelectedListener(this);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);
        saveButton.setOnClickListener(v->{savenewFood();});

    }
    public void onSelectedImage(String drawableKey, View v, TextView textView){
        textView.setVisibility(View.VISIBLE);
        newFood.setImage(drawableKey);
        ImageFilterButton selectedImage = v instanceof ImageFilterButton ? (ImageFilterButton) v : null;
        if(selectedImage!=null){
            selectedImage.setBrightness(0.65f);
        }
        for(String s : Defaults.defaultFoodImageTexts.keySet()){
            int id = Defaults.defaultFoodImageTexts.get(s);
            if(textView.getId() != id) {
                TextView otherImageTexts = findViewById(id);
                ImageFilterButton otherImageBtns = findViewById(Defaults.defaultFoodImages.get(s));
                otherImageTexts.setVisibility(View.GONE);
                otherImageBtns.setBrightness(1);
            }
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
        chip.setOnCloseIconClickListener(v -> {
            selectedCategory.removeView(v);
            categoriestoIgnore.remove(item);
            populateCategories();

        });
        newFood.setCategory(categoriestoIgnore);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void savenewFood(){
        addFoodLoader.setVisibility(View.VISIBLE);
        saveButton.setVisibility(View.GONE);

        if(newFood != null){
            System.out.println(newFood);
            DBController.DATABASE.createFood(newFood, new ICallback() {
                @Override
                public void execute(Object... args) {
                    addFoodLoader.setVisibility(View.GONE);
                    saveButton.setVisibility(View.VISIBLE);
                    if(args.length > 1){
                        String s  = args[0] instanceof String ? (args[0]).toString() : "";
                        boolean success = args[1] instanceof  Boolean ? (Boolean)args[1] : false;
                        System.out.println(success+"<<<<<<<<");
                        if(success){
                            Toast.makeText(CreateFood.this, "Successfully Added", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            System.out.println("ERROR");
                            Toast.makeText(CreateFood.this, "Failed to Add Restaurant", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }
}