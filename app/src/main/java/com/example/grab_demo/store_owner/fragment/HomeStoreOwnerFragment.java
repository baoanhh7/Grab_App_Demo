package com.example.grab_demo.store_owner.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.grab_demo.R;
import com.example.grab_demo.store_owner.adapter.ImageSliderAdapter_Home;

import java.util.Timer;
import java.util.TimerTask;


public class HomeStoreOwnerFragment extends Fragment {

    private int currentPage = 0;
    private Timer timer;
    private ViewPager viewPager;
    private View view;
    private int[] images = { R.drawable.voucher2, R.drawable.voucher3,R.drawable.voucher4};

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home_store_owner, container, false);
        addControls();
        addEvents();
        startAutoSlide();
        return view;
    }
    private void startAutoSlide() {
        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            public void run() {
                if (currentPage == images.length) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(update);
            }
        }, 1000, 3000); // Delay 1 sec, repeat every 3 sec
    }
    private void addEvents() {
    }

    private void addControls() {
        viewPager = view.findViewById(R.id.viewPager_HomeStoreOwner);
        ImageSliderAdapter_Home imageSliderAdapterHome = new ImageSliderAdapter_Home(getContext(), images);
        viewPager.setAdapter(imageSliderAdapterHome);
    }
}