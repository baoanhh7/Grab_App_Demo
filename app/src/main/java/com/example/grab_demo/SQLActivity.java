package com.example.grab_demo;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLActivity extends AppCompatActivity {
    TextView txt;
    Connection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlactivity);
        txt = findViewById(R.id.txt);
        ConnectionClass sql = new ConnectionClass();
        connection = sql.conClass();
        if(connection!= null){
            try {
                String query  = "Select * from Users";
                Statement smt = connection.createStatement();
                ResultSet resultSet = smt.executeQuery(query);
                while (resultSet.next()){
                    txt.setText(resultSet.getString(2));
                }
                connection.close();
            }catch (Exception e){
                Log.e("Error: ",e.getMessage());
            }
        }
    }

}
