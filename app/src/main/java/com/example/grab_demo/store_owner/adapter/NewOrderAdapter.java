package com.example.grab_demo.store_owner.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grab_demo.R;
import com.example.grab_demo.database.ConnectionClass;
import com.example.grab_demo.store_owner.OnItemClickListener;
import com.example.grab_demo.store_owner.model.Order;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class NewOrderAdapter extends RecyclerView.Adapter<NewOrderAdapter.ViewHolder> implements Filterable {

    Context context;
    ArrayList<Order> arr, arr1;
    Connection connection;
    private OnItemClickListener onItemClickListener;

    public NewOrderAdapter(Context context, ArrayList<Order> arr) {
        this.context = context;
        this.arr = arr;
        this.arr1 = arr;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_rcv_neworder, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = arr.get(position);
        holder.txtID.setText(order.getId() + "");
        String name = loadData(order.getDeliverId());
        holder.txtshippername.setText(name);
        holder.txtStatus.setText(order.getStatus());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClickID(arr.get(position).getId());
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

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString().trim();
                if (strSearch.isEmpty()) {
                    arr = arr1;
                } else {
                    ArrayList<Order> arrayList = new ArrayList<>();
                    for (Order order : arr1) {
                        if (String.valueOf(order.getId()).contains(strSearch)) {
                            arrayList.add(order);
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
                arr = (ArrayList<Order>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtID, txtshippername;
        TextView txtStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtID = itemView.findViewById(R.id.tv_idOrder_item_NewOrder);
            txtshippername = itemView.findViewById(R.id.tv_shipperName_item_NewOrder);
            txtStatus = itemView.findViewById(R.id.tv_status_item_NewOrder);
        }
    }
}

