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
import com.example.grab_demo.customer.model.Voucher;

import java.text.SimpleDateFormat;
import java.util.List;

public class C_VoucherAdapter extends RecyclerView.Adapter<C_VoucherAdapter.HomeViewHolder> {
    Context context;
    List<Voucher> storeList;
    private StClickItem stClickItem;

    public C_VoucherAdapter(Context context, List<Voucher> storeList) {
        this.context = context;
        this.storeList = storeList;
    }

    public void setOnClickItemListener(StClickItem stClickItem) {
        this.stClickItem = stClickItem;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_voucher, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        Voucher voucher = storeList.get(position);

        holder.txt_name_voucher.setText(voucher.getVoucherName());
        holder.txt_discount_voucher.setText(String.valueOf(voucher.getDiscount()));
        holder.txt_quantity_voucher.setText(String.valueOf(voucher.getQuantity()));

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        holder.txt_start_date.setText(dateFormat.format(voucher.getStartDate()));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stClickItem != null) {
                    stClickItem.onClickItem(String.valueOf(storeList.get(position).getVoucherId()));
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
        TextView txt_name_voucher, txt_discount_voucher, txt_start_date, txt_quantity_voucher;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_name_voucher = itemView.findViewById(R.id.txt_name_voucher);
            txt_discount_voucher = itemView.findViewById(R.id.txt_discount_voucher);
            txt_start_date = itemView.findViewById(R.id.txt_start_date);
            txt_quantity_voucher = itemView.findViewById(R.id.txt_quantity_voucher);
        }
    }
}
