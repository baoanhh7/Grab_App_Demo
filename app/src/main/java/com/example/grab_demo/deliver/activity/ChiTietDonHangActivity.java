package com.example.grab_demo.deliver.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.grab_demo.R;
import com.example.grab_demo.model.DonHangModel;
import com.squareup.picasso.Picasso;

public class ChiTietDonHangActivity extends AppCompatActivity {

    private ImageView imageViewHinhMonAn;
    private TextView textViewTenDonHang;
    private TextView textViewMaDonHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_don_hang);

        // Tham chiếu đến các thành phần trong layout
        imageViewHinhMonAn = findViewById(R.id.imageViewHinhMonAn);
        textViewTenDonHang = findViewById(R.id.textViewTenDonHang);
        textViewMaDonHang = findViewById(R.id.textViewMaDonHang);

        // Nhận dữ liệu đơn hàng từ Intent
        DonHangModel donHang = (DonHangModel) getIntent().getSerializableExtra("donhang");

        if (donHang != null) {
            // Thiết lập dữ liệu cho các thành phần
            textViewTenDonHang.setText(donHang.getTenDonHang());
            textViewMaDonHang.setText(donHang.getMaDonHang());
            Picasso.get().load(donHang.getHinhMonAn()).into(imageViewHinhMonAn);
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
