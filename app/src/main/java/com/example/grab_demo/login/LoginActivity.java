package com.example.grab_demo.login;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grab_demo.R;
import com.example.grab_demo.admin.AdminActivity;
import com.example.grab_demo.customer.activity.HomeActivity;
import com.example.grab_demo.database.ConnectionClass;
import com.example.grab_demo.deliver.activity.DriverHomeActivity;
import com.example.grab_demo.register.ForgotPasswordActivity;
import com.example.grab_demo.register.RoleRegisterActivity;
import com.example.grab_demo.store_owner.activity.StoreOwnerActivity;
import com.google.android.material.textfield.TextInputEditText;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginActivity extends AppCompatActivity {
    private Button btn_login, btn_createAccount;
    private TextInputEditText edt_user, edt_password;
    private TextView txt_forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeViews();
        setupEventListeners();
    }

    private void initializeViews() {
        btn_login = findViewById(R.id.btn_login);
        btn_createAccount = findViewById(R.id.btn_createAcount);
        edt_user = findViewById(R.id.edt_user);
        edt_password = findViewById(R.id.edt_password);
        txt_forgotPassword = findViewById(R.id.txt_forgotPassword);
    }

    private void setupEventListeners() {
        btn_createAccount.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, RoleRegisterActivity.class)));
        btn_login.setOnClickListener(v -> attemptLogin());
        txt_forgotPassword.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class)));
    }

    private void attemptLogin() {
        String username = edt_user.getText().toString().trim();
        String password = edt_password.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        new LoginTask().execute(username, password);
    }

    private void navigateToAppropriateActivity(UserInfo userInfo) {
        Intent intent;
        switch (userInfo.userType) {
            case "store_owner":
                intent = new Intent(this, StoreOwnerActivity.class);
                break;
            case "sales":
            case "it":
                intent = new Intent(this, AdminActivity.class);
                intent.putExtra("user_type", userInfo.userType);
                break;
            case "customer":
                intent = new Intent(this, HomeActivity.class);
                break;
            case "delivery":
                intent = new Intent(this, DriverHomeActivity.class);
                break;
            default:
                Log.e("Login", "Unknown user type: " + userInfo.userType);
                return;
        }
        intent.putExtra("user_id", userInfo.userId);
        startActivity(intent);
        finish();
    }

    private static class UserInfo {
        String userId;
        String userType;
        String status;

        UserInfo(String userId, String userType, String status) {
            this.userId = userId;
            this.userType = userType;
            this.status = status;
        }
    }

    private class LoginTask extends AsyncTask<String, Void, UserInfo> {
        @Override
        protected UserInfo doInBackground(String... params) {
            String username = params[0];
            String password = params[1];
            Connection connection = null;
            try {
                connection = new ConnectionClass().conClass();
                if (connection == null) return null;

                String query = "SELECT user_id, password, user_type, status FROM Users WHERE (phone_number = ? OR email = ?)";
                try (PreparedStatement smt = connection.prepareStatement(query)) {
                    smt.setString(1, username);
                    smt.setString(2, username);
                    try (ResultSet rs = smt.executeQuery()) {
                        if (rs.next()) {
                            String storedPassword = rs.getString("password");
                            String status = rs.getString("status");
                            if (password.equals(storedPassword)) {
                                return new UserInfo(rs.getString("user_id"), rs.getString("user_type"), status);
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                Log.e("LoginTask", "SQL Error", e);
            } finally {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        Log.e("LoginTask", "Error closing connection", e);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(UserInfo userInfo) {
            if (userInfo != null) {
                if ("locked".equals(userInfo.status)) {
                    Toast.makeText(LoginActivity.this, "Your account has been locked", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    navigateToAppropriateActivity(userInfo);
                }
            } else {
                Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
