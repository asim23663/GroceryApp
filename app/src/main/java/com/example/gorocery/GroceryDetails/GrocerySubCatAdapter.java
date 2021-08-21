package com.example.gorocery.GroceryDetails;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.gorocery.GroceryDetails.products.ProductsActivity;
import com.example.gorocery.R;

import java.util.ArrayList;

public class GrocerySubCatAdapter extends RecyclerView.Adapter<GrocerySubCatAdapter.ViewHolder> {

    ArrayList<GrocerySubCatFields> mList;
    Context ct;

    public GrocerySubCatAdapter(ArrayList<GrocerySubCatFields> mList, Context ct) {
        this.mList = mList;
        this.ct = ct;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.grocery_detail_list_item_view, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

        GrocerySubCatFields grocerySubCatFields = mList.get(i);

        viewHolder.tvDetailTitle.setText(grocerySubCatFields.getcName());

        Glide.with(ct).load(grocerySubCatFields.getcImgUrl()).apply(new RequestOptions()
                .placeholder(R.drawable.animated_loading_icon)
        ).into(viewHolder.img);
//        Glide for image

        viewHolder.frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(ct, "Coming Soon!", Toast.LENGTH_SHORT).show();

                ProductsActivity.grocerySubCatFields = mList.get(i);
                ct.startActivity(new Intent(ct, ProductsActivity.class));
                /*Intent i=new Intent(ct, ProductsActivity.class);
                i.putExtra("title_name",viewHolder.tvDetailTitle.getText());
                ct.startActivity(i);*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView tvDetailTitle;
        FrameLayout frameLayout;

        public ViewHolder(@NonNull View v) {
            super(v);

            img = v.findViewById(R.id.img_detail);
            tvDetailTitle = v.findViewById(R.id.tv_detail_title);
            frameLayout = v.findViewById(R.id.frame_layout_detail_list);
        }
    }
}
