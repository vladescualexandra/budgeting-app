package ro.ase.csie.degree.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

import ro.ase.csie.degree.util.DateConverter;

public class Transaction implements Serializable, Transactionable {

    private String id;
    private String user;
    private String details;
    private Category category;
    private Balance balance;
    private double amount;
    private Date date;

    public Transaction() {
        this.category = new Category();
        this.balance = new Balance();
    }

    public Transaction(Category category, Balance balance, double amount, Date date) {
        this.category = category;
        this.balance = balance;
        this.amount = amount;
        this.date = date;
    }

    public Transaction(String id, Category category, Balance balance, double amount, Date date) {
        this.id = id;
        this.category = category;
        this.balance = balance;
        this.amount = amount;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", details='" + details + '\'' +
                ", category=" + category +
                ", balance=" + balance +
                ", amount=" + amount +
                ", date=" + date +
                '}';
    }
}