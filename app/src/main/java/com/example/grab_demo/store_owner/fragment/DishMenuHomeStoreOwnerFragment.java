package com.example.grab_demo.store_owner.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.grab_demo.R;

public class DishMenuHomeStoreOwnerFragment extends Fragment {
    View view ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_dish_menu_home_store_owner, container, false);
        return  view;
    }
}