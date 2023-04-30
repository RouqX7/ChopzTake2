package com.example.chops.views;

import static com.google.common.collect.ComparisonChain.start;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.chops.Interfaces.ICallback;
import com.example.chops.R;
import com.example.chops.controllers.CustomerController;
import com.example.chops.controllers.DBController;
import com.example.chops.models.Customer;
import com.example.chops.views.Fragments.CartFragment;
import com.example.chops.views.Fragments.HomeFragment;
import com.example.chops.views.Fragments.ProfileFragment;
import com.example.chops.views.Fragments.SettingsFragment;
import com.example.chops.views.Fragments.SupportFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainViewActivity extends AppCompatActivity  implements NavigationBarView
        .OnItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onStart() {
        super.onStart();
       String uid = DBController.AUTHENTICATION.getCurrentUser();
       if(uid==null){
           Intent loginPage = new Intent(MainViewActivity.this, LoginActivity.class);
           startActivity(loginPage);
           finish();
       }else{
           DBController.DATABASE.retrieveCustomer(uid, new ICallback() {
               @Override
               public void execute(Object... args) {
                   if(args.length > 0){
                       Customer customer = args[0] instanceof Customer ? (Customer) args[0] : null;
                       if(customer!=null){
                           CustomerController.GET_CURRENT_USER = customer;
                       }
                   }

               }
           });
       }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view_activity);

        bottomNavigationView
                = findViewById(R.id.bottomNavView);

        bottomNavigationView
                .setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);
    }
    HomeFragment homeFragment = new HomeFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    SupportFragment supportFragment = new SupportFragment();
    SettingsFragment settingsFragment = new SettingsFragment();
    CartFragment cartFragment = new CartFragment();



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, homeFragment)
                        .commit();
                return true;

            case R.id.profile:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, profileFragment)
                        .commit();
                return true;

//            case R.id.support:
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.flFragment, supportFragment)
//                        .commit();
//                return true;
//            case R.id.settings:
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.flFragment, settingsFragment)
//                        .commit();
//                return true;
            case R.id.cart:
                Intent cartPage = new Intent(MainViewActivity.this, CartActivity.class);
                startActivity(cartPage);
                return true;
        }
        return false;
    }
}