package com.example.grab_demo.customer.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.grab_demo.R;
import com.example.grab_demo.customer.adapter.Home.ViewPagerCustomerAdapter;
import com.example.grab_demo.customer.fragment.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
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
        // Lấy chỉ số fragment từ intent
        sendDatatoFragment();

        createData();
        addEvents();
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

    private void createData() {


    }

    private void addControls() {
        viewPager = findViewById(R.id.viewpager_customer);
        bottomNavigationView = findViewById(R.id.bn_customer);
        ViewPagerCustomerAdapter adapter = new ViewPagerCustomerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

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
}