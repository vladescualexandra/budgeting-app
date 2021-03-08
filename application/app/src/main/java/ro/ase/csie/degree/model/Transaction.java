package ro.ase.csie.degree.model;

import java.util.Date;

public class Transaction {

    private String id;
    private TransactionType type;

    private Category category;

    private Balance balance;
    private Double amount;

    private Date date;

    Transaction() {

    }

    public Transaction(TransactionType type, Category category, Balance balance, Double amount, Date date) {
        this.type = type;
        this.category = category;
        this.balance = balance;
        this.amount = amount;
        this.date = date;
    }

    public Transaction(String id, TransactionType type, Category category, Balance balance, Double amount, Date date) {
        this.id = id;
        this.type = type;
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

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
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
