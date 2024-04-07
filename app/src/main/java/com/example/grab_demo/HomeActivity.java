package com.example.grab_demo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grab_demo.adapter.Home.HomeAdapter;
import com.example.grab_demo.adapter.Home.HomeSecondAdapter;
import com.example.grab_demo.adapter.Home.HomeVoucherAdapter;
import com.example.grab_demo.adapter.Home.OrderAgainAdapter;
import com.example.grab_demo.model.Product;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    RecyclerView rcv_header, rcv_second, rcv_voucher, rcv_orderAgain;
    List<Product> productList, productList2, productListVoucher, productListOderAgain;
    HomeAdapter homeAdapter;
    HomeSecondAdapter homeSecondAdapter;
    HomeVoucherAdapter homeVoucherAdapter;
    OrderAgainAdapter orderAgainAdapter;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        addControls();
        createData();

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

    private void addControls() {
        productList = new ArrayList<>();
        productList2 = new ArrayList<>();
        productListVoucher = new ArrayList<>();
        productListOderAgain = new ArrayList<>();

        rcv_header = findViewById(R.id.rcv_header);
        rcv_second = findViewById(R.id.rcv_second);
        rcv_voucher = findViewById(R.id.rcv_voucher);
        rcv_orderAgain = findViewById(R.id.rcv_orderAgain);

        homeAdapter = new HomeAdapter(this, productList);
        homeSecondAdapter = new HomeSecondAdapter(this, productList2);
        homeVoucherAdapter = new HomeVoucherAdapter(this, productListVoucher);
        orderAgainAdapter = new OrderAgainAdapter(this, productListOderAgain);

        rcv_header.setAdapter(homeAdapter);
        rcv_second.setAdapter(homeSecondAdapter);
        rcv_voucher.setAdapter(homeVoucherAdapter);
        rcv_orderAgain.setAdapter(orderAgainAdapter);

        searchView = findViewById(R.id.searchView);
        searchView.setIconifiedByDefault(false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rcv_header.setLayoutManager(linearLayoutManager);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rcv_second.setLayoutManager(gridLayoutManager);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rcv_voucher.setLayoutManager(linearLayoutManager1);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcv_orderAgain.setLayoutManager(linearLayoutManager2);
    }
}