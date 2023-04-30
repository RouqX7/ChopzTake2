package com.example.chops.models;
import java.util.ArrayList;
import java.util.UUID;

public class Customer {
    private String id;
    private String cartId;
    private Address address;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNo;
    private ArrayList<String> orders = new ArrayList<>();
    private double money = 1000;

    public ArrayList<String> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<String> orders) {
        this.orders = orders;
    }

    public Customer(String id, String cartId, Address address, String email, String firstName, String lastName, String phoneNo, ArrayList<String> orders, double money) {
        this.id = id;
        this.cartId = cartId;
        this.address = address;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNo = phoneNo;
        this.orders = orders;
        this.money = money;
    }

    public Customer(String id, String cartId, Address address, String email, String firstName, String lastName, String phoneNo, double money) {
        this.id = id;
        this.cartId = cartId;
        this.address = address;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNo = phoneNo;
        this.money = money;
    }

    public Customer() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money += money;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                '}';
    }
}
