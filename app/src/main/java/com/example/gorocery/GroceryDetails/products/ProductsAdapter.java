package com.example.gorocery.GroceryDetails.products;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.gorocery.GroceryDetails.productsDetail.ProductsDetailActivity;
import com.example.gorocery.R;
import com.example.gorocery.SharedPrefManager;
import com.example.gorocery.ShoppingCart.ShoppingCartFields;
import com.example.gorocery.ShoppingCart.shoppingCartDb.CartDbClient;
import com.example.gorocery.activities.LoginActivity;
import com.example.gorocery.activities.RegistrationActivity;
import com.example.gorocery.activities.SignInActivity;

import java.util.ArrayList;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.SubGroceryViewHolder>{

    ArrayList<ProductsFields> mList;
    Context ct;


    public ProductsAdapter(ArrayList<ProductsFields> mList, Context ct) {
        this.mList = mList;
        this.ct = ct;
    }

    @NonNull
    @Override
    public SubGroceryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater=LayoutInflater.from(viewGroup.getContext());
        View v=inflater.inflate(R.layout.sub_grocery_list_item_view,viewGroup,false);

        return new SubGroceryViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final SubGroceryViewHolder subGroceryViewHolder, final int i) {

        final ProductsFields subList=mList.get(i);

        int qty=1;
        subGroceryViewHolder.tvTitle.setText(subList.getpName());
//        subGroceryViewHolder.tvWeight.setText(subList.getWeight());
        subGroceryViewHolder.tvPrice.setText("Rs "+subList.getpPrice());

        Glide.with(ct).load(subList.getpImgUrl()).apply(new RequestOptions()
                .placeholder(R.drawable.loading)
        ).into(subGroceryViewHolder.img);



        subGroceryViewHolder.btnAddToCart.setVisibility(View.VISIBLE);
        subGroceryViewHolder.btnAddToCart.setEnabled(true);

        subGroceryViewHolder.linearLayout.setVisibility(View.GONE);


        /*open Products Detail*/
        subGroceryViewHolder.frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Toast.makeText(ct, "Coming Soon", Toast.LENGTH_SHORT).show();
                ProductsDetailActivity.productsFields=mList.get(i);
                ct.startActivity(new Intent(ct, ProductsDetailActivity.class));

            }

        });


        subGroceryViewHolder.btnAddToCart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new Thread(new Runnable()
                {
                    @Override public void run()
                    {
                        //creating a task
                        ShoppingCartFields cart = new ShoppingCartFields();
                        cart.setP_id(subList.getpId());
                        cart.setP_name(subList.getpName());
                        cart.setP_img(subList.getpImgUrl());
                        cart.setP_price(subList.getpPrice());
                        cart.setP_qty(1);
                        //adding to database
                        CartDbClient.getInstance(ct)
                                .getCartDb()
                                .shoppingCartDAO()
                                .insert(cart);
                    }
                }).start();
              /*  if (SharedPrefManager.getInstance(ct).isLoggedIn()){

                    //For background store Product to Cart
                    new Thread(new Runnable()
                    {
                        @Override public void run()
                        {
                            //creating a task
                            ShoppingCartFields cart = new ShoppingCartFields();
                            cart.setP_id(subList.getpId());
                            cart.setP_name(subList.getpName());
                            cart.setP_img(subList.getpImgUrl());
                            cart.setP_price(subList.getpPrice());
                            cart.setP_qty(1);
                            //adding to database
                            CartDbClient.getInstance(ct)
                                    .getCartDb()
                                    .shoppingCartDAO()
                                    .insert(cart);
                        }
                    }).start();


                    subGroceryViewHolder.btnAddToCart.setEnabled(false);
                    //Toast.makeText(ct, "Item Added to cart", Toast.LENGTH_SHORT).show();
                }else {
                    ct.startActivity(new Intent(ct, SignInActivity.class));

                }*/



            }
        });



    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class SubGroceryViewHolder extends RecyclerView.ViewHolder{

        TextView tvTitle,tvWeight,tvPrice,tvQty;
        ImageView img,imgDel,imgAddQty,imgMinusQty;
        Button btnAddToCart;
        LinearLayout linearLayout;
        FrameLayout frameLayout;
        public SubGroceryViewHolder(@NonNull View v) {
            super(v);

            tvTitle=v.findViewById(R.id.tv_sub_grocery_title);
            tvPrice=v.findViewById(R.id.tv_sub_grocery_price);
            tvWeight=v.findViewById(R.id.tv_sub_grocery_weight);
            img=v.findViewById(R.id.img_sub_grocery);
            btnAddToCart=v.findViewById(R.id.btn_add_to_cart);
            imgDel=v.findViewById(R.id.img_delete);
            imgAddQty=v.findViewById(R.id.img_add_qty);
            linearLayout=v.findViewById(R.id.linear_layout);
            tvQty=v.findViewById(R.id.tv_qty);
            imgMinusQty=v.findViewById(R.id.img_minus_qty);
            frameLayout=v.findViewById(R.id.frame_products);


        }
    }
}
