package com.example.grab_demo.deliver.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grab_demo.R;
import com.example.grab_demo.database.ConnectionClass;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StatisticalActivity extends AppCompatActivity {
    private static final String PREFS_NAME = "StatisticalPrefs";
    private static final String KEY_START_TIME = "startTime";
    private static final String KEY_ACCUMULATED_TIME = "accumulatedTime";
    private TextView tv_thu_nhap_chuyen_di, tv_da_thu_tien_mat, tv_thoi_gian_truc_tuyen, tv_so_chuyen_di;
    private long startTime;
    private long accumulatedTime = 0;
    private String userId;
    private Handler handler = new Handler();
    private Runnable updateTimeRunnable = new Runnable() {
        @Override
        public void run() {
            updateActivityTime();
            handler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistical);

        tv_thoi_gian_truc_tuyen = findViewById(R.id.tv_thoi_gian_truc_tuyen);
        tv_so_chuyen_di = findViewById(R.id.tv_so_chuyen_di);
        tv_thu_nhap_chuyen_di = findViewById(R.id.tv_thu_nhap_chuyen_di);
        tv_da_thu_tien_mat = findViewById(R.id.tv_da_thu_tien_mat);

        // Nhận Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("user_id")) {
            userId = intent.getStringExtra("user_id");
        }

        // Load thời gian bắt đầu và thời gian tích lũy từ SharedPreferences
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        startTime = prefs.getLong(KEY_START_TIME, System.currentTimeMillis());
        accumulatedTime = prefs.getLong(KEY_ACCUMULATED_TIME, 0);

        // Cập nhật thời gian hoạt động mỗi giây
        handler.post(updateTimeRunnable);

        // Thực hiện truy vấn
        if (userId != null) {
            new QueryTask().execute(userId);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Lưu thời gian bắt đầu và thời gian tích lũy vào SharedPreferences
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putLong(KEY_START_TIME, System.currentTimeMillis());
        editor.putLong(KEY_ACCUMULATED_TIME, accumulatedTime + (System.currentTimeMillis() - startTime));
        editor.apply();
        handler.removeCallbacks(updateTimeRunnable); // Ngừng cập nhật khi Activity bị dừng
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Lấy lại thời gian bắt đầu và thời gian tích lũy từ SharedPreferences
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        startTime = prefs.getLong(KEY_START_TIME, System.currentTimeMillis());
        accumulatedTime = prefs.getLong(KEY_ACCUMULATED_TIME, accumulatedTime);
        handler.post(updateTimeRunnable); // Bắt đầu cập nhật lại khi Activity tiếp tục
    }

    private void updateActivityTime() {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = accumulatedTime + (currentTime - startTime);

        // Chuyển đổi thành giờ, phút, giây
        int seconds = (int) (elapsedTime / 1000) % 60;
        int minutes = (int) ((elapsedTime / (1000 * 60)) % 60);
        int hours = (int) ((elapsedTime / (1000 * 60 * 60)) % 24);

        // Hiển thị thời gian hoạt động
        String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        tv_thoi_gian_truc_tuyen.setText(timeString);
    }

    // Lớp hỗ trợ để lưu kết quả truy vấn
    private static class QueryResult {
        int orderCount;
        BigDecimal totalCashCollected;
        BigDecimal totalIncome;

        QueryResult(int orderCount, BigDecimal totalCashCollected, BigDecimal totalIncome) {
            this.orderCount = orderCount;
            this.totalCashCollected = totalCashCollected;
            this.totalIncome = totalIncome;
        }
    }

    private class QueryTask extends AsyncTask<String, Void, QueryResult> {
        @Override
        protected QueryResult doInBackground(String... params) {
            String userId = params[0];
            int orderCount = 0;
            BigDecimal totalCashCollected = BigDecimal.ZERO;
            BigDecimal totalIncome = BigDecimal.ZERO;

            ConnectionClass connectionClass = new ConnectionClass();
            Connection connection = connectionClass.conClass();

            if (connection != null) {
                try {
                    // Đếm số đơn hàng đã giao
                    String countQuery = "SELECT COUNT(*) FROM Orders WHERE delivery_id = ? AND status = 'delivered'";
                    PreparedStatement countStatement = connection.prepareStatement(countQuery);
                    countStatement.setString(1, userId);
                    ResultSet countResult = countStatement.executeQuery();

                    if (countResult.next()) {
                        orderCount = countResult.getInt(1);
                    }
                    countResult.close();
                    countStatement.close();

                    // Tính tổng tiền mặt thu được
                    String cashQuery = "SELECT o.total_price, o.delivery_price " +
                            "FROM Orders o " +
                            "WHERE o.delivery_id = ? AND o.status = 'delivered' AND o.payment_method = 'cash'";
                    PreparedStatement cashStatement = connection.prepareStatement(cashQuery);
                    cashStatement.setString(1, userId);
                    ResultSet cashResult = cashStatement.executeQuery();

                    while (cashResult.next()) {
                        BigDecimal totalPrice = cashResult.getBigDecimal("total_price");
                        BigDecimal deliveryPrice = cashResult.getBigDecimal("delivery_price");
                        totalCashCollected = totalCashCollected.add(totalPrice).add(deliveryPrice);
                    }
                    cashResult.close();
                    cashStatement.close();

                    // Tính tổng thu nhập
                    totalIncome = new BigDecimal(orderCount).multiply(new BigDecimal("50.00"));

                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            return new QueryResult(orderCount, totalCashCollected, totalIncome);
        }

        @Override
        protected void onPostExecute(QueryResult result) {
            // Cập nhật giao diện người dùng
            tv_so_chuyen_di.setText("" + result.orderCount);
            tv_thu_nhap_chuyen_di.setText("" + result.totalIncome.toString() + " VND");
            tv_da_thu_tien_mat.setText("" + result.totalCashCollected.toString() + " VND");
        }
    }
}
