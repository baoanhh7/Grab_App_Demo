package com.example.grab_demo.deliver.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.grab_demo.R;
import com.example.grab_demo.deliver.activity.EditProfileActivity;
import com.example.grab_demo.model.UserModel;

public class NotificationsFragment extends Fragment {

    private UserModel userModel; // Assume you have UserModel instance here

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        Button editProfileButton = view.findViewById(R.id.editProfileButton);
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                intent.putExtra("userModel", userModel); // Pass UserModel object to EditProfileActivity
                startActivity(intent);
            }
        });
        return view;
    }

    // Method to update UserModel from data source
    public void updateUserModel(UserModel userModel) {
        this.userModel = userModel;
    }
}
