package com.example.grab_demo.deliver.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grab_demo.R;
import com.example.grab_demo.deliver.Adapter.DanhSachDonHangAdapter;
import com.example.grab_demo.model.DonHangModel;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private RecyclerView recyclerView;
    private DanhSachDonHangAdapter adapter;
    private ArrayList<DonHangModel> mangDonHang;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerviewdashboard);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        mangDonHang = new ArrayList<>();
        mangDonHang.add(new DonHangModel("Đơn hàng 1", "DH1", "https://th.bing.com/th/id/OIP.Kn8g4YmsfjJtuX9tgts38AHaE6?w=312&h=180&c=7&r=0&o=5&dpr=1.3&pid=1.7"));
//        mangDonHang.add(new DonHangModel("Đơn hàng 2", "Mã đơn 2", "https://example.com/hinh2.jpg"));
//        mangDonHang.add(new DonHangModel("Đơn hàng 3", "Mã đơn 3", "https://example.com/hinh3.jpg"));
        adapter = new DanhSachDonHangAdapter(getContext(), mangDonHang);
        recyclerView.setAdapter(adapter);
    }
}
