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

public class AddCateSalesActivity extends AppCompatActivity {
    ImageButton img_back_addCateSales, btn_camera_addCateSales;
    TextInputEditText edt_name_addCateSales;
    Button btn_addCateSales, btn_choose_image_addCateSales;
    Connection connection;
    String query;
    ImageView img_addCateSales;

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
        byte[] anh = getByteArrayFromImageView(img_addCateSales);
        ConnectionClass sql = new ConnectionClass();
        connection = sql.conClass();
        if (connection != null) {
            try {
                query = "INSERT INTO Categories(cate_name,cate_image) VALUES (?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, categoryName);
                preparedStatement.setBytes(2, anh);
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
        btn_choose_image_addCateSales = findViewById(R.id.btn_choose_image_addCateSales);
        img_addCateSales = findViewById(R.id.img_addCateSales);
        btn_camera_addCateSales = findViewById(R.id.btn_camera_addCateSales);
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
        if (ActivityCompat.checkSelfPermission(AddCateSalesActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddCateSalesActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
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
            img_addCateSales.setImageBitmap(imageBitmap);
        } else if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            try {
                Uri imageUri = data.getData();
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                img_addCateSales.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}