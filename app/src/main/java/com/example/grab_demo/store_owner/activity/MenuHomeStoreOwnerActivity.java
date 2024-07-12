package com.example.grab_demo.store_owner.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.grab_demo.R;
import com.example.grab_demo.store_owner.adapter.ViewPageMenuAdapter;
import com.example.grab_demo.store_owner.fragment.DishMenuHomeStoreOwnerFragment;
import com.google.android.material.tabs.TabLayout;

public class MenuHomeStoreOwnerActivity extends AppCompatActivity {

    ImageButton img_back_menuHSO;
    String storeId;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_home_store_owner);
        addControls();
        addEvents();
        storeId = getIntent().getStringExtra("store_id");
        Log.d("MenuHomeStoreOwnerActivity", "Received storeId: " + storeId);
        // Lấy chỉ số fragment từ intent
        sendDatatoFragment();
    }

    private void addEvents() {
        img_back_menuHSO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void sendDatatoFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.viewpager_MenuHSO, new DishMenuHomeStoreOwnerFragment());
        fragmentTransaction.commit();
    }

    public String getStoreId() {
        return storeId;
    }

    private void addControls() {
        tabLayout = findViewById(R.id.tablayout_MenuHSO);
        viewPager = findViewById(R.id.viewpager_MenuHSO);
        img_back_menuHSO = findViewById(R.id.img_back_menuHSO);

        ViewPageMenuAdapter viewPageMenuAdapter = new ViewPageMenuAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPageMenuAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}