package com.example.chops.models;
import java.util.UUID;

public class Customer {
    long id = System.currentTimeMillis();
    UUID customerUUID = UUID.randomUUID();
    String customerUID = customerUUID.toString();
    Basket basket = new Basket();
    Address address;
    String email;
    String password;
    String firstName;
    String lastName;
    double money = 1000;

    public Customer(long id, UUID customerUUID, String customerUID, Basket basket, Address address, String email, String password, String firstName, String lastName, double money) {
        this.id = id;
        this.customerUUID = customerUUID;
        this.customerUID = customerUID;
        this.basket = basket;
        this.address = address;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.money = money;
    }
}
