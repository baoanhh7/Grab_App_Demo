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
import com.example.grab_demo.admin.sales.adapter.ListStoreRegistrationAdapter;
import com.example.grab_demo.database.ConnectionClass;
import com.example.grab_demo.store_owner.OnItemClickListener;
import com.example.grab_demo.store_owner.model.Stores;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ApproveStoreRegistrationActivity extends AppCompatActivity {
    private static final long REFRESH_INTERVAL = 1000; // 3 giây
    ImageButton img_back_ApproveStoreRegistration;
    RecyclerView rv_ApproveStoreRegistration;
    androidx.appcompat.widget.SearchView searchView_ApproveStoreRegistration;
    ListStoreRegistrationAdapter listStoreRegistrationAdapter;
    ArrayList<Stores> arr;
    Connection connection;
    private Handler handler;
    private Runnable refreshRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_store_registration);
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
        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    private void loadData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ConnectionClass sql = new ConnectionClass();
                connection = sql.conClass();
                if (connection != null) {
                    try {
                        String query = "SELECT store_id, store_name,owner_id, image FROM Stores WHERE status = 'pending' ";
                        Statement smt = connection.createStatement();
                        ResultSet resultSet = smt.executeQuery(query);
                        final ArrayList<Stores> tempArr = new ArrayList<>();
                        while (resultSet.next()) {
                            Integer id = resultSet.getInt(1);
                            String storeName = resultSet.getString(2);
                            Integer ownerId = resultSet.getInt(3);
                            byte[] image = resultSet.getBytes(4);
                            tempArr.add(new Stores(storeName, ownerId, id, image));
                        }
                        connection.close();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                arr.clear();
                                arr.addAll(tempArr);
                                listStoreRegistrationAdapter.notifyDataSetChanged();
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
        img_back_ApproveStoreRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        searchView_ApproveStoreRegistration.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                listStoreRegistrationAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listStoreRegistrationAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }


    private void addControls() {
        img_back_ApproveStoreRegistration = findViewById(R.id.img_back_ApproveStoreRegistration);
        rv_ApproveStoreRegistration = findViewById(R.id.rv_ApproveStoreRegistration);
        searchView_ApproveStoreRegistration = findViewById(R.id.searchView_ApproveStoreRegistration);
        arr = new ArrayList<>();
        listStoreRegistrationAdapter = new ListStoreRegistrationAdapter(this, arr);
        rv_ApproveStoreRegistration.setAdapter(listStoreRegistrationAdapter);
        rv_ApproveStoreRegistration.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        listStoreRegistrationAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(String data) {

            }

            @Override
            public void onItemClickIStoreRegistration(int data, String name) {
                Intent intent = new Intent(ApproveStoreRegistrationActivity.this, StoreRegistrationActivity.class);
                intent.putExtra("store_id", data);
                intent.putExtra("owner_name", name);
                startActivity(intent);
                finish();
            }

            @Override
            public void onItemClickMessage(int sender_id, int reciever_id) {

            }

            @Override
            public void onItemClickID(int data) {

            }
        });
    }
}