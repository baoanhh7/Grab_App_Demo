package com.example.grab_demo.store_owner.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grab_demo.R;
import com.example.grab_demo.database.ConnectionClass;
import com.example.grab_demo.store_owner.OnItemClickListener;
import com.example.grab_demo.store_owner.model.Stores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


public class StoreHSOAdapter extends RecyclerView.Adapter<StoreHSOAdapter.ViewHolder> implements Filterable {

    Context context;
    ArrayList<Stores> arr, arr1;
    boolean flag = false;
    String status;
    private OnItemClickListener onItemClickListener;

    public StoreHSOAdapter(Context context, ArrayList<Stores> arr) {
        this.context = context;
        this.arr = arr;
        this.arr1 = arr;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_liststorehso, parent, false);
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
        holder.txtOpen.setText(stores.getGiomocua().toString());
        holder.txtClose.setText(stores.getGiodongcua().toString());
        holder.getCurrentStoreStatus();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(arr.get(position).getTensp());
                }
            }
        });
        holder.switchToggle_dishmenuHSO.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String status = isChecked ? "opened" : "closed";
                holder.updateStoreStatus(status);
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txtTen;
        TextView txtOpen, txtClose;
        Switch switchToggle_dishmenuHSO;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.ImageView_item_StoreHSO);
            txtTen = itemView.findViewById(R.id.tv_name_item_StoreHSO);
            txtOpen = itemView.findViewById(R.id.tv_opened_item_StoreHSO);
            txtClose = itemView.findViewById(R.id.tv_closed_item_StoreHSO);
            switchToggle_dishmenuHSO = itemView.findViewById(R.id.switchToggle_item_StoreHSO);
        }

        private void getCurrentStoreStatus() {
            ConnectionClass sql = new ConnectionClass();
            Connection connection = sql.conClass();
            if (connection != null) {
                try {
                    String query = "SELECT status FROM Stores WHERE store_name = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, txtTen.getText().toString().trim()); // Thay storeId bằng ID của cửa hàng bạn muốn lấy trạng thái
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        status = resultSet.getString(1);
                        switchToggle_dishmenuHSO.setChecked("opened".equals(status));
                    }
                    connection.close();
                } catch (Exception e) {
                    Log.e("Error: ", e.getMessage());
                }
            } else {
                Log.e("Error: ", "Connection null");
            }
        }

        private void updateStoreStatus(String status) {
            ConnectionClass sql = new ConnectionClass();
            Connection connection = sql.conClass();
            if (connection != null) {
                try {
                    String query = "UPDATE Stores SET status = ?, updated_at = ? WHERE store_name = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, status);
                    preparedStatement.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));
                    preparedStatement.setString(3, txtTen.getText().toString().trim()); // Thay storeId bằng ID của cửa hàng bạn muốn cập nhật
                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        Log.d("UpdateStoreStatus", "Update successfully");
                    } else {
                        Log.e("UpdateStoreStatus", "Update failed");
                    }
                    connection.close();
                } catch (Exception e) {
                    Log.e("Error: ", e.getMessage());
                }
            } else {
                Log.e("Error: ", "Connection null");
            }
        }

    }

}
