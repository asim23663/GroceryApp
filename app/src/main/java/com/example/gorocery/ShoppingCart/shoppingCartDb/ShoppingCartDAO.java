package com.example.gorocery.ShoppingCart.shoppingCartDb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gorocery.ShoppingCart.ShoppingCartFields;

import java.util.List;

@Dao
public interface ShoppingCartDAO {

    @Query("SELECT * FROM SHOPPINGCARTFIELDS")
    List<ShoppingCartFields> getAll();

    @Insert
    void insert(ShoppingCartFields task);

    @Delete
    void delete(ShoppingCartFields task);

    @Update
    void update(ShoppingCartFields task);

    @Query("DELETE FROM ShoppingCartFields")
    void emptyCart();
}
