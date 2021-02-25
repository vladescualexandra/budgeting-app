package com.example.budgeting_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.budgeting_app.user.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.regex.Matcher;

public class RegisterActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_SIGN_IN = 201;
    private final String WEB_CLIENT_ID = "163196167654-uo7k08goai2r5qhtkk3ea5b0bq4jimi6.apps.googleusercontent.com";

    private TextInputEditText tiet_name;
    private TextInputEditText tiet_email;
    private TextInputEditText tiet_password;
    private TextView tv_redirect_to_login;
    private Button btn_create_account;
    private SignInButton btn_register_google;

    Intent intent;

    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initComponents();

        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(WEB_CLIENT_ID)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, REQUEST_CODE_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SIGN_IN
            && data != null) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            logUser(account);
        } catch (ApiException e) {
            Toast.makeText(getApplicationContext(),
                    "Something went wrong.",
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void logUser(GoogleSignInAccount account) {
        SharedPreferences preferences = getSharedPreferences(User.USER_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(User.USER_KEY, account.getId());
        editor.putString(User.USER_NAME, account.getDisplayName());
        editor.putString(User.USER_EMAIL, account.getDisplayName());
        editor.apply();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }


    private void initComponents() {

        intent = getIntent();

        tiet_name = findViewById(R.id.register_name);
        tiet_email = findViewById(R.id.register_email);
        tiet_password = findViewById(R.id.register_password);
        tv_redirect_to_login = findViewById(R.id.login_redirect_to_login);
        btn_create_account = findViewById(R.id.register_create_account_btn);
        btn_register_google = findViewById(R.id.register_google_btn);

        tv_redirect_to_login.setOnClickListener(returnToLoginEventListener());
        btn_create_account.setOnClickListener(createAccountEventListener());
        btn_register_google.setOnClickListener(registerWithGoogleEventListener());
    }

    private View.OnClickListener registerWithGoogleEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        };
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
                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    if (currentUser != null) {
                        currentUser.reload();
                        FirebaseAuth.getInstance().signOut();
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
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(),
                            task.getException().toString(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    private boolean validate() {
        if (tiet_name.getText().toString().trim().length() < 3) {
            tiet_name.setError(getString(R.string.error_invalid_name));
            return false;
        } else {
            tiet_name.setError(null);
        }

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
}