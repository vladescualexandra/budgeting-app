package ro.ase.csie.degree.model;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.Serializable;

import ro.ase.csie.degree.firebase.FirebaseObject;

import static android.content.Context.MODE_PRIVATE;

public class Account extends FirebaseObject implements Serializable {

    public static final String USER_PREFS = "user_info";
    public static final String USER_KEY = "user_key";

    private String name;
    private String email;
    private Currency currency;

    public Account() {
        super(null, null);
    }

    public Account(String name, String email) {
        super(null, null);
        this.name = name;
        this.email = email;
        this.currency = new Currency();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setAccount(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(Account.USER_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Account.USER_KEY, this.getId());
        editor.apply();
    }

    public static String getUID(Context context) {
        return context
                .getSharedPreferences(Account.USER_PREFS, MODE_PRIVATE)
                .getString(Account.USER_KEY, null);
    }

    @Override
    public String toString() {
        return "Account{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", currency=" + currency +
                '}';
    }
}
