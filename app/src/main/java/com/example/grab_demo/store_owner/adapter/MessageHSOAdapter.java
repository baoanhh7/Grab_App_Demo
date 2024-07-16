package com.example.grab_demo.store_owner.adapter;

import android.content.Context;
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
import com.example.grab_demo.store_owner.OnItemClickListener;
import com.example.grab_demo.store_owner.model.Message;

import java.util.ArrayList;

public class MessageHSOAdapter extends RecyclerView.Adapter<MessageHSOAdapter.ViewHolder> implements Filterable {

    Context context;
    ArrayList<Message> arr, arr1;
    boolean flag = false;
    String status;
    private OnItemClickListener onItemClickListener;

    public MessageHSOAdapter(Context context, ArrayList<Message> arr) {
        this.context = context;
        this.arr = arr;
        this.arr1 = arr;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_message_store_owner, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message message = arr.get(position);
//        byte[] hinhAlbumByteArray = user.getHinh();
//        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAlbumByteArray, 0, hinhAlbumByteArray.length);
//        holder.img.setImageBitmap(bitmap);
        holder.txtTen.setText(message.getSender_id());
        holder.message.setText(message.getMessage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClickMessage(arr.get(position).getSender_id(), arr.get(position).getReceiver_id());
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
                    ArrayList<Message> arrayList = new ArrayList<>();
                    for (Message message : arr1) {
                        if (message.getReceiver_name().toLowerCase().contains(strSearch.toLowerCase())) {
                            arrayList.add(message);
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
                arr = (ArrayList<Message>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txtTen;
        TextView message;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_user_item_message_store_owner);
            txtTen = itemView.findViewById(R.id.tv_name_item_message_store_owner);
            message = itemView.findViewById(R.id.tv_message_item_message_store_owner);
        }


    }

}
