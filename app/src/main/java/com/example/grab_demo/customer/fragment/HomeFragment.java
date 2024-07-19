package com.example.grab_demo.customer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grab_demo.R;
import com.example.grab_demo.customer.activity.CartActivity;
import com.example.grab_demo.customer.activity.HomeActivity;
import com.example.grab_demo.customer.activity.StoreListActivity;
import com.example.grab_demo.customer.adapter.CategoryHomeAdapter;
import com.example.grab_demo.customer.adapter.HomeSecondAdapter;
import com.example.grab_demo.customer.adapter.HomeVoucherAdapter;
import com.example.grab_demo.customer.adapter.StoreListAdapter;
import com.example.grab_demo.customer.m_interface.StClickItem;
import com.example.grab_demo.customer.model.Category;
import com.example.grab_demo.customer.model.Store;
import com.example.grab_demo.database.ConnectionClass;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {
    Connection connection;
    String query;
    Statement smt;
    ResultSet resultSet;

    RecyclerView rcv_header, rcv_second, rcv_voucher, rcv_orderAgain;
    List<Category> filterCategory, categoryList, categoryList2, categoryListVoucher, categoryListOderAgain;
    List<Store> storeList;
    CategoryHomeAdapter categoryHomeAdapter;
    HomeSecondAdapter homeSecondAdapter;
    HomeVoucherAdapter homeVoucherAdapter;
    StoreListAdapter storeListAdapter;

    SearchView searchView;
    ImageView img_cart;
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

        addEvents();
        startAutoSlide();
        if (getActivity() instanceof HomeActivity) {
            homeActivity = (HomeActivity) getActivity();
            userId = homeActivity.getUserId();
        }
        Log.e("HomeFragment", userId);

        if (userId != null) {
            loadingData();
        } else {
            Log.e("HomeFragment", "userId is null");
        }
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

    private void loadingData() {
        ConnectionClass sql = new ConnectionClass();
        connection = sql.conClass();
        if (connection != null) {
            try {
                query = "SELECT cate_id, cate_name, cate_image FROM Categories";
                smt = connection.createStatement();
                resultSet = smt.executeQuery(query);

                categoryList.clear();
                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    String cateName = resultSet.getString(2);
                    byte[] cateImage = resultSet.getBytes(3);
                    categoryList.add(new Category(id, cateName, cateImage));
                }
                categoryHomeAdapter.notifyDataSetChanged();
                connection.close();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
        } else {
            Log.e("Error: ", "Connection null");
        }

//        productList.clear();
//        productList.add(new Product("Noodles & Congee", R.drawable.noodle));
//        productList.add(new Product("International Food", R.drawable.food));
//        productList.add(new Product("Healthy Food", R.drawable.healthy));
//        productList.add(new Product("Pizza", R.drawable.pizza));
//        homeAdapter.notifyDataSetChanged();

        categoryList2.clear();
        categoryList2.add(new Category("Near me", "Get it quick", R.color.hongNhat));
        categoryList2.add(new Category("Bửa tối nữa giá", "Chốt deal ngay!", R.color.lavender));
        categoryList2.add(new Category("Grab ngon rẻ", "Bao tiết kiệm", R.color.vangNhat));
        categoryList2.add(new Category("Tuần lễ món cơm", "Tặng món 0đ", R.color.xanhNhat));
        homeSecondAdapter.notifyDataSetChanged();

        categoryListVoucher.clear();
        categoryListVoucher.add(new Category("Voucher 1", "Get it quick", R.drawable.voucher4));
        categoryListVoucher.add(new Category("Voucher 2", "Chốt deal ngay!", R.drawable.voucher3));
        categoryListVoucher.add(new Category("Voucher 3", "Bao tiết kiệm", R.drawable.voucher2));
        categoryListVoucher.add(new Category("Voucher 4", "Tặng món 0đ", R.drawable.voucher1));
        homeVoucherAdapter.notifyDataSetChanged();

        categoryListOderAgain.clear();
        categoryListOderAgain.add(new Category("Pizza", "Bột pizza, sốt cà chua, phô mai", R.drawable.pizza, 10000));
        categoryListOderAgain.add(new Category("Japan noodle", "Bột mì, sup, egg", R.drawable.noodle, 20000));
        categoryListOderAgain.add(new Category("Kimbap", "Rice, carot, egg", R.drawable.food, 50000));
        categoryListOderAgain.add(new Category("Salad", "Rau, trái cây, hạt", R.drawable.healthy, 23456));
        categoryListOderAgain.add(new Category("Pizza", "Bột pizza, sốt cà chua, phô mai", R.drawable.pizza, 10000));
        categoryListOderAgain.add(new Category("Japan noodle", "Bột mì, sup, egg", R.drawable.noodle, 20000));
        categoryListOderAgain.add(new Category("Kimbap", "Rice, carot, egg", R.drawable.food, 50000));
        categoryListOderAgain.add(new Category("Salad", "Rau, trái cây, hạt", R.drawable.healthy, 23456));
        categoryListOderAgain.add(new Category("Pizza", "Bột pizza, sốt cà chua, phô mai", R.drawable.pizza, 10000));
        storeListAdapter.notifyDataSetChanged();
    }

    private void addEvents() {
        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CartActivity.class);
                intent.putExtra("user_id", userId);
                startActivity(intent);
            }
        });

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

        categoryHomeAdapter.setOnItemClick(new StClickItem() {
            @Override
            public void onClickItem(String data) {
                Intent intent = new Intent(getActivity(), StoreListActivity.class);
                intent.putExtra("cate_id", Integer.parseInt(data));
                startActivity(intent);
            }
        });

        homeSecondAdapter.setOnClickItemListener(new StClickItem() {
            @Override
            public void onClickItem(String data) {
                Intent intent = new Intent(getActivity(), StoreListActivity.class);
//                intent.putExtra("cate_id", Integer.parseInt(data));
                startActivity(intent);
            }
        });
    }

    private void filter(String text) {
        filterCategory.clear();
        for (Category category : categoryList) {
            if (category.getName().toLowerCase().contains(text.toLowerCase())) {
                filterCategory.add(category);
            }
        }
        categoryHomeAdapter.filterList(filterCategory);
    }

    private void addControls() {
        img_cart = view.findViewById(R.id.img_cart);

        filterCategory = new ArrayList<>();
        categoryList = new ArrayList<>();
        categoryList2 = new ArrayList<>();
        categoryListVoucher = new ArrayList<>();
        categoryListOderAgain = new ArrayList<>();
        storeList = new ArrayList<>();

        rcv_header = view.findViewById(R.id.rcv_header);
        rcv_second = view.findViewById(R.id.rcv_second);
        rcv_voucher = view.findViewById(R.id.rcv_voucher);
        rcv_orderAgain = view.findViewById(R.id.rcv_orderAgain);

        categoryHomeAdapter = new CategoryHomeAdapter(getActivity(), categoryList);
        homeSecondAdapter = new HomeSecondAdapter(getActivity(), categoryList2);
        homeVoucherAdapter = new HomeVoucherAdapter(getActivity(), categoryListVoucher);
        storeListAdapter = new StoreListAdapter(getActivity(), storeList);

        rcv_header.setAdapter(categoryHomeAdapter);
        rcv_second.setAdapter(homeSecondAdapter);
        rcv_voucher.setAdapter(homeVoucherAdapter);
        rcv_orderAgain.setAdapter(storeListAdapter);

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