package com.example.grab_demo.deliver.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grab_demo.R;

public class EditProfileActivity extends AppCompatActivity {

    private EditText nameEditText, birthdayEditText, phoneEditText, emailEditText, passwordEditText;
    private Button acceptButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        nameEditText = findViewById(R.id.nameEditText);
        birthdayEditText = findViewById(R.id.birthdayEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        acceptButton = findViewById(R.id.acceptButton);

        // Get the data from the intent and set it to the EditTexts
        nameEditText.setText(getIntent().getStringExtra("name"));
        birthdayEditText.setText(getIntent().getStringExtra("birthday"));
        phoneEditText.setText(getIntent().getStringExtra("phone"));
        emailEditText.setText(getIntent().getStringExtra("email"));
        passwordEditText.setText(getIntent().getStringExtra("password"));

        // Handle the accept button click
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle data saving or processing here
                finish();
            }
        });
    }
}
