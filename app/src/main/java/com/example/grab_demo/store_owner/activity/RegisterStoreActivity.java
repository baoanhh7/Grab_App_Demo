package com.example.grab_demo.store_owner.activity;

import static android.provider.MediaStore.ACTION_IMAGE_CAPTURE;

import android.Manifest;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
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
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class RegisterStoreActivity extends AppCompatActivity {

    ImageView img_registerstore;
    Button btn_choose_image_registerstore, btn_registerstore;
    ImageButton btn_camera_registerstore, img_back_registerstore;
    TextInputEditText edt_namestore_registerstore, edt_address_registerstore, edt_opened_registerstore, edt_closed_registerstore;
    Spinner sp_idCate_registerstore;
    Connection connection;
    ResultSet resultSet;
    Statement smt;
    List<String> listNameCate = new ArrayList<>();
    List<String> listIDCate = new ArrayList<>();
    String userId;
    String cateId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_store);
        userId = getIntent().getStringExtra("user_id");
        Log.d("RegisterStoreActivity", "Received user_id: " + userId);
        addControls();
        addEvents();
        createDataSpinner();
    }

    private void createDataSpinner() {
        ConnectionClass sql = new ConnectionClass();
        connection = sql.conClass();
        if (connection != null) {
            try {
                String query = "SELECT cate_id,cate_name FROM Categories";
                smt = connection.createStatement();
                resultSet = smt.executeQuery(query);
                while (resultSet.next()) {
                    cateId = resultSet.getString(1);
                    String cateName = resultSet.getString(2);
                    listNameCate.add(cateName);
                    listIDCate.add(cateId);
                }
                connection.close();
                ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listNameCate);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_idCate_registerstore.setAdapter(adapter);
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
        } else {
            Log.e("Error: ", "Connection null");
        }
    }

    private void addEvents() {
        btn_camera_registerstore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });
        btn_choose_image_registerstore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhoto();
            }
        });
        sp_idCate_registerstore.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cateId = listIDCate.get(position);
                Log.d("RegisterStoreActivity", cateId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btn_registerstore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });
        img_back_registerstore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        edt_opened_registerstore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Time(edt_opened_registerstore);
            }
        });
        edt_closed_registerstore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Time(edt_closed_registerstore);
            }
        });
    }

    private void addControls() {
        img_registerstore = findViewById(R.id.img_registerstore);
        btn_choose_image_registerstore = findViewById(R.id.btn_choose_image_registerstore);
        btn_registerstore = findViewById(R.id.btn_registerstore);
        btn_camera_registerstore = findViewById(R.id.btn_camera_registerstore);
        img_back_registerstore = findViewById(R.id.img_back_registerstore);
        edt_namestore_registerstore = findViewById(R.id.edt_namestore_registerstore);
        edt_address_registerstore = findViewById(R.id.edt_address_registerstore);
        edt_opened_registerstore = findViewById(R.id.edt_opened_registerstore);
        edt_closed_registerstore = findViewById(R.id.edt_closed_registerstore);
        sp_idCate_registerstore = findViewById(R.id.sp_idCate_registerstore);
    }

    private void Time(TextInputEditText edt) {
        // Lấy giờ hiện tại
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Tạo TimePickerDialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(RegisterStoreActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // Định dạng giờ theo yêu cầu (HH:mm)
                String selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);

                if (edt == edt_closed_registerstore) {
                    // Lấy giờ mở
                    String openedStr = edt_opened_registerstore.getText().toString();
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
                        java.util.Date openTime = sdf.parse(openedStr);
                        java.util.Date closeTime = sdf.parse(selectedTime);

                        if (closeTime.after(openTime)) {
                            edt.setText(selectedTime);
                        } else {
                            Toast.makeText(RegisterStoreActivity.this, "End time must be after start time", Toast.LENGTH_SHORT).show();
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                        Toast.makeText(RegisterStoreActivity.this, "Invalid time format", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    edt.setText(selectedTime);
                }
            }
        }, hour, minute, true);

        // Hiển thị TimePickerDialog
        timePickerDialog.show();

        // Hiển thị thông báo về định dạng giờ
        Toast.makeText(RegisterStoreActivity.this, "Select a time (HH:mm)", Toast.LENGTH_SHORT).show();
    }


    private void insertData() {
        byte[] anh = getByteArrayFromImageView(img_registerstore);
        // Lấy chuỗi tên danh mục từ EditText
        String storeName = edt_namestore_registerstore.getText().toString().trim();
        String address = edt_address_registerstore.getText().toString().trim();
        String openedStr = edt_opened_registerstore.getText().toString().trim();
        String closedStr = edt_closed_registerstore.getText().toString().trim();
        // Kiểm tra xem chuỗi có đúng định dạng không
        if (!openedStr.matches("\\d{2}:\\d{2}") || !closedStr.matches("\\d{2}:\\d{2}")) {
            Toast.makeText(this, "Invalid time format. Please use HH:mm", Toast.LENGTH_LONG).show();
            return;
        }
        Time opened;
        Time closed;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
            java.util.Date openDate = sdf.parse(openedStr);
            java.util.Date closeDate = sdf.parse(closedStr);
            opened = new Time(openDate.getTime());
            closed = new Time(closeDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, "Invalid time format", Toast.LENGTH_SHORT).show();
            return;
        }
        Integer cateid = Integer.parseInt(cateId);
        Integer userid = Integer.parseInt(userId);
        ConnectionClass sql = new ConnectionClass();
        connection = sql.conClass();
        if (connection != null) {
            try {
                String query = "INSERT INTO Stores(store_name,owner_id,status,cate_id,open_store,image,address,close_store) VALUES (?,?,?,?,?,?,?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, storeName);
                preparedStatement.setInt(2, userid);
                preparedStatement.setString(3, "pending");
                preparedStatement.setInt(4, cateid);
                preparedStatement.setTime(5, opened);
                preparedStatement.setBytes(6, anh);
                preparedStatement.setString(7, address);
                preparedStatement.setTime(8, closed);
                // Thực thi truy vấn INSERT
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    Log.d("RegisterStoreActivity", "Insert successfully");
                    // Gọi finish() để đóng activity sau khi chèn thành công
                    Intent intent = new Intent(RegisterStoreActivity.this, StoreOwnerActivity.class);
                    startActivity(intent);
                    finish();
                    // finish(); // Đóng activity hiện tại sau khi chuyển hướng
                } else {
                    Log.e("RegisterStoreActivity", "Insert failed");
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
        if (ActivityCompat.checkSelfPermission(RegisterStoreActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(RegisterStoreActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
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
            img_registerstore.setImageBitmap(imageBitmap);
        } else if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            try {
                Uri imageUri = data.getData();
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                img_registerstore.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }


}