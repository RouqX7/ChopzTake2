package com.example.chops.models;

public class Address {
    String street;
    String county;
    String postcode;

    public Address(String street, String county, String postcode) {
        this.street = street;
        this.county = county;
        this.postcode = postcode;
    }

    public Address(String street) {
        this.street = street;
    }
    public Address(){

    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
}
