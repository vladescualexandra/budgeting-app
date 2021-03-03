package ro.ase.csie.degree.model;

import java.io.Serializable;

public class Category implements Serializable {

    public static final String TYPE_EXPENSE = "expense";
    public static final String TYPE_INCOME = "income";


    private String id;
    private String user;
    private String type;
    private String icon;
    private String name;

    public Category() {

    }

    public Category(String id, String user, String type, String icon, String name) {
        this.id = id;
        this.user = user;
        this.type = type;
        this.icon = icon;
        this.name = name;
    }

    public Category(String user, String type, String icon, String name) {
        this.user = user;
        this.type = type;
        this.icon = icon;
        this.name = name;
    }

    public Category(String icon, String name) {
        this.icon = icon;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id='" + id + '\'' +
                ", user='" + user + '\'' +
                ", type='" + type + '\'' +
                ", icon='" + icon + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
