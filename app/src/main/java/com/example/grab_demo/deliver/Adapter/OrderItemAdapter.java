package com.example.grab_demo.deliver.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grab_demo.R;
import com.example.grab_demo.model.OrderItem;

import java.util.ArrayList;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.OrderItemViewHolder> {

    private ArrayList<OrderItem> orderItemList;

    public OrderItemAdapter(ArrayList<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    @NonNull
    @Override
    public OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemViewHolder holder, int position) {
        OrderItem orderItem = orderItemList.get(position);
        holder.textViewItemName.setText(orderItem.getItemName());
        holder.textViewQuantity.setText(String.valueOf(orderItem.getQuantity()));
        holder.textViewPrice.setText(orderItem.getPrice().toString() + " VND");
    }

    @Override
    public int getItemCount() {
        return orderItemList.size();
    }

    static class OrderItemViewHolder extends RecyclerView.ViewHolder {
        TextView textViewItemName, textViewQuantity, textViewPrice;

        public OrderItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewItemName = itemView.findViewById(R.id.textViewItemName);
            textViewQuantity = itemView.findViewById(R.id.textViewQuantity);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
        }
    }
}
