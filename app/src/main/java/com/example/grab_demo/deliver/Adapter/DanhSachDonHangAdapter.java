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

import com.example.grab_demo.deliver.activity.ChiTietDonHangActivity;
import com.example.grab_demo.model.DonHangModel;
import com.example.grab_demo.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DanhSachDonHangAdapter extends RecyclerView.Adapter<DanhSachDonHangAdapter.ViewHolder> {
    Context context;
    ArrayList<DonHangModel> mangDonHang;

    public DanhSachDonHangAdapter(Context context, ArrayList<DonHangModel> mangDonHang) {
        this.context = context;
        this.mangDonHang = mangDonHang;
    }

    @NonNull
    @Override
    public DanhSachDonHangAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_danh_sach_don_hang_da_giao, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DanhSachDonHangAdapter.ViewHolder holder, int position) {
        DonHangModel donHang = mangDonHang.get(position);
        holder.txtTenDonHang.setText(donHang.getTenDonHang());
        holder.txtMaDonHang.setText(donHang.getMaDonHang());
        Picasso.get().load(donHang.getHinhMonAn()).into(holder.hinhMonAn);
    }

    @Override
    public int getItemCount() {
        return mangDonHang.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenDonHang, txtMaDonHang;
        ImageView hinhMonAn, tim;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenDonHang = itemView.findViewById(R.id.textViewtendonhang);
            txtMaDonHang = itemView.findViewById(R.id.textViewmadonhang);
            hinhMonAn = itemView.findViewById(R.id.imageViewhinhmonan);
            tim = itemView.findViewById(R.id.imageViewtimdanhsachbaihat);

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