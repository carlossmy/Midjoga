package com.example.carlossmy.midjogaapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.carlossmy.midjogaapp.R;

public class SplashActivity extends AppCompatActivity {
    private int SPLASH_TIME = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

     /*   Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(SPLASH_TIME);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (SharedPref.getPrefForLoginStatus(SplashActivity.this)) {

                        ProjectUtils.genericIntent(SplashActivity.this, MainActivity.class, null, true);
                    } else {
                        ProjectUtils.genericIntent(SplashActivity.this, LoginActivity.class, null, true);
                    }

                }
            }
        };
        timer.start();*/
    }
}
