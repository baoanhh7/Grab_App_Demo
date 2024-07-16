package com.example.grab_demo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grab_demo.login.LoginActivity;

public class StartActivity extends AppCompatActivity {
    Button btn_start;
    //LottieAnimationView lottie;
    private Handler handler = new Handler();
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        addControls();

//        btn_start.animate().translationY(-1400).setDuration(2700).setStartDelay(0);
//        lottie.animate().translationY(2000).setDuration(2000).setStartDelay(2900);

        runnable = new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
                finish();
            }
        };
        handler.postDelayed(runnable, 999999999);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        handler.removeCallbacks(runnable);
    }

    private void addControls() {
        btn_start = findViewById(R.id.btn_start);
        //lottie = findViewById(R.id.lottie);
    }
}