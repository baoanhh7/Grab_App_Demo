package com.example.grab_demo.admin.sales.activity;

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
import com.example.grab_demo.admin.sales.adapter.QLTaiKhoanAdapter;
import com.example.grab_demo.database.ConnectionClass;
import com.example.grab_demo.store_owner.model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class QLTaiKhoanActivity extends AppCompatActivity {
    private static final long REFRESH_INTERVAL = 1000;
    RecyclerView rv_QLTaiKhoan;
    ImageButton img_back_QLTaiKhoan;
    ArrayList<User> arr;
    QLTaiKhoanAdapter qlTaiKhoanAdapter;
    Connection connection;
    private Handler handler;
    private Runnable refreshRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qltai_khoan);
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
                        String query = "SELECT user_id,username, status, VP  FROM Users ";
                        Statement smt = connection.createStatement();
                        ResultSet resultSet = smt.executeQuery(query);
                        final ArrayList<User> tempArr = new ArrayList<>();
                        while (resultSet.next()) {
                            Integer id = resultSet.getInt(1);
                            String username = resultSet.getString(2);
                            String status = resultSet.getString(3);
                            int VP = resultSet.getInt(4);
                            tempArr.add(new User(id, username, status, VP));
                        }
                        connection.close();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                arr.clear();
                                arr.addAll(tempArr);
                                qlTaiKhoanAdapter.notifyDataSetChanged();
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
        img_back_QLTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void addControls() {
        rv_QLTaiKhoan = findViewById(R.id.rv_QLTaiKhoan);
        img_back_QLTaiKhoan = findViewById(R.id.img_back_QLTaiKhoan);
        arr = new ArrayList<>();
        qlTaiKhoanAdapter = new QLTaiKhoanAdapter(this, arr);
        rv_QLTaiKhoan.setAdapter(qlTaiKhoanAdapter);
        rv_QLTaiKhoan.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }
}