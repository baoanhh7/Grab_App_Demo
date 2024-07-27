package com.example.grab_demo.deliver.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grab_demo.R;
import com.example.grab_demo.database.ConnectionClass;
import com.example.grab_demo.deliver.Adapter.OrderDetailAdapter;
import com.example.grab_demo.model.DonHangModel;
import com.example.grab_demo.model.OrderDetail;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChiTietDonHangActivity extends AppCompatActivity {
    private TextView textViewMaDonHang, textViewTrangThaiDonHang, textViewTongTien;
    private RecyclerView recyclerViewOrderDetails;
    private List<OrderDetail> orderDetails;
    private OrderDetailAdapter orderDetailAdapter;
    private Button buttonAccept, buttonComplete, buttonCanceled;
    private DonHangModel donHang;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_don_hang);

        textViewMaDonHang = findViewById(R.id.textViewMaDonHang);
        textViewTrangThaiDonHang = findViewById(R.id.textViewTrangThaiDonHang);
        textViewTongTien = findViewById(R.id.textViewTongTien);
        recyclerViewOrderDetails = findViewById(R.id.recyclerViewOrderDetails);
        // Khởi tạo danh sách orderDetails
        orderDetails = new ArrayList<>();

        // Thiết lập RecyclerView
        recyclerViewOrderDetails.setLayoutManager(new LinearLayoutManager(this));

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        if (intent != null) {
            donHang = (DonHangModel) intent.getSerializableExtra("donhang");
            userId = intent.getStringExtra("user_id");
            if (donHang != null) {
                // Hiển thị thông tin chung về đơn hàng
                textViewMaDonHang.setText("Mã đơn hàng: " + donHang.getOrderId());
                textViewTrangThaiDonHang.setText("Trạng thái: " + donHang.getStatus());

                // Truy vấn chi tiết đơn hàng
                loadOrderDetails(donHang.getOrderId());
            }
        }

    }

    private void loadOrderDetails(int orderId) {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.conClass();
        if (connection != null) {
            try {
                String query = "SELECT od.order_detail_id, od.order_id, od.item_id, od.quantity, od.price, i.item_name " +
                        "FROM OrderDetails od " +
                        "JOIN Items i ON od.item_id = i.item_id " +
                        "WHERE od.order_id = ?";
                Log.d("SQL Query", query);

                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, orderId);

                Log.d("OrderID", "Loading details for order ID: " + orderId);

                ResultSet resultSet = preparedStatement.executeQuery();

                orderDetails.clear();
                BigDecimal totalAmount = BigDecimal.ZERO;

                while (resultSet.next()) {
                    int orderDetailId = resultSet.getInt("order_detail_id");
                    int itemId = resultSet.getInt("item_id");
                    int quantity = resultSet.getInt("quantity");
                    BigDecimal price = resultSet.getBigDecimal("price");
                    String itemName = resultSet.getString("item_name");

                    Log.d("Order Detail", "ID: " + orderDetailId + ", Item: " + itemName + ", Quantity: " + quantity + ", Price: " + price);

                    OrderDetail orderDetail = new OrderDetail(orderDetailId, orderId, itemId, quantity, price, itemName);
                    orderDetails.add(orderDetail);

                    totalAmount = totalAmount.add(price.multiply(BigDecimal.valueOf(Integer.valueOf(5000))));//
                }

                if (orderDetails.isEmpty()) {
                    Log.d("Adapter", "No order details found");
                } else {
                    Log.d("Adapter", "Found " + orderDetails.size() + " order details");
                }

                orderDetailAdapter = new OrderDetailAdapter(this, orderDetails);
                recyclerViewOrderDetails.setAdapter(orderDetailAdapter);

                textViewTongTien.setText(totalAmount.toString() + " VND");

                resultSet.close();
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                Log.e("SQL Error", "Error message: " + e.getMessage());
                Log.e("SQL Error", "SQL State: " + e.getSQLState());
                Log.e("SQL Error", "Error Code: " + e.getErrorCode());
                Toast.makeText(this, "Lỗi khi tải chi tiết đơn hàng: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            Log.e("DB Connection", "Không thể kết nối đến cơ sở dữ liệu");
            Toast.makeText(this, "Không thể kết nối đến cơ sở dữ liệu", Toast.LENGTH_SHORT).show();
        }
    }
}
