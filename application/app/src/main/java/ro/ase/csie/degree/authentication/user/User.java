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
        this.name = name;
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

    @Override
    public String toString() {
        return "User{" +
                "key='" + key + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public void setAccount(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(User.USER_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(User.USER_KEY, this.key);
        editor.putString(User.USER_NAME, this.name);
        editor.putString(User.USER_EMAIL, this.email);
        editor.apply();
    }
}
