package com.example.grab_demo.database;

import android.content.Context;
import android.util.Log;

import com.example.grab_demo.model.UserModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDataSource {
    private static final String TAG = "UserDataSource";
    private Connection connection;

    public UserDataSource(Context context) {
        ConnectionClass connectionClass = new ConnectionClass();
        connection = connectionClass.conClass();
    }

    public static UserModel getUserById(Context context, String userId) {
        UserModel user = null;
        Connection connection = null;
        try {
            ConnectionClass connectionClass = new ConnectionClass();
            connection = connectionClass.conClass();

            if (connection == null) {
                Log.e(TAG, "Connection is null");
                return null;
            }

            String query = "SELECT * FROM Users WHERE user_id = ?";
            Log.d(TAG, "Executing query: " + query + " with userId: " + userId);

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new UserModel();
                user.setUserId(resultSet.getInt("user_id"));
                user.setPhoneNumber(resultSet.getString("phone_number"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
                Log.d(TAG, "User found: " + user.getUsername());
            } else {
                Log.d(TAG, "No user found for userId: " + userId);
            }
        } catch (SQLException e) {
            Log.e(TAG, "SQL Exception: " + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    Log.e(TAG, "Error closing connection: " + e.getMessage());
                }
            }
        }
        return user;
    }

    public boolean updateUserProfile(UserModel user) {
        boolean isSuccess = false;
        String query = "UPDATE Users SET username=?, phone_number=?, email=?, password=? WHERE user_id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPhoneNumber());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setInt(5, user.getUserId());

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