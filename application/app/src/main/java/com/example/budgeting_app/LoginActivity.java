package com.example.budgeting_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.budgeting_app.user.User;
import com.google.android.material.textfield.TextInputEditText;

import java.io.Serializable;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText tiet_email;
    private TextInputEditText tiet_password;
    private TextView tv_redirect_to_register;
    private Button btn_login;

    User user;

    Intent intent;
    public static final int CREATE_ACCOUNT_REQUEST_KEY = 201;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initComponents();
    }

    private void initComponents() {
        tiet_email = findViewById(R.id.login_email);
        tiet_password = findViewById(R.id.login_password);
        tv_redirect_to_register = findViewById(R.id.login_redirect_to_register);
        btn_login = findViewById(R.id.login_login_btn);

        tv_redirect_to_register.setOnClickListener(redirectToRegisterEvent());
        btn_login.setOnClickListener(loginEventListener());
    }

    private View.OnClickListener loginEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), MainActivity.class);

                if (validate()) {
                    intent.putExtra(User.USER_EXTRA, (Serializable) user);
                } else {
                    Toast.makeText(getApplicationContext(),
                            R.string.toast_invalid_account, Toast.LENGTH_SHORT).show();
                }

            }
        };
    }

    private View.OnClickListener redirectToRegisterEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivityForResult(intent, CREATE_ACCOUNT_REQUEST_KEY);
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREATE_ACCOUNT_REQUEST_KEY) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    String email = data.getStringExtra(User.USER_NAME);
                    tiet_email.setText(email);
                }
            }
        }
    }

    private boolean validate() {
        return false;
    }
}