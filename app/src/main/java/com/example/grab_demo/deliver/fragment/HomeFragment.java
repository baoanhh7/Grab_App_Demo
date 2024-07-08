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
                    startActivity(intent);
                }
            });
        }
        Button btnOpenMap = view.findViewById(R.id.btnOpenMap);
        btnOpenMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                startActivity(intent);
            }
        });
        Button btnDHNew = view.findViewById(R.id.btnDHNew);
        btnDHNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewDHFragment(v);
            }
        });
        return view;
    }

    public void openNewDHFragment(View view) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, NewDHFragment.newInstance());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
