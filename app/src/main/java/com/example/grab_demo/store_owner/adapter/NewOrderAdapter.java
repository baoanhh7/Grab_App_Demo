package com.example.grab_demo.store_owner.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grab_demo.R;
import com.example.grab_demo.database.ConnectionClass;
import com.example.grab_demo.store_owner.OnItemClickListener;
import com.example.grab_demo.store_owner.model.Order;
import com.example.grab_demo.store_owner.model.Stores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;


public class NewOrderAdapter extends RecyclerView.Adapter<NewOrderAdapter.ViewHolder>{

    Context context;
    ArrayList<Order> arr, arr1;
    private OnItemClickListener onItemClickListener;
    Connection connection;

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
        holder.txtID.setText(order.getId()+"");
        String name = loadData(order.getDeliverId());
        holder.txtshippername.setText(name);
        holder.txtStatus.setText(order.getStatus());
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

