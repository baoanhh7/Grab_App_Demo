package com.example.grab_demo.customer.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.grab_demo.R;
import com.example.grab_demo.customer.adapter.ViewPagerCustomerAdapter;
import com.example.grab_demo.customer.fragment.HomeFragment;
import com.example.grab_demo.database.ConnectionClass;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class HomeActivity extends AppCompatActivity {
    Connection connection;
    String query;
    Statement smt;
    ResultSet resultSet;
    String userId = "";

    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        addControls();
        // Nhận user_id từ intent
        userId = getIntent().getStringExtra("user_id");
        Log.d("HomeActivity", "Received user_id: " + userId);

        // Lưu user_id vào SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("user_id", Integer.parseInt(userId));
        editor.apply();

//        addDataToDatabase();
        addEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public String getUserId() {
        return userId;
    }

    private void sendDatatoFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.viewpager_customer, new HomeFragment());
        fragmentTransaction.commit();
    }

    private void addEvents() {

    }

    private void addDataToDatabase() {
        ConnectionClass sql = new ConnectionClass();
        connection = sql.conClass();

        if (connection != null) {
            try {
                byte[] imageChicken = getByteArrayFromDrawable(this, R.drawable.chickenfood);
                byte[] imageBurger = getByteArrayFromDrawable(this, R.drawable.burgerfood);
                byte[] imageRice = getByteArrayFromDrawable(this, R.drawable.ricefood);
                byte[] imageNoodle = getByteArrayFromDrawable(this, R.drawable.noodle);

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

                // Thực thi tất cả các câu lệnh INSERT
                int[] rowsAffected = preparedStatement.executeBatch();
                connection.close();

                // Kiểm tra kết quả
                boolean success = true;
                for (int count : rowsAffected) {
                    if (count <= 0) {
                        success = false;
                        break;
                    }
                }
                if (success) {
                    Log.d("HomeFragment", "Insert successfully");
                } else {
                    Log.e("HomeFragment", "Insert failed");
                }
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
        } else {
            Log.e("Error: ", "Connection null");
        }
    }

    private void addControls() {
        viewPager = findViewById(R.id.viewpager_customer);
        bottomNavigationView = findViewById(R.id.bn_customer);

        ViewPagerCustomerAdapter adapter = new ViewPagerCustomerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(5);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Do nothing
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.menu_customer_home).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.menu_customer_order).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.menu_customer_order_history).setChecked(true);
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.menu_customer_chat).setChecked(true);
                        break;
                    case 4:
                        bottomNavigationView.getMenu().findItem(R.id.menu_customer_profile).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // Do nothing
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemID = menuItem.getItemId();
                if (itemID == R.id.menu_customer_home) {
                    viewPager.setCurrentItem(0);
                } else if (itemID == R.id.menu_customer_order) {
                    viewPager.setCurrentItem(1);
                } else if (itemID == R.id.menu_customer_order_history) {
                    viewPager.setCurrentItem(2);
                } else if (itemID == R.id.menu_customer_chat) {
                    viewPager.setCurrentItem(3);
                } else if (itemID == R.id.menu_customer_profile) {
                    viewPager.setCurrentItem(4);
                }
                return true;
            }
        });
    }

    private byte[] getByteArrayFromDrawable(Context context, int drawableId) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableId);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        return outputStream.toByteArray();
    }
}