// GridAdapter.java
package com.example.grab_demo.customer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.grab_demo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GridAdapter extends ArrayAdapter<HashMap<String, String>> {

    private Context context;
    private List<HashMap<String, String>> items;
    private List<HashMap<String, String>> filteredItems;

    public GridAdapter(@NonNull Context context, @NonNull List<HashMap<String, String>> items) {
        super(context, 0, items);
        this.context = context;
        this.items = new ArrayList<>(items);
        this.filteredItems = new ArrayList<>(items);
    }

    @Override
    public int getCount() {
        return filteredItems.size();
    }

    @Nullable
    @Override
    public HashMap<String, String> getItem(int position) {
        return filteredItems.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item_search, parent, false);
        }

        ImageView itemImage = convertView.findViewById(R.id.itemImage);
        TextView itemText = convertView.findViewById(R.id.itemText);

        HashMap<String, String> item = getItem(position);

        if (item != null) {
            itemImage.setImageResource(Integer.parseInt(item.get("image")));
            itemText.setText(item.get("name"));
        }

        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<HashMap<String, String>> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(items);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (HashMap<String, String> item : items) {
                        if (item.get("name").toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                }

                results.values = filteredList;
                results.count = filteredList.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredItems.clear();
                filteredItems.addAll((List<HashMap<String, String>>) results.values);
                notifyDataSetChanged();
            }
        };
    }
}
