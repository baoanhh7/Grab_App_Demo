package com.example.grab_demo.deliver.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grab_demo.R;
import com.example.grab_demo.deliver.Adapter.OrderItemAdapter;
import com.example.grab_demo.model.DonHangModel;
import com.example.grab_demo.model.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class ChiTietDonHangNewActivity extends AppCompatActivity {
    private RecyclerView recyclerViewOrderItems;
    private TextView textViewTongTien;
    private Button buttonConfirm;
    private List<OrderItem> orderItemList;
    private OrderItemAdapter orderItemAdapter;
    private DonHangModel donHang; // Khai báo biến donHang ở đây để có thể truy cập từ bất kỳ phương thức nào trong lớp này

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_don_hang_new);

        // Ánh xạ các view từ layout
        recyclerViewOrderItems = findViewById(R.id.recyclerViewOrderItems);
        textViewTongTien = findViewById(R.id.textViewTongTien);
        buttonConfirm = findViewById(R.id.buttonConfirm);

        // Apply system window insets to the main layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Khởi tạo danh sách các món trong đơn hàng và adapter cho RecyclerView
        orderItemList = new ArrayList<>();
        orderItemList.add(new OrderItem("Phở", 2, 50000));
        orderItemList.add(new OrderItem("Bún bò", 1, 60000));

        orderItemAdapter = new OrderItemAdapter(orderItemList);
        recyclerViewOrderItems.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewOrderItems.setAdapter(orderItemAdapter);

        // Tính tổng tiền của đơn hàng
        int totalAmount = 0;
        for (OrderItem item : orderItemList) {
            totalAmount += item.getPrice() * item.getQuantity();
        }
        textViewTongTien.setText(totalAmount + " VND");

        // Lấy đối tượng đơn hàng từ Intent (nếu có)
        Intent intent = getIntent();
        if (intent != null) {
            donHang = intent.getParcelableExtra("donhang");
        }

        // Thiết lập sự kiện khi nhấn nút Xác nhận
        buttonConfirm.setOnClickListener(v -> {
            // Kiểm tra nếu đơn hàng không null thì chuyển sang ChiTietDonHangActivity
            if (donHang != null) {
                Intent chiTietIntent = new Intent(ChiTietDonHangNewActivity.this, ChiTietDonHangActivity.class);
                chiTietIntent.putExtra("donhang", donHang);
                startActivity(chiTietIntent);
            }
        });
    }
}
