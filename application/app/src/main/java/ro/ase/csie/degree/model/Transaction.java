package ro.ase.csie.degree.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

import ro.ase.csie.degree.firebase.FirebaseObject;
import ro.ase.csie.degree.firebase.Table;
import ro.ase.csie.degree.util.DateConverter;

public class Transaction extends FirebaseObject implements Parcelable {

    private String details;
    private Category category;
    private Balance balance;
    private double amount;
    private Date date;

    public Transaction() {
        super(null, null);
        this.category = new Category();
        this.balance = new Balance();
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
                "details='" + details + '\'' +
                ", category=" + category +
                ", balance=" + balance +
                ", amount=" + amount +
                ", date=" + date +
                '}';
    }


    protected Transaction(Parcel in) {
        this.id = in.readString();
        this.user = in.readString();
        this.details = in.readString();
        this.category = (Category) in.readSerializable();
        this.balance = (Balance) in.readSerializable();
        this.amount = in.readDouble();

        String dateString = in.readString();
        this.date = DateConverter.toDate(dateString);

        Log.e("readParcel", category.toString());
        Log.e("readParcel", balance.toString());


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
        Log.e("writeToParcel", category.toString());

        dest.writeSerializable(balance);
        Log.e("writeToParcel", balance.toString());


        dest.writeDouble(amount);

        String dateString = DateConverter.toString(date);
        dest.writeString(dateString);
    }
}