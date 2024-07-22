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
import com.example.grab_demo.admin.sales.adapter.QLStoreAdapter;
import com.example.grab_demo.database.ConnectionClass;
import com.example.grab_demo.store_owner.model.Stores;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class QLStoreActivity extends AppCompatActivity {
    private static final long REFRESH_INTERVAL = 1000;
    ImageButton img_back_QLStore;
    androidx.appcompat.widget.SearchView searchView_QLStore;
    RecyclerView rv_QLStore;
    QLStoreAdapter qlStoreAdapter;
    ArrayList<Stores> arr;
    Connection connection;
    private Handler handler;
    private Runnable refreshRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlstore);
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

    private void loadData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ConnectionClass sql = new ConnectionClass();
                connection = sql.conClass();
                if (connection != null) {
                    try {
                        String query = "SELECT store_id,store_name, owner_id, updated_at ,image FROM Stores WHERE status = 'closed' ";
                        Statement smt = connection.createStatement();
                        ResultSet resultSet = smt.executeQuery(query);
                        final ArrayList<Stores> tempArr = new ArrayList<>();
                        while (resultSet.next()) {
                            Integer id = resultSet.getInt(1);
                            String storeName = resultSet.getString(2);
                            int owner_id = resultSet.getInt(3);
                            Date updated_at = resultSet.getDate(4);
                            byte[] image = resultSet.getBytes(5);
                            tempArr.add(new Stores(image, id, owner_id, storeName, (java.sql.Date) updated_at));
                        }
                        connection.close();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                arr.clear();
                                arr.addAll(tempArr);
                                qlStoreAdapter.notifyDataSetChanged();
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

    @Override
    protected void onResume() {
        super.onResume();
        startAutoRefresh();
    }

    private void startAutoRefresh() {
        handler.postDelayed(refreshRunnable, REFRESH_INTERVAL);
    }

    private void addEvents() {
        img_back_QLStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void addControls() {
        img_back_QLStore = findViewById(R.id.img_back_QLStore);
        searchView_QLStore = findViewById(R.id.searchView_QLStore);
        rv_QLStore = findViewById(R.id.rv_QLStore);
        arr = new ArrayList<>();
        qlStoreAdapter = new QLStoreAdapter(this, arr);
        rv_QLStore.setAdapter(qlStoreAdapter);
        rv_QLStore.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }
}