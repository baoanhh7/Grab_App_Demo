package com.example.grab_demo.admin.sales.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grab_demo.R;
import com.example.grab_demo.admin.sales.adapter.VoucherAdapter;
import com.example.grab_demo.database.ConnectionClass;
import com.example.grab_demo.store_owner.model.Vouchers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class ListVoucherSalesActivity extends AppCompatActivity {
    private static final long REFRESH_INTERVAL = 1000; // 3 giây
    ImageButton img_back_ListVoucherSales, btn_add_ListVoucherSales;
    androidx.appcompat.widget.SearchView searchView_ListVoucherSales;
    RecyclerView rv_ListVoucherSales;
    VoucherAdapter voucherAdapter;
    ArrayList<Vouchers> arr;
    Connection connection;
    private Handler handler;
    private Runnable refreshRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_voucher_sales);
        addControls();
        addEvents();
        handler = new Handler(Looper.getMainLooper());
        refreshRunnable = new Runnable() {
            @Override
            public void run() {
                loadData();
                handler.postDelayed(this, REFRESH_INTERVAL);
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        startAutoRefresh();
    }

    private void startAutoRefresh() {
        handler.postDelayed(refreshRunnable, REFRESH_INTERVAL);
    }

    private void loadData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ConnectionClass sql = new ConnectionClass();
                connection = sql.conClass();
                if (connection != null) {
                    try {
                        String query = "SELECT voucher_id,voucher_name, condition,discount,start_date,end_date,quantity FROM Vouchers ";
                        Statement smt = connection.createStatement();
                        ResultSet resultSet = smt.executeQuery(query);
                        final ArrayList<Vouchers> tempArr = new ArrayList<>();
                        while (resultSet.next()) {
                            Integer id = resultSet.getInt(1);
                            String voucherName = resultSet.getString(2);
                            int condition = resultSet.getInt(3);
                            float discount = resultSet.getFloat(4);
                            Date start_date = resultSet.getDate(5);
                            Date end_date = resultSet.getDate(6);
                            int quantity = resultSet.getInt(7);
                            tempArr.add(new Vouchers(id, voucherName, condition, discount, start_date, end_date, quantity));
                        }
                        connection.close();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                arr.clear();
                                arr.addAll(tempArr);
                                voucherAdapter.notifyDataSetChanged();
                            }
                        });
                    } catch (Exception e) {
                        Log.e("Error: ", e.getMessage());
                    }
                } else {
                    Log.e("Error: ", "Connection null");
                }
            }
        }).start();
    }

    private void addEvents() {
        img_back_ListVoucherSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_add_ListVoucherSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListVoucherSalesActivity.this, AddVoucherSalesActivity.class));
                finish();
            }
        });
    }

    private void addControls() {
        img_back_ListVoucherSales = findViewById(R.id.img_back_ListVoucherSales);
        btn_add_ListVoucherSales = findViewById(R.id.btn_add_ListVoucherSales);
        searchView_ListVoucherSales = findViewById(R.id.searchView_ListVoucherSales);
        rv_ListVoucherSales = findViewById(R.id.rv_ListVoucherSales);
        arr = new ArrayList<>();
        voucherAdapter = new VoucherAdapter(this, arr);
        rv_ListVoucherSales.setAdapter(voucherAdapter);
        rv_ListVoucherSales.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }
}