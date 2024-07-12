package com.example.grab_demo.store_owner.activity;

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
import java.sql.Statement;

public class UpdateDishMenuActivity extends AppCompatActivity {
    ImageButton img_back_updateDishMenu, btn_camera_updateDishMenu;
    ImageView img_updateDishMenu;
    Button btn_choose_image_updateDishMenu, btn_updateDishMenu;
    TextInputEditText edt_name_updateDishMenu, edt_description_updateDishMenu, edt_price_updateDishMenu, edt_quantity_updateDishMenu;
    Integer itemID;
    Connection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_dish_menu);
        itemID = getIntent().getIntExtra("item_id", 0);
        Log.d("UpdateDishMenuActivity", "Received itemID: " + itemID);
        addControls();
        addEvents();
        loadData();
    }

    private void addEvents() {
        btn_updateDishMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertDB();
            }
        });
        btn_camera_updateDishMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });
        btn_choose_image_updateDishMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhoto();
            }
        });
        img_back_updateDishMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void addControls() {
        edt_quantity_updateDishMenu = findViewById(R.id.edt_quantity_updateDishMenu);
        img_back_updateDishMenu = findViewById(R.id.img_back_updateDishMenu);
        img_updateDishMenu = findViewById(R.id.img_updateDishMenu);
        btn_choose_image_updateDishMenu = findViewById(R.id.btn_choose_image_updateDishMenu);
        edt_description_updateDishMenu = findViewById(R.id.edt_description_updateDishMenu);
        edt_price_updateDishMenu = findViewById(R.id.edt_price_updateDishMenu);
        btn_camera_updateDishMenu = findViewById(R.id.btn_camera_updateDishMenu);
        edt_name_updateDishMenu = findViewById(R.id.edt_name_updateDishMenu);
        btn_updateDishMenu = findViewById(R.id.btn_updateDishMenu);
    }

    private void loadData() {
        ConnectionClass sql = new ConnectionClass();
        connection = sql.conClass();
        if (connection != null) {
            try {
                String query = "SELECT item_name,description,price,image,quantity FROM Items WHERE item_id = " + itemID;
                Statement smt = connection.createStatement();
                ResultSet resultSet = smt.executeQuery(query);
                while (resultSet.next()) {
                    edt_name_updateDishMenu.setText(resultSet.getString(1));
                    edt_description_updateDishMenu.setText(resultSet.getString(2));
                    edt_price_updateDishMenu.setText(resultSet.getString(3));
                    img_updateDishMenu.setImageBitmap(getImageViewFromByteArray(resultSet.getBytes(4)));
                    edt_quantity_updateDishMenu.setText(resultSet.getString(5));
                }
                connection.close();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
        } else {
            Log.e("Error: ", "Connection null");
        }
    }

    private void insertDB() {
        // Lấy chuỗi tên danh mục từ EditText
        String dishName = edt_name_updateDishMenu.getText().toString().trim();
        String description = edt_description_updateDishMenu.getText().toString().trim();
        Double price = Double.parseDouble(edt_price_updateDishMenu.getText().toString().trim());
        Integer quantity = Integer.parseInt(edt_quantity_updateDishMenu.getText().toString().trim());
        byte[] anh = getByteArrayFromImageView(img_updateDishMenu);
        String status = "inactive";
        ConnectionClass sql = new ConnectionClass();
        connection = sql.conClass();
        if (connection != null) {
            try {
                String query = "UPDATE Items SET item_name = ?,description = ?,price = ?,image = ?,quantity = ?,status = ?, updated_at = ? WHERE item_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, dishName);
                preparedStatement.setString(2, description);
                preparedStatement.setDouble(3, price);
                preparedStatement.setBytes(4, anh);
                preparedStatement.setInt(5, quantity);
                preparedStatement.setString(6, status);
                preparedStatement.setTimestamp(7, new java.sql.Timestamp(System.currentTimeMillis()));
                preparedStatement.setInt(8, itemID);
                // Thực thi truy vấn INSERT
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    Log.d("UpdateDishMenuActivity", "Update successfully");
                    // Gọi finish() để đóng activity sau khi chèn thành công
                    finish();
                } else {
                    Log.e("UpdateDishMenuActivity", "Update failed");
                }
                connection.close();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
        } else {
            Log.e("Error: ", "Connection null");
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
        if (ActivityCompat.checkSelfPermission(UpdateDishMenuActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(UpdateDishMenuActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
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
            img_updateDishMenu.setImageBitmap(imageBitmap);
        } else if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            try {
                Uri imageUri = data.getData();
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                img_updateDishMenu.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}