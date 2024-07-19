package com.example.grab_demo.customer.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grab_demo.R;
import com.example.grab_demo.customer.adapter.ListOrderAdapter;
import com.example.grab_demo.customer.model.Item;
import com.example.grab_demo.database.ConnectionClass;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CartActivity extends AppCompatActivity {
    Connection connection, connection2;
    String query, query2;
    Statement smt, smt2;
    ResultSet resultSet, resultSet2;

    RecyclerView rcv_cart;
    List<Item> itemList;
    ListOrderAdapter itemAdapter;

    ImageView img_back;

    int itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        addControls();

        itemId = getIntent().getIntExtra("item_id", -1);  // Lấy cate_id kiểu int với giá trị mặc định là -1
        if (itemId != -1) {
        } else {
            Log.e("CartActivity", "item_id is null");
        }

        loadData();

        addEvents();
    }

    private void loadData() {
        ConnectionClass sql = new ConnectionClass();
        connection = sql.conClass();
        if (connection != null) {
            try {
                query = "SELECT Items.item_id, Items.item_name, Items.price, Items.image FROM CartItems " +
                        "JOIN Items ON CartItems.item_id = Items.item_id " +
                        "WHERE CartItems.cart_id = 1"; // Sử dụng cart_id thật của bạn
                smt = connection.createStatement();
                resultSet = smt.executeQuery(query);

                itemList.clear();
                while (resultSet.next()) {
                    int itemId = resultSet.getInt(1);
                    String itemName = resultSet.getString(2);
                    double price = resultSet.getDouble(3);
                    byte[] image = resultSet.getBytes(4);
                    itemList.add(new Item(itemId, itemName, price, image));
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


    private void addEvents() {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void addControls() {
        img_back = findViewById(R.id.img_back);
        rcv_cart = findViewById(R.id.rcv_cart);
        itemList = new ArrayList<>();
        itemAdapter = new ListOrderAdapter(this, itemList);
        rcv_cart.setAdapter(itemAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcv_cart.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void onResume() {
        loadData();

        super.onResume();
    }
}