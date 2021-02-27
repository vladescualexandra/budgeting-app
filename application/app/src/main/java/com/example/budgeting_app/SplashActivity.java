package com.example.budgeting_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.budgeting_app.authentication.LoginActivity;
import com.example.budgeting_app.authentication.user.User;

public class SplashActivity extends AppCompatActivity {

    private final int DISPLAY_DURATION = 2000;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences preferences = getSharedPreferences(User.USER_PREFS, MODE_PRIVATE);
        String key = preferences.getString(User.USER_KEY, null);
        String username = preferences.getString(User.USER_NAME, null);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (key == null &&  username == null) {
                    intent = new Intent(getApplicationContext(), LoginActivity.class);
                } else {
                    intent = new Intent(getApplicationContext(), MainActivity.class);
                }
                startActivity(intent);
            }
        }, DISPLAY_DURATION);
    }
}