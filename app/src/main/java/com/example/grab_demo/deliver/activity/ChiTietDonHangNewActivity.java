package com.example.grab_demo.deliver.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grab_demo.R;
import com.example.grab_demo.database.ConnectionClass;
import com.example.grab_demo.deliver.Adapter.orderDetailAdapter;
import com.example.grab_demo.model.DonHangModel;
import com.example.grab_demo.model.OrderDetail;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChiTietDonHangNewActivity extends AppCompatActivity {

    private TextView textViewMaDonHang, textViewTrangThaiDonHang, textViewTongTien;
    private RecyclerView recyclerViewOrderDetails;
    private List<OrderDetail> orderDetails;
    private orderDetailAdapter orderDetailAdapter;
    private Button buttonAccept, buttonComplete, buttonCanceled;
    private DonHangModel donHang;
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_don_hang_new);

        // Ánh xạ các thành phần giao diện
        textViewMaDonHang = findViewById(R.id.textViewMaDonHang);
        textViewTrangThaiDonHang = findViewById(R.id.textViewTrangThaiDonHang);
        textViewTongTien = findViewById(R.id.textViewTongTien);
        recyclerViewOrderDetails = findViewById(R.id.recyclerViewOrderDetails);
        buttonAccept = findViewById(R.id.buttonAccept);
        buttonComplete = findViewById(R.id.buttonComplete);
        buttonCanceled = findViewById(R.id.buttoncanceled);

        // Khởi tạo danh sách orderDetails và adapter
        orderDetails = new ArrayList<>();
        orderDetailAdapter = new orderDetailAdapter(this, orderDetails);

        // Thiết lập RecyclerView và adapter
        recyclerViewOrderDetails.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewOrderDetails.setAdapter(orderDetailAdapter);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        if (intent != null) {
            donHang = (DonHangModel) intent.getSerializableExtra("donhang");
            userId = intent.getStringExtra("user_id"); // Nhận userId từ Intent
            if (donHang != null) {
                // Hiển thị thông tin chung về đơn hàng
                textViewMaDonHang.setText("Mã đơn hàng: " + donHang.getOrderId());
                textViewTrangThaiDonHang.setText("Trạng thái: " + donHang.getStatus());

                // Lấy danh sách OrderDetail từ DonHangModel
                List<OrderDetail> orderDetails = donHang.getOrderDetails();
                if (orderDetails != null && !orderDetails.isEmpty()) {
                    // Thêm các orderDetail vào danh sách và cập nhật adapter
                    this.orderDetails.addAll(orderDetails);
                    orderDetailAdapter.notifyDataSetChanged();

                    // Tính tổng tiền của đơn hàng
                    calculateTotalAmount(orderDetails);
                }
            }
        }

        // Thiết lập sự kiện click cho các nút
        buttonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateOrderStatus("confirmed");
            }
        });

        buttonComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateOrderStatus("delivered");
            }
        });

        buttonCanceled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateOrderStatus("canceled");
            }
        });
    }

    private void updateOrderStatus(String status) {
        if (donHang != null) {
            donHang.setStatus(status);
            textViewTrangThaiDonHang.setText("Trạng thái: " + status);

            // Cập nhật trạng thái đơn hàng lên cơ sở dữ liệu
            ConnectionClass connectionClass = new ConnectionClass();
            Connection connection = connectionClass.conClass();
            if (connection != null) {
                try {
                    String query = "UPDATE Orders SET status = ?, delivery_id = ? WHERE order_id = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, status);
                    preparedStatement.setString(2, userId); // Truyền userId vào câu truy vấn
                    preparedStatement.setInt(3, donHang.getOrderId());

                    int rowsAffected = preparedStatement.executeUpdate(); // Kiểm tra số hàng bị ảnh hưởng
                    preparedStatement.close();
                    connection.close();

                    if (rowsAffected > 0) {
                        Toast.makeText(ChiTietDonHangNewActivity.this, "Cập nhật trạng thái thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ChiTietDonHangNewActivity.this, "Không tìm thấy đơn hàng để cập nhật", Toast.LENGTH_SHORT).show();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    Log.e("SQL Error", e.getMessage());
                    Toast.makeText(ChiTietDonHangNewActivity.this, "Lỗi khi cập nhật trạng thái đơn hàng", Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.e("DB Connection", "Không thể kết nối đến cơ sở dữ liệu");
                Toast.makeText(ChiTietDonHangNewActivity.this, "Không thể kết nối đến cơ sở dữ liệu", Toast.LENGTH_SHORT).show();
            }
        }
    }



    private void calculateTotalAmount(List<OrderDetail> orderDetails) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (OrderDetail detail : orderDetails) {
            BigDecimal price = detail.getPrice();
            int quantity = detail.getQuantity();
            totalAmount = totalAmount.add(price.multiply(BigDecimal.valueOf(quantity)));
        }
        textViewTongTien.setText(totalAmount.toString() + " VND");
    }
}
