package com.example.grab_demo.store_owner.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.grab_demo.R;


public class TaiKhoanStoreOwnerFragment extends Fragment {
    Button btnEditProfile ;
    Button btnRegisterStore ;
    Button btnLogout ;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tai_khoan_store_owner, container, false);
        addControls();
        addEvents();
        return view;
    }

    private void addEvents() {
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện chỉnh sửa thông tin cá nhân
            }
        });

        btnRegisterStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện đăng ký cửa hàng
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện đăng xuất
            }
        });
    }

    private void addControls() {
        btnEditProfile = view.findViewById(R.id.btn_edit_profile_storeowner);
        btnRegisterStore = view.findViewById(R.id.btn_register_store_storeowner);
        btnLogout = view.findViewById(R.id.btn_logout_storeowner);
    }
}