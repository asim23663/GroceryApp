package com.example.gorocery.myOrders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gorocery.R;

import java.util.ArrayList;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder>{


    ArrayList<CustomerOrderFields> mList;
    Context ct;

    public MyOrderAdapter(ArrayList<CustomerOrderFields> mList, Context ct) {
        this.mList = mList;
        this.ct = ct;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View v=inflater.inflate(R.layout.item_view_my_order,parent,false);

        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CustomerOrderFields orderList=mList.get(position);

        holder.tvOrderId.setText(orderList.getOrderId());
        holder.tvOrderPrice.setText("RS "+orderList.getTotalAmount());
        holder.tvOrderDate.setText(orderList.getDate());
        if (orderList.getStatus()==1){
            holder.tvOrderStatus.setText("Pending");
        }else if (orderList.getStatus()==0){
            holder.tvOrderStatus.setText("Delivered");
        }



    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvOrderDate,tvOrderId,tvOrderStatus,tvOrderPrice;
        public ViewHolder(@NonNull View v) {
            super(v);

            tvOrderDate = v.findViewById(R.id.tv_order_date);
            tvOrderId = v.findViewById(R.id.tv_order_id);
            tvOrderStatus = v.findViewById(R.id.tv_order_status);
            tvOrderPrice = v.findViewById(R.id.tv_order_total_price);
        }
    }

}
