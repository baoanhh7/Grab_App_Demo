package com.example.grab_demo.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grab_demo.R;
import com.example.grab_demo.login.LoginActivity;

public class RoleRegisterActivity extends AppCompatActivity {
    Button btn_customer, btn_owner, btn_deliver;
    TextView txt_haveAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_register);

        addControls();
        addEvents();
    }

    private void addControls() {
        btn_customer = findViewById(R.id.btn_customer);
        btn_owner = findViewById(R.id.btn_ownerStore);
        btn_deliver = findViewById(R.id.btn_deliver);

        txt_haveAccount = findViewById(R.id.txt_haveAccount);
    }

    private void addEvents() {
        btn_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RoleRegisterActivity.this, RegisterCustomerActivity.class));
                finish();
            }
        });
        btn_owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RoleRegisterActivity.this, RegisterOwnerStoreActivity.class));
                finish();
            }
        });
        btn_deliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RoleRegisterActivity.this, RegisterDeliverActivity.class));
                finish();
            }
        });

        txt_haveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RoleRegisterActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
}