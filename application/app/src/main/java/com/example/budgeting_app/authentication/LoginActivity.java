package com.example.budgeting_app.authentication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.budgeting_app.MainActivity;
import com.example.budgeting_app.R;
import com.example.budgeting_app.util.InputValidation;
import com.google.android.gms.common.SignInButton;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private final int REQUEST_CODE_SIGN_IN = 201;
    private TextInputEditText tiet_email;
    private TextInputEditText tiet_password;
    private TextView tv_redirect_to_register;
    private Button btn_login;
    private SignInButton btn_login_google;

    private EmailAuthentication emailAuthentication;
    private GoogleAuthentication googleAuthentication;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initComponents();

        emailAuthentication = new EmailAuthentication(getApplicationContext());
        googleAuthentication = new GoogleAuthentication(getApplicationContext());

    }

    private void initComponents() {
        tiet_email = findViewById(R.id.login_email);
        tiet_password = findViewById(R.id.login_password);
        tv_redirect_to_register = findViewById(R.id.login_redirect_to_register);
        btn_login = findViewById(R.id.login_login_btn);
        btn_login_google = findViewById(R.id.login_google_btn);

        tv_redirect_to_register.setOnClickListener(redirectToRegisterEvent());
        btn_login.setOnClickListener(emailAuthEventListener());
        btn_login_google.setOnClickListener(googleAuthEventListener());
    }

    private View.OnClickListener googleAuthEventListener() {
        return v -> {
            Intent intent = googleAuthentication.getSignInIntent();
            startActivityForResult(intent, REQUEST_CODE_SIGN_IN);
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SIGN_IN
                && data != null) {
            googleAuthentication.handleSignInResult(data);
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }


    private View.OnClickListener emailAuthEventListener() {
        return (View.OnClickListener) v -> {
            intent = new Intent(getApplicationContext(), MainActivity.class);

            if (validate()) {
                emailAuthentication.loginAccount(tiet_email, tiet_password);
            } else {
                Toast.makeText(getApplicationContext(),
                        R.string.toast_invalid_account, Toast.LENGTH_SHORT).show();
            }
        };
    }


    private View.OnClickListener redirectToRegisterEvent() {
        return v -> {
            intent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);
        };
    }


    private boolean validate() {
        InputValidation validation = new InputValidation(getApplicationContext());
        return validation.loginValidation(tiet_email, tiet_password);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}