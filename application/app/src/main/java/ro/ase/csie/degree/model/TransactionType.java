package ro.ase.csie.degree.model;

import androidx.annotation.NonNull;

public enum TransactionType {

    EXPENSE ("EXPENSE"),
    INCOME ("INCOME"),
    TRANSFER ("TRANSFER");

    private String type;

    TransactionType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }


    @NonNull
    @Override
    public String toString() {
        return getType();
    }
}
