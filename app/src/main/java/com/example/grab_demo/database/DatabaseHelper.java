package com.example.grab_demo.database;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.grab_demo.R;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class DatabaseHelper {
    private ConnectionClass connectionClass;

    public DatabaseHelper() {
        connectionClass = new ConnectionClass();
    }

    public void addDataCate(Context context) {
        Connection connection = connectionClass.conClass();
        if (connection != null) {
            try {
                // Chuyển đổi drawable thành byte array
                byte[] imageChicken = getByteArrayFromDrawable(context, R.drawable.chickenfood);
                byte[] imageBurger = getByteArrayFromDrawable(context, R.drawable.burgerfood);
                byte[] imageRice = getByteArrayFromDrawable(context, R.drawable.ricefood);
                byte[] imageNoodle = getByteArrayFromDrawable(context, R.drawable.noodle);

                // Tạo câu lệnh INSERT
                String query = "INSERT INTO Categories(cate_name, cate_image) VALUES (?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);

                // Thực hiện chèn dữ liệu vào bảng
                preparedStatement.setString(1, "Chicken");
                preparedStatement.setBytes(2, imageChicken);
                preparedStatement.addBatch();

                preparedStatement.setString(1, "Burger");
                preparedStatement.setBytes(2, imageBurger);
                preparedStatement.addBatch();

                preparedStatement.setString(1, "Rice");
                preparedStatement.setBytes(2, imageRice);
                preparedStatement.addBatch();

                preparedStatement.setString(1, "Noodle");
                preparedStatement.setBytes(2, imageNoodle);
                preparedStatement.addBatch();

                int[] rowsAffected = preparedStatement.executeBatch();
                connection.close();

                boolean success = true;
                for (int count : rowsAffected) {
                    if (count <= 0) {
                        success = false;
                        break;
                    }
                }
                if (success) {
                    Log.d("DatabaseHelper", "Insert successfully");
                } else {
                    Log.e("DatabaseHelper", "Insert failed");
                }

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
        } else {
            Log.e("Error: ", "Connection null");
        }
    }

    public byte[] getByteArrayFromDrawable(Context context, int drawableId) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableId);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        return outputStream.toByteArray();
    }
}
