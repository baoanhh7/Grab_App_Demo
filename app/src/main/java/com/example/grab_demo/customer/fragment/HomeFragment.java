package com.example.grab_demo.customer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grab_demo.R;
import com.example.grab_demo.customer.activity.HomeActivity;
import com.example.grab_demo.customer.activity.ProductListActivity;
import com.example.grab_demo.customer.adapter.Home.HomeAdapter;
import com.example.grab_demo.customer.adapter.Home.HomeSecondAdapter;
import com.example.grab_demo.customer.adapter.Home.HomeVoucherAdapter;
import com.example.grab_demo.customer.adapter.Home.OrderAgainAdapter;
import com.example.grab_demo.m_interface.IClickItem;
import com.example.grab_demo.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {
    RecyclerView rcv_header, rcv_second, rcv_voucher, rcv_orderAgain;
    List<Product> filterProduct, productList, productList2, productListVoucher, productListOderAgain;
    HomeAdapter homeAdapter;
    HomeSecondAdapter homeSecondAdapter;
    HomeVoucherAdapter homeVoucherAdapter;
    OrderAgainAdapter orderAgainAdapter;
    SearchView searchView;
    private View view;
    private int currentPage = 0;
    private Timer timer;
    private HomeActivity homeActivity;
    private String userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home2, container, false);

        addControls();
        createData();

        addEvents();
        startAutoSlide();
        if (getActivity() instanceof HomeActivity) {
            homeActivity = (HomeActivity) getActivity();
            userId = homeActivity.getUserId();
        }
        Log.e("HomeFragment", userId);
        return view;
    }

    private void startAutoSlide() {
        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            public void run() {
                if (currentPage == homeVoucherAdapter.getItemCount()) {
                    currentPage = 0;
                }
                rcv_voucher.smoothScrollToPosition(currentPage++);
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

    private void createData() {
        productList.clear();
        productList.add(new Product("Noodles & Congee", R.drawable.noodle));
        productList.add(new Product("International Food", R.drawable.food));
        productList.add(new Product("Healthy Food", R.drawable.healthy));
        productList.add(new Product("Pizza", R.drawable.pizza));
        homeAdapter.notifyDataSetChanged();

        productList2.clear();
        productList2.add(new Product("Near me", "Get it quick", R.color.hongNhat));
        productList2.add(new Product("Bửa tối nữa giá", "Chốt deal ngay!", R.color.lavender));
        productList2.add(new Product("Grab ngon rẻ", "Bao tiết kiệm", R.color.vangNhat));
        productList2.add(new Product("Tuần lễ món cơm", "Tặng món 0đ", R.color.xanhNhat));
        homeSecondAdapter.notifyDataSetChanged();

        productListVoucher.clear();
        productListVoucher.add(new Product("Voucher 1", "Get it quick", R.drawable.voucher4));
        productListVoucher.add(new Product("Voucher 2", "Chốt deal ngay!", R.drawable.voucher3));
        productListVoucher.add(new Product("Voucher 3", "Bao tiết kiệm", R.drawable.voucher2));
        productListVoucher.add(new Product("Voucher 4", "Tặng món 0đ", R.drawable.voucher1));
        homeVoucherAdapter.notifyDataSetChanged();

        productListOderAgain.clear();
        productListOderAgain.add(new Product("Pizza", "Bột pizza, sốt cà chua, phô mai", R.drawable.pizza, 10000));
        productListOderAgain.add(new Product("Japan noodle", "Bột mì, sup, egg", R.drawable.noodle, 20000));
        productListOderAgain.add(new Product("Kimbap", "Rice, carot, egg", R.drawable.food, 50000));
        productListOderAgain.add(new Product("Salad", "Rau, trái cây, hạt", R.drawable.healthy, 23456));
        productListOderAgain.add(new Product("Pizza", "Bột pizza, sốt cà chua, phô mai", R.drawable.pizza, 10000));
        productListOderAgain.add(new Product("Japan noodle", "Bột mì, sup, egg", R.drawable.noodle, 20000));
        productListOderAgain.add(new Product("Kimbap", "Rice, carot, egg", R.drawable.food, 50000));
        productListOderAgain.add(new Product("Salad", "Rau, trái cây, hạt", R.drawable.healthy, 23456));
        productListOderAgain.add(new Product("Pizza", "Bột pizza, sốt cà chua, phô mai", R.drawable.pizza, 10000));
        orderAgainAdapter.notifyDataSetChanged();
    }

    private void addEvents() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });

        homeAdapter.setOnItemClick(new IClickItem() {
            @Override
            public void onClickItem(String data) {
                Intent intent = new Intent(getActivity(), ProductListActivity.class);
                startActivity(intent);
            }
        });

        homeSecondAdapter.setOnClickItemListener(new IClickItem() {
            @Override
            public void onClickItem(String data) {
                Intent intent = new Intent(getActivity(), ProductListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void filter(String text) {
        filterProduct.clear();
        for (Product product : productList) {
            if (product.getName().toLowerCase().contains(text.toLowerCase())) {
                filterProduct.add(product);
            }
        }
        homeAdapter.filterList(filterProduct);
    }

    private void addControls() {
        filterProduct = new ArrayList<>();
        productList = new ArrayList<>();
        productList2 = new ArrayList<>();
        productListVoucher = new ArrayList<>();
        productListOderAgain = new ArrayList<>();

        rcv_header = view.findViewById(R.id.rcv_header);
        rcv_second = view.findViewById(R.id.rcv_second);
        rcv_voucher = view.findViewById(R.id.rcv_voucher);
        rcv_orderAgain = view.findViewById(R.id.rcv_orderAgain);

        homeAdapter = new HomeAdapter(getActivity(), productList);
        homeSecondAdapter = new HomeSecondAdapter(getActivity(), productList2);
        homeVoucherAdapter = new HomeVoucherAdapter(getActivity(), productListVoucher);
        orderAgainAdapter = new OrderAgainAdapter(getActivity(), productListOderAgain);

        rcv_header.setAdapter(homeAdapter);
        rcv_second.setAdapter(homeSecondAdapter);
        rcv_voucher.setAdapter(homeVoucherAdapter);
        rcv_orderAgain.setAdapter(orderAgainAdapter);

        searchView = view.findViewById(R.id.searchView);
        searchView.setIconifiedByDefault(false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        rcv_header.setLayoutManager(linearLayoutManager);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        rcv_second.setLayoutManager(gridLayoutManager);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        rcv_voucher.setLayoutManager(linearLayoutManager1);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rcv_orderAgain.setLayoutManager(linearLayoutManager2);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}