package com.example.grab_demo.customer.adapter;

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
import com.example.grab_demo.customer.m_interface.StClickItem;
import com.example.grab_demo.customer.model.Category;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryHomeAdapter extends RecyclerView.Adapter<CategoryHomeAdapter.HomeViewHolder> {
    private Context context;
    private List<Category> categoryList;
    private StClickItem stClickItem;

    public CategoryHomeAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    public void setOnItemClick(StClickItem stClickItem) {
        this.stClickItem = stClickItem;
    }

    public void filterList(List<Category> categoryList) {
        this.categoryList = categoryList;
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
        Category category = categoryList.get(position);
        byte[] img = category.getImage();

        if (img != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
            holder.img_circle.setImageBitmap(bitmap);
        } else {
            Log.e("HomeAdapter", "Items array is null");
        }
        holder.txt_name.setText(category.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stClickItem != null) {
                    stClickItem.onClickItem(String.valueOf(categoryList.get(position).getCateID()));
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
        CircleImageView img_circle;
        TextView txt_name;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);

            img_circle = itemView.findViewById(R.id.img_circle);
            txt_name = itemView.findViewById(R.id.txt_name);
        }
    }
}
