package com.example.grab_demo.register;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grab_demo.R;
import com.example.grab_demo.database.ConnectionClass;
import com.example.grab_demo.login.LoginActivity;
import com.google.android.material.textfield.TextInputEditText;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class RegisterOwnerStoreActivity extends AppCompatActivity {
    TextView txt_haveAccount_registerOS;
    TextInputEditText edt_name_registerOS, edt_email_registerOS, edt_password_registerOS, edt_rePassword_registerOS,
            edt_phone_registerOS, edt_BankingName_registerOS, edt_BankingID_registerOS;
    ImageView img_back_registerOS;
    Button btn_createAcount_registerOS;
    Connection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_owner_store);
        addControls();
        addEvents();
    }

    private void addControls() {
        txt_haveAccount_registerOS = findViewById(R.id.txt_haveAccount_registerOS);
        edt_BankingID_registerOS = findViewById(R.id.edt_BankingID_registerOS);
        edt_BankingName_registerOS = findViewById(R.id.edt_BankingName_registerOS);
        edt_email_registerOS = findViewById(R.id.edt_email_registerOS);
        edt_name_registerOS = findViewById(R.id.edt_name_registerOS);
        edt_password_registerOS = findViewById(R.id.edt_password_registerOS);
        edt_phone_registerOS = findViewById(R.id.edt_phone_registerOS);
        edt_rePassword_registerOS = findViewById(R.id.edt_rePassword_registerOS);
        img_back_registerOS = findViewById(R.id.img_back_registerOS);
        btn_createAcount_registerOS = findViewById(R.id.btn_createAcount_registerOS);
    }

    private void insertData() {
        String name = edt_name_registerOS.getText().toString().trim();
        String email = edt_email_registerOS.getText().toString().trim();
        String phone = edt_phone_registerOS.getText().toString().trim();
        String password = edt_password_registerOS.getText().toString().trim();
        String rePassword = edt_rePassword_registerOS.getText().toString().trim();
        String bankingName = edt_BankingName_registerOS.getText().toString().trim();
        String bankingID = edt_BankingID_registerOS.getText().toString().trim();
        String userType = "store_owner";
        String status = "inactive";

        if (!password.equals(rePassword)) {
            // Show error message if passwords do not match
            Toast.makeText(RegisterOwnerStoreActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            Log.e("RegisterDeliverActivity", "Passwords do not match");
            return;
        }
        if (phone.length() != 10) {
            Toast.makeText(RegisterOwnerStoreActivity.this, "Phone number must be 10 digits", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!email.endsWith("@gmail.com")) {
            Toast.makeText(RegisterOwnerStoreActivity.this, "Email must end with @gmail.com", Toast.LENGTH_SHORT).show();
            return;
        }
        ConnectionClass sql = new ConnectionClass();
        connection = sql.conClass();
        if (connection != null) {
            try {
                String query = "INSERT INTO Users(username, password, email, phone_number, user_type, bank_name, bank_account,status) VALUES (?, ?, ?, ?, ?, ?,?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, phone);
                preparedStatement.setString(5, userType);
                preparedStatement.setString(6, bankingName);
                preparedStatement.setString(7, bankingID);
                preparedStatement.setString(8, status);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    Log.d("RegisterOSActivity", "Insert successfully");
                    Toast.makeText(RegisterOwnerStoreActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterOwnerStoreActivity.this, RoleRegisterActivity.class));
                    finish();
                } else {
                    Log.e("RegisterOSActivity", "Insert failed");
                    Toast.makeText(RegisterOwnerStoreActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                }
                connection.close();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
                Toast.makeText(RegisterOwnerStoreActivity.this, "An error occurred: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.e("Error: ", "Connection null");
            Toast.makeText(RegisterOwnerStoreActivity.this, "Database connection failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void addEvents() {
        txt_haveAccount_registerOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterOwnerStoreActivity.this, LoginActivity.class));
                finish();
            }
        });
        img_back_registerOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterOwnerStoreActivity.this, RoleRegisterActivity.class));
                finish();
            }
        });
        btn_createAcount_registerOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });
    }
}