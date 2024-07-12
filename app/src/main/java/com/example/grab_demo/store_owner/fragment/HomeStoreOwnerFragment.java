package com.example.grab_demo.store_owner.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.grab_demo.ConnectionClass;
import com.example.grab_demo.R;
import com.example.grab_demo.store_owner.activity.ListQuanActivity;
import com.example.grab_demo.store_owner.activity.MenuHomeStoreOwnerActivity;
import com.example.grab_demo.store_owner.activity.OrderHomeStoreOwnerActivity;
import com.example.grab_demo.store_owner.activity.StoreOwnerActivity;
import com.example.grab_demo.store_owner.adapter.ImageSliderAdapter_Home;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class HomeStoreOwnerFragment extends Fragment {

    Connection connection;
    String query;
    Statement smt;
    ResultSet resultSet;
    TextView tvGreeting_home_storeowner, tv_revenue_today, tv_revenue_yesterday;
    Spinner SPQuan_home_storeowner;
    private int currentPage = 0;
    private Timer timer;
    private ViewPager viewPager;
    private View view;
    private int[] images = {R.drawable.voucher2, R.drawable.voucher3, R.drawable.voucher4};
    private CardView cardview_menuHSO, cardview_orderHSO, cardview_shopHSO, cardview_messageHSO;
    private String userId;
    private StoreOwnerActivity storeOwnerActivity;
    List<String> listNameStore = new ArrayList<>();
    List<String> listIDStore = new ArrayList<>();
    String storeID;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home_store_owner, container, false);
        addControls();
        addEvents();
        startAutoSlide();
        if (getActivity() instanceof StoreOwnerActivity) {
            storeOwnerActivity = (StoreOwnerActivity) getActivity();
            userId = storeOwnerActivity.getUserId();
        }

        Log.e("HomeStoreOwnerFragment", userId);
        // Ensure userId is not null before using
        if (userId != null) {
            loadData(); // Load data using userId
            createDataSpinner();
        } else {
            Log.e("HomeStoreOwnerFragment", "userId is null");
        }
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
        cardview_menuHSO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MenuHomeStoreOwnerActivity.class);
                startActivity(intent);
            }
        });
        cardview_orderHSO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OrderHomeStoreOwnerActivity.class);
                startActivity(intent);
            }
        });
        cardview_shopHSO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FragmentManager fragmentManager = getParentFragmentManager();
//                Fragment fragment = fragmentManager.findFragmentById(R.id.viewpager_StoreOwner); // Thay R.id.your_fragment_container bằng ID của Fragment container
//                if (fragment != null) {
//                    fragmentManager.beginTransaction().remove(fragment).commit();
//                }
                // Tạo Intent để chuyển sang Activity mới
                Intent intent = new Intent(getActivity(), ListQuanActivity.class);

                // Đính kèm dữ liệu vào Intent
                intent.putExtra("user_id", userId);

                // Chuyển sang Activity mới
                startActivity(intent);

            }
        });
        SPQuan_home_storeowner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                storeID = listIDStore.get(position);
                Log.d("HomeStoreOwnerFragment", storeID);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void addControls() {
        viewPager = view.findViewById(R.id.viewPager_HomeStoreOwner);
        cardview_menuHSO = view.findViewById(R.id.cardview_menuHSO);
        cardview_orderHSO = view.findViewById(R.id.cardview_orderHSO);
        cardview_shopHSO = view.findViewById(R.id.cardview_shopHSO);
        cardview_messageHSO = view.findViewById(R.id.cardview_messageHSO);
        tvGreeting_home_storeowner = view.findViewById(R.id.tvGreeting_home_storeowner);
        SPQuan_home_storeowner = view.findViewById(R.id.SPQuan_home_storeowner);
        tv_revenue_today = view.findViewById(R.id.tv_revenue_today);
        tv_revenue_yesterday = view.findViewById(R.id.tv_revenue_yesterday);
        ImageSliderAdapter_Home imageSliderAdapterHome = new ImageSliderAdapter_Home(getContext(), images);
        viewPager.setAdapter(imageSliderAdapterHome);
    }

    private void loadData() {
        ConnectionClass sql = new ConnectionClass();
        connection = sql.conClass();
        if (connection != null) {
            try {
                query = "SELECT username FROM Users WHERE user_id = " + userId;
                smt = connection.createStatement();
                resultSet = smt.executeQuery(query);
                while (resultSet.next()) {
                    tvGreeting_home_storeowner.setText(resultSet.getString(1));
                }
                connection.close();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
        } else {
            Log.e("Error: ", "Connection null");
        }
    }
    private void createDataSpinner() {
        ConnectionClass sql = new ConnectionClass();
        connection = sql.conClass();
        if (connection != null) {
            try {
                String query = "SELECT store_id,store_name FROM Stores WHERE owner_id = " + userId;
                smt = connection.createStatement();
                resultSet = smt.executeQuery(query);
                while (resultSet.next()) {
                    storeID = resultSet.getString(1);
                    String cateName = resultSet.getString(2);
                    listNameStore.add(cateName);
                    listIDStore.add(storeID);
                }
                connection.close();
                ArrayAdapter adapter = new ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, listNameStore);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SPQuan_home_storeowner.setAdapter(adapter);
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
        } else {
            Log.e("Error: ", "Connection null");
        }
    }
}