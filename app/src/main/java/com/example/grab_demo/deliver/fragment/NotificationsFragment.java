package com.example.grab_demo.deliver.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.grab_demo.R;
import com.example.grab_demo.database.UserDataSource;
import com.example.grab_demo.deliver.activity.EditProfileActivity;
import com.example.grab_demo.model.UserModel;

public class NotificationsFragment extends Fragment {

    private static final String ARG_USER_ID = "user_id";
    private UserDataSource dataSource;
    private String userId;

    private TextView userNameTextView;
    private TextView phoneNumberTextView;
    private TextView emailTextView;
    private TextView passwordTextView;

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

        Button editProfileButton = view.findViewById(R.id.editProfileButton);
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                intent.putExtra("user_id", userId);
                startActivity(intent);
            }
        });

        loadUserData();

        return view;
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