package com.example.budgeting_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.budgeting_app.user.User;

public class MainActivity extends AppCompatActivity {

    private String USER_KEY;
    private String USER_EMAIL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        USER_KEY = getSharedPreferences(User.USER_PREFS, MODE_PRIVATE)
                .getString(User.USER_KEY, null);

        if (USER_KEY != null) {
            Toast.makeText(getApplicationContext(),
                    USER_KEY, Toast.LENGTH_LONG).show();
            Log.e("user_key", USER_KEY);
        } else {
            Toast.makeText(getApplicationContext(),
                    "null?", Toast.LENGTH_LONG).show();
        }

    }
}