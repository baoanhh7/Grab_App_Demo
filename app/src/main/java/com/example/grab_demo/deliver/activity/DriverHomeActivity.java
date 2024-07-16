package com.example.grab_demo.deliver.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.grab_demo.R;
import com.example.grab_demo.deliver.fragment.DashboardFragment;
import com.example.grab_demo.deliver.fragment.HomeFragment;
import com.example.grab_demo.deliver.fragment.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DriverHomeActivity extends AppCompatActivity {

    private String userId;

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    if (item.getItemId() == R.id.navigation_home) {
                        selectedFragment = HomeFragment.newInstance(userId); // Pass userId
                    } else if (item.getItemId() == R.id.navigation_dashboard) {
                        selectedFragment = DashboardFragment.newInstance(userId); // Pass userId
                    } else if (item.getItemId() == R.id.navigation_notifications) {
                        selectedFragment = NotificationsFragment.newInstance(userId); // Pass userId
                    }
                    if (selectedFragment != null) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,
                                selectedFragment).commit();
                    }

                    return true;
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_home);

        userId = getIntent().getStringExtra("user_id");

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        // Set HomeFragment as default
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,
                    HomeFragment.newInstance(userId)).commit(); // Pass userId
        }
    }
}
