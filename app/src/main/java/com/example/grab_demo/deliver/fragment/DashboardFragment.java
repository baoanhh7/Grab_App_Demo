// DashboardFragment.java
package com.example.grab_demo.deliver.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grab_demo.R;
import com.example.grab_demo.database.ConnectionClass;
import com.example.grab_demo.deliver.Adapter.DanhSachDonHangoldAdapter;
import com.example.grab_demo.model.DonHangModel;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class DashboardFragment extends Fragment {

    private static final String ARG_USER_ID = "user_id";
    private String userId;

    private RecyclerView recyclerView;
    private DanhSachDonHangoldAdapter adapter;
    private ArrayList<DonHangModel> mangDonHang;

    public static DashboardFragment newInstance(String userId) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userId = getArguments().getString(ARG_USER_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerviewdashboard);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mangDonHang = new ArrayList<>();
        fetchDataFromDatabase(); // Gọi phương thức để lấy dữ liệu
    }

    private void fetchDataFromDatabase() {
        ConnectionClass sql = new ConnectionClass();
        Connection connection = sql.conClass();
        if (connection != null) {
            Log.d("DatabaseConnection", "Connected to database successfully");
            try {
                // Prepare SQL query to fetch data from the database
                String query = "SELECT order_id, customer_id, store_id, delivery_id, delivery_price, total_price, payment_method, status, voucher_id, created_at, updated_at FROM Orders WHERE status = 'delivered' AND delivery_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, userId);

                // Execute query and get the result set
                ResultSet resultSet = preparedStatement.executeQuery();

                // Loop through the result set and populate DonHangModel objects into mangDonHang
                while (resultSet.next()) {
                    int orderId = resultSet.getInt("order_id");
                    int customerId = resultSet.getInt("customer_id");
                    int storeId = resultSet.getInt("store_id");
                    int deliveryId = resultSet.getInt("delivery_id");
                    BigDecimal deliveryPrice = resultSet.getBigDecimal("delivery_price");
                    BigDecimal totalPrice = resultSet.getBigDecimal("total_price");
                    String paymentMethod = resultSet.getString("payment_method");
                    String status = resultSet.getString("status");
                    int voucherId = resultSet.getInt("voucher_id");
                    Date createdAt = resultSet.getTimestamp("created_at");
                    Date updatedAt = resultSet.getTimestamp("updated_at");

                    DonHangModel donHang = new DonHangModel(orderId, customerId, storeId, deliveryId, deliveryPrice, totalPrice, paymentMethod, status, voucherId, createdAt, updatedAt);
                    mangDonHang.add(donHang);
                }

                // Close resources
                resultSet.close();
                preparedStatement.close();
            } catch (SQLException e) {
                Log.e("DatabaseConnection", "Error executing query: " + e.getMessage());
            } finally {
                try {
                    if (connection != null) {
                        connection.close(); // Close connection
                        Log.d("DatabaseConnection", "Database connection closed");
                    }
                } catch (SQLException throwables) {
                    Log.e("DatabaseConnection", "Error closing connection: " + throwables.getMessage());
                }
            }
        } else {
            Log.e("DatabaseConnection", "Failed to connect to database");
        }

        // After fetching data, update the adapter
        adapter = new DanhSachDonHangoldAdapter(getContext(), mangDonHang);
        recyclerView.setAdapter(adapter);
    }
}
