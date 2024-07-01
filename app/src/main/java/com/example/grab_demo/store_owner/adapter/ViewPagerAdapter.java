package com.example.grab_demo.store_owner.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.grab_demo.store_owner.fragment.HomeStoreOwnerFragment;
import com.example.grab_demo.store_owner.fragment.TaiKhoanStoreOwnerFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new HomeStoreOwnerFragment();
            case 1:
                return new TaiKhoanStoreOwnerFragment();
            default:
                return new HomeStoreOwnerFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
