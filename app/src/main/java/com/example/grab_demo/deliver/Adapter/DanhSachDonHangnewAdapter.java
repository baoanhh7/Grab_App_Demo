package com.example.grab_demo.deliver.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    public DanhSachDonHangnewAdapter(Context context, ArrayList<DonHangModel> mangDonHang, String userId) {
        this.context = context;
        this.mangDonHang = mangDonHang;
        this.userId=userId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dong_danh_sach_don_hang_new, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DonHangModel donHang = mangDonHang.get(position);
        holder.txtMaDonHang.setText(String.valueOf(donHang.getOrderId())); // Hiển thị mã đơn hàng
        holder.txtTrangThaiDonHang.setText(donHang.getStatus()); // Hiển thị trạng thái đơn hàng

        // Xử lý sự kiện click vào một đơn hàng
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChiTietDonHangNewActivity.class);
                intent.putExtra("donhang", donHang);
                intent.putExtra("user_id", userId); // Truyền userId qua Intent
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mangDonHang.size();
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
