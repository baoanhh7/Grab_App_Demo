package com.example.grab_demo.store_owner.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grab_demo.R;
import com.example.grab_demo.store_owner.ImageUtils;
import com.example.grab_demo.store_owner.activity.AddDishMenuActivity;
import com.example.grab_demo.store_owner.adapter.DishMenuHSOAdapter;
import com.example.grab_demo.store_owner.model.DishMenuHSO;

import java.util.ArrayList;

public class DishMenuHomeStoreOwnerFragment extends Fragment {
    ImageButton btn_add_dishmenuHSO;
    View view;
    RecyclerView rv_DishMenuHSO;
    ArrayList<DishMenuHSO> arr;
    DishMenuHSOAdapter dishMenuHSOAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_dish_menu_home_store_owner, container, false);
        addControls();
        addEvents();
        addDB();
        return view;
    }

    private void addEvents() {
        btn_add_dishmenuHSO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddDishMenuActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addDB() {
// Chuyển drawable sang mảng byte
        byte[] imageBytes = ImageUtils.convertDrawableToByteArray(getActivity(), R.drawable.pizza);
        arr.add(new DishMenuHSO(imageBytes, "Gà rán", "40.000 d"));
        imageBytes = ImageUtils.convertDrawableToByteArray(getActivity(), R.drawable.burger);
        arr.add(new DishMenuHSO(imageBytes, "Gà rán", "40.000 d"));
        imageBytes = ImageUtils.convertDrawableToByteArray(getActivity(), R.drawable.bread);
        arr.add(new DishMenuHSO(imageBytes, "Gà rán", "40.000 d"));
        imageBytes = ImageUtils.convertDrawableToByteArray(getActivity(), R.drawable.burger);
        arr.add(new DishMenuHSO(imageBytes, "Gà rán", "40.000 d"));
        imageBytes = ImageUtils.convertDrawableToByteArray(getActivity(), R.drawable.burger);
        arr.add(new DishMenuHSO(imageBytes, "Gà rán", "40.000 d"));
    }

    private void addControls() {
        rv_DishMenuHSO = view.findViewById(R.id.rv_DishMenuHSO);
        btn_add_dishmenuHSO = view.findViewById(R.id.btn_add_dishmenuHSO);
        arr = new ArrayList<>();
        dishMenuHSOAdapter = new DishMenuHSOAdapter(getActivity(), arr);
        rv_DishMenuHSO.setAdapter(dishMenuHSOAdapter);
        rv_DishMenuHSO.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    }
}