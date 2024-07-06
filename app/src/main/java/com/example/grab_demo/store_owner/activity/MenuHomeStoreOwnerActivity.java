package com.example.grab_demo.store_owner.activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.grab_demo.R;
import com.example.grab_demo.store_owner.adapter.ViewPageMenuAdapter;
import com.google.android.material.tabs.TabLayout;

public class MenuHomeStoreOwnerActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_home_store_owner);
        addControls();
    }

    private void addControls() {
        tabLayout = findViewById(R.id.tablayout_MenuHSO);
        viewPager = findViewById(R.id.viewpager_MenuHSO);

        ViewPageMenuAdapter viewPageMenuAdapter = new ViewPageMenuAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPageMenuAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}