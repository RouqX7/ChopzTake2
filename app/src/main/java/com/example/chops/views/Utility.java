package com.example.chops.views;

import android.content.Context;
import android.widget.Toast;

public class Utility {

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    //regex  "^[a-zA-Z0-9_+&*-]+(?:\\."+
    //                "[a-zA-Z0-9_+&*-]+)*@" +
    //                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
    //                "A-Z]{2,7}$";
    //"^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$"
}
