package com.example.grab_demo.adapter.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grab_demo.R;
import com.example.grab_demo.model.Product;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {
    private Context context;
    private List<Product> productList;

    public HomeAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_head_home, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.txt_name.setText(product.getName());
        holder.img_circle.setImageResource(product.getImg());
    }

    @Override
    public int getItemCount() {
        if (productList != null) return productList.size();
        return 0;
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {
        CircleImageView img_circle;
        TextView txt_name;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);

            img_circle = itemView.findViewById(R.id.img_circle);
            txt_name = itemView.findViewById(R.id.txt_name);
        }
    }
}
