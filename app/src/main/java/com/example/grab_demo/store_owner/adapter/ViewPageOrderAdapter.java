package com.example.grab_demo.store_owner.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.grab_demo.store_owner.fragment.HistoryOrderHSOFragment;
import com.example.grab_demo.store_owner.fragment.NewOrderHSOFragment;


public class ViewPageOrderAdapter extends FragmentStatePagerAdapter {
    public ViewPageOrderAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new NewOrderHSOFragment();
            case 1:
                return new HistoryOrderHSOFragment();
            default:
                return new NewOrderHSOFragment();
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
                title = "Mới";
                break;
            case 1:
                title = "Lịch sử";
                break;
        }
        return title;
    }
}