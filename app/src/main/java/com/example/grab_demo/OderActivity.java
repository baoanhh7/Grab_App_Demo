package com.example.grab_demo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class OderActivity extends AppCompatActivity {

    ImageButton btn_close;
    Button btn_order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oder);

        addControls();

        addEvents();
    }

    private void addEvents() {
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OderActivity.this, "Order Successfully!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addControls() {
        btn_close = findViewById(R.id.btn_close);
        btn_order = findViewById(R.id.btn_order);
    }
}