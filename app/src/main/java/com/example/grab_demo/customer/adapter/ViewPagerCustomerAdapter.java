package com.example.grab_demo.customer.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.grab_demo.customer.fragment.ChatFragment;
import com.example.grab_demo.customer.fragment.HomeFragment;
import com.example.grab_demo.customer.fragment.OrderFragment;
import com.example.grab_demo.customer.fragment.OrderHistoryFragment;
import com.example.grab_demo.customer.fragment.ProfileFragment;

public class ViewPagerCustomerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerCustomerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new OrderFragment();
            case 2:
                return new OrderHistoryFragment();
            case 3:
                return new ChatFragment();
            case 4:
                return new ProfileFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getCount() {
        return 5;
    }
}




