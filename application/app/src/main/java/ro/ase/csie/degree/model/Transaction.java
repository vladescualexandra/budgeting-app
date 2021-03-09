package ro.ase.csie.degree.model;

import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable, Transactionable {

    private String id;
    private Category category;
    private Balance balance;
    private Double amount;
    private Date date;

    Transaction() {

    }

    public Transaction(Category category, Balance balance, Double amount, Date date) {
        this.category = category;
        this.balance = balance;
        this.amount = amount;
        this.date = date;
    }

    public Transaction(String id,  Category category, Balance balance, Double amount, Date date) {
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
