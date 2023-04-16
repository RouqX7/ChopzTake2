package com.example.chops.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.MenuItem;

import com.example.chops.R;
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

            case R.id.support:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, supportFragment)
                        .commit();
                return true;
            case R.id.settings:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, settingsFragment)
                        .commit();
                return true;
            case R.id.cart:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, cartFragment)
                        .commit();
                return true;
        }
        return false;
    }
}