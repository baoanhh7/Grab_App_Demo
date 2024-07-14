package com.example.grab_demo.store_owner.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grab_demo.R;
import com.example.grab_demo.store_owner.OnItemClickListener;
import com.example.grab_demo.store_owner.model.DishMenuHSO;

import java.sql.Connection;
import java.util.ArrayList;

public class rcv_listitemsold_monthAdapter extends RecyclerView.Adapter<rcv_listitemsold_monthAdapter.ViewHolder> {

    Context context;
    ArrayList<DishMenuHSO> arr;
    boolean flag = false;
    Connection connection;
    String status;
    private OnItemClickListener onItemClickListener;

    public rcv_listitemsold_monthAdapter(Context context, ArrayList<DishMenuHSO> arr) {
        this.context = context;
        this.arr = arr;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_rcv_listitemsold_month, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DishMenuHSO dishMenuHSO = arr.get(position);
        byte[] hinhAlbumByteArray = dishMenuHSO.getHinh();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAlbumByteArray, 0, hinhAlbumByteArray.length);
        holder.img.setImageBitmap(bitmap);
        holder.txtTen.setText(dishMenuHSO.getTensp());
        holder.txtGia.setText(dishMenuHSO.getGiasp() + "");
        double totalAmount = dishMenuHSO.getGiasp() * dishMenuHSO.getSoluong();
        holder.txtTotalAmountMonth.setText(totalAmount + "đ");
        holder.txtSL.setText(dishMenuHSO.getSoluong() + "");
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
        ImageView img;
        TextView txtTen;
        TextView txtGia;
        TextView txtTotalAmountMonth;
        TextView txtSL;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.ImageView_item_rcv_listitemsold_month);
            txtTen = itemView.findViewById(R.id.tv_name_item_rcv_listitemsold_month);
            txtGia = itemView.findViewById(R.id.tv_price_item_rcv_listitemsold_month);
            txtTotalAmountMonth = itemView.findViewById(R.id.tv_price_total_item_rcv_listitemsold_month);
            txtSL = itemView.findViewById(R.id.tv_quantity_item_rcv_listitemsold_month);
        }
    }
}
