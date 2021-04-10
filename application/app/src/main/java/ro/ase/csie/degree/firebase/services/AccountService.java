package ro.ase.csie.degree.firebase.services;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import ro.ase.csie.degree.SplashActivity;
import ro.ase.csie.degree.async.Callback;
import ro.ase.csie.degree.firebase.FirebaseService;
import ro.ase.csie.degree.firebase.Table;
import ro.ase.csie.degree.model.Account;
import ro.ase.csie.degree.model.Transaction;

public class AccountService {

    public static final String ATTRIBUTE_EMAIL = "email";
    private FirebaseService firebaseService;

    public AccountService() {
        firebaseService = FirebaseService.getInstance();
    }

    public Account upsert(Account account) {
        if (account == null) {
            return null;
        }

        if (account.getId() == null || account.getId().trim().isEmpty()) {
            String id = firebaseService.getDatabase().push().getKey();
            account.setId(id);
        }

        firebaseService
                .getDatabase()
                .child(Table.USERS.toString())
                .child(account.getId())
                .setValue(account);

        return account;
    }

    public void getAccount(final Callback<Account> callback, String email) {
        Log.e("FirebaseService", "getAccount: " + email);
        Query query =
                firebaseService
        .getDatabase()
                .child(Table.USERS.toString())
                .orderByChild(ATTRIBUTE_EMAIL)
                .equalTo(email);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() == null) {
                    callback.updateUI(null);
                } else {
                    for (DataSnapshot item : snapshot.getChildren()) {
                        Account account = item.getValue(Account.class);
                        if (account != null) {
                            callback.updateUI(account);
                        } else {
                            Log.e("FirebaseService", "ACCOUNT IS NULL!");
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("cancel", error.getMessage());
            }
        });
    }

    public void getAccount(final Callback<Account> callback) {
        Query query = firebaseService
                .getDatabase()
                .child(Table.USERS.toString())
                .child(SplashActivity.KEY);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() == null) {
                    callback.updateUI(null);
                } else {
                    Account account = snapshot.getValue(Account.class);
                    if (account != null) {
                        callback.updateUI(account);
                    } else {
                        Log.e("FirebaseService", "ACCOUNT IS NULL!");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("cancel", error.getMessage());
            }
        });
    }
}
