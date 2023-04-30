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
import com.example.chops.R;
import com.example.chops.controllers.DBController;
import com.example.chops.controllers.ValidationController;
import com.example.chops.models.Customer;
import com.google.firebase.auth.FirebaseAuth;

import java.util.UUID;

public class CreateAccountActivity extends AppCompatActivity {

    EditText emailEditText,passwordEditText,confirmPasswordEditText;
    Button createAccountBtn;
    ProgressBar progressBar;
    TextView loginBtnTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        emailEditText=findViewById(R.id.checkout_fname);
        passwordEditText=findViewById(R.id.checkout_lname);
        confirmPasswordEditText = findViewById(R.id.checkout_address);
        createAccountBtn = findViewById(R.id.checkout_pay);
        progressBar = findViewById(R.id.progress_bar);
        loginBtnTextView = findViewById(R.id.login_text_view_btn);

        createAccountBtn.setOnClickListener(v-> createAccount());
        loginBtnTextView.setOnClickListener((v) -> startActivity(new Intent(CreateAccountActivity.this,LoginActivity.class)));
    }
    void createAccount(){
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        String emailError = ValidationController.validateEmail(email);
        String passwordError = ValidationController.validatePassword(password);
        String confirmPasswordError = ValidationController.validateConfirmPassword(password,confirmPassword);
        emailEditText.setError(emailError);
        passwordEditText.setError(passwordError);
        confirmPasswordEditText.setError(confirmPasswordError);

        if(emailError!= null||passwordError!=null|| confirmPasswordError!=null)
            return;
        createAccountInFirebase(email,password);

    }

    void createAccountInFirebase(String email,String password){
        changeInProgress(true);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        DBController.AUTHENTICATION.signUpWithEmailAndPassword(email, password, new ICallback() {
            @Override
            public void execute(Object... args) {
                if(args.length>1){
                    String uid = args[0] instanceof String ? (String) args[0] : null;
                   String errorMessage = args.length>2 ? ((String) args[args.length-1]): null;
                    boolean success = args[1] instanceof Boolean ? (Boolean) args[1] : false;
                    if(success) {
                        //creating acc is done
                        Utility.showToast(CreateAccountActivity.this,"Successfully create account, Check email to verify");
                        System.out.println("uid = "+ uid);
                        Customer c = new Customer(uid, UUID.randomUUID().toString(),null,email,null,null,null,1000);
                        DBController.DATABASE.createCustomer(c, new ICallback() {
                            @Override
                            public void execute(Object... args) {
                                String result = args[0] instanceof String ? (String) args[0] : null;
                                boolean success = args[1] instanceof Boolean ? (Boolean) args[1] : false;
                                changeInProgress(false);
                                if(success){
                                    CustomerProvider.currentCustomer = c;
                                    goToHomepage();
                                    finish();
                                }else{
                                    Utility.showToast(CreateAccountActivity.this,result);
                                }
                            }
                        });
                    }else{
                        //failure
                        Utility.showToast(CreateAccountActivity.this,errorMessage);

                    }
                }
            }
        });



    }

    void changeInProgress(boolean inProgress){
        if(inProgress){
            progressBar.setVisibility(View.VISIBLE);
            createAccountBtn.setVisibility(View.GONE);
        }else{
            progressBar.setVisibility(View.GONE);
            createAccountBtn.setVisibility(View.GONE);
        }

    }

    public void goToHomepage(){
        Intent intent =  new Intent(this, MainViewActivity.class);
        startActivity(intent);
    }

}