package com.example.grab_demo.deliver.activity;

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

public class RegisterDeliverActivity extends AppCompatActivity {

    ImageView img_avatar, img_back;
    Button btn_choose_image, btn_register;
    ImageButton btn_camera;
    TextInputEditText edt_name, edt_email, edt_phone, edt_password, edt_rePassword;
    Connection connection;
    String userType = "delivery"; // User type for delivery

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_deliver);

        addControls();
        addEvents();
    }

    private void addControls() {
        btn_register = findViewById(R.id.btn_createAcount);
        img_back = findViewById(R.id.img_back);
        edt_name = findViewById(R.id.edt_name);
        edt_email = findViewById(R.id.edt_email);
        edt_phone = findViewById(R.id.edt_phone);
        edt_password = findViewById(R.id.edt_password);
        edt_rePassword = findViewById(R.id.edt_rePassword);
    }

    private void addEvents() {
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Close activity on back button press
            }
        });
    }

    private void insertData() {
        byte[] avatar = getByteArrayFromImageView(img_avatar);
        String name = edt_name.getText().toString().trim();
        String email = edt_email.getText().toString().trim();
        String phone = edt_phone.getText().toString().trim();
        String password = edt_password.getText().toString().trim();
        String rePassword = edt_rePassword.getText().toString().trim();

        if (!password.equals(rePassword)) {
            // Show error message if passwords do not match
            Toast.makeText(RegisterDeliverActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            Log.e("RegisterDeliverActivity", "Passwords do not match");
            return;
        }

        ConnectionClass sql = new ConnectionClass();
        connection = sql.conClass();
        if (connection != null) {
            try {
                String query = "INSERT INTO Users(username, password, email, phone_number, user_type, image) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, phone);
                preparedStatement.setString(5, userType);
                preparedStatement.setBytes(6, avatar);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    Log.d("RegisterDeliverActivity", "Insert successfully");
                    Toast.makeText(RegisterDeliverActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    finish(); // Close activity after successful registration
                } else {
                    Log.e("RegisterDeliverActivity", "Insert failed");
                    Toast.makeText(RegisterDeliverActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                }
                connection.close();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
                Toast.makeText(RegisterDeliverActivity.this, "An error occurred: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.e("Error: ", "Connection null");
            Toast.makeText(RegisterDeliverActivity.this, "Database connection failed", Toast.LENGTH_SHORT).show();
        }
    }

    private byte[] getByteArrayFromImageView(ImageView img) {
        BitmapDrawable drawable = (BitmapDrawable) img.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(ACTION_IMAGE_CAPTURE);
        if (ActivityCompat.checkSelfPermission(RegisterDeliverActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(RegisterDeliverActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
            return;
        }
        startActivityForResult(takePictureIntent, 99);
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
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            img_avatar.setImageBitmap(imageBitmap);
        } else if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            try {
                Uri imageUri = data.getData();
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                img_avatar.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
