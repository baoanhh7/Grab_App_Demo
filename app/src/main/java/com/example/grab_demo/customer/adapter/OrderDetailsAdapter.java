package com.example.grab_demo.customer.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grab_demo.R;
import com.example.grab_demo.customer.m_interface.StClickItem;
import com.example.grab_demo.customer.model.OrderDetails;
import com.example.grab_demo.database.ConnectionClass;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.HomeViewHolder> {
    Context context;
    List<OrderDetails> storeList;
    private StClickItem stClickItem;

    public OrderDetailsAdapter(Context context, List<OrderDetails> storeList) {
        this.context = context;
        this.storeList = storeList;
    }

    public void setOnClickItemListener(StClickItem stClickItem) {
        this.stClickItem = stClickItem;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_details, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        OrderDetails orderDetails = storeList.get(position);
        holder.txt_item_name.setText(getItemName(orderDetails.getItemId()));
        holder.txt_item_price.setText(String.valueOf(orderDetails.getPrice()));
        holder.txt_item_quantity.setText(String.valueOf(orderDetails.getQuantity()));

        holder.txt_item_total.setText(String.valueOf(orderDetails.getPrice() * orderDetails.getQuantity()));

    }

    public String getItemName(int itemId) {
        ConnectionClass sql = new ConnectionClass();
        Connection connection = sql.conClass();
        String itemName = "";

        if (connection != null) {
            try {
                String query = "SELECT item_name FROM Items WHERE item_id = " + itemId;
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                if (resultSet.next()) {
                    itemName = resultSet.getString("item_name");
                }
                connection.close();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
        } else {
            Log.e("Error: ", "Connection null");
        }
        return itemName;
    }


    @Override
    public int getItemCount() {
        if (storeList != null) return storeList.size();
        return 0;
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {
        TextView txt_item_name, txt_item_price, txt_item_quantity, txt_item_total;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_item_name = itemView.findViewById(R.id.txt_item_name);
            txt_item_price = itemView.findViewById(R.id.txt_item_price);
            txt_item_quantity = itemView.findViewById(R.id.txt_item_quantity);
            txt_item_total = itemView.findViewById(R.id.txt_item_total);
        }
    }
}
