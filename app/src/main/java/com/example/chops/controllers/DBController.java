package com.example.chops.controllers;

import com.example.chops.DBHandler.FireAuth;
import com.example.chops.DBHandler.FireDatabase;
import com.example.chops.DBHandler.IAuth;
import com.example.chops.DBHandler.IDatabase;
import com.example.chops.DBHandler.MockDB;

public class DBController {
    public static IAuth AUTHENTICATION = new FireAuth();
    public static IDatabase DATABASE = new FireDatabase();
}
