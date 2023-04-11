package com.example.chops.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.chops.R;
import com.example.chops.controllers.TestController;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomePageActivity extends AppCompatActivity {

    TextView addressText;
    FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;
    TestController testControl = new TestController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        String currentUserEmail = currentUser.getEmail();

        addressText.setText(currentUserEmail);
        testControl.testMethod();
    }

}