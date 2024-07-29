package com.example.grab_demo.customer.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.grab_demo.R;
import com.example.grab_demo.customer.activity.HomeActivity;
import com.example.grab_demo.database.ConnectionClass;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ProfileFragment extends Fragment {
    Connection connection;
    String query;
    Statement smt;
    ResultSet resultSet;
    TextView txt_name;
    private View view;
    private HomeActivity homeActivity;
    private String userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        addControls();
        if (getActivity() instanceof HomeActivity) {
            homeActivity = (HomeActivity) getActivity();
            userId = homeActivity.getUserId();
        }
        Log.e("HomeFragment", userId);

        if (userId != null) {
            loadData(); // Load data using userId
        } else {
            Log.e("HomeFragment", "userId is null");
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (userId != null) {
            loadData(); // Load data using userId
        }
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
                    txt_name.setText(resultSet.getString(1));
                }
                connection.close();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
        } else {
            Log.e("Error: ", "Connection null");
        }
    }

    private void addControls() {
        txt_name = view.findViewById(R.id.txt_name);
    }
}