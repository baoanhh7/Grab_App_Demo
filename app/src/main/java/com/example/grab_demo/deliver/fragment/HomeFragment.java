package com.example.grab_demo.deliver.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.grab_demo.R;
import com.example.grab_demo.deliver.activity.MapsActivity;
import com.google.android.gms.maps.SupportMapFragment;

public class HomeFragment extends Fragment {

    private static final String ARG_USER_ID = "user_id";
    private String userId;

    public static HomeFragment newInstance(String userId) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userId = getArguments().getString(ARG_USER_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null && mapFragment.getView() != null) {
            mapFragment.getView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), MapsActivity.class);
                    intent.putExtra("user_id", userId); // Pass user_id to MapsActivity
                    startActivity(intent);
                }
            });
        }

        Button btnOpenMap = view.findViewById(R.id.btnOpenMap);
        btnOpenMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                intent.putExtra("user_id", userId); // Pass user_id to MapsActivity
                startActivity(intent);
            }
        });

        Button btnDHNew = view.findViewById(R.id.btnDHNew);
        btnDHNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewDHFragment();
            }
        });

        return view;
    }

    public void openNewDHFragment() {
        NewDHFragment newDHFragment = NewDHFragment.newInstance(userId); // Create new instance with user_id
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, newDHFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
