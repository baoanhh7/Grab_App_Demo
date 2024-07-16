package com.example.grab_demo.admin.sales.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grab_demo.R;
import com.example.grab_demo.database.ConnectionClass;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class StoreRegistrationActivity extends AppCompatActivity {

    ImageButton img_back_storeregistration, btn_check_registerstore, btn_close_registerstore;
    ImageView img_storeregistration;
    MaterialAutoCompleteTextView tv_ownerName_registerstore, tv_namestore_registerstore, tv_nameCate_registerstore, tv_opened_registerstore, tv_address_registerstore;
    Button btn_registerstore;
    String ownerName;
    int storeId;
    Connection connection;
    int cateId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_registration);
        ownerName = getIntent().getStringExtra("owner_name");
        Log.d("ownername", ownerName);
        storeId = getIntent().getIntExtra("store_id", 0);
        addControls();
        addEvents();
        loadDataStore();
        loadDataCate();
        tv_ownerName_registerstore.setText(ownerName);
    }

    private void loadDataCate() {
        ConnectionClass connectionClass = new ConnectionClass();
        connection = connectionClass.conClass();
        if (connection != null) {
            try {
                String query = "SELECT cate_name FROM Categories WHERE cate_id = " + cateId;
                Statement smt = connection.createStatement();
                ResultSet resultSet = smt.executeQuery(query);
                while (resultSet.next()) {
                    String cateName = resultSet.getString(1);
                    tv_nameCate_registerstore.setText(cateName);
                }
                connection.close();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
        } else {
            Log.e("Error: ", "Connection null");
        }
    }

    private void loadDataStore() {
        ConnectionClass connectionClass = new ConnectionClass();
        connection = connectionClass.conClass();
        if (connection != null) {
            try {
                String query = "SELECT store_name,cate_id,open_store,address, image FROM Stores WHERE store_id =  " + storeId;
                Statement smt = connection.createStatement();
                ResultSet resultSet = smt.executeQuery(query);
                while (resultSet.next()) {
                    String storeName = resultSet.getString(1);
                    cateId = resultSet.getInt(2);
                    String opened = resultSet.getString(3);
                    String address = resultSet.getString(4);
                    img_storeregistration.setImageBitmap(getImageViewFromByteArray(resultSet.getBytes(5)));
                    tv_namestore_registerstore.setText(storeName);
                    tv_opened_registerstore.setText(opened);
                    tv_address_registerstore.setText(address);
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
        img_back_storeregistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_check_registerstore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertDataCheck();
                finish();
            }
        });
        btn_close_registerstore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertDataClose();
                finish();
            }
        });
    }

    private void addControls() {
        img_storeregistration = findViewById(R.id.img_storeregistration);
        img_back_storeregistration = findViewById(R.id.img_back_storeregistration);
        tv_ownerName_registerstore = findViewById(R.id.tv_ownerName_registerstore);
        tv_namestore_registerstore = findViewById(R.id.tv_namestore_registerstore);
        tv_nameCate_registerstore = findViewById(R.id.tv_nameCate_registerstore);
        tv_opened_registerstore = findViewById(R.id.tv_opened_registerstore);
        tv_address_registerstore = findViewById(R.id.tv_address_registerstore);
        btn_registerstore = findViewById(R.id.btn_registerstore);
        btn_check_registerstore = findViewById(R.id.btn_check_registerstore);
        btn_close_registerstore = findViewById(R.id.btn_close_registerstore);
        tv_address_registerstore.setFocusable(false);
        tv_opened_registerstore.setFocusable(false);
        tv_nameCate_registerstore.setFocusable(false);
        tv_namestore_registerstore.setFocusable(false);
        tv_ownerName_registerstore.setFocusable(false);
    }

    private Bitmap getImageViewFromByteArray(byte[] byteArray) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        return bitmap;
    }

    private void insertDataCheck() {
        ConnectionClass sql = new ConnectionClass();
        connection = sql.conClass();
        if (connection != null) {
            try {
                String query = "UPDATE Stores SET status = ?,updated_at = ? WHERE store_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, "closed");
                preparedStatement.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));
                preparedStatement.setInt(3, storeId); // Thiết lập điều kiện WHERE để xác định hàng cần cập nhật
                // Thực thi truy vấn INSERT
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    Log.d("StoreRegistrationActivity", "Update successfully");
                    // Gọi finish() để đóng activity sau khi chèn thành công
                    finish();
                } else {
                    Log.e("StoreRegistrationActivity", "Update failed");
                }
                connection.close();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
        } else {
            Log.e("Error: ", "Connection null");
        }
    }

    private void insertDataClose() {
        ConnectionClass sql = new ConnectionClass();
        connection = sql.conClass();
        if (connection != null) {
            try {
                String query = "UPDATE Stores SET status = ?,updated_at = ? WHERE store_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, "rejected");
                preparedStatement.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));
                preparedStatement.setInt(3, storeId); // Thiết lập điều kiện WHERE để xác định hàng cần cập nhật
                // Thực thi truy vấn INSERT
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    Log.d("StoreRegistrationActivity", "Update successfully");
                    // Gọi finish() để đóng activity sau khi chèn thành công
                    finish();
                } else {
                    Log.e("StoreRegistrationActivity", "Update failed");
                }
                connection.close();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
        } else {
            Log.e("Error: ", "Connection null");
        }
    }
}