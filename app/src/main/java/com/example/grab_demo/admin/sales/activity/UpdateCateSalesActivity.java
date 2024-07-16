package com.example.grab_demo.admin.sales.activity;

import static android.provider.MediaStore.ACTION_IMAGE_CAPTURE;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.grab_demo.R;
import com.example.grab_demo.database.ConnectionClass;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UpdateCateSalesActivity extends AppCompatActivity {
    ImageButton img_back_updateCateSales, btn_camera_updateCateSales;
    TextInputEditText edt_name_updateCateSales;
    Button btn_updateCateSales, btn_choose_image_updateCateSales;
    Connection connection;
    String query;
    ResultSet resultSet;
    ImageView img_updateCateSales;
    Integer cateID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_cate_sales);
        cateID = getIntent().getIntExtra("cateID", 0);
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
                query = "UPDATE Categories SET cate_name = ?,cate_image = ?, updated_at = ? WHERE cate_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, categoryName);
                preparedStatement.setBytes(2, getByteArrayFromImageView(img_updateCateSales));
                preparedStatement.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis()));
                preparedStatement.setInt(4, cateID); // Thiết lập điều kiện WHERE để xác định hàng cần cập nhật
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
        btn_choose_image_updateCateSales = findViewById(R.id.btn_choose_image_updateCateSales);
        img_updateCateSales = findViewById(R.id.img_updateCateSales);
        ConnectionClass sql = new ConnectionClass();
        connection = sql.conClass();
        if (connection != null) {
            try {
                query = "SELECT cate_name,cate_image FROM Categories WHERE cate_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, cateID);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    edt_name_updateCateSales.setText(resultSet.getString(1));
                    img_updateCateSales.setImageBitmap(getImageViewFromByteArray(resultSet.getBytes(2)));
                }
                connection.close();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
        }
    }

    private Bitmap getImageViewFromByteArray(byte[] byteArray) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        return bitmap;
    }

    private byte[] getByteArrayFromImageView(ImageView img) {
        BitmapDrawable drawable = (BitmapDrawable) img.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(ACTION_IMAGE_CAPTURE);
        if (ActivityCompat.checkSelfPermission(UpdateCateSalesActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(UpdateCateSalesActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
            return;
        }
        //if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
        startActivityForResult(takePictureIntent, 99);
        //}
    }

    private void choosePhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 99 && resultCode == Activity.RESULT_OK) {
            // Xử lý ảnh đã chụp tại đây (nếu cần)
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            // Hiển thị ảnh hoặc thực hiện các xử lý khác theo nhu cầu của bạn
            img_updateCateSales.setImageBitmap(imageBitmap);
        } else if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            try {
                Uri imageUri = data.getData();
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                img_updateCateSales.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}