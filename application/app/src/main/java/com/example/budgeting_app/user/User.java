package com.example.budgeting_app.user;

public class User {

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

    public User(String key, String name) {
        this.key = key;
        this.name = name;
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
}
