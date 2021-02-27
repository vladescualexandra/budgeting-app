package com.example.budgeting_app.authentication;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.budgeting_app.authentication.user.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class GoogleAuthentication {

    private final String WEB_CLIENT_ID = "163196167654-uo7k08goai2r5qhtkk3ea5b0bq4jimi6.apps.googleusercontent.com";

    private final Context context;
    private final GoogleSignInClient mGoogleSignInClient;

    public GoogleAuthentication(Context context) {
        this.context = context;
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(WEB_CLIENT_ID)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
    }

    public GoogleSignInClient getClient() {
        return mGoogleSignInClient;
    }

    public Intent getSignInIntent() {
        return mGoogleSignInClient.getSignInIntent();
    }

    public void handleSignInResult(Intent data) {
        try {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            GoogleSignInAccount account = task.getResult(ApiException.class);
            if (account != null) {
                setAccount(account);
            } else {
                Toast.makeText(context,
                        "Something went wrong.",
                        Toast.LENGTH_SHORT).show();
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    private void setAccount(GoogleSignInAccount account) {
        User user = new User(account.getId(), account.getDisplayName(), account.getEmail());
        user.setAccount(context, user);
    }


}
