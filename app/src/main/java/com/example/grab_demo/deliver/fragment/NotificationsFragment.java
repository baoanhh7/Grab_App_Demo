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

public class NotificationsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        Button editProfileButton = view.findViewById(R.id.editProfileButton);
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                intent.putExtra("name", "Anna Avetisyan");
                intent.putExtra("birthday", "");  // Replace with actual birthday
                intent.putExtra("phone", "818 123 4567");
                intent.putExtra("email", "info@aplusdesign.co");
                intent.putExtra("password", "123456789");  // Replace with actual password
                startActivity(intent);
            }
        });

        return view;
    }
}
