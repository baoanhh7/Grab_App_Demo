package com.example.grab_demo.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grab_demo.R;
import com.example.grab_demo.login.LoginActivity;

public class RegisterDeliverActivity extends AppCompatActivity {
    TextView txt_haveAccount;
    Button btn_createAcount;
    ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_deliver);

        addControls();
        addEvents();

    }

    private void addEvents() {
        txt_haveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterDeliverActivity.this, LoginActivity.class));
                finish();
            }
        });

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterDeliverActivity.this, RoleRegisterActivity.class));
                finish();
            }
        });
    }

    private void addControls() {
        txt_haveAccount = findViewById(R.id.txt_haveAccount);
        img_back = findViewById(R.id.img_back);
        btn_createAcount = findViewById(R.id.btn_createAcount);
    }
}