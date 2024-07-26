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
import android.widget.Toast;

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

public class AddDishMenuActivity extends AppCompatActivity {
    TextInputEditText edt_name_addDishMenu, edt_description_addDishMenu, edt_price_addDishMenu, edt_quantity_addDishMenu;
    ImageButton img_back_addDishMenu, btn_camera_addDishMenu;
    ImageView img_addDishMenu;
    Button btn_choose_image_addDishMenu, btn_addDishMenu;
    String storeID;
    Connection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dish_menu);
        storeID = getIntent().getStringExtra("store_id");
        Log.d("AddDishMenuActivity", "Received storeId: " + storeID);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btn_addDishMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertDB();
            }
        });
        btn_camera_addDishMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });
        btn_choose_image_addDishMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhoto();
            }
        });
        img_back_addDishMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void addControls() {
        edt_name_addDishMenu = findViewById(R.id.edt_name_addDishMenu);
        img_back_addDishMenu = findViewById(R.id.img_back_addDishMenu);
        img_addDishMenu = findViewById(R.id.img_addDishMenu);
        btn_choose_image_addDishMenu = findViewById(R.id.btn_choose_image_addDishMenu);
        edt_description_addDishMenu = findViewById(R.id.edt_description_addDishMenu);
        edt_price_addDishMenu = findViewById(R.id.edt_price_addDishMenu);
        btn_camera_addDishMenu = findViewById(R.id.btn_camera_addDishMenu);
        btn_addDishMenu = findViewById(R.id.btn_addDishMenu);
        edt_quantity_addDishMenu = findViewById(R.id.edt_quantity_addDishMenu);
    }

    private void insertDB() {
        // Lấy chuỗi tên danh mục từ EditText
        String dishName = edt_name_addDishMenu.getText().toString().trim();
        String description = edt_description_addDishMenu.getText().toString().trim();
        Double price = Double.parseDouble(edt_price_addDishMenu.getText().toString().trim());
        Integer quantity = Integer.parseInt(edt_quantity_addDishMenu.getText().toString().trim());
        Integer storeid = Integer.parseInt(storeID);
        byte[] anh = getByteArrayFromImageView(img_addDishMenu);
        String status = "inactive";
        if (price <= 0 || quantity <= 0) {
            Toast.makeText(this, "price, quantity must be greater than 0", Toast.LENGTH_SHORT).show();
            return;
        }
        ConnectionClass sql = new ConnectionClass();
        connection = sql.conClass();
        if (connection != null) {
            try {
                String query = "INSERT INTO Items(item_name,description,price,image,quantity,store_id,status) VALUES (?,?,?,?,?,?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, dishName);
                preparedStatement.setString(2, description);
                preparedStatement.setDouble(3, price);
                preparedStatement.setBytes(4, anh);
                preparedStatement.setInt(5, quantity);
                preparedStatement.setInt(6, storeid);
                preparedStatement.setString(7, status);
                // Thực thi truy vấn INSERT
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    Log.d("AddDishMenuActivity", "Insert successfully");
                    // Gọi finish() để đóng activity sau khi chèn thành công
                    finish();
                } else {
                    Log.e("AddDishMenuActivity", "Insert failed");
                }
                connection.close();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
        } else {
            Log.e("Error: ", "Connection null");
        }
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
        if (ActivityCompat.checkSelfPermission(AddDishMenuActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddDishMenuActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
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
            img_addDishMenu.setImageBitmap(imageBitmap);
        } else if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            try {
                Uri imageUri = data.getData();
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                img_addDishMenu.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}