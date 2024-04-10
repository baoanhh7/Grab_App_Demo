package com.example.grab_demo;

import android.os.Bundle;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        addControls();
        createData();
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
        rcv_productList = findViewById(R.id.rcv_productList);
        productList = new ArrayList<>();
        orderAgainAdapter = new OrderAgainAdapter(this, productList);

        rcv_productList.setAdapter(orderAgainAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcv_productList.setLayoutManager(linearLayoutManager);
    }
}