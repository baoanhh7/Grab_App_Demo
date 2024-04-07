package com.example.grab_demo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grab_demo.adapter.HomeAdapter;
import com.example.grab_demo.adapter.HomeSecondAdapter;
import com.example.grab_demo.model.Product;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    RecyclerView rcv_header, rcv_second;
    List<Product> productList, productList2;
    HomeAdapter homeAdapter;
    HomeSecondAdapter homeSecondAdapter;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        addControls();
        createData();

    }

    private void createData() {
        productList.clear();
        productList.add(new Product("Noodles & Congee", R.drawable.noodle));
        productList.add(new Product("International Food", R.drawable.food));
        productList.add(new Product("Healthy Food", R.drawable.healthy));
        productList.add(new Product("Pizza", R.drawable.pizza));
        homeAdapter.notifyDataSetChanged();

        productList2.clear();
        productList2.add(new Product("Near me", "Get it quick", R.color.hongNhat));
        productList2.add(new Product("Bửa tối nữa giá", "Chốt deal ngay!", R.color.lavender));
        productList2.add(new Product("Grab ngon rẻ", "Bao tiết kiệm", R.color.vangNhat));
        productList2.add(new Product("Tuần lễ món cơm", "Tặng món 0đ", R.color.xanhNhat));
        homeSecondAdapter.notifyDataSetChanged();

    }

    private void addControls() {
        productList = new ArrayList<>();
        productList2 = new ArrayList<>();

        rcv_header = findViewById(R.id.rcv_header);
        rcv_second = findViewById(R.id.rcv_second);

        homeAdapter = new HomeAdapter(this, productList);
        homeSecondAdapter = new HomeSecondAdapter(this, productList2);

        rcv_header.setAdapter(homeAdapter);
        rcv_second.setAdapter(homeSecondAdapter);

        searchView = findViewById(R.id.searchView);
        searchView.setIconifiedByDefault(false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rcv_header.setLayoutManager(linearLayoutManager);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rcv_second.setLayoutManager(gridLayoutManager);
    }
}