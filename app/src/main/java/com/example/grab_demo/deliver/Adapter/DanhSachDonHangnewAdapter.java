package com.example.grab_demo.deliver.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grab_demo.R;
import com.example.grab_demo.deliver.activity.ChiTietDonHangNewActivity;
import com.example.grab_demo.model.DonHangModel;

import java.util.ArrayList;

public class DanhSachDonHangnewAdapter extends RecyclerView.Adapter<DanhSachDonHangnewAdapter.ViewHolder> {
    private Context context;
    private ArrayList<DonHangModel> mangDonHang;
    private String userId;
    private String userStatus;
    private boolean isEnabled = true;

    public DanhSachDonHangnewAdapter(Context context, ArrayList<DonHangModel> mangDonHang, String userId, String userStatus) {
        this.context = context;
        this.mangDonHang = mangDonHang;
        this.userId = userId;
        this.userStatus = userStatus;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dong_danh_sach_don_hang_new, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (isEnabled && !"inactive".equalsIgnoreCase(userStatus)) {
            DonHangModel donHang = mangDonHang.get(position);
            holder.txtMaDonHang.setText(String.valueOf(donHang.getOrderId()));
            holder.txtTrangThaiDonHang.setText(donHang.getStatus());

            holder.itemView.setVisibility(View.VISIBLE);
            holder.itemView.setEnabled(true);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ChiTietDonHangNewActivity.class);
                    intent.putExtra("donhang", donHang);
                    intent.putExtra("user_id", userId);
                    context.startActivity(intent);
                }
            });
        } else {
            // Ẩn hoặc vô hiệu hóa item khi adapter bị tắt hoặc khi trạng thái người dùng là "inactive"
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setEnabled(false);
            if ("inactive".equalsIgnoreCase(userStatus)) {
                Toast.makeText(context, "Tài khoản của bạn đang bị vô hiệu hóa", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public int getItemCount() {
        return isEnabled && !"inactive".equalsIgnoreCase(userStatus) ? mangDonHang.size() : 0;
    }

    public void updateData(ArrayList<DonHangModel> newData) {
        this.mangDonHang = newData;
        notifyDataSetChanged();
    }

    public void setEnabledAndUpdate(boolean enabled) {
        this.isEnabled = enabled;
        notifyDataSetChanged();
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTrangThaiDonHang, txtMaDonHang;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaDonHang = itemView.findViewById(R.id.textViewmadonhang);
            txtTrangThaiDonHang = itemView.findViewById(R.id.textViewtrangthaidonhang);
        }
    }
}
