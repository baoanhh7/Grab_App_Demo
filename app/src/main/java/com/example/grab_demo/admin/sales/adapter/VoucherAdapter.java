package com.example.grab_demo.admin.sales.adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grab_demo.R;
import com.example.grab_demo.admin.sales.activity.UpdateVoucherSalesActivity;
import com.example.grab_demo.database.ConnectionClass;
import com.example.grab_demo.store_owner.OnItemClickListener;
import com.example.grab_demo.store_owner.model.Vouchers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;


public class VoucherAdapter extends RecyclerView.Adapter<VoucherAdapter.ViewHolder> {

    Context context;
    ArrayList<Vouchers> arr;
    Connection connection;
    private OnItemClickListener onItemClickListener;

    public VoucherAdapter(Context context, ArrayList<Vouchers> arr) {
        this.context = context;
        this.arr = arr;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_listvoucher_sales, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Vouchers vouchers = arr.get(position);
        holder.txtTen.setText(vouchers.getName());
        holder.start_date.setText(vouchers.getStart_date().toString());
        holder.end_date.setText(vouchers.getEnd_date().toString());
        holder.quantity.setText(vouchers.getQuantity() + "");
        holder.discount.setText(vouchers.getDiscount() + "");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClickIStoreRegistration(arr.get(position).getId(), arr.get(position).getName());
                }
            }
        });
        holder.btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateVoucherSalesActivity.class);
                intent.putExtra("voucherID", vouchers.getId());
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
                        delete(vouchers.getId(), position);
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

    private void delete(int idVoucher, int position) {
        ConnectionClass sql = new ConnectionClass();
        connection = sql.conClass();
        if (connection != null) {
            try {
                String query = "DELETE FROM Vouchers WHERE voucher_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, idVoucher); // Thiết lập điều kiện WHERE để xác định hàng cần xóa

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

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTen, start_date, end_date, quantity, discount;
        ImageButton btn_update, btn_delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTen = itemView.findViewById(R.id.tv_name_item_ListVoucherSales);
            start_date = itemView.findViewById(R.id.tv_startdate_item_ListVoucherSales);
            end_date = itemView.findViewById(R.id.tv_enddate_item_ListVoucherSales);
            quantity = itemView.findViewById(R.id.tv_quantity_item_ListVoucherSales);
            discount = itemView.findViewById(R.id.tv_discount_item_ListVoucherSales);
            btn_delete = itemView.findViewById(R.id.btn_delete_ListVoucherSales);
            btn_update = itemView.findViewById(R.id.btn_update_ListVoucherSales);
        }
    }
}
