package com.example.chops.views.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.chops.Interfaces.ICallback;
import com.example.chops.R;
import com.example.chops.models.Restaurant;
import com.example.chops.views.Adapters.RestaurantAdapter;
import com.example.chops.views.Menu;

import java.util.ArrayList;


public class CartFragment extends Fragment {
    private RecyclerView.Adapter cartAdapter;
    private RecyclerView cartRecyclerViewList;
    TextView totalFeeText, deliveryFeeText, totalText, emptyTxt;
    private ScrollView scrollView;


    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_cart, container, false);
        return view;


    }

    private void cartRecyclerViewList(View v) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        cartRecyclerViewList = v.findViewById(R.id.cartText);
        cartRecyclerViewList.setLayoutManager(linearLayoutManager);
        cartAdapter = new RestaurantAdapter(new ArrayList<>(), new ICallback() {
            @Override
            public void execute(Object... args) {
                if (args.length > 0) {
                    Restaurant restaurant = args[0] instanceof Restaurant ? (Restaurant) args[0] : null;
                    Intent menuPage = new Intent(getContext(), Menu.class);
                    menuPage.putExtra("currentRestaurant", restaurant);
                    requireActivity().setResult(Activity.RESULT_OK);
                    startActivity(menuPage);
                }
            }
        });
        cartRecyclerViewList.setAdapter(cartAdapter);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cartRecyclerViewList(view);
    }
}