package com.example.grab_demo.admin.sales.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grab_demo.R;
import com.example.grab_demo.database.ConnectionClass;
import com.example.grab_demo.store_owner.OnItemClickListener;
import com.example.grab_demo.store_owner.model.Stores;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class ListStoreRegistrationAdapter extends RecyclerView.Adapter<ListStoreRegistrationAdapter.ViewHolder> implements Filterable {

    Context context;
    ArrayList<Stores> arr, arr1;
    boolean flag = false;
    String status;
    private OnItemClickListener onItemClickListener;

    public ListStoreRegistrationAdapter(Context context, ArrayList<Stores> arr) {
        this.context = context;
        this.arr = arr;
        this.arr1 = arr;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list_store_registration, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Stores stores = arr.get(position);
        byte[] hinhAlbumByteArray = stores.getHinh();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAlbumByteArray, 0, hinhAlbumByteArray.length);
        holder.img.setImageBitmap(bitmap);
        holder.txtTen.setText(stores.getTensp());
        String name = loadData(stores.getOwner_id());
        holder.txtownername.setText(name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClickIStoreRegistration(arr.get(position).getId(), name);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()) {
                    arr = arr1;
                } else {
                    ArrayList<Stores> arrayList = new ArrayList<>();
                    for (Stores stores : arr1) {
                        if (stores.getTensp().toLowerCase().contains(strSearch.toLowerCase())) {
                            arrayList.add(stores);
                        }
                    }
                    arr = arrayList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = arr;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                arr = (ArrayList<Stores>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    private String loadData(int id) {
        ConnectionClass sql = new ConnectionClass();
        Connection connection = sql.conClass();
        String name = "";
        if (connection != null) {
            try {
                String query = "SELECT username FROM Users where user_id = " + id;
                Statement smt = connection.createStatement();
                ResultSet resultSet = smt.executeQuery(query);
                while (resultSet.next()) {
                    name = resultSet.getString(1);
                }
                connection.close();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
        } else {
            Log.e("Error: ", "Connection null");
        }
        return name;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txtTen;
        TextView txtownername;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.ImageView_item_ListStoreRegistration);
            txtTen = itemView.findViewById(R.id.tv_name_item_ListStoreRegistration);
            txtownername = itemView.findViewById(R.id.tv_ownerName_item_ListStoreRegistration);
        }
    }
}

