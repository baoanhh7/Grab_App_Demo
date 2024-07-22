package com.example.grab_demo.deliver.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.grab_demo.R;
import com.example.grab_demo.database.UserDataSource;
import com.example.grab_demo.deliver.activity.EditProfileActivity;
import com.example.grab_demo.deliver.activity.StatisticalActivity;
import com.example.grab_demo.model.UserModel;

public class NotificationsFragment extends Fragment {

    private static final String ARG_USER_ID = "user_id";
    private UserDataSource dataSource;
    private String userId;

    private TextView userNameTextView;
    private TextView phoneNumberTextView;
    private TextView emailTextView;
    private TextView passwordTextView;
    private Button editProfileButton;
    private Button ButtonThongkedoanhthu, buttonLogout;
    private ToggleButton toggleButton;

    public static NotificationsFragment newInstance(String userId) {
        NotificationsFragment fragment = new NotificationsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataSource = new UserDataSource(getContext());
        if (getArguments() != null) {
            userId = getArguments().getString(ARG_USER_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        userNameTextView = view.findViewById(R.id.userName);
        phoneNumberTextView = view.findViewById(R.id.phoneNumberTextView);
        emailTextView = view.findViewById(R.id.emailTextView);
        passwordTextView = view.findViewById(R.id.passwordTextView);
        ButtonThongkedoanhthu = view.findViewById(R.id.ButtonThongkedoanhthu);
        editProfileButton = view.findViewById(R.id.editProfileButton);
        buttonLogout = view.findViewById(R.id.Buttonlogout);
        toggleButton = view.findViewById(R.id.toggleButton);
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userId != null && !userId.isEmpty()) {
                    UserModel user = UserDataSource.getUserById(getContext(), userId);
                    if (user != null) {
                        Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                        intent.putExtra("user_id", userId);
                        intent.putExtra("username", user.getUsername());
                        intent.putExtra("phone_number", user.getPhoneNumber());
                        intent.putExtra("email", user.getEmail());
                        // Lưu ý: Chúng ta không truyền mật khẩu vì lý do bảo mật
                        startActivity(intent);
                    } else {
                        // Xử lý trường hợp không tìm thấy dữ liệu người dùng
                        // Bạn có thể hiển thị một thông báo toast hoặc hộp thoại cảnh báo
                    }
                } else {
                    // Xử lý trường hợp userId là null hoặc rỗng
                    // Bạn có thể hiển thị một thông báo toast hoặc hộp thoại cảnh báo
                }
            }
        });
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutAndExit();
            }
        });
        // Xử lý sự kiện của ToggleButton
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                } else {

                }
            }
        });
        ButtonThongkedoanhthu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userId != null && !userId.isEmpty()) {
                    UserModel user = UserDataSource.getUserById(getContext(), userId);
                    if (user != null) {
                        Intent intent = new Intent(getActivity(), StatisticalActivity.class);
                        intent.putExtra("user_id", userId);
                        startActivity(intent);
                    } else {
                        // Xử lý trường hợp không tìm thấy dữ liệu người dùng
                        // Bạn có thể hiển thị một thông báo toast hoặc hộp thoại cảnh báo
                    }
                } else {
                    // Xử lý trường hợp userId là null hoặc rỗng
                    // Bạn có thể hiển thị một thông báo toast hoặc hộp thoại cảnh báo
                }
            }
        });

        loadUserData();

        return view;
    }

    private void logoutAndExit() {
        // Logic để đăng xuất người dùng, ví dụ xóa dữ liệu người dùng, token, etc.

        // Dừng ứng dụng
        if (getActivity() != null) {
            ActivityCompat.finishAffinity(getActivity());
            System.exit(0);
        }
    }

    private void loadUserData() {
        if (userId != null && !userId.isEmpty()) {
            UserModel user = UserDataSource.getUserById(getContext(), userId);

            if (user != null) {
                userNameTextView.setText(user.getUsername());
                phoneNumberTextView.setText(user.getPhoneNumber());
                emailTextView.setText(user.getEmail());
                passwordTextView.setText("********");
            } else {
                displayNoUserDataFound();
            }
        } else {
            displayNoUserIdProvided();
        }
    }


    private void displayNoUserDataFound() {
        userNameTextView.setText("Không tìm thấy dữ liệu người dùng");
        phoneNumberTextView.setText("N/A");
        emailTextView.setText("N/A");
        passwordTextView.setText("N/A");
    }

    private void displayNoUserIdProvided() {
        userNameTextView.setText("Không có ID người dùng được cung cấp");
        phoneNumberTextView.setText("N/A");
        emailTextView.setText("N/A");
        passwordTextView.setText("N/A");
    }

    @Override
    public void onResume() {
        super.onResume();
        loadUserData();
    }
}