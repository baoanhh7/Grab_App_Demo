package com.example.grab_demo.customer.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grab_demo.R;
import com.example.grab_demo.customer.m_interface.StClickItem;
import com.example.grab_demo.customer.model.Store;

import java.util.List;

public class StoreListAdapter extends RecyclerView.Adapter<StoreListAdapter.HomeViewHolder> {
    Context context;
    List<Store> storeList;
    private StClickItem stClickItem;

    public StoreListAdapter(Context context, List<Store> storeList) {
        this.context = context;
        this.storeList = storeList;
    }

    public void setOnClickItemListener(StClickItem stClickItem) {
        this.stClickItem = stClickItem;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_store_list, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        Store store = storeList.get(position);
        byte[] img = store.getImage();

        holder.txt_name.setText(store.getStoreName());
        holder.txt_status.setText(store.getStatus());
        holder.txt_address.setText(store.getAddress());

        if (img != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
            holder.img.setImageBitmap(bitmap);
        } else {
            Log.e("HomeAdapter", "Items array is null");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stClickItem != null) {
                    String storeStatus = store.getStatus();
                    if (storeStatus.equals("opened")) {
                        stClickItem.onClickItem(String.valueOf(storeList.get(position).getStoreId()));
                    } else {
                        showStoreClosedDialog();
                    }
                }
            }
        });

        if (store.getStatus().equals("closed")) {
            holder.txt_status.setTextColor(context.getResources().getColor(R.color.red));
        } else if (store.getStatus().equals("pending")) {
            holder.txt_status.setTextColor(context.getResources().getColor(R.color.xamnhe));
        }
    }

    private void showStoreClosedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Cửa hàng đã đóng cửa. Vui lòng chọn cửa hàng khác.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    public int getItemCount() {
        if (storeList != null) return storeList.size();
        return 0;
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txt_name, txt_status, txt_address;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_status = itemView.findViewById(R.id.txt_status);
            txt_address = itemView.findViewById(R.id.txt_address);
        }
    }
}
