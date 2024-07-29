package com.example.grab_demo.customer.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grab_demo.R;
import com.example.grab_demo.customer.adapter.OrderDetailsAdapter;
import com.example.grab_demo.customer.model.OrderDetails;
import com.example.grab_demo.database.ConnectionClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderDetailsActivity extends AppCompatActivity {
    Connection connection;
    String query;
    Statement smt;
    ResultSet resultSet;

    TextView txt_order_id, txt_voucher, txt_total_money;
    Button btn_cancel_order;
    ImageButton btn_close;

    RecyclerView rcv_orderDetails;
    List<OrderDetails> orderDetailsList;
    OrderDetailsAdapter orderDetailsAdapter;

    int orderId, voucherId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_order);

        addControls();

        orderId = getIntent().getIntExtra("order_id", -1);
        voucherId = getIntent().getIntExtra("voucher_id", -1);

        if (orderId == -1) {
            Log.e("OrderDetailsActivity", "order_id is null");
        } else {
            txt_order_id.setText(String.valueOf(orderId));
            loadOrderDetails(orderId);
        }

        if (voucherId == -1) {
            Log.e("OrderDetailsActivity", "voucher_id is null");
        } else {
            loadVoucherDiscount(voucherId);
        }
        addEvents();
    }

    private void loadVoucherDiscount(int voucherId) {
        ConnectionClass sql = new ConnectionClass();
        connection = sql.conClass();
        if (connection != null) {
            try {
                query = "SELECT discount FROM Vouchers WHERE voucher_id = " + voucherId;
                smt = connection.createStatement();
                resultSet = smt.executeQuery(query);

                if (resultSet.next()) {
                    final double discount = resultSet.getDouble(1);
                    txt_voucher.setText(String.valueOf(discount));
                }
                connection.close();
            } catch (Exception e) {
                Log.e("Error: ", Objects.requireNonNull(e.getMessage()));
            }
        } else {
            Log.e("Error: ", "Connection null");
        }
    }

    private void loadOrderDetails(int orderId) {
        ConnectionClass sql = new ConnectionClass();
        connection = sql.conClass();
        if (connection != null) {
            try {
                query = "select order_id, item_id, quantity, price from OrderDetails where order_id = " + orderId;
                smt = connection.createStatement();
                resultSet = smt.executeQuery(query);

                orderDetailsList.clear();

                while (resultSet.next()) {
                    int order_id = resultSet.getInt(1);
                    int item_id = resultSet.getInt(2);
                    int quantity = resultSet.getInt(3);
                    double price = resultSet.getDouble(4);

                    orderDetailsList.add(new OrderDetails(order_id, item_id, quantity, price));
                }
                // Truy vấn để lấy total_price từ bảng Order
                query = "SELECT total_price FROM Orders WHERE order_id = " + orderId;
                resultSet = smt.executeQuery(query);

                double totalPrice = 0;
                if (resultSet.next()) {
                    totalPrice = resultSet.getDouble(1);
                }

                txt_total_money.setText(String.valueOf(totalPrice));

                connection.close();
                orderDetailsAdapter.notifyDataSetChanged();

            } catch (Exception e) {
                Log.e("Error: ", Objects.requireNonNull(e.getMessage()));
            }
        } else {
            Log.e("Error: ", "Connection null");
        }
    }

    private void addEvents() {
        btn_close.setOnClickListener(v -> finish());

        btn_cancel_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkOrderStatusAndShowDialog(orderId);
            }
        });
    }

    private void checkOrderStatusAndShowDialog(final int orderId) {
        ConnectionClass sql = new ConnectionClass();
        connection = sql.conClass();
        if (connection != null) {
            try {
                // Truy vấn để kiểm tra trạng thái đơn hàng
                String query = "SELECT status FROM Orders WHERE order_id = ?";
                PreparedStatement pstmt = connection.prepareStatement(query);
                pstmt.setInt(1, orderId);
                ResultSet resultSet = pstmt.executeQuery();

                if (resultSet.next()) {
                    String status = resultSet.getString("status");

                    if ("pending".equals(status)) {
                        // Hiển thị hộp thoại xác nhận nếu trạng thái là 'pending'
                        showCancelOrderDialog(orderId);
                    } else {
                        // Thông báo lỗi nếu trạng thái không phải 'pending'
                        Toast.makeText(this, "Không thể hủy đơn hàng này.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Thông báo lỗi nếu không tìm thấy đơn hàng
                    Toast.makeText(this, "Order not found.", Toast.LENGTH_SHORT).show();
                }

                connection.close();

            } catch (Exception e) {
                Log.e("Error: ", Objects.requireNonNull(e.getMessage()));
            }
        } else {
            Log.e("Error: ", "Connection null");
        }
    }

    private void showCancelOrderDialog(int orderId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(OrderDetailsActivity.this);
        builder.setTitle("Thông báo")
                .setMessage("Xác nhận hủy đơn hàng?")
                .setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Thực hiện hủy đơn hàng khi người dùng nhấn "Xác nhận"
                        cancelOrder(orderId);
                        finish();
                    }
                })
                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Đóng hộp thoại khi người dùng nhấn "Không"
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void cancelOrder(int orderId) {
        ConnectionClass sql = new ConnectionClass();
        connection = sql.conClass();
        if (connection != null) {
            try {
                // Truy vấn để cập nhật status thành 'canceled'
                String query = "UPDATE Orders SET status = 'canceled' WHERE order_id = ?";
                PreparedStatement pstmt = connection.prepareStatement(query);
                pstmt.setInt(1, orderId);

                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    Log.i("Success: ", "Order status updated to 'canceled'");
                } else {
                    Log.e("Error: ", "Order update failed");
                    Toast.makeText(this, "Failed to cancel order", Toast.LENGTH_SHORT).show();
                }

                connection.close();

            } catch (Exception e) {
                Log.e("Error: ", Objects.requireNonNull(e.getMessage()));
            }
        } else {
            Log.e("Error: ", "Connection null");
        }
    }

    private void addControls() {
        txt_order_id = findViewById(R.id.txt_order_id);
        txt_voucher = findViewById(R.id.txt_voucher);
        txt_total_money = findViewById(R.id.txt_total_money);
        btn_cancel_order = findViewById(R.id.btn_cancel_order);
        btn_close = findViewById(R.id.btn_close);

        rcv_orderDetails = findViewById(R.id.rcv_orderDetails);
        orderDetailsList = new ArrayList<>();
        orderDetailsAdapter = new OrderDetailsAdapter(this, orderDetailsList);
        rcv_orderDetails.setAdapter(orderDetailsAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcv_orderDetails.setLayoutManager(linearLayoutManager);

    }
}