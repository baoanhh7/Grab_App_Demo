package com.example.grab_demo.admin.sales.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grab_demo.R;
import com.example.grab_demo.database.ConnectionClass;
import com.example.grab_demo.store_owner.OnItemClickListener;
import com.example.grab_demo.store_owner.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class QLTaiKhoanAdapter extends RecyclerView.Adapter<QLTaiKhoanAdapter.ViewHolder> {

    Context context;
    ArrayList<User> arr, arr1;
    Connection connection;
    private OnItemClickListener onItemClickListener;

    public QLTaiKhoanAdapter(Context context, ArrayList<User> arr) {
        this.context = context;
        this.arr = arr;
        this.arr1 = arr;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_qltaikhoan, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = arr.get(position);
        holder.txtTen.setText(user.getName());
        holder.txtStatus.setText(user.getStatus());
        holder.txtVP.setText(user.getVP() + "");
        String status = loadData(user.getId());
        if (status.equalsIgnoreCase("locked")) {
            holder.btn_delete.setVisibility(View.GONE);
        } else {
            holder.btn_delete.setVisibility(View.VISIBLE);
            holder.btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Xác nhận xóa");
                    builder.setMessage("Bạn có muốn xóa không ?");

                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int vps = user.getVP();
                            if (vps > 3) {
                                delete(user.getId(), position);
                            } else
                                Toast.makeText(context, "Bạn không thể xóa", Toast.LENGTH_SHORT).show();
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
    }

    private void delete(int id, int position) {
        ConnectionClass sql = new ConnectionClass();
        connection = sql.conClass();
        if (connection != null) {
            try {
                String query = "UPDATE  Users SET status = 'locked', updated_at = GETDATE() WHERE user_id = ?"; // Thay thế tên stored procedure và tham số tương ứng
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, id); // Thiết lập điều kiện WHERE để xác định hàng cần xóa

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


    private String loadData(int id) {
        ConnectionClass sql = new ConnectionClass();
        Connection connection = sql.conClass();
        String status = "";
        if (connection != null) {
            try {
                String query = "SELECT status FROM Users where user_id = " + id;
                Statement smt = connection.createStatement();
                ResultSet resultSet = smt.executeQuery(query);
                while (resultSet.next()) {
                    status = resultSet.getString(1);
                }
                connection.close();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
        } else {
            Log.e("Error: ", "Connection null");
        }
        return status;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTen, txtStatus;
        TextView txtVP;
        ImageButton btn_delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTen = itemView.findViewById(R.id.tv_ownerName_item_QLTaiKhoan);
            txtStatus = itemView.findViewById(R.id.tv_status_item_QLTaiKhoan);
            txtVP = itemView.findViewById(R.id.tv_VP_item_QLTaiKhoan);
            btn_delete = itemView.findViewById(R.id.btn_delete_QLTaiKhoan);
        }
    }
}

