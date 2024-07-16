package com.example.grab_demo.store_owner.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grab_demo.R;
import com.example.grab_demo.database.ConnectionClass;
import com.example.grab_demo.store_owner.adapter.rcv_listitemsold_monthAdapter;
import com.example.grab_demo.store_owner.adapter.rcv_name_piechart_monthAdapter;
import com.example.grab_demo.store_owner.model.DishMenuHSO;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class Revenue_Month extends AppCompatActivity {
    TextView tv_revenue_month, total_amount_month;
    PieChart pie_chart_month;
    ImageView back_button;
    RecyclerView rcv_name_piechart_month, rcv_listitemsold_month;
    Connection connection;
    ArrayList<DishMenuHSO> arr, arr1;
    Spinner SP_revenuemonth;
    String storeID;
    Integer storeid;
    int month, getMonth, year;
    rcv_listitemsold_monthAdapter rcv_listitemsold_monthAdapter;
    rcv_name_piechart_monthAdapter rcv_name_piechart_monthAd;
    List<Integer> listmonth = new ArrayList<>();
    Double totalAmountMonth = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revenue_month);
        // Ensure userId is not null before using
        storeID = getIntent().getStringExtra("store_id");
        Log.d("Revenue_Month", "Received storeId: " + storeID);
        if (storeID != null) {
            storeid = Integer.parseInt(storeID);
            addControls();
            addEvents();
            getYearMonthDay();
            createDataSpinnerMonthData();
            loadData();
        } else {
            Log.e("Revenue_Month", "storeID is null");
        }
    }

    private void loadData() {
        ConnectionClass sql = new ConnectionClass();
        connection = sql.conClass();
        loadDataNameItem(connection);
        loadDataItem(connection);
        setData();
        total_amount_month.setText(totalAmountMonth + " đ");
    }

    private void loadDataItem(Connection connection) {
        // Tạo CallableStatement để gọi stored procedure
        Log.e("SQL", "GetSoldItemsInMonth");
        if (connection != null) {
            try {
                String sql = "EXEC GetSoldItemsInMonth ?, ?,?"; // Thay thế tên stored procedure và tham số tương ứng
                PreparedStatement stmt = connection.prepareStatement(sql);
                Log.d("SQL", sql);
                stmt.setInt(1, getMonth);
                stmt.setInt(2, year);
                stmt.setInt(3, storeid);
                ResultSet rs = stmt.executeQuery();
                arr.clear();
                // Xử lý kết quả trả về
                while (rs.next()) {
                    String itemName = rs.getString("item_name");
                    Log.d("itemName", itemName);
                    byte[] image = rs.getBytes(2);
                    Double gia = rs.getDouble("price");
                    Integer SL = rs.getInt("total_quantity_sold");
                    arr.add(new DishMenuHSO(image, itemName, gia, SL));
                    totalAmountMonth += SL * gia;
                }
                // Đóng kết nối và các tài nguyên
                rs.close();
                stmt.close();
                rcv_listitemsold_monthAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

        } else {
            Log.e("Error: ", "Connection null");
        }
    }

    private void loadDataNameItem(Connection connection) {
        if (connection != null) {
            try {
                String sql = "EXEC GetItemNamesFromOrderDetails ?, ?,?";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setInt(1, getMonth);
                stmt.setInt(2, year);
                stmt.setInt(3, storeid);
                ResultSet rs = stmt.executeQuery();
                arr1.clear();
                while (rs.next()) {
                    String itemName = rs.getString("item_name");
                    Integer color = getRandomColor();
                    arr1.add(new DishMenuHSO(itemName, color));
                    Log.d("Color", color + "");
                }
                rcv_name_piechart_monthAd.notifyDataSetChanged();
                rs.close();
                stmt.close();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
        } else {
            Log.e("Error: ", "Connection null");
        }
    }

    private void addEvents() {
        SP_revenuemonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getMonth = listmonth.get(position);
                tv_revenue_month.setText(getMonth + "");
                loadData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void createDataSpinnerMonthData() {
        for (int i = 1; i <= 12; i++) {
            listmonth.add(i);
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listmonth);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SP_revenuemonth.setAdapter(adapter);
    }

    private void addControls() {
        tv_revenue_month = findViewById(R.id.tv_revenue_month);
        total_amount_month = findViewById(R.id.total_amount_month);
        pie_chart_month = findViewById(R.id.pie_chart_month);
        back_button = findViewById(R.id.back_button);
        rcv_name_piechart_month = findViewById(R.id.rcv_name_piechart_month);
        rcv_listitemsold_month = findViewById(R.id.rcv_listitemsold_month);
        SP_revenuemonth = findViewById(R.id.SP_revenuemonth);
        arr = new ArrayList<>();
        arr1 = new ArrayList<>();
        rcv_listitemsold_monthAdapter = new rcv_listitemsold_monthAdapter(this, arr);
        rcv_listitemsold_month.setAdapter(rcv_listitemsold_monthAdapter);
        rcv_listitemsold_month.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rcv_name_piechart_monthAd = new rcv_name_piechart_monthAdapter(this, arr1);
        rcv_name_piechart_month.setAdapter(rcv_name_piechart_monthAd);
        rcv_name_piechart_month.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
    }

    private void getYearMonthDay() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1; // 0 (January) to 11 (December), so add 1
    }

    private int getRandomColor() {
        Random random = new Random();
        return Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    private void setData() {
        List<Integer> colors = new ArrayList<>();
        for (DishMenuHSO dishMenuHSO : arr1) {
            colors.add(dishMenuHSO.getColors());
        }
        for (int i = 0; i < arr.size(); i++) {
            DishMenuHSO dishMenuHSO = arr.get(i);
            Log.d("Color", dishMenuHSO.getColors() + "");
            pie_chart_month.addPieSlice(
                    new PieModel(
                            dishMenuHSO.getTensp(),
                            (float) (dishMenuHSO.getSoluong() * dishMenuHSO.getGiasp()),
                            colors.get(i)));
        }

        // To animate the pie chart
        pie_chart_month.startAnimation();
    }
}
