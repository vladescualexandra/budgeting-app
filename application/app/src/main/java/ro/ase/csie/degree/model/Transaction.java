package ro.ase.csie.degree.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

import ro.ase.csie.degree.firebase.FirebaseObject;
import ro.ase.csie.degree.firebase.Table;
import ro.ase.csie.degree.util.DateConverter;

public class Transaction extends FirebaseObject implements Serializable{

    private String user;
    private String details;
    private Category category;
    private Balance balance;
    private double amount;
    private Date date;

    public Transaction() {
        super();
        this.category = new Category();
        this.balance = new Balance();
    }


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Balance getBalance() {
        return balance;
    }

    public void setBalance(Balance balance) {
        this.balance = balance;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

}