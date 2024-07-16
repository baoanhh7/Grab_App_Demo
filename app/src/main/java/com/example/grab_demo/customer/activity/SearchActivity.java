// activity_search.java
package com.example.grab_demo.customer.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grab_demo.R;
import com.example.grab_demo.customer.adapter.GridAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchActivity extends AppCompatActivity {

    GridView gridView;
    EditText searchBar;
    ArrayList<HashMap<String, String>> items;
    GridAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ImageView backButton = findViewById(R.id.backButton);
        TextView title = findViewById(R.id.title);
        searchBar = findViewById(R.id.searchBar);
        gridView = findViewById(R.id.gridView);

        // Sample data
        items = new ArrayList<>();
        String[] names = {"Burger", "Đồ uống", "Pizza", "Gà rán", "Cơm", "Đồ ngọt", "Nước", "Trái cây", "Bánh mì", "Khác"};
        int[] images = {R.drawable.burger, R.drawable.noodles, R.drawable.pizzaicon, R.drawable.chicken, R.drawable.rice,
                R.drawable.cake, R.drawable.drink, R.drawable.fruit, R.drawable.bread, R.drawable.other};

        for (int i = 0; i < names.length; i++) {
            HashMap<String, String> map = new HashMap<>();
            map.put("name", names[i]);
            map.put("image", String.valueOf(images[i]));
            items.add(map);
        }

        adapter = new GridAdapter(this, items);
        gridView.setAdapter(adapter);

        // Hide GridView initially
        gridView.setVisibility(View.GONE);

        // Search functionality
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    gridView.setVisibility(View.GONE);
                } else {
                    gridView.setVisibility(View.VISIBLE);
                }
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        backButton.setOnClickListener(v -> onBackPressed());
    }
}
