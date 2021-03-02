package ro.ase.csie.degree.authentication.user;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.Serializable;

import static android.content.Context.MODE_PRIVATE;

public class User implements Serializable {

    public static final String USER_PREFS = "user_info";
    public static final String USER_EXTRA = "user_extra";
    public static final String USER_KEY = "user_key";
    public static final String USER_NAME = "user_name";
    public static final String USER_EMAIL = "user_email";

    private String key;
    private String name;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(String key, String name, String email) {
        this.key = key;
        this.name = "Default name";
        this.email = email;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAccount(Context context, User user) {
        SharedPreferences preferences = context.getSharedPreferences(User.USER_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(User.USER_KEY, user.getKey());
        editor.putString(User.USER_NAME, user.getName());
        editor.putString(User.USER_EMAIL, user.getEmail());
        editor.apply();
    }
}
