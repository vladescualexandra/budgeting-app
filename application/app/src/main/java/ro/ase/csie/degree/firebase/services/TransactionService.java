package ro.ase.csie.degree.firebase.services;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ro.ase.csie.degree.main.SplashActivity;
import ro.ase.csie.degree.async.Callback;
import ro.ase.csie.degree.firebase.DateDisplayType;
import ro.ase.csie.degree.firebase.FirebaseService;
import ro.ase.csie.degree.firebase.Table;
import ro.ase.csie.degree.model.Transaction;
import ro.ase.csie.degree.util.DateConverter;

public class TransactionService {

    private FirebaseService firebaseService;

    public TransactionService() {
        firebaseService = FirebaseService.getInstance();
    }


    public void upsert(Transaction transaction) {
        if (transaction == null) {
            return;
        }

        if (transaction.getId() == null || transaction.getId().trim().isEmpty()) {
            String id = firebaseService.getDatabase().push().getKey();
            transaction.setId(id);
        }

        transaction.setUser(SplashActivity.KEY);

        firebaseService
                .getDatabase()
                .child(Table.TRANSACTIONS.toString())
                .child(transaction.getId())
                .setValue(transaction);

    }

    public void delete(Transaction transaction) {
        if (transaction == null || transaction.getId() == null || transaction.getId().trim().isEmpty()) {
            return;
        }

        firebaseService
                .getDatabase()
                .child(Table.TRANSACTIONS.toString())
                .child(transaction.getId())
                .removeValue();
    }

    public void updateTransactionsUI(final Callback<List<Transaction>> callback, DateDisplayType type, Date filter) {
        Query query = firebaseService.getQuery(Table.TRANSACTIONS);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Transaction> list = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Transaction transaction = data.getValue(Transaction.class);
                    if (transaction != null) {
                        if (DateConverter.filter(type, transaction.getDate(), filter)) {
                            list.add(transaction);
                        }
                    }
                }
                callback.updateUI(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
