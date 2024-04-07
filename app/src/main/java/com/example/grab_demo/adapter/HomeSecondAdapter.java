package com.example.grab_demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grab_demo.R;
import com.example.grab_demo.model.Product;

import java.util.List;

public class HomeSecondAdapter extends RecyclerView.Adapter<HomeSecondAdapter.HomeViewHolder> {
    Context context;
    List<Product> productList;

    public HomeSecondAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_second_home, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.txt_name.setText(product.getName());
        holder.txt_describe.setText(product.getDescribe());
        holder.img_bg.setImageResource(product.getImg());
    }

    @Override
    public int getItemCount() {
        if (productList != null) return productList.size();
        return 0;
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {
        TextView txt_name, txt_describe;
        ImageView img_bg;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_name = itemView.findViewById(R.id.txt_name);
            txt_describe = itemView.findViewById(R.id.txt_describe);
            img_bg = itemView.findViewById(R.id.img_bg);
        }
    }
}
