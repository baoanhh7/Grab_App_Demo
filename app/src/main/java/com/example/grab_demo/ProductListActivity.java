package com.example.grab_demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grab_demo.adapter.Home.OrderAgainAdapter;
import com.example.grab_demo.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {
    RecyclerView rcv_productList;
    List<Product> productList;
    OrderAgainAdapter orderAgainAdapter;
    ImageButton btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        addControls();
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
        orderAgainAdapter.setOnClickItemListener(new IClickItem() {
            @Override
            public void onClickItem(String data) {
                Intent intent = new Intent(ProductListActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });
    }

    private void createData() {
        productList.clear();
        productList.add(new Product("Cơm tấm Phúc Lộc Thọ", "Rice, pork ribs, egg, tomato.", R.drawable.qc, 35000));
        productList.add(new Product("Cơm tấm Phúc Lộc Thọ", "Rice, pork ribs, egg, tomato.", R.drawable.qc, 40000));
        productList.add(new Product("Cơm tấm Phúc Lộc Thọ", "Rice, pork ribs, egg, tomato.", R.drawable.qc, 65000));
        productList.add(new Product("Cơm tấm Phúc Lộc Thọ", "Rice, pork ribs, egg, tomato.", R.drawable.qc, 99000));
        orderAgainAdapter.notifyDataSetChanged();
    }

    private void addControls() {
        btn_back = findViewById(R.id.btn_back);

        rcv_productList = findViewById(R.id.rcv_productList);
        productList = new ArrayList<>();
        orderAgainAdapter = new OrderAgainAdapter(this, productList);

        rcv_productList.setAdapter(orderAgainAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcv_productList.setLayoutManager(linearLayoutManager);
    }
}