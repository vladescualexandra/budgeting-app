package ro.ase.csie.degree.model;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.io.Serializable;

import ro.ase.csie.degree.MainActivity;
import ro.ase.csie.degree.SplashActivity;
import ro.ase.csie.degree.async.Callback;
import ro.ase.csie.degree.firebase.FirebaseObject;
import ro.ase.csie.degree.firebase.FirebaseService;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class Account extends FirebaseObject implements Serializable {

    private static Account account = null;

    public static final String USER_PREFS = "user_info";
    public static final String USER_KEY = "user_key";

    private String name;
    private String email;
    private Currency currency;

    private static FirebaseService firebaseService = FirebaseService.getInstance();

    private Account() {
        super(null, null);
    }

    private Account(String name, String email) {
        super(null, null);
        this.name = name;
        this.email = email;
    }

    public static Account getInstance() {
        return account;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public static void updateAccount() {
        firebaseService.upsert(account);
    }

    public static void retrieveAccount(Context context) {
        firebaseService.getAccount(getAccountCallback(context));
    }

    public static void authenticate(Context context, String email) {
        firebaseService.getAccount(getAccountCallback(context), email);
    }

    public static void createAccount(Context context, String name, String email) {
        account = (Account) firebaseService.upsert(new Account(name, email));
        enterAccount(context);
    }

    public static void googleAuthentication(Context context, GoogleSignInAccount gsa) {
        firebaseService.getAccount(getGoogleAccountCallback(context, gsa), gsa.getEmail());
    }

    public static void signOut(Context context) {
        account = null;
        SharedPreferences prefs = context.getSharedPreferences(USER_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(USER_KEY);
        editor.apply();
    }

    @Override
    public String toString() {
        return "Account{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", currency=" + currency +
                ", id='" + id + '\'' +
                ", user='" + user + '\'' +
                '}';
    }

    @NonNull
    @Override
    protected Object clone() throws CloneNotSupportedException {
        Account account = new Account();
        account.id = this.id;
        account.user = this.user;
        account.name = this.name;
        account.email = this.email;
        account.currency = this.currency;
        return account;
    }

    private static Callback<Account> getAccountCallback(Context context) {
        return result -> {
            if (result != null) {
                getAccount(context, result);
            } else {
            }
        };
    }

    private static void getAccount(Context context, Account result) {
        try {
            account = (Account) result.clone();
            enterAccount(context);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    private static Callback<Account> getGoogleAccountCallback(Context context, GoogleSignInAccount gsa) {
        return result -> {
            if (result != null) {
                getAccount(context, result);
            } else {
                account = (Account) firebaseService.upsert(new Account(gsa.getDisplayName(), gsa.getEmail()));
//                getAccount(context, account);
            }
        };
    }

    private static void enterAccount(Context context) {
        saveKey(context);
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(USER_KEY, account);
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private static void saveKey(Context context) {
        SplashActivity.KEY = account.getId();
        SharedPreferences prefs = context.getSharedPreferences(USER_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(USER_KEY, SplashActivity.KEY);
        editor.apply();
    }


}
