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
import com.google.android.material.textfield.TextInputEditText;

public class ForgotPasswordActivity extends AppCompatActivity {
    ImageView img_back;
    Button btn_sendCode, btn_goToSignUp;
    TextView txt_haveAccount;
    TextInputEditText edt_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        addControls();
        addEvents();

    }

    private void addEvents() {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
                finish();
            }
        });

        txt_haveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
                finish();
            }
        });

        btn_goToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgotPasswordActivity.this, RoleRegisterActivity.class));
                finish();
            }
        });

    }

    private void addControls() {
        img_back = findViewById(R.id.img_back);
        btn_sendCode = findViewById(R.id.btn_createAcount);
        btn_goToSignUp = findViewById(R.id.btn_goToSignUp);
        txt_haveAccount = findViewById(R.id.txt_haveAccount);
        edt_email = findViewById(R.id.edt_email);
    }
}