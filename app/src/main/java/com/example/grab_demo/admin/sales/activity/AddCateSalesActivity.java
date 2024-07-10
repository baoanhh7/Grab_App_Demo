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
import java.sql.Statement;

public class AddCateSalesActivity extends AppCompatActivity {
    ImageButton img_back_addCateSales;
    TextInputEditText edt_name_addCateSales;
    Button btn_addCateSales;
    Connection connection;
    String query;
    Statement smt;
    ResultSet resultSet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cate_sales);
        addControls();
        addEvents();
    }

    private void insertDB() {
        // Lấy chuỗi tên danh mục từ EditText
        String categoryName = edt_name_addCateSales.getText().toString().trim();
        ConnectionClass sql = new ConnectionClass();
        connection = sql.conClass();
        if (connection != null) {
            try {
                query = "INSERT INTO Categories(cate_name) VALUES (?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, categoryName);
                // Thực thi truy vấn INSERT
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    Log.d("AddCateSalesActivity", "Insert successfully");
                    // Gọi finish() để đóng activity sau khi chèn thành công
                    finish();
                } else {
                    Log.e("AddCateSalesActivity", "Insert failed");
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
        img_back_addCateSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_addCateSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertDB();
//                finish();
            }
        });
    }

    private void addControls() {
        img_back_addCateSales = findViewById(R.id.img_back_addCateSales);
        edt_name_addCateSales = findViewById(R.id.edt_name_addCateSales);
        btn_addCateSales = findViewById(R.id.btn_addCateSales);
    }
}