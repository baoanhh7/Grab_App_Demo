package com.example.grab_demo.store_owner.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.grab_demo.R;
import com.example.grab_demo.store_owner.adapter.ViewPageOrderAdapter;
import com.google.android.material.tabs.TabLayout;

public class OrderHomeStoreOwnerActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ImageButton img_back_orderHSO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_home_store);
        addControls();
        addEvents();
    }

    private void addEvents() {
        img_back_orderHSO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void addControls() {
        tabLayout = findViewById(R.id.tablayout_OrderHSO);
        viewPager = findViewById(R.id.viewpager_OrderHSO);
        img_back_orderHSO = findViewById(R.id.img_back_orderHSO);
        ViewPageOrderAdapter viewPageOrderAdapter = new ViewPageOrderAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPageOrderAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}