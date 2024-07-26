package com.example.grab_demo.store_owner.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grab_demo.R;
import com.example.grab_demo.database.ConnectionClass;
import com.example.grab_demo.store_owner.adapter.StoreHSOAdapter;
import com.example.grab_demo.store_owner.model.Stores;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;

public class ListQuanActivity extends AppCompatActivity {
    ImageButton img_back_ShopHSO;
    RecyclerView rv_ShopHSO;
    ArrayList<Stores> arr;
    StoreHSOAdapter storeHSOAdapter;
    Connection connection;
    String query;
    Statement smt;
    ResultSet resultSet;
    String userId = "";
    androidx.appcompat.widget.SearchView searchView_ShopHSO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_quan);
        userId = getIntent().getStringExtra("user_id");
        Log.d("ListQuanActivity", "Received user_id: " + userId);
        addControls();
        addEvents();
        addDB();

    }

    private void addEvents() {
        img_back_ShopHSO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        searchView_ShopHSO.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                storeHSOAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                storeHSOAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private void addDB() {
        ConnectionClass sql = new ConnectionClass();
        connection = sql.conClass();
        if (connection != null) {
            try {
                query = "SELECT store_name, open_store, image, close_store FROM Stores WHERE owner_id = " + userId + " AND status = 'closed' OR status = 'opened' ";
                smt = connection.createStatement();
                resultSet = smt.executeQuery(query);
                while (resultSet.next()) {
                    String storeName = resultSet.getString(1);
                    Time openStore = resultSet.getTime(2);
                    byte[] image = resultSet.getBytes(3);
                    Time closeStore = resultSet.getTime(4);
                    arr.add(new Stores(image, storeName, openStore, closeStore));
                }
                connection.close();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
        } else {
            Log.e("Error: ", "Connection null");
        }
    }

    private void addControls() {
        img_back_ShopHSO = findViewById(R.id.img_back_ShopHSO);
        rv_ShopHSO = findViewById(R.id.rv_ShopHSO);
        searchView_ShopHSO = findViewById(R.id.searchView_ShopHSO);
        arr = new ArrayList<>();
        storeHSOAdapter = new StoreHSOAdapter(this, arr);
        rv_ShopHSO.setAdapter(storeHSOAdapter);
        rv_ShopHSO.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }
}