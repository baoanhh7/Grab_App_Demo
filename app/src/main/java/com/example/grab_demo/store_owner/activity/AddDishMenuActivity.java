package com.example.grab_demo.store_owner.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grab_demo.R;
import com.google.android.material.textfield.TextInputEditText;

public class AddDishMenuActivity extends AppCompatActivity {
    TextInputEditText textInputEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dish_menu);
        textInputEditText = findViewById(R.id.edt_description_addDishMenu);
    }
}