package ro.ase.csie.degree.model;

import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

import ro.ase.csie.degree.firebase.FirebaseObject;
import ro.ase.csie.degree.firebase.Table;

public class Category extends FirebaseObject implements Serializable {

    private TransactionType type;
    private String icon;
    private String name;

    public Category() {
        super(null, null);
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

    @Override
    public String toString() {
        return "Category{" +
                "type=" + type +
                ", icon='" + icon + '\'' +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", user='" + user + '\'' +
                '}';
    }

}
