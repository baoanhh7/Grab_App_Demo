package com.example.grab_demo.admin.sales.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grab_demo.R;
import com.example.grab_demo.database.ConnectionClass;
import com.google.android.material.textfield.TextInputEditText;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class UpdateVoucherSalesActivity extends AppCompatActivity {
    TextInputEditText edt_name_updateVoucherSales, edt_condition_updateVoucherSales, edt_discount_updateVoucherSales,
            edt_startdate_updateVoucherSales, edt_enddate_updateVoucherSales, edt_quantity_updateVoucherSales;
    ImageButton img_back_updateVoucherSales;
    Button btn_updateVoucherSales;
    int voucherID;
    Connection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_voucher_sales);
        voucherID = getIntent().getIntExtra("voucherID", 0);
        addControls();
        addEvents();
        loadData();
    }

    private void loadData() {
        ConnectionClass sql = new ConnectionClass();
        connection = sql.conClass();
        if (connection != null) {
            try {
                String query = "SELECT voucher_name,condition,discount,start_date,end_date,quantity FROM Vouchers WHERE voucher_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, voucherID);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    edt_name_updateVoucherSales.setText(resultSet.getString(1));
                    edt_condition_updateVoucherSales.setText(resultSet.getString(2));
                    edt_discount_updateVoucherSales.setText(resultSet.getString(3));
                    edt_startdate_updateVoucherSales.setText(resultSet.getString(4));
                    edt_enddate_updateVoucherSales.setText(resultSet.getString(5));
                    edt_quantity_updateVoucherSales.setText(resultSet.getString(6));
                }
                connection.close();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
        }
    }

    private void addEvents() {
        img_back_updateVoucherSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_updateVoucherSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });
        edt_startdate_updateVoucherSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date(edt_startdate_updateVoucherSales);
            }
        });
        edt_enddate_updateVoucherSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date(edt_enddate_updateVoucherSales);
            }
        });
    }

    private void Date(TextInputEditText edt) {
        // Lấy ngày hiện tại
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Tạo DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateVoucherSalesActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Định dạng ngày theo yêu cầu (yyyy-MM-dd)
                        String selectedDate = String.format(Locale.getDefault(), "%d-%02d-%02d", year, monthOfYear + 1, dayOfMonth);
                        if (edt == edt_enddate_updateVoucherSales) {
                            // Lấy ngày bắt đầu
                            String startDateStr = edt_startdate_updateVoucherSales.getText().toString();
                            try {
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                java.util.Date startDate = sdf.parse(startDateStr);
                                java.util.Date endDate = sdf.parse(selectedDate);

                                if (endDate.after(startDate)) {
                                    edt.setText(selectedDate);
                                } else {
                                    Toast.makeText(UpdateVoucherSalesActivity.this, "End date must be after start date", Toast.LENGTH_SHORT).show();
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                                Toast.makeText(UpdateVoucherSalesActivity.this, "Invalid date format", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            edt.setText(selectedDate);
                        }
                    }
                }, year, month, day);

        // Hiển thị DatePickerDialog
        datePickerDialog.show();

        // Hiển thị thông báo về định dạng ngày
        Toast.makeText(UpdateVoucherSalesActivity.this, "Select a date (yyyy-MM-dd)", Toast.LENGTH_SHORT).show();
    }

    private void updateData() {
        String name = edt_name_updateVoucherSales.getText().toString().trim();
        int condition = Integer.parseInt(edt_condition_updateVoucherSales.getText().toString().trim());
        Float discount = Float.valueOf(edt_discount_updateVoucherSales.getText().toString().trim());
        Date startdate = Date.valueOf(edt_startdate_updateVoucherSales.getText().toString().trim());
        Date enddate = Date.valueOf(edt_enddate_updateVoucherSales.getText().toString().trim());
        Integer quantity = Integer.parseInt(edt_quantity_updateVoucherSales.getText().toString().trim());
        if (discount <= 0 || quantity <= 0 || condition <= 0) {
            Toast.makeText(this, "Discount, quantity and condition must be greater than 0", Toast.LENGTH_SHORT).show();
            return;
        }
        ConnectionClass sql = new ConnectionClass();
        connection = sql.conClass();
        if (connection != null) {
            try {
                String query = "Update Vouchers set voucher_name=?,condition=?,discount = ?,start_date = ?,end_date = ?,quantity = ? where voucher_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, name);
                preparedStatement.setInt(2, condition);
                preparedStatement.setFloat(3, discount);
                preparedStatement.setDate(4, startdate);
                preparedStatement.setDate(5, enddate);
                preparedStatement.setInt(6, quantity);
                preparedStatement.setInt(7, voucherID);
                // Thực thi truy vấn INSERT
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    Log.d("AddVoucherSalesActivity", "Insert successfully");
                    // Gọi finish() để đóng activity sau khi chèn thành công
                    finish();
                } else {
                    Log.e("AddVoucherSalesActivity", "Insert failed");
                }
                connection.close();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
        } else {
            Log.e("Error: ", "Connection null");
        }
    }

    private void addControls() {
        edt_name_updateVoucherSales = findViewById(R.id.edt_name_updateVoucherSales);
        edt_condition_updateVoucherSales = findViewById(R.id.edt_condition_updateVoucherSales);
        edt_discount_updateVoucherSales = findViewById(R.id.edt_discount_updateVoucherSales);
        edt_startdate_updateVoucherSales = findViewById(R.id.edt_startdate_updateVoucherSales);
        edt_enddate_updateVoucherSales = findViewById(R.id.edt_enddate_updateVoucherSales);
        edt_quantity_updateVoucherSales = findViewById(R.id.edt_quantity_updateVoucherSales);
        img_back_updateVoucherSales = findViewById(R.id.img_back_updateVoucherSales);
        btn_updateVoucherSales = findViewById(R.id.btn_updateVoucherSales);
    }
}