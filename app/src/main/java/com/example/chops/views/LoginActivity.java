package com.example.chops.views;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.chops.DataProvider.CustomerProvider;
import com.example.chops.Interfaces.ICallback;
import com.example.chops.controllers.DBController;
import com.example.chops.models.Customer;

import com.example.chops.R;

public class LoginActivity extends AppCompatActivity {
    EditText emailEditText, passwordEditText;
    Button loginBtn;
    ProgressBar progressBar;
    TextView createAccountBtnTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText=findViewById(R.id.checkout_fname);
        passwordEditText=findViewById(R.id.checkout_lname);
        progressBar = findViewById(R.id.progress_bar);
        loginBtn = findViewById(R.id.login_btn);
        createAccountBtnTextView = findViewById(R.id.create_account_text_view_btn);

        loginBtn.setOnClickListener((v) -> loginUser(emailEditText.getText().toString(),passwordEditText.getText().toString()) );
        createAccountBtnTextView.setOnClickListener((v) -> startActivity(new Intent( LoginActivity.this,CreateAccountActivity.class) ));

    }
    void loginUser(String email,String password){
        changeInProgress(true);
        DBController.AUTHENTICATION.signInWithEmailAndPassword(email, password, new ICallback() {
            @Override
            public void execute(Object... args) {
                if(args.length>1) {
                    String uid = args[0] instanceof String ? (String) args[0] : null;
                    String errorMessage = args.length > 2 ? ((String) args[args.length - 1]) : null;
                    boolean success = args[1] instanceof Boolean ? (Boolean) args[1] : false;

                    if (success) {
                        //creating acc is done
                        Utility.showToast(LoginActivity.this, "Successfully create account, Check email to verify");
                        System.out.println("uid = " + uid);
                        DBController.DATABASE.retrieveCustomer(uid, new ICallback() {
                            @Override
                            public void execute(Object... args) {
                                if (args.length > 1) {
                                    Customer c = args[0] instanceof Customer ? (Customer) args[0] : null;
                                    String errorMessage = args.length > 2 ? ((String) args[args.length - 1]) : "";
                                    boolean success = args[1] instanceof Boolean ? (Boolean) args[1] : false;
                                    changeInProgress(false);
                                    if (success && c != null) {
                                        CustomerProvider.currentCustomer = c;
                                        goToHomepage();
                                        finish();
                                    } else {
                                        Utility.showToast(LoginActivity.this, errorMessage);
                                    }

                                }
                            }
                        });
                    } else {
                        Utility.showToast(LoginActivity.this, errorMessage);
                    }
                }
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
    public void goToHomepage(){
        Intent intent =  new Intent(this, MainViewActivity.class);
        startActivity(intent);
    }
}