package com.example.grab_demo.customer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grab_demo.R;
import com.example.grab_demo.customer.adapter.Home.HomeVoucherAdapter;
import com.example.grab_demo.m_interface.StClickItem;
import com.example.grab_demo.model.Category;

import java.util.ArrayList;
import java.util.List;

public class StoreMenuActivity extends AppCompatActivity {
    RecyclerView rcv_menu;
    List<Category> categoryList;
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

        homeVoucherAdapter.setOnClickItemListener(new StClickItem() {
            @Override
            public void onClickItem(String data) {
                Intent intent = new Intent(StoreMenuActivity.this, OderActivity.class);
                startActivity(intent);
            }
        });
    }

    private void createData() {
        categoryList.clear();
        categoryList.add(new Category("Cơm Sườn Phúc Lộc Thọ & 1 Món", R.drawable.comsuon));
        categoryList.add(new Category("Cơm Sườn Phúc Lộc Thọ & 2 Món", R.drawable.comsuon));
        categoryList.add(new Category("Cơm Sườn Phúc Lộc Thọ & 3 Món", R.drawable.comsuon));
        categoryList.add(new Category("Cơm Sườn Phúc Lộc Thọ & 4 Món", R.drawable.comsuon));
        categoryList.add(new Category("Cơm Sườn Phúc Lộc Thọ & 5 Món", R.drawable.comsuon));
        categoryList.add(new Category("Cơm Sườn Phúc Lộc Thọ & 6 Món", R.drawable.comsuon));
        homeVoucherAdapter.notifyDataSetChanged();
    }

    private void addControls() {
        btn_back = findViewById(R.id.btn_back);

        rcv_menu = findViewById(R.id.rcv_menu);
        categoryList = new ArrayList<>();
        homeVoucherAdapter = new HomeVoucherAdapter(this, categoryList);
        rcv_menu.setAdapter(homeVoucherAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rcv_menu.setLayoutManager(gridLayoutManager);
    }
}