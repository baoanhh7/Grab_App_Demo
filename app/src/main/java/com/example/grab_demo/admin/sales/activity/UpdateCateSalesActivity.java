package com.example.grab_demo.admin.sales.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grab_demo.ConnectionClass;
import com.example.grab_demo.R;
import com.google.android.material.textfield.TextInputEditText;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UpdateCateSalesActivity extends AppCompatActivity {
    ImageButton img_back_updateCateSales;
    TextInputEditText edt_name_updateCateSales;
    Button btn_updateCateSales;
    Connection connection;
    String query;
    String categoryId;
    ResultSet resultSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_cate_sales);
        addControls();
        addEvents();
    }

    private void insertDB() {
        // Lấy chuỗi tên danh mục từ EditText
        String categoryName = edt_name_updateCateSales.getText().toString().trim();
        ConnectionClass sql = new ConnectionClass();
        connection = sql.conClass();
        if (connection != null) {
            try {
                query = "UPDATE Categories SET cate_name = ?, updated_at = ? WHERE cate_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, categoryName);
                preparedStatement.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));
                preparedStatement.setString(3, categoryId); // Thiết lập điều kiện WHERE để xác định hàng cần cập nhật
                // Thực thi truy vấn INSERT
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    Log.d("UpdateCateSalesActivity", "Update successfully");
                    // Gọi finish() để đóng activity sau khi chèn thành công
                    finish();
                } else {
                    Log.e("UpdateCateSalesActivity", "Update failed");
                }
                connection.close();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
        } else {
            Log.e("Error: ", "Connection null");
        }
    }

    private void addEvents() {
        img_back_updateCateSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_updateCateSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertDB();
//                finish();
            }
        });
    }

    private void addControls() {
        img_back_updateCateSales = findViewById(R.id.img_back_updateCateSales);
        edt_name_updateCateSales = findViewById(R.id.edt_name_updateCateSales);
        btn_updateCateSales = findViewById(R.id.btn_updateCateSales);
        edt_name_updateCateSales.setText(getIntent().getStringExtra("cateName"));
        ConnectionClass sql = new ConnectionClass();
        connection = sql.conClass();
        if (connection != null) {
            try {
                query = "SELECT cate_id FROM Categories WHERE cate_name = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, edt_name_updateCateSales.getText().toString().trim());
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    categoryId = resultSet.getString(1);
                    Log.e("categoryId", categoryId);
                }
                connection.close();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
        }
    }
}