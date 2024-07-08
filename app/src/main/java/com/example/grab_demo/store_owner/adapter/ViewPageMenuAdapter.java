package com.example.grab_demo.store_owner.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.grab_demo.store_owner.fragment.DishMenuHomeStoreOwnerFragment;
import com.example.grab_demo.store_owner.fragment.GroupDishMenuHSOFragment;


public class ViewPageMenuAdapter extends FragmentStatePagerAdapter {
    public ViewPageMenuAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new DishMenuHomeStoreOwnerFragment();
            case 1:
                return new GroupDishMenuHSOFragment();
            default:
                return new DishMenuHomeStoreOwnerFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "Món";
                break;
            case 1:
                title = " Tuỳ chọn món";
                break;
        }
        return title;
    }
}