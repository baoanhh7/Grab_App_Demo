package com.example.grab_demo.customer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grab_demo.R;
import com.example.grab_demo.customer.adapter.C_VoucherAdapter;
import com.example.grab_demo.customer.m_interface.StClickItem;
import com.example.grab_demo.customer.model.Voucher;
import com.example.grab_demo.database.ConnectionClass;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class VoucherActivity extends AppCompatActivity {
    Connection connection;
    String query;
    Statement smt;
    ResultSet resultSet;

    ImageView btn_back;
    RecyclerView rcv_voucher;
    List<Voucher> voucherList;
    C_VoucherAdapter voucherAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher);

        addControls();

        loadData();

        addEvents();
    }

    private void loadData() {
        ConnectionClass sql = new ConnectionClass();
        connection = sql.conClass();
        if (connection != null) {
            try {
                query = "SELECT voucher_id, voucher_name, discount, start_date, quantity FROM Vouchers";
                smt = connection.createStatement();
                resultSet = smt.executeQuery(query);

                voucherList.clear();
                while (resultSet.next()) {
                    int voucherId = resultSet.getInt(1);
                    String voucherName = resultSet.getString(2);
                    double discount = resultSet.getDouble(3);
                    Date startDate = resultSet.getDate(4);
                    int quantity = resultSet.getInt(5);

                    voucherList.add(new Voucher(voucherId, voucherName, discount, startDate, quantity));
                }
                voucherAdapter.notifyDataSetChanged();
                connection.close();

            } catch (Exception e) {
                Log.e("Error: ", Objects.requireNonNull(e.getMessage()));
            }
        } else {
            Log.e("Error: ", "Connection null");
        }
    }

    private void addEvents() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        voucherAdapter.setOnClickItemListener(new StClickItem() {
            @Override
            public void onClickItem(String data) {
                Intent intent = new Intent(VoucherActivity.this, CartActivity.class);
                intent.putExtra("voucher_id", Integer.parseInt(data));
                startActivity(intent);
                finish();
            }
        });
    }

    private void addControls() {
        btn_back = findViewById(R.id.img_back);
        rcv_voucher = findViewById(R.id.rcv_voucher);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcv_voucher.setLayoutManager(linearLayoutManager);  // Set LayoutManager cho RecyclerView

        voucherList = new ArrayList<>();
        voucherAdapter = new C_VoucherAdapter(this, voucherList);
        rcv_voucher.setAdapter(voucherAdapter);
    }
}