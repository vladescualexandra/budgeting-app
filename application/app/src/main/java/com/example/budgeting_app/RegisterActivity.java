package com.example.budgeting_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.budgeting_app.user.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import java.util.regex.Matcher;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText tiet_name;
    private TextInputEditText tiet_email;
    private TextInputEditText tiet_password;
    private TextView tv_redirect_to_login;
    private Button btn_create_account;

    Intent intent;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initComponents();


    }

    private void initComponents() {

        intent = getIntent();

        tiet_name = findViewById(R.id.register_name);
        tiet_email = findViewById(R.id.register_email);
        tiet_password = findViewById(R.id.register_password);
        tv_redirect_to_login = findViewById(R.id.login_redirect_to_login);
        btn_create_account = findViewById(R.id.register_create_account_btn);

        tv_redirect_to_login.setOnClickListener(returnToLoginEventListener());
        btn_create_account.setOnClickListener(createAccountEventListener());
    }

    private View.OnClickListener returnToLoginEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), LoginActivity.class);
                setResult(RESULT_CANCELED);
                finish();
            }
        };
    }

    private View.OnClickListener createAccountEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    mAuth = FirebaseAuth.getInstance();
                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    if (currentUser != null) {
                        currentUser.reload();
                    } else {
                        mAuth.createUserWithEmailAndPassword(tiet_email.getText().toString().trim(),
                                tiet_password.getText().toString().trim())
                                .addOnCompleteListener(createAccount());
                    }
                }
            }
        };
    }

    private OnCompleteListener<AuthResult> createAccount() {
        return new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),
                            "Account created successfully.",
                            Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                    intent = new Intent(getApplicationContext(), LoginActivity.class);
                    intent.putExtra(User.USER_EMAIL, user.getEmail());

                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    private boolean validate() {
        if (tiet_name.getText().toString().trim().length() < 3) {
            tiet_name.setError("Name must have at least 3 characters.");
            return false;
        } else {
            tiet_name.setError(null);
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(tiet_email.getText().toString().trim()).matches()) {
            tiet_email.setError("Invalid e-mail address.");
            return false;
        } else {
            tiet_email.setError(null);
        }

        if (tiet_password.getText().toString().trim().length() < 8) {
            tiet_password.setError("Password mush have at least 8 characters.");
            return false;
        } else {
            tiet_password.setError(null);
        }

        return true;
    }
}