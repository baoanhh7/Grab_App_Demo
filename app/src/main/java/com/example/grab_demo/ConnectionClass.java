package com.example.grab_demo;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionClass {
    Connection connection;
    private static final String DB_URL = "jdbc:jtds:sqlserver://172.31.117.71:1433;databaseName=FoodOrderingSystem";
    private static final String USER = "sa";
    private static final String PASS = "1";
    @SuppressLint("NewApi")
    public Connection conClass(){
        StrictMode.ThreadPolicy a = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(a);
        // Tải driver JDBC
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        }catch (Exception e){
            Log.e("Error is ", e.getMessage());
        }
        return connection;
    }
}
