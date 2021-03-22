package ro.ase.csie.degree.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

import ro.ase.csie.degree.firebase.FirebaseObject;
import ro.ase.csie.degree.util.DateConverter;

public class Transaction extends FirebaseObject implements Parcelable {

    protected String details;
    protected Category category;
    protected Balance balance_from;
    protected double amount;
    protected Date date;

    public Transaction() {
        super(null, null);
        this.category = new Category();
        this.balance_from = new Balance();
    }

    public Transaction(Transaction transaction) {
        super(transaction.getId(), transaction.getUser());
        this.details = transaction.getDetails();
        this.category = transaction.getCategory();
        this.balance_from = transaction.getBalance_from();
        this.amount = transaction.getAmount();
        this.date = transaction.getDate();
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Balance getBalance_from() {
        return balance_from;
    }

    public void setBalance_from(Balance balance_from) {
        this.balance_from = balance_from;
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
                "details='" + details + '\'' +
                ", category=" + category +
                ", balance=" + balance_from +
                ", amount=" + amount +
                ", date=" + date +
                '}';
    }


    protected Transaction(Parcel in) {
        this.id = in.readString();
        this.user = in.readString();
        this.details = in.readString();
        this.category = (Category) in.readSerializable();
        this.balance_from = (Balance) in.readSerializable();
        this.amount = in.readDouble();

        String dateString = in.readString();
        this.date = DateConverter.toDate(dateString);
    }

    public static final Creator<Transaction> CREATOR = new Creator<Transaction>() {
        @Override
        public Transaction createFromParcel(Parcel in) {
            return new Transaction(in);
        }

        @Override
        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(user);
        dest.writeString(details);
        dest.writeSerializable(category);
        dest.writeSerializable(balance_from);
        dest.writeDouble(amount);

        String dateString = DateConverter.toString(date);
        dest.writeString(dateString);
    }
}