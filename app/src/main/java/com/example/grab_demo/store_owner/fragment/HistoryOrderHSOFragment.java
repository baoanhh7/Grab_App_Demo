package com.example.grab_demo.store_owner.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grab_demo.R;
import com.example.grab_demo.database.ConnectionClass;
import com.example.grab_demo.store_owner.activity.OrderHomeStoreOwnerActivity;
import com.example.grab_demo.store_owner.adapter.NewOrderAdapter;
import com.example.grab_demo.store_owner.model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class HistoryOrderHSOFragment extends Fragment {

    private static final long REFRESH_INTERVAL = 1000;
    SearchView searchView_HistoryOrderHSO;
    RecyclerView rcv_HistoryOrderHSO;
    NewOrderAdapter historyOrderHSOAdapter;
    ArrayList<Order> arr;
    View view;
    OrderHomeStoreOwnerActivity orderHomeStoreOwnerActivity;
    int storeID;
    Connection connection;
    private Handler handler;
    private Runnable refreshRunnable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_history_order_h_s_o, container, false);
        if (getActivity() instanceof OrderHomeStoreOwnerActivity) {
            orderHomeStoreOwnerActivity = (OrderHomeStoreOwnerActivity) getActivity();
            storeID = orderHomeStoreOwnerActivity.getStoreID();
        }
        if (storeID > 0) {
            Log.e("NewOrderHSOFragment", storeID + "");
            addControls();
            addEvents();
            handler = new Handler(Looper.getMainLooper());
            refreshRunnable = new Runnable() {
                @Override
                public void run() {
                    loadData();
                    handler.postDelayed(this, REFRESH_INTERVAL);
                }
            };
        } else {
            Log.e("NewOrderHSOFragment", "storeID is null");
        }
        return view;
    }

    private void addEvents() {
        searchView_HistoryOrderHSO.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                historyOrderHSOAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                historyOrderHSOAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        startAutoRefresh();
    }

    private void startAutoRefresh() {
        handler.postDelayed(refreshRunnable, REFRESH_INTERVAL);
    }

    private void loadData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ConnectionClass sql = new ConnectionClass();
                connection = sql.conClass();
                if (connection != null) {
                    try {
                        String query = "SELECT order_id,delivery_id, status FROM Orders WHERE store_id = ? AND status = 'delivered' ";
                        PreparedStatement smt = connection.prepareStatement(query);
                        smt.setInt(1, storeID);
                        ResultSet resultSet = smt.executeQuery();
                        final ArrayList<Order> tempArr = new ArrayList<>();
                        while (resultSet.next()) {
                            Integer id = resultSet.getInt(1);
                            int delivery_id = resultSet.getInt(2);
                            String status = resultSet.getString(3);
                            tempArr.add(new Order(id, delivery_id, status));
                        }
                        connection.close();

                        orderHomeStoreOwnerActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                arr.clear();
                                arr.addAll(tempArr);
                                historyOrderHSOAdapter.notifyDataSetChanged();
                            }
                        });
                    } catch (Exception e) {
                        Log.e("Error: ", e.getMessage());
                    }
                } else {
                    Log.e("Error: ", "Connection null");
                }
            }
        }).start();
    }

    private void addControls() {
        rcv_HistoryOrderHSO = view.findViewById(R.id.rcv_history_order);
        searchView_HistoryOrderHSO = view.findViewById(R.id.searchView_HistoryOrder);
        arr = new ArrayList<>();
        historyOrderHSOAdapter = new NewOrderAdapter(getContext(), arr);
        rcv_HistoryOrderHSO.setAdapter(historyOrderHSOAdapter);
        rcv_HistoryOrderHSO.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }
}