package com.example.grab_demo.store_owner.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.grab_demo.store_owner.fragment.HomeStoreOwnerFragment;
import com.example.grab_demo.store_owner.fragment.MessageStoreOwnerFragment;
import com.example.grab_demo.store_owner.fragment.TaiKhoanStoreOwnerFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        // Tạo fragment và truyền userId qua Bundle
//        Bundle bundle = new Bundle();
//        bundle.putString("user_id", userId);
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new HomeStoreOwnerFragment();
            case 1:
                fragment = new MessageStoreOwnerFragment();
            case 2:
                fragment = new TaiKhoanStoreOwnerFragment();
            default:
                fragment = new HomeStoreOwnerFragment();
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
