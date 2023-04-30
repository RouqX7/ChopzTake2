package com.example.chops.views;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.chops.Interfaces.ICallback;
import com.example.chops.R;
import com.example.chops.controllers.CartController;
import com.example.chops.controllers.CustomerController;
import com.example.chops.controllers.DBController;
import com.example.chops.models.Address;
import com.example.chops.models.Customer;
import com.example.chops.models.Food;
import com.example.chops.models.Order;
import com.example.chops.models.Restaurant;

import java.util.ArrayList;
import java.util.UUID;

public class CheckOutActivity extends AppCompatActivity {
    ArrayList<Food> foodOrdered = new ArrayList<>();
    Order currentOrder = new Order();
    Customer customer;
    Button checkoutPayBtn;
    TextView checkoutNotes, checkoutFname, checkoutLname, checkoutPhone, checkoutAddress;

    double totalPrice = 0;
    Restaurant restaurant = new Restaurant();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        checkoutPayBtn = findViewById(R.id.checkout_pay);
        checkoutFname = findViewById(R.id.checkout_fname);
        checkoutLname = findViewById(R.id.checkout_lname);
        checkoutPhone = findViewById(R.id.checkout_phoneNumber);
        checkoutAddress = findViewById(R.id.checkout_address);
        checkoutNotes = findViewById(R.id.checkout_notes);
        customer = CustomerController.GET_CURRENT_USER;

        Bundle data = getIntent().getExtras();
        if (data != null) {
            foodOrdered = data.getParcelableArrayList("currentOrderedFood", Food.class);
            currentOrder = data.getParcelable("currentOrder", Order.class);
            totalPrice = data.getDouble("currentOrderTotal");
            restaurant = data.getParcelable("currentOrderedRestaurant", Restaurant.class);
        }
        setUpUserDetails();
        setupOrderDetails();

    }

    private void setUpUserDetails() {
        if (customer != null) {
            checkoutFname.setText(customer.getFirstName() !=null ? customer.getFirstName() : "");
            checkoutLname.setText(customer.getLastName() != null ? customer.getLastName() : "");
            if(customer.getAddress()!=null)
            checkoutAddress.setText(customer.getAddress().getStreet());
            checkoutPhone.setText(customer.getPhoneNo() != null ? customer.getPhoneNo() : "");
        }

    }

    private void setupOrderDetails() {
        Order order = currentOrder;
        System.out.println("O->"+order.getMeals());
        order.setOrderedTime(System.currentTimeMillis());
        if (order != null) {
            checkoutPayBtn.setText("Pay €" + totalPrice);
            checkoutPayBtn.setOnClickListener(e -> {
                DBController.DATABASE.payForOrder(order, (args)->{
                    order.setNotes(checkoutNotes.getText().toString());
                    if (customer == null) {
                        String guestId = UUID.randomUUID().toString();
                        customer = new Customer("guest_" + guestId, "guest_" + guestId, new Address(checkoutAddress.getText().toString()), "", checkoutFname.getText().toString(), checkoutLname.getText().toString(), checkoutPhone.getText().toString(), 100);

                    }
                    order.setUserId(customer.getId());

                    System.out.println("CartItems: "+ order.getMeals());
                    Intent completionPage = new Intent(CheckOutActivity.this, OrderCompletedScreen.class);
                    completionPage.putExtra("currentOrderedRestaurant", restaurant);
                    completionPage.putExtra("currentOrder", order);
                    completionPage.putExtra("currentOrderTotal",totalPrice);
                    completionPage.putParcelableArrayListExtra("currentOrderedFood", foodOrdered);
                    setResult(Activity.RESULT_OK);
                    startActivity(completionPage);
                    finish();

                });
                // go to order complete screen


            });
        }
    }
}