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
import com.example.budgeting_app.settings.SettingsActivity;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private String USER_KEY;

    private Button btn_settings;

    private TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initComponents();


        btn_settings = findViewById(R.id.menu_settings);
        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initComponents() {
        tabLayout = findViewById(R.id.main_tabs);
        tabLayout.addOnTabSelectedListener(changeTabEventListener());

        SharedPreferences prefs = getSharedPreferences(User.USER_PREFS, MODE_PRIVATE);
        USER_KEY = prefs.getString(User.USER_KEY, null);

        TodayFragment fragment = new TodayFragment();
        show(fragment);
    }

    private TabLayout.OnTabSelectedListener changeTabEventListener() {
        return new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment;
                switch (tab.getPosition()) {
                    case 1:
                        fragment = new DayFragment();
                        break;
                    case 2:
                        fragment = new WeekFragment();
                        break;
                    case 3:
                        fragment = new MonthFragment();
                        break;
                    case 4:
                        fragment = new YearFragment();
                        break;
                    case 5:
                        fragment = new TotalFragment();
                        break;
                    default:
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}