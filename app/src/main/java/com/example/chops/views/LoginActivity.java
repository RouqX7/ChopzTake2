package com.example.chops.views;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.chops.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

import com.example.chops.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    EditText emailEditText, passwordEditText;
    Button loginBtn;
    ProgressBar progressBar;
    TextView createAccountBtnTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText=findViewById(R.id.email_edit_text);
        passwordEditText=findViewById(R.id.password_edit_text);
        progressBar = findViewById(R.id.progress_bar);
        loginBtn = findViewById(R.id.login_btn);
        createAccountBtnTextView = findViewById(R.id.create_account_text_view_btn);

        loginBtn.setOnClickListener((v) -> loginUser() );
        createAccountBtnTextView.setOnClickListener((v) -> startActivity(new Intent( LoginActivity.this,CreateAccountActivity.class) ));

    }
    void loginUser(){
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        boolean isValidated = validateData(email,password);
        if(!isValidated){
            return;
        }
        loginAccountInFirebase(email,password);


    }

    void loginAccountInFirebase(String email,String password){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        changeInProgress(true);
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            changeInProgress(false);
            if(task.isSuccessful()){
                //login is succesful
                if(firebaseAuth.getCurrentUser().isEmailVerified()){
                    //go to mainactivity
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }else{
                    Utility.showToast(LoginActivity.this,"Email is not verified, Please Verify your email.");
                }
            }else{
                //login failed
                Utility.showToast(LoginActivity.this,task.getException().getLocalizedMessage());

            }
        });
    }

    void changeInProgress(boolean inProgress){
        if(inProgress){
            progressBar.setVisibility(View.VISIBLE);
            loginBtn.setVisibility(View.GONE);
        }else{
            progressBar.setVisibility(View.GONE);
            loginBtn.setVisibility(View.GONE);
        }

    }

    boolean validateData(String email,String password){
        //validate the data that are input by the user.

        String regex =  "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern regexHolder = Pattern.compile(regex);
        Matcher regexMatcher = regexHolder.matcher(email);
        boolean isEmailMatching = regexMatcher.matches();

        if(!isEmailMatching){
            emailEditText.setError("Email is invalid");
            return false;
        }if (password.length()<6){
            passwordEditText.setError("Password length is invalid");
            return false;
        }
        return true;
    }

}