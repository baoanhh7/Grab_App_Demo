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
import com.example.grab_demo.deliver.activity.ChiTietDonHangNewActivity;
import com.example.grab_demo.model.DonHangModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DanhSachDonHangnewAdapter extends RecyclerView.Adapter<DanhSachDonHangnewAdapter.ViewHolder> {
    Context context;
    ArrayList<DonHangModel> mangDonHang;

    public DanhSachDonHangnewAdapter(Context context, ArrayList<DonHangModel> mangDonHang) {
        this.context = context;
        this.mangDonHang = mangDonHang;
    }

    @NonNull
    @Override
    public DanhSachDonHangnewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_danh_sach_don_hang_new, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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
        TextView txtTenDonHang, txtMaDonHang,txtDiaChi;
        ImageView hinhMonAn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenDonHang = itemView.findViewById(R.id.textViewtendonhang);
            txtMaDonHang = itemView.findViewById(R.id.textViewmadonhang);
            hinhMonAn = itemView.findViewById(R.id.imageViewhinhmonan);
            txtDiaChi = itemView.findViewById(R.id.textViewDiaChidonhang);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ChiTietDonHangNewActivity.class);
                    intent.putExtra("donhang", mangDonHang.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
