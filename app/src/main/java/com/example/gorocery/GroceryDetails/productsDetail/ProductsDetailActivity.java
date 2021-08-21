package com.example.gorocery.GroceryDetails.productsDetail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.gorocery.GroceryDetails.products.ProductsFields;
import com.example.gorocery.R;
import com.example.gorocery.SharedPrefManager;
import com.example.gorocery.ShoppingCart.ShoppingCartActivity;
import com.example.gorocery.ShoppingCart.ShoppingCartFields;
import com.example.gorocery.ShoppingCart.shoppingCartDb.CartDbClient;
import com.example.gorocery.activities.SignInActivity;

import java.util.Objects;

public class ProductsDetailActivity extends AppCompatActivity {

    public static ProductsFields productsFields=null;

    int mQty=1;
    private Toolbar toolbar;
    private TextView tvType,tvAvailablity,tvModel,tvPrice,tvName,tvQty;
    private ImageView imgProduct,imgAddqty,imgMinusQty;
    RatingBar ratingProduct;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_detail);

        Objects.requireNonNull(getSupportActionBar()).hide();

        init();


        tvQty.setText(mQty+"");

        toolbar.setTitle(productsFields.getpName());
        tvName.setText(productsFields.getpName());
        tvType.setText(productsFields.getpType());
        tvPrice.setText(productsFields.getpPrice()+" Rs");
        tvModel.setText(productsFields.getpModel());

        if (productsFields.getStatus().equals("1")){
            tvAvailablity.setText("Available");
        }else if (productsFields.getStatus().equals("0")){
            tvAvailablity.setText("Not Available");
        }


        Glide.with(this).load(productsFields.getpImgUrl()).apply(new RequestOptions()
                .placeholder(R.drawable.loading)
        ).into(imgProduct);
    }


    public void init(){
        toolbar=findViewById(R.id.toolbar_product_detail);
        tvType=findViewById(R.id.tv_p_type);
        tvAvailablity=findViewById(R.id.tv_p_availability);
        tvModel=findViewById(R.id.tv_p_model);
        tvPrice=findViewById(R.id.tv_p_price);
        ratingProduct=findViewById(R.id.rating_product);
        tvName=findViewById(R.id.tv_p_name);
        imgProduct=findViewById(R.id.img_p_detail);
        tvQty=findViewById(R.id.tv_p_qty);
        imgAddqty=findViewById(R.id.add_qty);
        imgMinusQty=findViewById(R.id.minus_qty);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @SuppressLint("SetTextI18n")
    public void addQty(View view) {

        tvQty.setText(++mQty+"");
    }

    @SuppressLint("SetTextI18n")
    public void minusQty(View view) {

        if (mQty!=1){
            tvQty.setText(--mQty+"");

        }
    }

    public void addToCart(View view) {

        if (SharedPrefManager.getInstance(ProductsDetailActivity.this).isLoggedIn()){

            //For background store Product to Cart
            new Thread(new Runnable()
            {
                @Override public void run()
                {
                    //creating a task
                    ShoppingCartFields cart = new ShoppingCartFields();
                    cart.setP_id(productsFields.getpId());
                    cart.setP_name(productsFields.getpName());
                    cart.setP_img(productsFields.getpImgUrl());
                    cart.setP_price(productsFields.getpPrice());
                    cart.setP_qty(1);
                    //adding to database
                    CartDbClient.getInstance(ProductsDetailActivity.this)
                            .getCartDb()
                            .shoppingCartDAO()
                            .insert(cart);
                }
            }).start();


            findViewById(R.id.btn_add_to_cart_p_detail).setEnabled(false);

            //Toast.makeText(ct, "Item Added to cart", Toast.LENGTH_SHORT).show();
        }else {
            startActivity(new Intent(ProductsDetailActivity.this, SignInActivity.class));

        }
    }

    public void openCart(View view) {

        startActivity(new Intent(this, ShoppingCartActivity.class));
        //Toast.makeText(this, "Open Cart", Toast.LENGTH_SHORT).show();
    }
}
