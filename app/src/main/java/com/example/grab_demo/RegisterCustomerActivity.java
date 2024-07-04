package com.example.grab_demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterCustomerActivity extends AppCompatActivity {

    TextView txt_haveAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_customer);

        addControls();
        addEvents();
    }

    private void addEvents() {
        txt_haveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterCustomerActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    private void addControls() {
        txt_haveAccount = findViewById(R.id.txt_haveAccount);
    }
}