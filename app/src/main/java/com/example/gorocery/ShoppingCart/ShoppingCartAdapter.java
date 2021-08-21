package com.example.gorocery.ShoppingCart;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.gorocery.R;
import com.example.gorocery.ShoppingCart.shoppingCartDb.CartDbClient;

import java.util.List;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.SubGroceryViewHolder>{

    private Context ct;
    private List<ShoppingCartFields> mCartList;

    public ShoppingCartAdapter(Context ct, List<ShoppingCartFields> mCartList) {
        this.ct = ct;
        this.mCartList = mCartList;



    }

    @NonNull
    @Override
    public SubGroceryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater=LayoutInflater.from(viewGroup.getContext());
        View v=inflater.inflate(R.layout.sub_grocery_list_item_view,viewGroup,false);




        return new SubGroceryViewHolder(v);
    }

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    @Override
    public void onBindViewHolder(@NonNull final SubGroceryViewHolder subGroceryViewHolder, final int i) {

        final ShoppingCartFields subList= mCartList.get(i);



        subGroceryViewHolder.tvTitle.setText(subList.getP_name());
        subGroceryViewHolder.tvPrice.setText("Rs "+subList.getP_price());



        Glide.with(ct).load(subList.getP_img()).apply(new RequestOptions()
                .placeholder(R.drawable.animated_loading_icon)
        ).into(subGroceryViewHolder.img);

        subGroceryViewHolder.btnAddToCart.setVisibility(View.GONE);
        subGroceryViewHolder.linearLayout.setVisibility(View.VISIBLE);

        subGroceryViewHolder.tvQty.setText(subList.getP_qty()+"");
        if (subList.getP_qty()>1){
            subGroceryViewHolder.imgDel.setVisibility(View.GONE);
            subGroceryViewHolder.imgMinusQty.setVisibility(View.VISIBLE);
        }

        ShoppingCartActivity.refreshPrice();





        //Quantity Increment
        subGroceryViewHolder.imgAddQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //creating a task
                int qty=subList.getP_qty();
                subGroceryViewHolder.tvQty.setText(++qty +"");

                if (qty>1){
                    subGroceryViewHolder.imgDel.setVisibility(View.GONE);
                    subGroceryViewHolder.imgMinusQty.setVisibility(View.VISIBLE);


                    //For background store Product to Cart
                    new Thread(new Runnable()
                    {
                        @Override public void run()
                        {


                            subList.setP_qty(Integer.valueOf(subGroceryViewHolder.tvQty.getText().toString()));

                            //updating qty to database
                            CartDbClient.getInstance(ct)
                                    .getCartDb()
                                    .shoppingCartDAO()
                                    .update(subList);
                        }
                    }).start();

                   notifyDataSetChanged();
                }
            }
        });

        //Quantity decrement
        subGroceryViewHolder.imgMinusQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int qty=subList.getP_qty();

                subGroceryViewHolder.tvQty.setText(--qty +"");

//                notifyDataSetChanged();
                //Toast.makeText(ct, "Qty: "+ qty, Toast.LENGTH_SHORT).show();

                //For background store Product to Cart
                new Thread(new Runnable()
                {
                    @Override public void run()
                    {


                        subList.setP_qty(Integer.valueOf(subGroceryViewHolder.tvQty.getText().toString()));

                        //updating qty to database
                        CartDbClient.getInstance(ct)
                                .getCartDb()
                                .shoppingCartDAO()
                                .update(subList);
                    }
                }).start();

                notifyDataSetChanged();

                if (qty==1){
                    subGroceryViewHolder.imgMinusQty.setVisibility(View.GONE);
                    subGroceryViewHolder.imgDel.setVisibility(View.VISIBLE);

                }

            }
        });

        //Delete Item from cart
        subGroceryViewHolder.imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable()
                {
                    @Override public void run()
                    {
                        CartDbClient.getInstance(ct).getCartDb()
                                .shoppingCartDAO()
                                .delete(subList);





                    }
                }).start();


                if (mCartList.size()==1){

                    ShoppingCartActivity.mLinearCheckout.setVisibility(View.GONE);
                    ShoppingCartActivity.mScrollViewRecipt.setVisibility(View.GONE);
                    ShoppingCartActivity.mLinearStartShopping.setVisibility(View.VISIBLE);
                    ShoppingCartActivity.imgRadioYourBill.setImageResource(R.drawable.ic_radio_button_unchecked);
                }
                mCartList.remove(i);
                notifyItemRemoved(i);
                notifyItemRangeChanged(i, mCartList.size());
                notifyDataSetChanged();



            }
        });

    }

    @Override
    public int getItemCount() {
        return mCartList.size();
    }



    public class SubGroceryViewHolder extends RecyclerView.ViewHolder{

        TextView tvTitle,tvWeight,tvPrice,tvQty;
        ImageView img,imgDel,imgAddQty,imgMinusQty;
        Button btnAddToCart;
        LinearLayout linearLayout;
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


        }
    }
}
