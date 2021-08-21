package com.example.gorocery.ShoppingCart.shoppingCartDb;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.gorocery.ShoppingCart.ShoppingCartFields;

@Database(entities = {ShoppingCartFields.class}, version = 1)
public abstract class CartDb extends RoomDatabase {
    public abstract ShoppingCartDAO shoppingCartDAO();
}
