package com.example.grab_demo.customer.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grab_demo.R;
import com.example.grab_demo.database.ConnectionClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;

public class OrderActivity extends AppCompatActivity {
    Connection connection, connection2;
    String query, query2;
    Statement smt, smt2;
    ResultSet resultSet, resultSet2;

    ImageButton btn_close;
    Button btn_order;
    TextView txt_item_name, txt_price, txt_description, txt_quantity;
    ImageView img_circle;

    int itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oder);

        addControls();

        itemId = getIntent().getIntExtra("item_id", -1);  // Lấy cate_id kiểu int với giá trị mặc định là -1
        if (itemId != -1) {
            loadData(itemId);
        } else {
            Log.e("OrderActivity", "item_id is null");
        }

        addEvents();
    }

    private void loadData(int itemId) {
        ConnectionClass sql = new ConnectionClass();
        connection = sql.conClass();
        if (connection != null) {
            try {
                query = "SELECT item_name, description, price, image, quantity FROM Items " +
                        "WHERE item_id = " + itemId;
                smt = connection.createStatement();
                resultSet = smt.executeQuery(query);

                if (resultSet.next()) {
                    txt_item_name.setText(resultSet.getString(1));
                    txt_description.setText(resultSet.getString(2));
                    txt_price.setText(String.valueOf(resultSet.getDouble(3)));
                    byte[] image = resultSet.getBytes(4);
                    if (image != null) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                        img_circle.setImageBitmap(bitmap);
                    }
                    int quantity = resultSet.getInt(5);
                    txt_quantity.setText(String.valueOf(quantity));
                } else {
                    Log.e("OrderActivity", "No data found for item_id: " + itemId);
                }

                connection.close();
            } catch (Exception e) {
                Log.e("Error: ", Objects.requireNonNull(e.getMessage()));
            }
        } else {
            Log.e("Error: ", "Connection null");
        }
    }

    private void addEvents() {
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertDataToCart(itemId);
                Toast.makeText(OrderActivity.this, "Add to cart successfully!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void insertDataToCart(int itemId) {
        ConnectionClass sql = new ConnectionClass();
        connection2 = sql.conClass();
        if (connection2 != null) {
            try {
                // Kiểm tra xem item có tồn tại trong CartItems chưa
                query2 = "SELECT quantity FROM CartItems WHERE cart_id = ? AND item_id = ?";
                PreparedStatement checkStmt = connection2.prepareStatement(query2);
                checkStmt.setInt(1, 1);  // Giả sử cart_id là 1, bạn có thể thay đổi giá trị này theo yêu cầu của bạn
                checkStmt.setInt(2, itemId);

                ResultSet resultSet = checkStmt.executeQuery();
                if (resultSet.next()) {
                    // Nếu item đã tồn tại, cập nhật quantity
                    int currentQuantity = resultSet.getInt("quantity");
                    int newQuantity = currentQuantity + 1;

                    query2 = "UPDATE CartItems SET quantity = ? WHERE cart_id = ? AND item_id = ?";
                    PreparedStatement updateStmt = connection2.prepareStatement(query2);
                    updateStmt.setInt(1, newQuantity);
                    updateStmt.setInt(2, 1);  // cart_id
                    updateStmt.setInt(3, itemId);

                    int rowsAffected = updateStmt.executeUpdate();
                    if (rowsAffected > 0) {
                        Log.d("OrderActivity", "Update successfully");
                    } else {
                        Log.e("OrderActivity", "Update failed");
                    }
                } else {
                    // Nếu item chưa tồn tại, chèn vào bảng với số lượng là 1
                    query2 = "INSERT INTO CartItems (cart_id, item_id, quantity) VALUES (?, ?, ?)";
                    PreparedStatement insertStmt = connection2.prepareStatement(query2);
                    insertStmt.setInt(1, 1);  // cart_id
                    insertStmt.setInt(2, itemId);
                    insertStmt.setInt(3, 1);  // quantity là 1

                    int rowsAffected = insertStmt.executeUpdate();
                    if (rowsAffected > 0) {
                        Log.d("OrderActivity", "Insert successfully");
                    } else {
                        Log.e("OrderActivity", "Insert failed");
                    }
                }

                connection2.close();
            } catch (Exception e) {
                Log.e("Error: ", Objects.requireNonNull(e.getMessage()));
            }
        } else {
            Log.e("Error: ", "Connection null");
        }
    }

    private void addControls() {
        btn_close = findViewById(R.id.btn_close);
        btn_order = findViewById(R.id.btn_order);

        txt_item_name = findViewById(R.id.txt_item_name);
        txt_price = findViewById(R.id.txt_price);
        txt_description = findViewById(R.id.txt_description);
        txt_quantity = findViewById(R.id.txt_quantity);

        img_circle = findViewById(R.id.img_circle);
    }
}