package com.example.budgeting_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.budgeting_app.user.User;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText tiet_email;
    private TextInputEditText tiet_password;
    private TextView tv_redirect_to_register;
    private Button btn_login;
    private SignInButton btn_login_google;

    User user;

    Intent intent;

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

                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    mAuth.signInWithEmailAndPassword(tiet_email.getText().toString().trim(),
                            tiet_password.getText().toString())
                            .addOnCompleteListener(completeLoginEvent(mAuth));
                    intent.putExtra(User.USER_EXTRA, (Serializable) user);
                } else {
                    Toast.makeText(getApplicationContext(),
                            R.string.toast_invalid_account, Toast.LENGTH_SHORT).show();
                }

            }
        };
    }

    private OnCompleteListener<AuthResult> completeLoginEvent(FirebaseAuth mAuth) {
        return new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    intent = new Intent(getApplicationContext(), MainActivity.class);
                    FirebaseUser user = mAuth.getCurrentUser();
                    Toast.makeText(getApplicationContext(),
                            user.getEmail(), Toast.LENGTH_SHORT).show();
                    intent.putExtra(User.USER_KEY, user);
                    startActivity(intent);
                }
            }
        };
    }


    private View.OnClickListener redirectToRegisterEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        };
    }


    private boolean validate() {
        if (!Patterns.EMAIL_ADDRESS.matcher(tiet_email.getText().toString().trim()).matches()) {
            tiet_email.setError(getString(R.string.error_invalid_email));
            return false;
        } else {
            tiet_email.setError(null);
        }

        if (tiet_password.getText().toString().trim().length() < 8) {
            tiet_password.setError(getString(R.string.error_invalid_password));
            return false;
        } else {
            tiet_password.setError(null);
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}