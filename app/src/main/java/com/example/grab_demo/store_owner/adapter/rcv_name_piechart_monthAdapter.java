package com.example.grab_demo.store_owner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grab_demo.R;
import com.example.grab_demo.store_owner.OnItemClickListener;
import com.example.grab_demo.store_owner.model.DishMenuHSO;

import java.util.ArrayList;

public class rcv_name_piechart_monthAdapter extends RecyclerView.Adapter<rcv_name_piechart_monthAdapter.ViewHolder> {

    Context context;
    ArrayList<DishMenuHSO> arr;
    private OnItemClickListener onItemClickListener;

    public rcv_name_piechart_monthAdapter(Context context, ArrayList<DishMenuHSO> arr) {
        this.context = context;
        this.arr = arr;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_rcv_name_piechart_month, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DishMenuHSO dishMenuHSO = arr.get(position);
        holder.txtTen.setText(dishMenuHSO.getTensp());
        holder.view.setBackgroundColor(dishMenuHSO.getColors());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(arr.get(position).getTensp());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView txtTen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.view_color_item_rcv_name_piechart_month);
            txtTen = itemView.findViewById(R.id.tv_color_item_rcv_name_piechart_month);
        }
    }
}
