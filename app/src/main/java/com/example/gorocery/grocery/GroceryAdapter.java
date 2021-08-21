package com.example.gorocery.grocery;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.gorocery.GroceryDetails.GrocerySubCategoryActivity;
import com.example.gorocery.R;

import java.util.ArrayList;

public class GroceryAdapter extends RecyclerView.Adapter<GroceryAdapter.GroceryViewHolder> {

    ArrayList<GroceryItemsFields> mList;
    Context ct;

    public GroceryAdapter(ArrayList<GroceryItemsFields> mList, Context ct) {
        this.mList = mList;
        this.ct = ct;
    }

    @NonNull
    @Override
    public GroceryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.list_item_view, viewGroup, false);

        return new GroceryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GroceryViewHolder groceryViewHolder, final int i) {

        final GroceryItemsFields groceryList = mList.get(i);

        groceryViewHolder.tvTitle.setText(groceryList.getcName());
        // groceryViewHolder.img_grocery.setImageResource(groceryList.getImgResId());


        Glide.with(ct).load(groceryList.getcImgUrl()).apply(new RequestOptions()
                .placeholder(R.drawable.animated_loading_icon)
        ).into(groceryViewHolder.img_grocery);

        groceryViewHolder.frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Toast.makeText(ct, "Cat Id: "+groceryList.getcId(), Toast.LENGTH_SHORT).show();
                GrocerySubCategoryActivity.groceryItemsFields = mList.get(i);
                ct.startActivity(new Intent(ct, GrocerySubCategoryActivity.class));

            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class GroceryViewHolder extends RecyclerView.ViewHolder {

        FrameLayout frameLayout;
        TextView tvTitle;
        ImageView img_grocery;

        public GroceryViewHolder(@NonNull View v) {
            super(v);

            tvTitle = v.findViewById(R.id.tv_title);

            img_grocery = v.findViewById(R.id.img_grocery);
            frameLayout = v.findViewById(R.id.frame_list_item);

        }
    }
}
