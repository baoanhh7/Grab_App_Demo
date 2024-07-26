package com.example.grab_demo.customer.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grab_demo.R;
import com.example.grab_demo.customer.adapter.StoreListAdapter;
import com.example.grab_demo.customer.m_interface.StClickItem;
import com.example.grab_demo.customer.model.Store;
import com.example.grab_demo.database.ConnectionClass;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StoreListActivity extends AppCompatActivity {
    Connection connection, connection2;
    String query, query2;
    Statement smt, smt2;
    ResultSet resultSet, resultSet2;

    RecyclerView rcv_productList;
    List<Store> storeList;
    StoreListAdapter storeListAdapter;
    ImageButton btn_back;
    TextView txt_title;
    ImageView img_bg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        addControls();

        int cateId = getIntent().getIntExtra("cate_id", -1);  // Lấy cate_id kiểu int với giá trị mặc định là -1
        if (cateId != -1) {
            loadData(cateId);
            loadCateData(cateId);  // Load category data
        } else {
            Log.e("ProductListActivity", "cate_id is null");
        }

        addEvents();
    }

    private void addEvents() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        storeListAdapter.setOnClickItemListener(new StClickItem() {
            @Override
            public void onClickItem(String data) {
                Intent intent = new Intent(StoreListActivity.this, StoreMenuActivity.class);
                intent.putExtra("store_id", Integer.parseInt(data));  // Truyền storeId
                startActivity(intent);
            }
        });
    }

    private void loadData(int cateId) {
        ConnectionClass sql = new ConnectionClass();
        connection = sql.conClass();
        if (connection != null) {
            try {
                query = "SELECT store_id, store_name, owner_id, status, cate_id, open_store, image, address FROM Stores " +
                        "WHERE cate_id = " + cateId;
                smt = connection.createStatement();
                resultSet = smt.executeQuery(query);

                storeList.clear();
                while (resultSet.next()) {
                    int storeId = resultSet.getInt(1);
                    String storeName = resultSet.getString(2);
                    int ownerId = resultSet.getInt(3);
                    String status = resultSet.getString(4);
                    String openStore = resultSet.getString(6);
                    byte[] image = resultSet.getBytes(7);
                    String address = resultSet.getString(8);
                    storeList.add(new Store(storeId, storeName, cateId, image, status, address));
                }
                storeListAdapter.notifyDataSetChanged();
                connection.close();
            } catch (Exception e) {
                Log.e("Error: ", Objects.requireNonNull(e.getMessage()));
            }
        } else {
            Log.e("Error: ", "Connection null");
        }

    }

    private void loadCateData(int cateId) {
        ConnectionClass sql1 = new ConnectionClass();
        connection2 = sql1.conClass();
        if (connection2 != null) {
            try {
                query2 = "SELECT cate_name, cate_image FROM Categories WHERE cate_id = " + cateId;
                smt2 = connection2.createStatement();
                resultSet2 = smt2.executeQuery(query2);

                if (resultSet2.next()) {
                    String name = resultSet2.getString(1);
                    byte[] image = resultSet2.getBytes(2);

                    txt_title.setText(name);
                    if (image != null) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                        img_bg.setImageBitmap(bitmap);
                    }
                }

                connection2.close();
            } catch (Exception e) {
                Log.e("Error: ", Objects.requireNonNull(e.getMessage()));
            }
        } else {
            Log.e("Error: ", "Connection null");
        }
    }

    private void addControls() {
        btn_back = findViewById(R.id.btn_back);
        txt_title = findViewById(R.id.txt_title);
        img_bg = findViewById(R.id.img_bg);

        rcv_productList = findViewById(R.id.rcv_productList);
        storeList = new ArrayList<>();
        storeListAdapter = new StoreListAdapter(this, storeList);

        rcv_productList.setAdapter(storeListAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcv_productList.setLayoutManager(linearLayoutManager);
    }
}