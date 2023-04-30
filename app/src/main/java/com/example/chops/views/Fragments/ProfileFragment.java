package com.example.chops.views.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chops.Interfaces.ICallback;
import com.example.chops.R;
import com.example.chops.controllers.CustomerController;
import com.example.chops.controllers.DBController;
import com.example.chops.models.Address;
import com.example.chops.models.Customer;
import com.example.chops.models.Order;
import com.example.chops.views.Adapters.UserOrdersAdapter;
import com.example.chops.views.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    TextView name, address, wallet;
    RecyclerView ordersRecyclerView;
    UserOrdersAdapter adapter;
    FloatingActionButton editorFab;
    LinearLayout editorView, profileView;
    Button signOutBtn, saveBtn;
    EditText nameField, lnameField, walletField, locationField;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name = view.findViewById(R.id.profile_name);
        address = view.findViewById(R.id.profile_address);
        wallet = view.findViewById(R.id.profile_wallet);
        editorFab = view.findViewById(R.id.edit_profile);
        editorView = view.findViewById(R.id.profileEditor);
        profileView = view.findViewById(R.id.profileViewer);
        ordersRecyclerView = view.findViewById(R.id.ordersRecyclerView);
        nameField = view.findViewById(R.id.firstname_edit);
        lnameField = view.findViewById(R.id.lastname_edit);
        walletField = view.findViewById(R.id.wallet_edit);
        locationField = view.findViewById(R.id.address_edit);
        saveBtn = view.findViewById(R.id.save_profileBtn);
        signOutBtn = view.findViewById(R.id.sign_out_btn);
        Customer customer = CustomerController.GET_CURRENT_USER;
        name.setText(customer.getFirstName() != null ? customer.getFirstName() : customer.getLastName());
        address.setText(customer.getAddress()!=null ? customer.getAddress().getStreet() : "--");
        wallet.setText("£"+customer.getMoney());
       nameField.setText(customer.getFirstName() != null ? customer.getFirstName() : "");
       lnameField.setText(customer.getLastName() != null ? customer.getLastName() : "");
       walletField.setText(customer.getMoney()+"");
        editorFab.setOnClickListener(e->{
            profileView.setVisibility(View.GONE);
            editorView.setVisibility(View.VISIBLE);
            editorFab.setVisibility(View.GONE);

        });
        signOutBtn.setOnClickListener(e->{
            DBController.AUTHENTICATION.signOut();
            startActivity(new Intent(getActivity(), LoginActivity.class));
        });
        saveBtn.setOnClickListener(e->{
            customer.setFirstName(nameField.getText().toString());
            customer.setLastName(lnameField.getText().toString());
            customer.setMoney(Double.parseDouble(walletField.getText().toString()));
            customer.setAddress(new Address(locationField.getText().toString()));
            name.setText(customer.getFirstName() != null ? customer.getFirstName() : customer.getLastName());
            address.setText(customer.getAddress()!=null ? customer.getAddress().getStreet() : "--");
            wallet.setText("£"+customer.getMoney());
            nameField.setText(customer.getFirstName() != null ? customer.getFirstName() : "");
            lnameField.setText(customer.getLastName() != null ? customer.getLastName() : "");
            walletField.setText(customer.getMoney()+"");
            DBController.DATABASE.updateCustomer(customer,(args)->{
                profileView.setVisibility(View.VISIBLE);
                editorView.setVisibility(View.GONE);
                editorFab.setVisibility(View.VISIBLE);
            });
        });
        adapter = new UserOrdersAdapter(new ArrayList<>(), new ICallback() {
            @Override
            public void execute(Object... args) {

            }
        });
        ordersRecyclerView.setAdapter(adapter);
        ArrayList<String> orderIDs = new ArrayList<>(customer.getOrders());
        System.out.println("Orders"+orderIDs);
        DBController.DATABASE.getUserOrders(orderIDs, new ICallback() {
            @Override
            public void execute(Object... args) {
                if(args.length > 0){
                    ArrayList<Order> orders = args[0] instanceof ArrayList ? (ArrayList<Order>) args[0] : new ArrayList<>();
                    System.out.println("My Orders -> "+orders);
                    if(!orders.isEmpty()){
                        adapter.updateOrders(orders);
                    }
                }
            }
        },new ArrayList<>());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
}