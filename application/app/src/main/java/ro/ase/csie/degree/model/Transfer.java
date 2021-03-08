package ro.ase.csie.degree.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;

import java.util.Date;

import ro.ase.csie.degree.R;

public class Transfer extends Transaction{

    private Balance destination;
    private static final Category transferCategory = new Category("category_transfer", "Transfer");
    ;

    public Transfer() {

    }

    public Transfer(TransactionType type, Balance balance, Double amount, Date date, Balance destination) {
        super(type, transferCategory, balance, amount, date);
        this.destination = destination;
    }

    public Transfer(String id, TransactionType type, Balance balance, Double amount, Date date, Balance destination) {
        super(id, type, transferCategory, balance, amount, date);
        this.destination = destination;
    }

    public Balance getDestination() {
        return destination;
    }

    public void setDestination(Balance destination) {
        this.destination = destination;
    }
}
