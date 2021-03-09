package ro.ase.csie.degree.model;

import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable, Transactionable {

    private String id;
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

    public Transaction(String id,  Category category, Balance balance, double amount, Date date) {
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
}
