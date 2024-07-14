package com.example.grab_demo.database;

import android.content.Context;
import android.util.Log;

import com.example.grab_demo.model.UserModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDataSource {
    private static final String TAG = "UserDataSource";
    private Connection connection;

    public UserDataSource(Context context) {
        ConnectionClass connectionClass = new ConnectionClass();
        connection = connectionClass.conClass();
    }

    public boolean updateUserProfile(UserModel user) {
        boolean isSuccess = false;
        String query = "UPDATE Users SET username=?, phone=?, email=?, password=? WHERE userId=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(3, user.getPhoneNumber());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setInt(6, user.getUserId());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                Log.d(TAG, "User profile updated successfully");
                isSuccess = true;
            } else {
                Log.d(TAG, "Failed to update user profile");
            }
        } catch (SQLException e) {
            Log.e(TAG, "SQL Exception: " + e.getMessage());
        }
        return isSuccess;
    }
}
