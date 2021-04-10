package ro.ase.csie.degree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


import ro.ase.csie.degree.authentication.LoginActivity;
import ro.ase.csie.degree.model.Account;
import ro.ase.csie.degree.util.Streak;

public class SplashActivity extends AppCompatActivity {

    private final int DISPLAY_DURATION = 2000;
    public static String KEY;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        KEY = getSharedPreferences(Account.USER_PREFS, MODE_PRIVATE)
                .getString(Account.USER_KEY, null);

        new Handler().postDelayed(() -> {
            if (KEY == null) {
                intent = new Intent(getApplicationContext(), LoginActivity.class);
            } else {
                Account.retrieveAccount(getApplicationContext());
                intent = new Intent(getApplicationContext(), MainActivity.class);

                Streak.handleStreak(getApplicationContext());
            }
            startActivity(intent);
        }, DISPLAY_DURATION);
    }



}