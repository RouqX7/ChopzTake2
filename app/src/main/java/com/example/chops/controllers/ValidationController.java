package com.example.chops.controllers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationController {
    public static String validateEmail(String email){
        //validate the data that are input by the user.
        String regex =  "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern regexHolder = Pattern.compile(regex);
        Matcher regexMatcher = regexHolder.matcher(email);
        boolean isEmailMatching = regexMatcher.matches();

        if(!isEmailMatching){
            return "Email is invalid";
        }
        return null;
    }
    public static String validatePassword(String password){
        String regex =  "^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z[0-9]]{8,}$";


        Pattern regexHolder = Pattern.compile(regex);
        Matcher regexMatcher = regexHolder.matcher(password);
        boolean isPasswordMatching = regexMatcher.matches();
        if(!isPasswordMatching){
            return "Password is invalid";
        }
        return null;
    }
    public static String validateConfirmPassword(String password, String confirmPassword){
       return password.equals(confirmPassword)? null : "Password does not match";
    }
}
