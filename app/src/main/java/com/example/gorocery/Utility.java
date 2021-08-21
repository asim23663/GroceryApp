package com.example.gorocery;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.PopupWindow;

import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {


    public static PopupWindow popupWindow = null;
    public static String mapLocation = "null";

    public static boolean isEmailValid(String email) {

        boolean isValid = false;
        try {
            //String expression = "^[a-zA-Z0-9.-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,4}$";
            String expression = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
            CharSequence inputStr = email;

            Pattern pattern = Pattern.compile(expression,
                    Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(inputStr);
            if (matcher.matches()) {
                isValid = true;
            }
        } catch (Exception e) {
            Log.w("Invalid", "isEmailValid Message = " + e.toString());
        }

        return isValid;
    }





    public static ProgressDialog show(Context context, String title, String message) {
        ProgressDialog progressDialog = new ProgressDialog(context, R.style.DialogStyle);
        progressDialog.setMessage(message);
        progressDialog.setTitle(title);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        return progressDialog;
    }
    FirebaseAuth auth;
    FirebaseUser user;

//    public static void getAuthInstacne(){
//        FirebaseAuth.getInstance();
//
//    }
//    public static FirebaseUser getCurrentUser(){
//
//        return user;
//    }
}
