package com.example.grab_demo.store_owner.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grab_demo.R;
import com.example.grab_demo.database.ConnectionClass;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RevenueHSOActivity extends AppCompatActivity {
    Spinner SP_startDay_revenueHSO, SP_endDay_revenueHSO;
    TextView tv_revenueHSO;
    String storeId;
    int month, day, daystart, year, dayend;
    Double revenue = 0.0;
    Connection connection;
    Statement smt;
    ResultSet resultSet;
    List<Integer> listDayStart = new ArrayList<>();
    List<Integer> listDayEnd = new ArrayList<>();
    int sDay;
    Button btn_revenueMonth;
    ImageView back_button_revenueHSO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revenue_hsoactivity);
        storeId = getIntent().getStringExtra("store_id");
        Log.d("RevenueHSOActivity", "Received storeId: " + storeId);
        addControls();
        addEvents();
        createDataSpinnerDayStart();
        getYearMonthDay();
        sDay = dayend - daystart;
    }

    private void addEvents() {
        SP_startDay_revenueHSO.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                daystart = listDayStart.get(position);
                Log.d("daystart", String.valueOf(daystart));
                createDataSpinnerDayEnd(daystart);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        SP_endDay_revenueHSO.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dayend = listDayEnd.get(position);
                revenue = 0.0;
                getRevenue();
                Log.d("RevenueYesterday", String.valueOf(revenue));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btn_revenueMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo Intent để chuyển sang Activity mới
                Intent intent = new Intent(RevenueHSOActivity.this, Revenue_Month.class);
                // Đính kèm dữ liệu vào Intent
                intent.putExtra("store_id", storeId);
                // Chuyển sang Activity mới
                startActivity(intent);
            }
        });
        back_button_revenueHSO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void addControls() {
        tv_revenueHSO = findViewById(R.id.tv_revenueHSO);
        SP_startDay_revenueHSO = findViewById(R.id.SP_startDay_revenueHSO);
        SP_endDay_revenueHSO = findViewById(R.id.SP_endDay_revenueHSO);
        btn_revenueMonth = findViewById(R.id.btn_revenueMonth);
        back_button_revenueHSO = findViewById(R.id.back_button_revenueHSO);
    }

    private void getRevenue() {
        ConnectionClass sql = new ConnectionClass();
        connection = sql.conClass();
        if (connection != null) {
            try {

                String query = "SELECT updated_at,delivery_price,total_price FROM Orders WHERE store_id = " + storeId;
                smt = connection.createStatement();
                resultSet = smt.executeQuery(query);
                while (resultSet.next()) {
                    java.sql.Timestamp sqlTimestamp = resultSet.getTimestamp(1);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(sqlTimestamp);
                    int yearNew = year - cal.get(Calendar.YEAR);
                    int monthNew = month - (cal.get(Calendar.MONTH) + 1); // Tháng trong Calendar bắt đầu từ 0
                    int dayNew = cal.get(Calendar.DAY_OF_MONTH);
                    Log.d("Year", String.valueOf(yearNew));
                    Log.d("Month", String.valueOf(monthNew));
                    Log.d("Day", String.valueOf(dayNew));
                    Log.d("DayEnd", String.valueOf(dayend));
                    if (yearNew == 0 && monthNew == 0 && dayend >= dayNew) {
                        BigDecimal total_price = resultSet.getBigDecimal(3);
                        BigDecimal delivery_price = resultSet.getBigDecimal(2);
                        if (total_price != null && delivery_price != null) {
                            revenue += (total_price.subtract(delivery_price).doubleValue());
                            Log.d("RevenueYesterday", String.valueOf(revenue));
                        } else {
                            Log.e("Error", "total_price or delivery_price is null");
                        }
                    }
                }
                tv_revenueHSO.setText(revenue + " đ");
                connection.close();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
        } else {
            Log.e("Error: ", "Connection null");
        }
    }

    private void getYearMonthDay() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1; // 0 (January) to 11 (December), so add 1
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    private void createDataSpinnerDayStart() {
        for (int i = 1; i <= 31; i++) {
            listDayStart.add(i);
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listDayStart);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SP_startDay_revenueHSO.setAdapter(adapter);
    }

    private void createDataSpinnerDayEnd(int day) {
        listDayEnd.clear();
        for (int i = day + 1; i <= 31; i++) {
            listDayEnd.add(i);
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listDayEnd);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SP_endDay_revenueHSO.setAdapter(adapter);
    }
}