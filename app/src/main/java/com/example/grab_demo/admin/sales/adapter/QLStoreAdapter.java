package com.example.grab_demo.admin.sales.adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class QLStoreAdapter extends RecyclerView.Adapter<QLStoreAdapter.ViewHolder> implements Filterable {

    Context context;
    ArrayList<Stores> arr, arr1;
    Connection connection;
    private OnItemClickListener onItemClickListener;

    public QLStoreAdapter(Context context, ArrayList<Stores> arr) {
        this.context = context;
        this.arr = arr;
        this.arr1 = arr;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_qlstore, parent, false);
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

        // Giả sử stores.getUpdated_at() trả về một đối tượng Date
        Date updatedAt = stores.getUpdated_at();

        // Chuyển đổi Date thành LocalDate
        LocalDate updatedAtLocal = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            updatedAtLocal = updatedAt.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }

        // Lấy ngày hiện tại
        LocalDate currentDate = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            currentDate = LocalDate.now();
        }

        // Tính khoảng thời gian giữa hai ngày
        Period period = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            period = Period.between(updatedAtLocal, currentDate);
        }

        // Lấy số tháng
        int months = 0;
        int days = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            if (period.getYears() == 0 && period.getMonths() == 0) {
                days = period.getDays();
                holder.txtclosed.setText(days + " ngay trước");
            } else {
                months = period.getYears() * 12 + period.getMonths();
                holder.txtclosed.setText(months + " tháng trước");
            }
        }

        // Hiển thị kết quả

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClickIStoreRegistration(arr.get(position).getId(), name);
                }
            }
        });
        int finalMonths = months;
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có muốn xóa không ?");

                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (finalMonths >= 3) {
                            delete(stores.getId(), position);
                        } else
                            Toast.makeText(context, "Bạn không đủ 3 tháng để xóa", Toast.LENGTH_SHORT).show();
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
    }

    private void delete(int idStore, int position) {
        ConnectionClass sql = new ConnectionClass();
        connection = sql.conClass();
        if (connection != null) {
            try {
                String query = "EXEC DeleteStore ?"; // Thay thế tên stored procedure và tham số tương ứng
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, idStore); // Thiết lập điều kiện WHERE để xác định hàng cần xóa

                // Thực thi truy vấn DELETE
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    Log.d("DeleteCateSalesActivity", "Delete successfully");
                    // Xóa mục khỏi danh sách và thông báo cho adapter
                    arr.remove(position);
                    notifyItemRemoved(position);
                } else {
                    Log.e("DeleteCateSalesActivity", "Delete failed");
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
        TextView txtTen, txtownername;
        TextView txtclosed;
        ImageButton btn_delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.ImageView_item_QLStore);
            txtTen = itemView.findViewById(R.id.tv_name_item_QLStore);
            txtclosed = itemView.findViewById(R.id.tv_closed_item_QLStore);
            txtownername = itemView.findViewById(R.id.tv_ownerName_item_QLStore);
            btn_delete = itemView.findViewById(R.id.btn_delete_QLStore);
        }
    }
}

