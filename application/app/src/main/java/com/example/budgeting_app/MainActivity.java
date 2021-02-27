package com.example.budgeting_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.budgeting_app.authentication.GoogleAuthentication;
import com.example.budgeting_app.authentication.user.User;
import com.example.budgeting_app.fragments.DayFragment;
import com.example.budgeting_app.fragments.MonthFragment;
import com.example.budgeting_app.fragments.TodayFragment;
import com.example.budgeting_app.fragments.TotalFragment;
import com.example.budgeting_app.fragments.WeekFragment;
import com.example.budgeting_app.fragments.YearFragment;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private String USER_KEY;
    private String USER_NAME;
    private String USER_EMAIL;

    TextView tv_text;
    private TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getUID();
        initComponents();

        TodayFragment fragment = new TodayFragment();
        show(fragment);

    }

    private TabLayout.OnTabSelectedListener changeTabEventListener() {
        return new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 1:
                        // day
                        fragment = new DayFragment();
                        break;
                    case 2:
                        fragment = new WeekFragment();
                        // week
                        break;
                    case 3:
                        // month
                        fragment = new MonthFragment();
                        break;
                    case 4:
                        // year
                        fragment = new YearFragment();
                        break;
                    case 5:
                        // total
                        fragment = new TotalFragment();
                        break;
                    default:
                        // today
                        fragment = new TodayFragment();
                        break;
                }
                show(fragment);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        };
    }

    private void show(Fragment fragment) {
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.main_fragment, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void initComponents() {
        Button btn_sign_out = findViewById(R.id.sign_out);


        tv_text = findViewById(R.id.textView2);
        tv_text.setText(USER_EMAIL);

        btn_sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoogleAuthentication ga = new GoogleAuthentication(getApplicationContext());
                GoogleSignInClient client = ga.getClient();
                client.signOut().addOnCompleteListener(signOutEventListener());
            }
        });

        tabLayout = findViewById(R.id.main_tabs);
        tabLayout.addOnTabSelectedListener(changeTabEventListener());
    }

    private OnCompleteListener<Void> signOutEventListener() {
        return task -> {
            SharedPreferences preferences = getSharedPreferences(User.USER_PREFS, MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove(User.USER_KEY);
            editor.remove(User.USER_NAME);
            editor.remove(User.USER_EMAIL);
            editor.apply();

            Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
            startActivity(intent);
        };
    }

    private void getUID() {
        SharedPreferences prefs = getSharedPreferences(User.USER_PREFS, MODE_PRIVATE);
        USER_KEY = prefs.getString(User.USER_KEY, null);
        USER_EMAIL = prefs.getString(User.USER_EMAIL, null);

        Log.e("getUID", USER_EMAIL);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}