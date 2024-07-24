package com.example.grab_demo.admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grab_demo.R;
import com.example.grab_demo.admin.sales.activity.ApproveStoreRegistrationActivity;
import com.example.grab_demo.admin.sales.activity.ListCateSalesActivity;
import com.example.grab_demo.admin.sales.activity.ListVoucherSalesActivity;
import com.example.grab_demo.admin.sales.activity.QLStoreActivity;
import com.example.grab_demo.admin.sales.activity.QLTaiKhoanActivity;
import com.example.grab_demo.login.LoginActivity;

public class AdminActivity extends AppCompatActivity {
    String userType = "";
    FrameLayout FL_sales, FL_IT;
    Button btn_voucher_sales, btn_cate_sales, btn_duyetdondk_sales, btn_quanlycuahang_sales, btn_quanlytkHSO_sales,
            btn_logout_sales, btn_createaccountNVKD_IT, btn_quanlytkNVGH_IT, btn_logout_IT;

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
        btn_logout_IT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_logout_sales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, LoginActivity.class));
                finish();
            }
        });
        btn_cate_sales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, ListCateSalesActivity.class));
            }
        });
        btn_duyetdondk_sales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, ApproveStoreRegistrationActivity.class));
            }
        });
        btn_voucher_sales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, ListVoucherSalesActivity.class));
            }
        });
        btn_quanlycuahang_sales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, QLStoreActivity.class));
            }
        });
        btn_quanlytkHSO_sales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, QLTaiKhoanActivity.class));
            }
        });
    }

    private void addControls() {
        FL_sales = findViewById(R.id.FL_sales);
        FL_IT = findViewById(R.id.FL_IT);
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