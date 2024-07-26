package com.example.grab_demo.customer.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grab_demo.R;
import com.example.grab_demo.customer.m_interface.OnQuantityChangeListener;
import com.example.grab_demo.customer.m_interface.StClickItem;
import com.example.grab_demo.customer.model.Item;
import com.example.grab_demo.database.ConnectionClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Objects;

public class ListOrderAdapter extends RecyclerView.Adapter<ListOrderAdapter.HomeViewHolder> {
    Context context;
    List<Item> itemList;
    private StClickItem stClickItem;
    private OnQuantityChangeListener quantityChangeListener;


    public ListOrderAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    public void setOnQuantityChangeListener(OnQuantityChangeListener listener) {
        this.quantityChangeListener = listener;
    }

    public void setOnClickItemListener(StClickItem stClickItem) {
        this.stClickItem = stClickItem;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_order, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        Item item = itemList.get(position);

        holder.txt_name.setText(item.getItemName());
        holder.txt_price.setText(String.valueOf(item.getPrice()));
        byte[] img = item.getImage();

        if (img != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
            holder.img.setImageBitmap(bitmap);
        } else {
            Log.e("ListOrderAdapter", "Items array is null");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stClickItem != null) {
                    stClickItem.onClickItem(String.valueOf(item.getItemId()));
                }
            }
        });

        holder.img_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = item.getQuantity();
                if (count > 1) {
                    count--;
                    item.setQuantity(count);
                    holder.txt_count.setText(String.valueOf(count));
                    updateItemQuantity(item);
                }
            }
        });

        holder.img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = item.getQuantity();

                if (count < 10) {
                    count++;
                    item.setQuantity(count);
                    holder.txt_count.setText(String.valueOf(count));
                    updateItemQuantity(item);
                }
            }
        });

        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItemFromCart(item.getItemId());
                itemList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, itemList.size());
                Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show();
            }
        });

        updateQuantity(holder, item);

    }

    private void updateQuantity(HomeViewHolder holder, Item item) {
        ConnectionClass sql = new ConnectionClass();
        Connection connection = sql.conClass();
        if (connection != null) {
            try {
                // Prepare SQL query to fetch the quantity with both item_id and cart_id
                String query = "SELECT quantity FROM CartItems WHERE item_id = ? AND cart_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, item.getItemId());
                preparedStatement.setInt(2, 1);

                // Execute the query and process the result
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    int quantity = resultSet.getInt("quantity");
                    // Update the quantity in the item object
                    item.setQuantity(quantity);
                    // Update the TextView
                    holder.txt_count.setText(String.valueOf(quantity));
                } else {
                    Log.e("ListOrderAdapter", "No data found for item_id: " + item.getItemId());
                }

                // Close resources
                resultSet.close();
                preparedStatement.close();
                connection.close();
            } catch (Exception e) {
                Log.e("Error: ", Objects.requireNonNull(e.getMessage()));
            }
        } else {
            Log.e("Error: ", "Connection null");
        }
    }


    @Override
    public int getItemCount() {
        if (itemList != null) return itemList.size();
        return 0;
    }

    private void updateItemQuantity(Item item) {
        ConnectionClass sql = new ConnectionClass();
        Connection connection = sql.conClass();
        if (connection != null) {
            try {
                String query = "UPDATE CartItems SET quantity = ? WHERE item_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, item.getQuantity());
                preparedStatement.setInt(2, item.getItemId());
                preparedStatement.executeUpdate();
                connection.close();

                if (quantityChangeListener != null) {
                    quantityChangeListener.onQuantityChanged();
                }

            } catch (Exception e) {
                Log.e("Error: ", Objects.requireNonNull(e.getMessage()));
            }
        } else {
            Log.e("Error: ", "Connection null");
        }
    }

    private void deleteItemFromCart(int itemId) {
        ConnectionClass sql = new ConnectionClass();
        Connection connection = sql.conClass();
        if (connection != null) {
            try {
                String query = "DELETE FROM CartItems WHERE item_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, itemId);
                preparedStatement.executeUpdate();
                connection.close();
            } catch (Exception e) {
                Log.e("Error: ", Objects.requireNonNull(e.getMessage()));
            }
        } else {
            Log.e("Error: ", "Connection null");
        }
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {
        TextView txt_name, txt_price, txt_count;
        ImageView img, img_remove, img_add, img_delete;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_name = itemView.findViewById(R.id.txt_name);
            txt_price = itemView.findViewById(R.id.txt_price);
            txt_count = itemView.findViewById(R.id.txt_count);

            img = itemView.findViewById(R.id.img);
            img_remove = itemView.findViewById(R.id.img_remove);
            img_add = itemView.findViewById(R.id.img_add);
            img_delete = itemView.findViewById(R.id.img_delete);
        }
    }
}
