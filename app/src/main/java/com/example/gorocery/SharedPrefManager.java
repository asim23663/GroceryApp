package com.example.gorocery;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.gorocery.ShoppingCart.ShoppingCartFields;
import com.example.gorocery.activities.SignInActivity;
import com.example.gorocery.model.UserFields;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import java.util.ArrayList;

public class SharedPrefManager {


    //the constants
    private static final String PROFILE_PREF = "groceryApp";




    public final String KEY_NAME = "customer_name";
    public final String KEY_EMAIL = "customer_email";
    public final String KEY_PHONE = "customer_mobile";
    public static final String KEY_GENDER = "gender";
    public static final String KEY_DOB = "date_of_birth";
    public static final String KEY_ADDRESS = "customer_short_address";
    public static final String KEY_LOCATION = "location";
    public final String KEY_PASSWORD = "password";


    public final String KEY_API_TOKEN = "token";


    public SharedPreferences sharedPrefs;
    private static SharedPrefManager mInstance;
    private static Context mCtx;
    public SharedPreferences.Editor editor;


    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void saveUserData(UserFields user) {

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(PROFILE_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_NAME, user.getName());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_PHONE, user.getMobile());
        editor.putString(KEY_GENDER, user.getGender());
        editor.putString(KEY_DOB, user.getDob());
        editor.putString(KEY_ADDRESS, user.getAddress());
        editor.putString(KEY_LOCATION, user.getLocation());
        editor.putString(KEY_API_TOKEN, user.getApiToken());
       // editor.putString(KEY_PASSWORD, user.getPassword());
        editor.apply();
    }



    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(PROFILE_PREF, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMAIL, null) != null;
    }

    //this method will give the logged in user
    public UserFields getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(PROFILE_PREF, Context.MODE_PRIVATE);
        return new UserFields(
                sharedPreferences.getString(KEY_NAME, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_ADDRESS, null),
                sharedPreferences.getString(KEY_PASSWORD, null),
                sharedPreferences.getString(KEY_LOCATION, null),
                sharedPreferences.getString(KEY_PHONE, null),
                sharedPreferences.getString(KEY_GENDER, null),
                sharedPreferences.getString(KEY_DOB, null),
                sharedPreferences.getString(KEY_API_TOKEN, null)
        );
    }




    //this method will logout the user
    public void logout() {

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(PROFILE_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        FirebaseAuth.getInstance().signOut();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, SignInActivity.class));
    }


}
