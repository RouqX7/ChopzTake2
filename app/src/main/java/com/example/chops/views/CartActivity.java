package com.example.chops.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.chops.R;

public class CartActivity extends AppCompatActivity {
    private RecyclerView.Adapter cartAdapter;
    private RecyclerView cartRecyclerViewList;
    TextView totalFeeText, deliveryFeeText, totalText, emptyTxt;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartRecyclerViewList = findViewById(R.id.cartView);



    }
}