package com.example.grab_demo.deliver.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grab_demo.R;
import com.example.grab_demo.database.UserDataSource;
import com.example.grab_demo.model.UserModel;

public class EditProfileActivity extends AppCompatActivity {
    private EditText nameEditText, phoneEditText, emailEditText, passwordEditText;
    private Button acceptButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Initialize views
        nameEditText = findViewById(R.id.nameEditText);
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
        if (!isInputValid()) {
            return;
        }
        String userId = getIntent().getStringExtra("user_id");

        // Lấy dữ liệu mới từ các trường EditText
        String newUsername = nameEditText.getText().toString().trim();
        String newPhoneNumber = phoneEditText.getText().toString().trim();
        String newEmail = emailEditText.getText().toString().trim();
        String newPassword = passwordEditText.getText().toString().trim();

        // Tạo đối tượng UserModel mới với dữ liệu cập nhật
        UserModel user = new UserModel();
        user.setUserId(Integer.parseInt(userId));
        user.setUsername(newUsername);
        user.setPhoneNumber(newPhoneNumber);
        user.setEmail(newEmail);
        user.setPassword(newPassword);

        // Cập nhật hồ sơ người dùng trong cơ sở dữ liệu
        UserDataSource userDataSource = new UserDataSource(EditProfileActivity.this);
        boolean updated = userDataSource.updateUserProfile(user);

        if (updated) {
            Toast.makeText(EditProfileActivity.this, "Hồ sơ đã được cập nhật thành công", Toast.LENGTH_SHORT).show();
            finish(); // Kết thúc activity sau khi cập nhật thành công
        } else {
            Toast.makeText(EditProfileActivity.this, "Không thể cập nhật hồ sơ", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isInputValid() {
        if (nameEditText.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tên", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (phoneEditText.getText().toString().trim().isEmpty() || phoneEditText.getText().toString().trim().length() != 10) {
            Toast.makeText(this, "Vui lòng nhập số điện thoại đúng (10 số)", Toast.LENGTH_SHORT).show();
            return false;
        }
        String email = emailEditText.getText().toString().trim();
        if (email.isEmpty() || !email.endsWith("@gmail.com")) {
            Toast.makeText(this, "Vui lòng nhập email đúng định dạng (@gmail.com)", Toast.LENGTH_SHORT).show();
            return false;
        }
        String password = passwordEditText.getText().toString().trim();
        if (password.isEmpty() || password.length() < 8) {
            Toast.makeText(this, "Mật khẩu phải có ít nhất 8 ký tự", Toast.LENGTH_SHORT).show();
            return false;
        }
        // Thêm các kiểm tra hợp lệ khác nếu cần
        return true;
    }
}
