package com.example.grab_demo.customer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.grab_demo.database.ConnectionClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CartRepository {
    private MutableLiveData<Integer> cartItemCount = new MutableLiveData<>();

    public LiveData<Integer> getCartItemCount() {
        return cartItemCount;
    }

    public void fetchCartItemCount(String userId) {
        new Thread(() -> {
            ConnectionClass sql = new ConnectionClass();
            Connection connection = sql.conClass();
            if (connection != null) {
                try {
                    String query = "SELECT COUNT(*) FROM CartItems WHERE user_id = ?";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, userId);
                    ResultSet resultSet = statement.executeQuery();
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        cartItemCount.postValue(count);
                    }
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

