package com.example.grab_demo.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grab_demo.R;
import com.example.grab_demo.login.LoginActivity;

import java.util.ArrayList;
import java.util.List;

public class RegisterCustomerActivity extends AppCompatActivity {

    TextView txt_haveAccount;
    Button btn_createAcount;
    ImageView img_back;
    Spinner sp_idBank;
    List<String> listNameBank = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_customer);

        addControls();
        addEvents();

        createData();
    }

    private void createData() {
        listNameBank.add("Vietcombank");
        listNameBank.add("MB Bank");
        listNameBank.add("ACB");
        listNameBank.add("DongA Bank");

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listNameBank);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_idBank.setAdapter(adapter);
    }

    private void addEvents() {
        txt_haveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterCustomerActivity.this, LoginActivity.class));
                finish();
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterCustomerActivity.this, RoleRegisterActivity.class));
                finish();
            }
        });
    }

    private void addControls() {
        txt_haveAccount = findViewById(R.id.txt_haveAccount);
        img_back = findViewById(R.id.img_back);
        btn_createAcount = findViewById(R.id.btn_createAcount);
        sp_idBank = findViewById(R.id.sp_idBank);
    }
}