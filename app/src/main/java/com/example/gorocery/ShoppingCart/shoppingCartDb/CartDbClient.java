package com.example.gorocery.ShoppingCart.shoppingCartDb;

import android.content.Context;

import androidx.room.Room;

public class CartDbClient {


    private Context mCtx;
    private static CartDbClient mInstance;

    //our app database object
    private CartDb cartDb;

    private CartDbClient(Context mCtx) {
        this.mCtx = mCtx;

        //creating the app database with Room database builder
        //cartDB is the name of the database
        cartDb = Room.databaseBuilder(mCtx, CartDb.class, "cartDB").build();
    }

    public static synchronized CartDbClient getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new CartDbClient(mCtx);
        }
        return mInstance;
    }

    public CartDb getCartDb() {
        return cartDb;
    }
}
