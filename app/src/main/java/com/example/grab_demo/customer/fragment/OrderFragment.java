package com.example.grab_demo.customer.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grab_demo.R;
import com.example.grab_demo.customer.adapter.C_OrderAdapter;
import com.example.grab_demo.customer.model.Order;
import com.example.grab_demo.database.ConnectionClass;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment {
    Connection connection;
    String query;
    Statement smt;
    ResultSet resultSet;

    RecyclerView rcv_order;
    List<Order> orderList;
    C_OrderAdapter orderAdapter;

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_order, container, false);

        addControls();

        addEvents();

        loadData();
        return view;
    }

    private void loadData() {
        ConnectionClass sql = new ConnectionClass();
        connection = sql.conClass();
        if (connection != null) {
            try {
                query = "SELECT order_id, delivery_id, total_price, status FROM Orders WHERE status IN ('pending', 'confirmed')";
                smt = connection.createStatement();
                resultSet = smt.executeQuery(query);

                orderList.clear();
                while (resultSet.next()) {
                    int orderId = resultSet.getInt(1);
                    int deliveryId = resultSet.getInt(2);
                    double totalPrice = resultSet.getDouble(3);
                    String status = resultSet.getString(4);
                    orderList.add(new Order(orderId, deliveryId, totalPrice, status));
                }
                orderAdapter.notifyDataSetChanged();
                connection.close();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
        } else {
            Log.e("Error: ", "Connection null");
        }
    }

    private void addEvents() {

    }

    private void addControls() {
        rcv_order = view.findViewById(R.id.rcv_order);
        orderList = new ArrayList<>();
        orderAdapter = new C_OrderAdapter(getActivity(), orderList);
        rcv_order.setAdapter(orderAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rcv_order.setLayoutManager(linearLayoutManager);  // Set LayoutManager cho RecyclerView
    }
}