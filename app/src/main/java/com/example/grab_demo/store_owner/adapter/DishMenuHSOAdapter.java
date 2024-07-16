package com.example.grab_demo.store_owner.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grab_demo.R;
import com.example.grab_demo.database.ConnectionClass;
import com.example.grab_demo.store_owner.OnItemClickListener;
import com.example.grab_demo.store_owner.activity.UpdateDishMenuActivity;
import com.example.grab_demo.store_owner.model.DishMenuHSO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DishMenuHSOAdapter extends RecyclerView.Adapter<DishMenuHSOAdapter.ViewHolder> implements Filterable {

    Context context;
    ArrayList<DishMenuHSO> arr, arr1;
    boolean flag = false;
    Connection connection;
    String status;
    private OnItemClickListener onItemClickListener;

    public DishMenuHSOAdapter(Context context, ArrayList<DishMenuHSO> arr) {
        this.context = context;
        this.arr = arr;
        this.arr1 = arr;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_dishmenu_hso, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DishMenuHSO dishMenuHSO = arr.get(position);
        byte[] hinhAlbumByteArray = dishMenuHSO.getHinh();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAlbumByteArray, 0, hinhAlbumByteArray.length);
        holder.img.setImageBitmap(bitmap);
        holder.txtTen.setText(dishMenuHSO.getTensp());
        holder.txtGia.setText(dishMenuHSO.getGiasp() + "");
        holder.txtMoTa.setText(dishMenuHSO.getMota());
        holder.txtSL.setText(dishMenuHSO.getSoluong() + "");
        holder.getCurrentStoreStatus(dishMenuHSO.getId());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(arr.get(position).getTensp());
                }
            }
        });
        holder.btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateDishMenuActivity.class);
                intent.putExtra("item_id", dishMenuHSO.getId());
                context.startActivity(intent);
            }
        });
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có muốn xóa không ?");

                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete(dishMenuHSO.getId(), position);
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });
        holder.switchToggle_dishmenuHSO.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String status = isChecked ? "active" : "inactive";
                holder.updateDishMenuStatus(status, dishMenuHSO.getId());
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

    private void delete(int idStore, int position) {
        ConnectionClass sql = new ConnectionClass();
        connection = sql.conClass();
        if (connection != null) {
            try {
                String query = "DELETE FROM Items WHERE item_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, idStore); // Thiết lập điều kiện WHERE để xác định hàng cần xóa

                // Thực thi truy vấn DELETE
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    Log.d("DeleteDishMenuHSO", "Delete successfully");
                    // Xóa mục khỏi danh sách và thông báo cho adapter
                    arr.remove(position);
                    notifyItemRemoved(position);
                } else {
                    Log.e("DeleteDishMenuHSO", "Delete failed");
                }
                connection.close();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
        } else {
            Log.e("Error: ", "Connection null");
        }
        notifyDataSetChanged();
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
                    ArrayList<DishMenuHSO> arrayList = new ArrayList<>();
                    for (DishMenuHSO dishMenuHSO : arr1) {
                        if (dishMenuHSO.getTensp().toLowerCase().contains(strSearch.toLowerCase())) {
                            arrayList.add(dishMenuHSO);
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
                arr = (ArrayList<DishMenuHSO>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txtTen;
        TextView txtGia;
        TextView txtMoTa;
        TextView txtSL;
        Switch switchToggle_dishmenuHSO;
        ImageButton btn_update, btn_delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.ImageView_item_dishmenuHSO);
            txtTen = itemView.findViewById(R.id.tv_name_item_dishmenuHSO);
            txtGia = itemView.findViewById(R.id.tv_price_item_dishmenuHSO);
            txtMoTa = itemView.findViewById(R.id.tv_description_item_dishmenuHSO);
            txtSL = itemView.findViewById(R.id.tv_quantity_item_dishmenuHSO);
            switchToggle_dishmenuHSO = itemView.findViewById(R.id.switchToggle_dishmenuHSO);
            btn_delete = itemView.findViewById(R.id.btn_delete_dishmenu);
            btn_update = itemView.findViewById(R.id.btn_update_dishmenu);
        }

        private void getCurrentStoreStatus(int id) {
            ConnectionClass sql = new ConnectionClass();
            Connection connection = sql.conClass();
            if (connection != null) {
                try {
                    String query = "SELECT status FROM Items WHERE item_id = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1, id); // Thay storeId bằng ID của cửa hàng bạn muốn lấy trạng thái
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        status = resultSet.getString(1);
                        switchToggle_dishmenuHSO.setChecked("active".equals(status));
                    }
                    connection.close();
                } catch (Exception e) {
                    Log.e("Error: ", e.getMessage());
                }
            } else {
                Log.e("Error: ", "Connection null");
            }
        }

        private void updateDishMenuStatus(String status, int id) {
            ConnectionClass sql = new ConnectionClass();
            Connection connection = sql.conClass();

            if (connection != null) {
                try {
                    String query = "UPDATE Items SET status = ?, updated_at = ? WHERE item_id = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, status);
                    preparedStatement.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));
                    preparedStatement.setInt(3, id); // Thay storeId bằng ID của cửa hàng bạn muốn cập nhật
                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        Log.d("UpdateDishMenuStatus", "Update successfully");
                    } else {
                        Log.e("UpdateDishMenuStatus", "Update failed");
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
