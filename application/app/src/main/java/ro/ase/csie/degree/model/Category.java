package ro.ase.csie.degree.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

import ro.ase.csie.degree.firebase.FirebaseObject;
import ro.ase.csie.degree.firebase.Table;

public class Category extends FirebaseObject implements Serializable {

    private String user;
    private TransactionType type;
    private String icon;
    private String name;

    public Category() {
        super();
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

    public void setType(TransactionType type) {
        this.type = type;
    }

    public TransactionType getType() {
        return type;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @NonNull
    @Override
    public String toString() {
        return this.name;
    }
}
