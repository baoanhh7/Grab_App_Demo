package com.example.grab_demo.store_owner.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grab_demo.R;


public class MessageStoreOwnerFragment extends Fragment {

    View view;
    SearchView searchView_ShopHSO;
    RecyclerView recycleView_messages_storeowner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_message_store_owner, container, false);
        return view;
    }
}