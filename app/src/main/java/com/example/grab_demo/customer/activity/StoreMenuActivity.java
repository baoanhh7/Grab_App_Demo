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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grab_demo.R;
import com.example.grab_demo.customer.adapter.MenuItemAdapter;
import com.example.grab_demo.customer.m_interface.StClickItem;
import com.example.grab_demo.customer.model.Item;
import com.example.grab_demo.database.ConnectionClass;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StoreMenuActivity extends AppCompatActivity {
    Connection connection, connection2;
    String query, query2;
    Statement smt, smt2;
    ResultSet resultSet, resultSet2;

    RecyclerView rcv_store_menu;
    List<Item> itemList;
    MenuItemAdapter itemAdapter;
    ImageButton btn_back;
    ImageView img_bg;
    TextView txt_store_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        addControls();

        int storeId = getIntent().getIntExtra("store_id", -1);  // Lấy cate_id kiểu int với giá trị mặc định là -1
        if (storeId != -1) {
            loadData(storeId);
            loadStoreData(storeId);
        } else {
            Log.e("ProductListActivity", "cate_id is null");
        }

        createData();

        addEvents();
    }

    private void addEvents() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        itemAdapter.setOnClickItemListener(new StClickItem() {
            @Override
            public void onClickItem(String data) {
                Intent intent = new Intent(StoreMenuActivity.this, OrderActivity.class);
                intent.putExtra("item_id", Integer.parseInt(data));
                startActivity(intent);
            }
        });
    }

    private void loadData(int storeId) {
        ConnectionClass sql = new ConnectionClass();
        connection = sql.conClass();
        if (connection != null) {
            try {
                query = "SELECT item_id, item_name, price, image FROM Items " +
                        "WHERE store_id = " + storeId;
                smt = connection.createStatement();
                resultSet = smt.executeQuery(query);

                itemList.clear();
                while (resultSet.next()) {
                    int itemId = resultSet.getInt(1);
                    String storeName = resultSet.getString(2);
                    double price = resultSet.getDouble(3);
                    byte[] image = resultSet.getBytes(4);
                    itemList.add(new Item(itemId, storeName, price, image));
                }
                itemAdapter.notifyDataSetChanged();
                connection.close();
            } catch (Exception e) {
                Log.e("Error: ", Objects.requireNonNull(e.getMessage()));
            }
        } else {
            Log.e("Error: ", "Connection null");
        }

    }

    private void loadStoreData(int storeId) {
        ConnectionClass sql1 = new ConnectionClass();
        connection2 = sql1.conClass();
        if (connection2 != null) {
            try {
                query2 = "SELECT store_name, image FROM Stores WHERE store_id = " + storeId;
                smt2 = connection2.createStatement();
                resultSet2 = smt2.executeQuery(query2);

                if (resultSet2.next()) {
                    String name = resultSet2.getString(1);
                    byte[] image = resultSet2.getBytes(2);

                    txt_store_name.setText(name);
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

    private void createData() {
//        categoryList.clear();
//        categoryList.add(new Category("Cơm Sườn Phúc Lộc Thọ & 1 Món", R.drawable.comsuon));
//        categoryList.add(new Category("Cơm Sườn Phúc Lộc Thọ & 2 Món", R.drawable.comsuon));
//        categoryList.add(new Category("Cơm Sườn Phúc Lộc Thọ & 3 Món", R.drawable.comsuon));
//        categoryList.add(new Category("Cơm Sườn Phúc Lộc Thọ & 4 Món", R.drawable.comsuon));
//        categoryList.add(new Category("Cơm Sườn Phúc Lộc Thọ & 5 Món", R.drawable.comsuon));
//        categoryList.add(new Category("Cơm Sườn Phúc Lộc Thọ & 6 Món", R.drawable.comsuon));
//        homeVoucherAdapter.notifyDataSetChanged();
    }

    private void addControls() {
        btn_back = findViewById(R.id.btn_back);
        img_bg = findViewById(R.id.img_bg);
        txt_store_name = findViewById(R.id.txt_store_name);
        rcv_store_menu = findViewById(R.id.rcv_store_menu);

        itemList = new ArrayList<>();
        itemAdapter = new MenuItemAdapter(this, itemList);
        rcv_store_menu.setAdapter(itemAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rcv_store_menu.setLayoutManager(gridLayoutManager);
    }
}