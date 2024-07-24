package com.example.grab_demo.store_owner.adapter;

import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
        switch (position) {
            case 0:
                return new HomeStoreOwnerFragment();
            case 1:
                return new MessageStoreOwnerFragment();
            case 2:
                return new TaiKhoanStoreOwnerFragment();
            default:
                return new HomeStoreOwnerFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
//        return super.getItemPosition(object);
    }

    @Override
    public void restoreState(@Nullable Parcelable state, @Nullable ClassLoader loader) {
//        super.restoreState(state, loader);
        try {
            super.restoreState(state, loader);
        } catch (IllegalStateException e) {
            Log.e("YourTag", "Failed to restore state: " + e.getMessage());
        }
    }
}
