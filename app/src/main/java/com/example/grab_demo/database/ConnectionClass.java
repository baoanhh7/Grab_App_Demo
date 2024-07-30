package com.example.grab_demo.database;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionClass {
    //    private static final String DB_URL = "jdbc:jtds:sqlserver://172.31.54.212:1433;databaseName=FoodOrderingSystem";
//    private static final String DB_URL = "jdbc:jtds:sqlserver://192.168.1.16:1433;databaseName=FoodOrderingSystem";
//    private static final String DB_URL = "jdbc:jtds:sqlserver://10.20.1.44:1433;databaseName=FoodOrderingSystem";
    //private static final String DB_URL = "jdbc:jtds:sqlserver://172.31.119.2:1433;databaseName=FoodOrderingSystem";
    private static final String DB_URL = "jdbc:jtds:sqlserver://172.31.51.180:1433;databaseName=FoodOrderingSystem";
    // 172.31.119.2
    //bao anh
   // private static final String DB_URL = "jdbc:jtds:sqlserver://192.168.1.58:1433;databaseName=FoodOrderingSystem";

    private static final String USER = "sa";
    //private static final String PASS = "YourStrong!Passw0rd";
    private static final String PASS = "1";
    Connection connection;

    @SuppressLint("NewApi")
    public Connection conClass() {
        StrictMode.ThreadPolicy a = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(a);
        // Tải driver JDBC
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (Exception e) {
            Log.e("Error is ", e.getMessage());
        }
        return connection;
    }
}
