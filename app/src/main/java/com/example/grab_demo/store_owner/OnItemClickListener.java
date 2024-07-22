package com.example.grab_demo.store_owner;

public interface OnItemClickListener {
    void onItemClick(String data);

    void onItemClickIStoreRegistration(int data, String name);

    void onItemClickMessage(int sender_id, int reciever_id);

    void onItemClickID(int data);
}
