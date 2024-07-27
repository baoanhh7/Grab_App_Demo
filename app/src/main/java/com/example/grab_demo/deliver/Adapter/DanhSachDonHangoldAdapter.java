package com.example.grab_demo.deliver.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grab_demo.R;
import com.example.grab_demo.deliver.activity.ChiTietDonHangActivity;
import com.example.grab_demo.model.DonHangModel;

import java.util.ArrayList;

public class DanhSachDonHangoldAdapter extends RecyclerView.Adapter<DanhSachDonHangoldAdapter.ViewHolder> {
    private Context context;
    private ArrayList<DonHangModel> mangDonHang;


    public DanhSachDonHangoldAdapter(Context context, ArrayList<DonHangModel> mangDonHang) {
        this.context = context;
        this.mangDonHang = mangDonHang;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_danh_sach_don_hang_da_giao, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DonHangModel donHang = mangDonHang.get(position);
        holder.txtMaDonHang.setText(donHang.getOrderId() + ""); // Hiển thị mã đơn hàng
        holder.txtTrangThaiDonHang.setText(donHang.getStatus()); // Hiển thị trạng thái đơn hàng
    }

    @Override
    public int getItemCount() {
        return mangDonHang.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMaDonHang, txtTrangThaiDonHang;
        ImageView hinhMonAn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaDonHang = itemView.findViewById(R.id.textViewmadonhang);
            txtTrangThaiDonHang = itemView.findViewById(R.id.textViewtrangthaidonhang);
            hinhMonAn = itemView.findViewById(R.id.imageViewhinhmonan);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ChiTietDonHangActivity.class);
                    intent.putExtra("donhang", mangDonHang.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
