package com.example.grab_demo.store_owner.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.grab_demo.R;
import com.example.grab_demo.store_owner.adapter.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class StoreOwnerActivity extends AppCompatActivity {
private ViewPager viewPager;
private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_store_owner);
        addControls();
    }

    private void addControls() {
        viewPager = findViewById(R.id.viewpager_StoreOwner);
        bottomNavigationView = findViewById(R.id.BN_StoreOwner);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Được gọi khi trang đang được cuộn
                // Bạn có thể thực hiện các hành động khi trang đang được cuộn
            }

            @Override
            public void onPageSelected(int position) {
                // Được gọi khi trang mới đã được chọn
                // Cập nhật trạng thái của BottomNavigationView dựa trên trang hiện tại của ViewPager
                switch (position){
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.menu_home_storeowner).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.menu_taikhoan_storeowner).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // Được gọi khi trạng thái cuộn của trang thay đổi
                // Bạn có thể thực hiện các hành động tùy chỉnh dựa trên trạng thái cuộn
            }
        });


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemID =menuItem.getItemId();
                if(itemID == R.id.menu_home_storeowner){
                    viewPager.setCurrentItem(0);
                }else if(itemID == R.id.menu_taikhoan_storeowner){
                    viewPager.setCurrentItem(1);
                }
                return true;
            }
        });
    }
}