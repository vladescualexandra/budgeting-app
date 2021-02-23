package com.example.budgeting_app.user;

public class User {

    public static final String USER_PREFS = "user_info";
    public static final String USER_KEY = "user_key";
    public static final String USER_NAME = "user_name";

    private String key;
    private String name;

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
