package com.example.grab_demo.store_owner.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grab_demo.R;
import com.example.grab_demo.database.ConnectionClass;
import com.example.grab_demo.store_owner.activity.AddDishMenuActivity;
import com.example.grab_demo.store_owner.activity.MenuHomeStoreOwnerActivity;
import com.example.grab_demo.store_owner.adapter.DishMenuHSOAdapter;
import com.example.grab_demo.store_owner.model.DishMenuHSO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DishMenuHomeStoreOwnerFragment extends Fragment {
    ImageButton btn_add_dishmenuHSO;
    View view;
    RecyclerView rv_DishMenuHSO;
    ArrayList<DishMenuHSO> arr;
    DishMenuHSOAdapter dishMenuHSOAdapter;
    String storeID;
    MenuHomeStoreOwnerActivity menuHomeStoreOwnerActivity;
    SearchView searchView_DishMenuHSO;
    TextView tv_DishMenuHSO;
    Connection connection;
    String query;
    Statement smt;
    ResultSet resultSet;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_dish_menu_home_store_owner, container, false);
        if (getActivity() instanceof MenuHomeStoreOwnerActivity) {
            menuHomeStoreOwnerActivity = (MenuHomeStoreOwnerActivity) getActivity();
            storeID = menuHomeStoreOwnerActivity.getStoreId();
        }
        Log.e("DishMenuHomeStoreOwnerFragment", storeID);
        // Ensure userId is not null before using
        if (storeID != null) {
            addControls();
            addEvents();
            addDB();
            loadData();
        } else {
            Log.e("DishMenuHomeStoreOwnerFragment", "storeID is null");
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        addDB();
    }

    private void addEvents() {
        btn_add_dishmenuHSO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo Intent để chuyển sang Activity mới
                Intent intent = new Intent(getActivity(), AddDishMenuActivity.class);
                // Đính kèm dữ liệu vào Intent
                intent.putExtra("store_id", storeID);
                // Chuyển sang Activity mới
                startActivity(intent);
            }
        });
        searchView_DishMenuHSO.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dishMenuHSOAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                dishMenuHSOAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private void addDB() {
        ConnectionClass sql = new ConnectionClass();
        connection = sql.conClass();
        if (connection != null) {
            try {
                query = "SELECT item_name,description,price,image,quantity,item_id FROM Items WHERE store_id = " + storeID;
                smt = connection.createStatement();
                resultSet = smt.executeQuery(query);
                arr.clear();
                while (resultSet.next()) {
                    String dishName = resultSet.getString(1);
                    String description = resultSet.getString(2);
                    byte[] image = resultSet.getBytes(4);
                    Double gia = resultSet.getDouble(3);
                    Integer SL = resultSet.getInt(5);
                    Integer id = resultSet.getInt(6);
                    arr.add(new DishMenuHSO(image, id, dishName, description, gia, SL));
                }
                dishMenuHSOAdapter.notifyDataSetChanged();
                connection.close();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
        } else {
            Log.e("Error: ", "Connection null");
        }
    }

    private void addControls() {
        rv_DishMenuHSO = view.findViewById(R.id.rv_DishMenuHSO);
        btn_add_dishmenuHSO = view.findViewById(R.id.btn_add_dishmenuHSO);
        searchView_DishMenuHSO = view.findViewById(R.id.searchView_DishMenuHSO);
        tv_DishMenuHSO = view.findViewById(R.id.tv_DishMenuHSO);
        arr = new ArrayList<>();
        dishMenuHSOAdapter = new DishMenuHSOAdapter(getActivity(), arr);
        rv_DishMenuHSO.setAdapter(dishMenuHSOAdapter);
        rv_DishMenuHSO.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    }

    private void loadData() {
        ConnectionClass sql = new ConnectionClass();
        connection = sql.conClass();
        if (connection != null) {
            try {
                query = "SELECT store_name FROM Stores WHERE store_id = " + storeID;
                smt = connection.createStatement();
                resultSet = smt.executeQuery(query);
                while (resultSet.next()) {
                    tv_DishMenuHSO.setText(resultSet.getString(1));
                }
                connection.close();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
        } else {
            Log.e("Error: ", "Connection null");
        }
    }
}