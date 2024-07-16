package com.example.grab_demo.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginActivity extends AppCompatActivity {
    Button btn_login, btn_createAcount;
    Connection connection;
    String query;
    Statement smt;
    ResultSet resultSet;
    TextInputEditText edt_user, edt_password;
    TextView txt_forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        addControls();
        addEvents();
    }

    private void addEvents() {
        btn_createAcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RoleRegisterActivity.class));
                finish();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });

        txt_forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
                finish();
            }
        });
    }

    private void addControls() {
        btn_login = findViewById(R.id.btn_login);
        btn_createAcount = findViewById(R.id.btn_createAcount);
        edt_user = findViewById(R.id.edt_user);
        edt_password = findViewById(R.id.edt_password);
        txt_forgotPassword = findViewById(R.id.txt_forgotPassword);
    }

    private void loadData() {
        ConnectionClass sql = new ConnectionClass();
        connection = sql.conClass();
        if (connection != null) {
            try {
                query = "Select user_id, password, phone_number, user_type, email from Users";
                smt = connection.createStatement();
                resultSet = smt.executeQuery(query);
                while (resultSet.next()) {

                    if (edt_user.getText().toString().equals(resultSet.getString(3)) || edt_user.getText().toString().equals(resultSet.getString(5)) && edt_password.getText().toString().equals(resultSet.getString(2))) {
                        Log.d("Login", "Checking user: " + resultSet.getString(3));
                        if (resultSet.getString(4).equals("store_owner")) {
                            String userId = resultSet.getString(1); // Lấy user_id từ kết quả truy vấn
                            Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                            Log.d("Login", "User is store_owner");
                            Intent intent = new Intent(LoginActivity.this, StoreOwnerActivity.class);
                            intent.putExtra("user_id", userId); // Truyền user_id qua intent
                            startActivity(intent);
                            finish();
                        } else if (resultSet.getString(4).equals("sales") || resultSet.getString(4).equals("it")) {
                            String userType = resultSet.getString(4);
                            Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                            intent.putExtra("user_type", userType);
                            startActivity(intent);
                            finish();
                        } else if (resultSet.getString(4).equals("customer")) {
                            String userID = resultSet.getString(1);
                            Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                            Log.d("Login", "User is customer");
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            intent.putExtra("user_id", userID);
                            startActivity(intent);
                            finish();
                        } else if (resultSet.getString(4).equals("delivery")) {
                            String userID = resultSet.getString(1);
                            Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                            Log.d("Login", "User is delivery");
                            Intent intent = new Intent(LoginActivity.this, DriverHomeActivity.class);
                            intent.putExtra("user_id", userID);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Log.e("Error: ", "Failed");
                    }
                }
                connection.close();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
        } else {
            Log.e("Error: ", "Connection null");
        }
    }
}