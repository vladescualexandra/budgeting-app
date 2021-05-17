package ro.ase.csie.degree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


import com.rbddevs.splashy.Splashy;

import ro.ase.csie.degree.authentication.LoginActivity;
import ro.ase.csie.degree.model.Account;
import ro.ase.csie.degree.util.Streak;
import ro.ase.csie.degree.util.language.LanguageManager;
import ro.ase.csie.degree.util.theme.ThemeManager;

public class SplashActivity extends AppCompatActivity {

    private final int DISPLAY_DURATION = 4000;
    private final int ANIMATION_DURATION = 3000;
    public static String KEY;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        LanguageManager.getSettings(getBaseContext());
        ThemeManager.getTheme(getApplicationContext());

        KEY = getSharedPreferences(Account.USER_PREFS, MODE_PRIVATE)
                .getString(Account.USER_KEY, null);

        new Splashy(this)
                .setLogo(R.drawable.logo)
                .setTitle(R.string.app_name)
                .setSubTitle(R.string.app_motto)
                .setDuration(DISPLAY_DURATION)
                .setAnimation(Splashy.Animation.GROW_LOGO_FROM_CENTER, ANIMATION_DURATION)
                .show();

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