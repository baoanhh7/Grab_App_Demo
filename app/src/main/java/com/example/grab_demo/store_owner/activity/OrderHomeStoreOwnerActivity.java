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
import com.example.grab_demo.store_owner.adapter.ViewPageOrderAdapter;
import com.example.grab_demo.store_owner.fragment.NewOrderHSOFragment;
import com.google.android.material.tabs.TabLayout;

public class OrderHomeStoreOwnerActivity extends AppCompatActivity {
    ImageButton img_back_orderHSO;
    int storeID;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_home_store);
        storeID = Integer.parseInt(getIntent().getStringExtra("store_id"));
        Log.d("OrderHomeStoreOwnerActivity", "Received store_id: " + storeID);
        addControls();
        addEvents();
        sendDatatoFragment();
    }

    private void sendDatatoFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.viewpager_OrderHSO, new NewOrderHSOFragment());
        fragmentTransaction.commit();
    }

    public int getStoreID() {
        return storeID;
    }

    private void addEvents() {
        img_back_orderHSO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(OrderHomeStoreOwnerActivity.this, StoreOwnerActivity.class));
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