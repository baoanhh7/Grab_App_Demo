package com.example.grab_demo.customer.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grab_demo.R;
import com.example.grab_demo.customer.adapter.ListOrderAdapter;
import com.example.grab_demo.customer.model.Item;
import com.example.grab_demo.database.ConnectionClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CartActivity extends AppCompatActivity {
    private static final long REFRESH_INTERVAL = 1000; // 1s

    Connection connection, connection2;
    String query, query2;
    Statement smt, smt2;
    ResultSet resultSet, resultSet2;

    RecyclerView rcv_cart;
    List<Item> itemList;
    ListOrderAdapter itemAdapter;
    ImageView img_back;
    TextView txt_name_voucher, txt_orderMoney, txt_shipMoney, txt_voucher, txt_totalMoney;
    Button btn_orderNow, btn_getVoucher;
    int voucherId = 1;
    int storeId;
    double orderMoney = 0;
    double shipMoney = 0;
    double voucher = 0;
    double totalMoney = 0;
    int userId;
    private Handler handler;
    private Runnable refreshRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        addControls();

        voucherId = getIntent().getIntExtra("voucher_id", -1);  // Lấy cate_id kiểu int với giá trị mặc định là -1

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        userId = sharedPreferences.getInt("user_id", -1);
        Log.e("CartActivity", "userId is null");

        if (voucherId == -1) {
            Log.e("CartActivity", "voucher_id is null");
        }

        // Truy vấn và cập nhật tên voucher
        loadVoucherName(voucherId);

        handler = new Handler(Looper.getMainLooper());
        refreshRunnable = new Runnable() {
            @Override
            public void run() {
                loadData();
                handler.postDelayed(this, REFRESH_INTERVAL);
            }
        };
        addEvents();
    }

    private void loadVoucherName(int voucherId) {
        ConnectionClass sql = new ConnectionClass();
        connection = sql.conClass();
        if (connection != null) {
            try {
                query = "SELECT voucher_name, discount FROM Vouchers WHERE voucher_id = " + voucherId;
                smt = connection.createStatement();
                resultSet = smt.executeQuery(query);

                if (resultSet.next()) {
                    final String voucherName = resultSet.getString(1);
                    final double discount = resultSet.getDouble(2);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            txt_name_voucher.setText(voucherName);
                            txt_voucher.setText(String.valueOf(discount));
                            voucher = discount;
                        }
                    });
                }
                connection.close();
            } catch (Exception e) {
                Log.e("Error: ", Objects.requireNonNull(e.getMessage()));
            }
        } else {
            Log.e("Error: ", "Connection null");
        }
    }

    private void startAutoRefresh() {
        handler.postDelayed(refreshRunnable, REFRESH_INTERVAL);
        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    private void loadData() {
        ConnectionClass sql = new ConnectionClass();
        connection = sql.conClass();
        if (connection != null) {
            try {
                query = "SELECT Items.item_id, Items.item_name, Items.price, Items.image, CartItems.quantity FROM CartItems " +
                        "JOIN Items ON CartItems.item_id = Items.item_id " +
                        "WHERE CartItems.cart_id = 1"; // Sử dụng cart_id thật của bạn
                smt = connection.createStatement();
                resultSet = smt.executeQuery(query);

                itemList.clear();
                orderMoney = 0; // Reset orderMoney

                while (resultSet.next()) {
                    int itemId = resultSet.getInt(1);
                    String itemName = resultSet.getString(2);
                    double price = resultSet.getDouble(3);
                    byte[] image = resultSet.getBytes(4);
                    int quantity = resultSet.getInt(5);

                    orderMoney += price * quantity; // Tính toán Order Money

                    itemList.add(new Item(itemId, itemName, price, image, quantity));
                }
                itemAdapter.notifyDataSetChanged();
                connection.close();

                calculateTotalMoney(); // Tính toán tổng tiền

            } catch (Exception e) {
                Log.e("Error: ", Objects.requireNonNull(e.getMessage()));
            }
        } else {
            Log.e("Error: ", "Connection null");
        }
    }

    private void calculateTotalMoney() {
        // Giả sử giá trị shipMoney và voucher
        shipMoney = 50000;

        // Tính toán Total Money
        totalMoney = orderMoney + shipMoney - voucher;

        // Cập nhật giao diện người dùng trên UI thread
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                txt_orderMoney.setText(String.valueOf(orderMoney));
                txt_shipMoney.setText(String.valueOf(shipMoney));
                if (totalMoney < 0)
                    totalMoney = 0;
                else
                    txt_totalMoney.setText(String.valueOf(totalMoney));
            }
        });
    }

    private void addEvents() {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_getVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalMoney > 50000) {
                    Intent intent = new Intent(CartActivity.this, VoucherActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                    builder.setTitle("Thông báo");
                    builder.setMessage("Tổng giá trị đơn hàng phải trên 50.000đ");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }
            }
        });
        btn_orderNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orderMoney > 0) {
                    insertDataToOrder(userId);
//                clearCartItems(); // Xóa các item trong CartItems
                    Toast.makeText(CartActivity.this, "Order successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                    builder.setTitle("Thông báo");
                    builder.setMessage("Giỏ hàng trống!");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }
            }
        });
    }

    private void clearCartItems() {
        ConnectionClass sql = new ConnectionClass();
        Connection connection = sql.conClass();

        if (connection != null) {
            try {
                String deleteQuery = "DELETE FROM CartItems WHERE cart_id = 1"; // Sử dụng cart_id thật của bạn
                PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery);
                int rowsAffected = deleteStmt.executeUpdate();
                if (rowsAffected > 0) {
                    Log.d("CartActivity", "Cart items cleared successfully");
                } else {
                    Log.e("CartActivity", "Failed to clear cart items");
                }
            } catch (SQLException e) {
                Log.e("Error: ", Objects.requireNonNull(e.getMessage()));
            } finally {
                try {
                    if (connection != null && !connection.isClosed()) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    Log.e("Error: ", Objects.requireNonNull(e.getMessage()));
                }
            }
        } else {
            Log.e("Error: ", "Connection null");
        }
    }


    private void insertDataToOrder(int userId) {
        ConnectionClass sql = new ConnectionClass();
        connection2 = sql.conClass();

        if (connection2 != null) {
            try {
                // Kiểm tra và xử lý userId
                if (userId == -1) {
                    Log.e("CartActivity", "UserId is null or empty");
                    return;
                }
                int customerId;
                try {
                    customerId = userId;
                } catch (NumberFormatException e) {
                    Log.e("CartActivity", "Invalid userId format: " + e.getMessage());
                    return;
                }

                // Kiểm tra và xử lý txt_shipMoney
                String shipMoneyText = txt_shipMoney.getText().toString();
                double deliveryPrice;
                try {
                    deliveryPrice = Double.parseDouble(shipMoneyText);
                } catch (NumberFormatException e) {
                    Log.e("CartActivity", "Invalid ship money format: " + e.getMessage());
                    return;
                }

                // Kiểm tra và xử lý txt_totalMoney
                String totalMoneyText = txt_totalMoney.getText().toString();
                double totalPrice;
                try {
                    totalPrice = Double.parseDouble(totalMoneyText);
                } catch (NumberFormatException e) {
                    Log.e("CartActivity", "Invalid total money format: " + e.getMessage());
                    return;
                }

                // Lấy store_id từ CartItems
                String query1 = "SELECT i.store_id FROM CartItems c JOIN Items i ON c.item_id = i.item_id WHERE c.cart_id = 1";
                PreparedStatement insertStmt1 = connection2.prepareStatement(query1);
                ResultSet rs = insertStmt1.executeQuery();
                int storeId = -1;
                if (rs.next()) {
                    storeId = rs.getInt(1);
                } else {
                    Log.e("CartActivity", "store_id is null");
                    return;
                }
                rs.close();
                insertStmt1.close();

                // Chuẩn bị và thực hiện câu lệnh SQL để thêm đơn hàng
                String query2 = "INSERT INTO Orders (customer_id, store_id, delivery_price, total_price, payment_method, voucher_id, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement insertStmt = connection2.prepareStatement(query2, Statement.RETURN_GENERATED_KEYS);
                insertStmt.setInt(1, customerId);
                insertStmt.setInt(2, storeId);
                insertStmt.setDouble(3, deliveryPrice);
                insertStmt.setDouble(4, totalPrice);
                insertStmt.setString(5, "cash");
                if (voucherId == -1) { // Kiểm tra nếu voucherId là null
                    insertStmt.setNull(6, java.sql.Types.INTEGER);
                } else {
                    insertStmt.setInt(6, voucherId);
                }
                insertStmt.setString(7, "pending");

                int rowsAffected = insertStmt.executeUpdate();
                if (rowsAffected > 0) {

                    //Lấy orderId đơn hàng vua them
                    ResultSet generatedKeys = insertStmt.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int orderId = generatedKeys.getInt(1);

                        // Truy vấn danh sách item_id và quantity từ CartItems
                        String query3 = "SELECT cart_id, item_id, quantity FROM CartItems WHERE cart_id = 1";
                        PreparedStatement stmtDetails = connection2.prepareStatement(query3);
                        ResultSet rsDetails = stmtDetails.executeQuery();

                        String insertOrderDetailsQuery = "INSERT INTO OrderDetails (order_id, item_id, quantity, price) VALUES (?, ?, ?, ?)";
                        PreparedStatement insertStmt2 = connection2.prepareStatement(insertOrderDetailsQuery);

                        while (rsDetails.next()) {
                            int itemId = rsDetails.getInt("item_id");
                            int quantity = rsDetails.getInt("quantity");

                            // Truy vấn giá của item từ bảng Items
                            String query4 = "SELECT price FROM Items WHERE item_id = ?";
                            PreparedStatement stmtItem = connection2.prepareStatement(query4);
                            stmtItem.setInt(1, itemId);
                            ResultSet rsItem = stmtItem.executeQuery();

                            if (rsItem.next()) {
                                double itemPrice = rsItem.getDouble("price");
                                double price = itemPrice * quantity;

                                insertStmt2.setInt(1, orderId);
                                insertStmt2.setInt(2, itemId);
                                insertStmt2.setInt(3, quantity);
                                insertStmt2.setDouble(4, price);

                                insertStmt2.executeUpdate();
                            } else {
                                Log.e("CartActivity", "Item not found with item_id: " + itemId);
                            }

                            rsItem.close();
                            stmtItem.close();
                        }

                        rsDetails.close();
                        stmtDetails.close();
                        insertStmt2.close();

                        Log.d("CartActivity", "Insert successfully");
                    } else {
                        Log.e("CartActivity", "Failed to retrieve order_id");
                    }
                    generatedKeys.close();

                } else {
                    Log.e("CartActivity", "Insert failed");
                }
                insertStmt.close();
            } catch (SQLException e) {
                Log.e("Error: ", Objects.requireNonNull(e.getMessage()));
                try {
                    connection2.rollback(); // Rollback nếu có lỗi xảy ra
                } catch (SQLException ex) {
                    Log.e("Error: ", Objects.requireNonNull(ex.getMessage()));
                }
            } finally {
                try {
                    if (connection2 != null && !connection2.isClosed()) {
                        connection2.setAutoCommit(true); // Đặt lại AutoCommit
                        connection2.close(); // Đóng kết nối
                    }
                } catch (SQLException e) {
                    Log.e("Error: ", Objects.requireNonNull(e.getMessage()));
                }
            }
        } else {
            Log.e("Error: ", "Connection null");
        }
    }


    private void addControls() {
        txt_name_voucher = findViewById(R.id.txt_name_voucher);
        txt_orderMoney = findViewById(R.id.txt_orderMoney);
        txt_shipMoney = findViewById(R.id.txt_shipMoney);
        txt_voucher = findViewById(R.id.txt_voucher);
        txt_totalMoney = findViewById(R.id.txt_totalMoney);

        img_back = findViewById(R.id.img_back);
        btn_orderNow = findViewById(R.id.btn_orderNow);
        btn_getVoucher = findViewById(R.id.btn_getVoucher);

        rcv_cart = findViewById(R.id.rcv_cart);
        itemList = new ArrayList<>();
        itemAdapter = new ListOrderAdapter(this, itemList);
        rcv_cart.setAdapter(itemAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcv_cart.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void onResume() {
        loadData(); // Tải dữ liệu khi Activity được hiển thị

        super.onResume();
        startAutoRefresh(); // Bắt đầu làm mới tự động
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(refreshRunnable); // Ngừng làm mới khi Activity không còn hiển thị
    }
}