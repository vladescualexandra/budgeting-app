package com.example.budgeting_app.model;

import java.io.Serializable;

public class Category implements Serializable {

    private String icon;
    private String name;

    public Category(String icon, String name) {
        this.name = name;
        this.icon = "ic_baseline_add_circle_24";
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
