package com.example.grab_demo.customer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grab_demo.R;
import com.example.grab_demo.customer.m_interface.StClickItem;
import com.example.grab_demo.customer.model.Category;

import java.util.List;

public class HomeVoucherAdapter extends RecyclerView.Adapter<HomeVoucherAdapter.HomeViewHolder> {
    Context context;
    List<Category> categoryList;
    private StClickItem stClickItem;

    public HomeVoucherAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    public void setOnClickItemListener(StClickItem stClickItem) {
        this.stClickItem = stClickItem;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_voucher_home, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        Category category = categoryList.get(position);

        holder.txt_name.setText(category.getName());
        holder.txt_describe.setText(category.getDescribe());
        holder.img.setImageResource(category.getImg());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stClickItem != null) {
                    stClickItem.onClickItem(categoryList.get(position).getName());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (categoryList != null) return categoryList.size();
        return 0;
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {
        TextView txt_name, txt_describe;
        ImageView img;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_name = itemView.findViewById(R.id.txt_name);
            txt_describe = itemView.findViewById(R.id.txt_describe);
            img = itemView.findViewById(R.id.img);
        }
    }
}
