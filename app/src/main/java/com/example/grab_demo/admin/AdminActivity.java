package com.example.grab_demo.admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grab_demo.R;
import com.example.grab_demo.admin.sales.ListCateSalesActivity;

public class AdminActivity extends AppCompatActivity {
    String userType = "";
    FrameLayout FL_sales, FL_IT;
    ImageButton img_back_Admin;
    Button btn_edit_profile_storeowner,btn_voucher_sales,btn_cate_sales,btn_duyetdondk_sales,btn_quanlycuahang_sales,btn_quanlytkHSO_sales,
            btn_logout_sales,btn_createaccountNVKD_IT,btn_quanlytkNVGH_IT,btn_logout_IT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        // Nhận user_id từ intent
        userType = getIntent().getStringExtra("user_type");
        Log.d("StoreOwnerActivity", "Received user_id: " + userType);
        addControls();
        addEvents();
    }

    private void addEvents() {
        if (userType.equals("it")) {
            FL_IT.setVisibility(View.VISIBLE);
            FL_sales.setVisibility(View.GONE);
        } else {
            FL_sales.setVisibility(View.VISIBLE);
            FL_IT.setVisibility(View.GONE);

        }
        img_back_Admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_cate_sales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, ListCateSalesActivity.class));
            }
        });
    }

    private void addControls() {
        img_back_Admin = findViewById(R.id.img_back_Admin);
        FL_sales = findViewById(R.id.FL_sales);
        FL_IT = findViewById(R.id.FL_IT);
        btn_edit_profile_storeowner = findViewById(R.id.btn_edit_profile_storeowner);
        btn_voucher_sales = findViewById(R.id.btn_voucher_sales);
        btn_cate_sales = findViewById(R.id.btn_cate_sales);
        btn_duyetdondk_sales = findViewById(R.id.btn_duyetdondk_sales);
        btn_quanlycuahang_sales = findViewById(R.id.btn_quanlycuahang_sales);
        btn_quanlytkHSO_sales = findViewById(R.id.btn_quanlytkHSO_sales);
        btn_logout_sales = findViewById(R.id.btn_logout_sales);
        btn_createaccountNVKD_IT = findViewById(R.id.btn_createaccountNVKD_IT);
        btn_quanlytkNVGH_IT = findViewById(R.id.btn_quanlytkNVGH_IT);
        btn_logout_IT = findViewById(R.id.btn_logout_IT);
    }
}