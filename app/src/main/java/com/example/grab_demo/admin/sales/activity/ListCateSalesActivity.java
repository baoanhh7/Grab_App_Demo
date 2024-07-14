package com.example.grab_demo.admin.sales.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grab_demo.R;
import com.example.grab_demo.admin.sales.adapter.CateAdapter;
import com.example.grab_demo.database.ConnectionClass;
import com.example.grab_demo.store_owner.model.Cate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ListCateSalesActivity extends AppCompatActivity {
    ImageButton img_back_ListCateSales, btn_add_ListCateSales;
    SearchView searchView_ListCateSales;
    RecyclerView rv_ListCateSales;
    ArrayList<Cate> arr;
    CateAdapter cateAdapter;
    Connection connection;
    String query;
    Statement smt;
    ResultSet resultSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_cate_sales);
        addControls();
        addEvents();
        addDB();
    }

    @Override
    protected void onResume() {
        super.onResume();
        addDB();
    }

    private void addDB() {
        ConnectionClass sql = new ConnectionClass();
        connection = sql.conClass();
        if (connection != null) {
            try {
                query = "SELECT * FROM Categories";
                smt = connection.createStatement();
                resultSet = smt.executeQuery(query);
                arr.clear();
                while (resultSet.next()) {
                    Integer cateId = resultSet.getInt(1);
                    String cateName = resultSet.getString(2);
                    byte[] cateImage = resultSet.getBytes(5);
                    arr.add(new Cate(cateId, cateName, cateImage));
                }
                cateAdapter.notifyDataSetChanged();
                connection.close();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
        } else {
            Log.e("Error: ", "Connection null");
        }
    }

    private void addEvents() {
        img_back_ListCateSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_add_ListCateSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListCateSalesActivity.this, AddCateSalesActivity.class));
            }
        });
    }

    private void addControls() {
        img_back_ListCateSales = findViewById(R.id.img_back_ListCateSales);
        btn_add_ListCateSales = findViewById(R.id.btn_add_ListCateSales);
        searchView_ListCateSales = findViewById(R.id.searchView_ListCateSales);
        rv_ListCateSales = findViewById(R.id.rv_ListCateSales);
        arr = new ArrayList<>();
        cateAdapter = new CateAdapter(this, arr);
        rv_ListCateSales.setAdapter(cateAdapter);
        rv_ListCateSales.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }
}