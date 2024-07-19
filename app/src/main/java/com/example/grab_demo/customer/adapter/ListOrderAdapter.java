package com.example.grab_demo.customer.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grab_demo.R;
import com.example.grab_demo.customer.m_interface.StClickItem;
import com.example.grab_demo.customer.model.Item;

import java.util.List;

public class ListOrderAdapter extends RecyclerView.Adapter<ListOrderAdapter.HomeViewHolder> {
    Context context;
    List<Item> itemList;
    private StClickItem stClickItem;

    public ListOrderAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    public void setOnClickItemListener(StClickItem stClickItem) {
        this.stClickItem = stClickItem;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_order, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        Item item = itemList.get(position);

        holder.txt_name.setText(item.getItemName());
        holder.txt_price.setText(String.valueOf(item.getPrice()));
        byte[] img = item.getImage();

        if (img != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
            holder.img.setImageBitmap(bitmap);
        } else {
            Log.e("ListOrderAdapter", "Items array is null");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stClickItem != null) {
                    stClickItem.onClickItem(String.valueOf(item.getItemId()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (itemList != null) return itemList.size();
        return 0;
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {
        TextView txt_name, txt_price, txt_count;
        ImageView img, img_remove, img_add, img_delete;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_name = itemView.findViewById(R.id.txt_name);
            txt_price = itemView.findViewById(R.id.txt_price);
            txt_count = itemView.findViewById(R.id.txt_count);

            img = itemView.findViewById(R.id.img);
            img_remove = itemView.findViewById(R.id.img_remove);
            img_add = itemView.findViewById(R.id.img_add);
            img_delete = itemView.findViewById(R.id.img_delete);
        }
    }
}
