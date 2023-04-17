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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chops.Config.Defaults;
import com.example.chops.Domain.CategoryDomain;
import com.example.chops.Interfaces.ICallback;
import com.example.chops.R;
import com.example.chops.controllers.DBController;
import com.example.chops.models.Restaurant;
import com.example.chops.views.Adapters.CategoryAdapter;
import com.example.chops.views.Adapters.RestaurantAdapter;
import com.example.chops.views.Menu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;


public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }


    private RecyclerView.Adapter adapter;
    private RestaurantAdapter restAdapter;
    private RecyclerView recyclerViewCategory, recyclerViewFastList;

    ImageView imageView;
    TextView textView;
    TextView addressText;
    FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,
                              Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
    private void recyclerViewFastList(View v) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViewFastList=v.findViewById(R.id.view2);
        recyclerViewFastList.setLayoutManager(linearLayoutManager);
        restAdapter = new RestaurantAdapter(new ArrayList<>(), new ICallback() {
            @Override
            public void execute(Object... args) {
                if(args.length>0){
                    Restaurant restaurant = args[0] instanceof Restaurant ? (Restaurant) args[0] : null;
                    Intent menuPage = new Intent(getActivity(), Menu.class);
                    menuPage.putExtra("currentRestaurant",restaurant);
                    requireActivity().setResult(Activity.RESULT_OK);
                    startActivity(menuPage);
                }
            }
        });
        recyclerViewFastList.setAdapter(restAdapter);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewCategory(view);
        recyclerViewFastList(view);

        DBController.DATABASE.streamRestaurants(new ICallback() {
            @Override
            public void execute(Object... args) {
                ArrayList<Restaurant> restaurants = new ArrayList<>();
                if(args.length > 1){
                    restaurants = args[0] instanceof ArrayList ? (ArrayList) args[0] : new ArrayList<>();
                    boolean success = args[1] instanceof Boolean ? (Boolean) args[1] : false;
                    String errorMessages = args.length > 2 ? args[2] instanceof  String ? (String) args[2] : "":null;

                    if(success){
                        System.out.println("RESTAURANT:::___>" + restaurants);
                        restAdapter.updateRestaurants(restaurants);
                    }
                }
            }
        });

    }

    public void  recyclerViewCategory(View v){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViewCategory=v.findViewById(R.id.view1);
        recyclerViewCategory.setLayoutManager(linearLayoutManager);

        ArrayList<CategoryDomain> categoryList = new ArrayList<>(Arrays.asList(Defaults.defaultFoodCatagories));

        adapter = new CategoryAdapter(categoryList);
        recyclerViewCategory.setAdapter(adapter);


    }

}