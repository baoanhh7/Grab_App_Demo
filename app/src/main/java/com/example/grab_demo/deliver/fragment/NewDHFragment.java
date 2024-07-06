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

public class NewDHFragment extends Fragment {

    private RecyclerView recyclerView;
    private DanhSachDonHangAdapter adapter;
    private ArrayList<DonHangModel> mangDonHang;

    public static NewDHFragment newInstance() {
        return new NewDHFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_d_h, container, false);

        recyclerView = view.findViewById(R.id.recyclerviewdashboard);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Dummy data for testing
        mangDonHang = new ArrayList<>();
        mangDonHang.add(new DonHangModel("Đơn hàng 1", "Mã đơn 1", "https://via.placeholder.com/65"));
        mangDonHang.add(new DonHangModel("Đơn hàng 2", "Mã đơn 2", "https://via.placeholder.com/65"));
        mangDonHang.add(new DonHangModel("Đơn hàng 2", "Mã đơn 2", "https://via.placeholder.com/65"));
        mangDonHang.add(new DonHangModel("Đơn hàng 2", "Mã đơn 2", "https://via.placeholder.com/65"));
        mangDonHang.add(new DonHangModel("Đơn hàng 2", "Mã đơn 2", "https://via.placeholder.com/65"));
        adapter = new DanhSachDonHangAdapter(getContext(), mangDonHang);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
