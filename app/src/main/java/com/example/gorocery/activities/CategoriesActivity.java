package com.example.gorocery.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import com.example.gorocery.R;
import com.example.gorocery.grocery.GroceryAdapter;
import com.example.gorocery.grocery.GroceryItemsFields;

import java.util.ArrayList;
import java.util.Objects;

public class CategoriesActivity extends AppCompatActivity {


    RecyclerView mRvCategory;
    Toolbar mToolbar;
    ArrayList<GroceryItemsFields> mList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        Objects.requireNonNull(getSupportActionBar()).hide();

        mToolbar=findViewById(R.id.toolbar_category);
//        setSupportActionBar(mToolbar);
        mRvCategory=findViewById(R.id.rv_categories);

        mList=new ArrayList<>();

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //loadCategories();

    }

    /*public void loadCategories(){

        mList.add(new GroceryItemsFields("Grocery","Oil,Daalain,Olives etc.. ",R.drawable.grocery));
        mList.add(new GroceryItemsFields("Fruits & Vegetables","Fruits,potato,Orange etc.. ",R.drawable.fruits_veg));


        mRvCategory.setAdapter(new GroceryAdapter(mList,this));
        mRvCategory.setLayoutManager(new LinearLayoutManager(this));
    }*/
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
