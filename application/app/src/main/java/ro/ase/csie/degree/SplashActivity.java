package ro.ase.csie.degree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import ro.ase.csie.degree.R;


import ro.ase.csie.degree.authentication.LoginActivity;
import ro.ase.csie.degree.authentication.user.User;

public class SplashActivity extends AppCompatActivity {

    private final int DISPLAY_DURATION = 2000;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        String key = User.getUID(getApplicationContext());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (key == null) {
                    intent = new Intent(getApplicationContext(), LoginActivity.class);
                } else {
                    intent = new Intent(getApplicationContext(), MainActivity.class);
                }
                startActivity(intent);
            }
        }, DISPLAY_DURATION);
    }
}