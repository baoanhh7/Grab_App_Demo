package com.example.grab_demo.store_owner.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.grab_demo.R;
import com.example.grab_demo.login.LoginActivity;
import com.example.grab_demo.store_owner.activity.RegisterStoreActivity;
import com.example.grab_demo.store_owner.activity.StoreOwnerActivity;


public class TaiKhoanStoreOwnerFragment extends Fragment {
    Button btnEditProfile;
    Button btnRegisterStore;
    Button btnLogout;
    View view;
    private StoreOwnerActivity storeOwnerActivity;
    private String userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tai_khoan_store_owner, container, false);
        if (getActivity() instanceof StoreOwnerActivity) {
            storeOwnerActivity = (StoreOwnerActivity) getActivity();
            userId = storeOwnerActivity.getUserId();
        }
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
                // Tạo Intent để chuyển sang Activity mới
                Intent intent = new Intent(requireContext(), RegisterStoreActivity.class);

                // Đính kèm dữ liệu vào Intent
                intent.putExtra("user_id", userId);

                // Chuyển sang Activity mới
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện đăng xuất
                startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().finish();
            }
        });
    }

    private void addControls() {
        btnEditProfile = view.findViewById(R.id.btn_edit_profile_storeowner);
        btnRegisterStore = view.findViewById(R.id.btn_register_store_storeowner);
        btnLogout = view.findViewById(R.id.btn_logout_storeowner);
    }
}