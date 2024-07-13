package com.example.grab_demo.customer.adapter.Home;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grab_demo.R;
import com.example.grab_demo.m_interface.IClickItem;
import com.example.grab_demo.model.Product;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {
    private Context context;
    private List<Product> productList;
    private IClickItem iClickItem;

    public HomeAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    public void setOnItemClick(IClickItem iClickItem) {
        this.iClickItem = iClickItem;
    }

    public void filterList(List<Product> productList) {
        this.productList = productList;
        notifyDataSetChanged();
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
        byte[] img = product.getImage();

        if (img != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
            holder.img_circle.setImageBitmap(bitmap);
        } else {
            Log.e("HomeAdapter", "Items array is null");
        }
        holder.txt_name.setText(product.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iClickItem != null) {
                    iClickItem.onClickItem(productList.get(position).getName());
                }
            }
        });
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
