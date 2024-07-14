package com.example.grab_demo.admin.sales.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grab_demo.R;

public class ApproveStoreRegistrationActivity extends AppCompatActivity {
    ImageButton img_back_ApproveStoreRegistration;
    RecyclerView rv_ApproveStoreRegistration;
    androidx.appcompat.widget.SearchView searchView_ApproveStoreRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_store_registration);
        addControls();
        addEvents();
    }

    private void addEvents() {
        img_back_ApproveStoreRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void addControls() {
        img_back_ApproveStoreRegistration = findViewById(R.id.img_back_ApproveStoreRegistration);
        rv_ApproveStoreRegistration = findViewById(R.id.rv_ApproveStoreRegistration);
        searchView_ApproveStoreRegistration = findViewById(R.id.searchView_ApproveStoreRegistration);
    }
}