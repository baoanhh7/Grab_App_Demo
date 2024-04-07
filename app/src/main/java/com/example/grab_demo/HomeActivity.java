package com.example.grab_demo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grab_demo.adapter.HomeAdapter;
import com.example.grab_demo.model.Product;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    RecyclerView rcv_header;
    List<Product> productList;
    HomeAdapter homeAdapter;

    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        addControls();
        createData();

    }

    private void createData() {

        productList.add(new Product("Noodles & Congee", R.drawable.noodle));
        productList.add(new Product("International Food", R.drawable.food));
        productList.add(new Product("Healthy Food", R.drawable.healthy));
        productList.add(new Product("Pizza", R.drawable.pizza));

        homeAdapter.notifyDataSetChanged();
    }

    private void addControls() {
        productList = new ArrayList<>();
        rcv_header = findViewById(R.id.rcv_header);
        homeAdapter = new HomeAdapter(this, productList);
        rcv_header.setAdapter(homeAdapter);
        searchView = findViewById(R.id.searchView);
        searchView.setIconifiedByDefault(false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rcv_header.setLayoutManager(linearLayoutManager);
    }
}