package com.example.grab_demo.customer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grab_demo.R;
import com.example.grab_demo.customer.m_interface.StClickItem;
import com.example.grab_demo.customer.model.Order;

import java.util.List;

public class C_OrderAdapter extends RecyclerView.Adapter<C_OrderAdapter.HomeViewHolder> {
    Context context;
    List<Order> storeList;
    private StClickItem stClickItem;

    public C_OrderAdapter(Context context, List<Order> storeList) {
        this.context = context;
        this.storeList = storeList;
    }

    public void setOnClickItemListener(StClickItem stClickItem) {
        this.stClickItem = stClickItem;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_customer_order, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        Order order = storeList.get(position);

        holder.txt_id_order.setText(String.valueOf(order.getOrderId()));
        holder.txt_id_delivery.setText(String.valueOf(order.getDeliveryId()));
        holder.txt_total_money.setText(String.valueOf(order.getTotalPrice()));
        holder.txt_status.setText(order.getStatus());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stClickItem != null) {
                    stClickItem.onClickItem(String.valueOf(storeList.get(position).getOrderId()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (storeList != null) return storeList.size();
        return 0;
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {
        TextView txt_id_order, txt_id_delivery, txt_total_money, txt_status;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_id_order = itemView.findViewById(R.id.txt_id_order);
            txt_id_delivery = itemView.findViewById(R.id.txt_id_delivery);
            txt_total_money = itemView.findViewById(R.id.txt_total_money);
            txt_status = itemView.findViewById(R.id.txt_status);
        }
    }
}
