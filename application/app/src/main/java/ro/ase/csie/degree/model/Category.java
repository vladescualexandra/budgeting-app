package ro.ase.csie.degree.model;

import androidx.annotation.Nullable;

import java.io.Serializable;

import ro.ase.csie.degree.firebase.FirebaseObject;

public class Category extends FirebaseObject implements Serializable {

    private TransactionType type;
    private int color;
    private String name;

    public Category() {
        super(null, null);
    }


    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
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
        return this.name;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        Category category = (Category) obj;
        if (!this.getType().equals(category.getType())) {
            return false;
        }
        if (!this.getName().equals(category.getName())) {
            return false;
        }
        return true;
    }


}
