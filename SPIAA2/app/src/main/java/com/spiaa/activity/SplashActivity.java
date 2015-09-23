package com.spiaa.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.spiaa.R;

import java.util.TimerTask;
import java.util.Timer;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                finish();

                Intent intent = new Intent();
                intent.setClass(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }, 3000);
    }
}
