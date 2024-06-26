package com.example.grab_demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grab_demo.adapter.Home.HomeVoucherAdapter;
import com.example.grab_demo.model.Product;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {
    RecyclerView rcv_menu;
    List<Product> productList;
    HomeVoucherAdapter homeVoucherAdapter;
    ImageButton btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

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

        homeVoucherAdapter.setOnClickItemListener(new IClickItem() {
            @Override
            public void onClickItem(String data) {
                Intent intent = new Intent(MenuActivity.this, OderActivity.class);
                startActivity(intent);
            }
        });
    }

    private void createData() {
        productList.clear();
        productList.add(new Product("Cơm Sườn Phúc Lộc Thọ & 1 Món", R.drawable.comsuon));
        productList.add(new Product("Cơm Sườn Phúc Lộc Thọ & 2 Món", R.drawable.comsuon));
        productList.add(new Product("Cơm Sườn Phúc Lộc Thọ & 3 Món", R.drawable.comsuon));
        productList.add(new Product("Cơm Sườn Phúc Lộc Thọ & 4 Món", R.drawable.comsuon));
        productList.add(new Product("Cơm Sườn Phúc Lộc Thọ & 5 Món", R.drawable.comsuon));
        productList.add(new Product("Cơm Sườn Phúc Lộc Thọ & 6 Món", R.drawable.comsuon));
        homeVoucherAdapter.notifyDataSetChanged();
    }

    private void addControls() {
        btn_back = findViewById(R.id.btn_back);

        rcv_menu = findViewById(R.id.rcv_menu);
        productList = new ArrayList<>();
        homeVoucherAdapter = new HomeVoucherAdapter(this, productList);
        rcv_menu.setAdapter(homeVoucherAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rcv_menu.setLayoutManager(gridLayoutManager);
    }
}