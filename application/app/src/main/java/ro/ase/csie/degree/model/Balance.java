package ro.ase.csie.degree.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

import ro.ase.csie.degree.firebase.FirebaseObject;
import ro.ase.csie.degree.firebase.Table;

public class Balance extends FirebaseObject implements Serializable {

    private String name;
    private double available_amount;

    public Balance() {
        super();
    }

    public Balance(String name, double available_amount) {
        super();
        this.name = name;
        this.available_amount = available_amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAvailable_amount() {
        return available_amount;
    }

    public void setAvailable_amount(double available_amount) {
        this.available_amount = available_amount;
    }


    @NonNull
    @Override
    public String toString() {
        return this.name + " - " + this.available_amount;
    }

    public boolean operation(TransactionType type, double amount) {
        if (type.equals(TransactionType.EXPENSE)) {
            return withdraw(amount);
        } else {
            return deposit(amount);
        }
    }

    public boolean deposit(double amount) {
        if (amount <= 0) {
            return false;
        } else {
            this.available_amount += amount;
            return true;
        }
    }

    public boolean withdraw(double amount) {
        if (amount <= 0 || amount >= this.available_amount) {
            return false;
        } else {
            this.available_amount -= amount;
            return true;
        }
    }
}
