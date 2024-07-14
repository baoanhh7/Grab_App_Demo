package com.example.grab_demo.deliver.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.grab_demo.R;
import com.example.grab_demo.model.UserModel;
import com.example.grab_demo.database.UserDataSource;

public class EditProfileActivity extends AppCompatActivity {
    private EditText nameEditText, birthdayEditText, phoneEditText, emailEditText, passwordEditText;
    private Button acceptButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Initialize views
        nameEditText = findViewById(R.id.nameEditText);
        birthdayEditText = findViewById(R.id.birthdayEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        acceptButton = findViewById(R.id.acceptButton);

        // Set click listener for Accept button
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserProfile();
            }
        });
    }

    private void updateUserProfile() {
        // Get text from EditText fields
        String name = nameEditText.getText().toString().trim();
        String birthday = birthdayEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Create a new UserModel object
        UserModel user = new UserModel();
        user.setUsername(name);
        user.setPhoneNumber(phone);
        user.setEmail(email);
        user.setPassword(password);
        // Set other fields accordingly (address, rating, userType, etc.)

        // Example of updating user profile in database using UserDataSource
        UserDataSource userDataSource = new UserDataSource(EditProfileActivity.this);
        boolean updated = userDataSource.updateUserProfile(user);

        if (updated) {
            Toast.makeText(EditProfileActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
            // Optionally, you can finish the activity or navigate to another screen
            // finish();
        } else {
            Toast.makeText(EditProfileActivity.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
        }
    }
}
