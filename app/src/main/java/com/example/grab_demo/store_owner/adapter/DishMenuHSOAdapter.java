package com.example.grab_demo.store_owner.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grab_demo.R;
import com.example.grab_demo.store_owner.OnItemClickListener;
import com.example.grab_demo.store_owner.activity.UpdateDishMenuActivity;
import com.example.grab_demo.store_owner.model.DishMenuHSO;

import java.util.ArrayList;

public class DishMenuHSOAdapter extends RecyclerView.Adapter<DishMenuHSOAdapter.ViewHolder>{

    Context context;
    ArrayList<DishMenuHSO> arr;
    private OnItemClickListener onItemClickListener;
    boolean flag = false;

    public DishMenuHSOAdapter(Context context, ArrayList<DishMenuHSO> arr) {
        this.context = context;
        this.arr = arr;
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
//        holder.switchToggle_dishmenuHSO.setChecked(flag);
//        holder.switchToggle_dishmenuHSO.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(flag == false)
//                {
//                    flag = true;
//                }else
//                    flag = false;
//            }
//        });
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
//                intent.putExtra("id", artists.getArtistID());
//                intent.putExtra("name", artists.getArtistName());
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
//                        delete(artists.getArtistID());
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

    @Override
    public int getItemCount() {
        return arr.size();
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txtTen;
        TextView txtGia;
        Switch switchToggle_dishmenuHSO;
        ImageButton btn_update, btn_delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.ImageView_item_dishmenuHSO);
            txtTen = itemView.findViewById(R.id.tv_name_item_dishmenuHSO);
            txtGia = itemView.findViewById(R.id.tv_price_item_dishmenuHSO);
            switchToggle_dishmenuHSO = itemView.findViewById(R.id.switchToggle_dishmenuHSO);
            btn_delete = itemView.findViewById(R.id.btn_delete_dishmenu);
            btn_update = itemView.findViewById(R.id.btn_update_dishmenu);
        }
    }
}
