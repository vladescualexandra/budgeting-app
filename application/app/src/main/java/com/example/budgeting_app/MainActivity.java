package com.example.budgeting_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.budgeting_app.authentication.GoogleAuthentication;
import com.example.budgeting_app.user.User;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private String USER_KEY;
    private String USER_NAME;
    private String USER_EMAIL;

    TextView tv_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getUID();
        initComponents();
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