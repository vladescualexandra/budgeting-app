package ro.ase.csie.degree.firebase;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ro.ase.csie.degree.authentication.user.User;
import ro.ase.csie.degree.model.Balance;
import ro.ase.csie.degree.model.Category;
import ro.ase.csie.degree.model.Transaction;
import ro.ase.csie.degree.util.DateConverter;

public class FirebaseService<T extends FirebaseObject> {


    public static final String ATTRIBUTE_USER = "user";

    private final DatabaseReference database;
    private static FirebaseService firebaseService;
    private final String user_key;
    private Query query;


    private FirebaseService(Context context, Table table) {
        database = FirebaseDatabase.getInstance().getReference(table.toString());
        user_key = getUID(context);
    }

    private String getUID(Context context) {
        return User.getUID(context);
    }

    public static FirebaseService getInstance(Context context, Table table) {
        if (firebaseService == null) {
            synchronized (FirebaseService.class) {
                if (firebaseService == null) {
                    firebaseService = new FirebaseService(context, table);
                }
            }
        }
        return firebaseService;
    }

    private String getPath(T object) {
        if (object instanceof Balance) {
            return Table.BALANCES.toString();
        } else if (object instanceof Category) {
            return Table.CATEGORIES.toString();
        }else if (object instanceof Transaction) {
            return Table.TRANSACTIONS.toString();
        }
        return null;
    }


    public void upsert(T object) {
        if (object == null) {
            return;
        }

        if (object.getId() == null || object.getId().trim().isEmpty()) {
            String id = database.push().getKey();
            object.setId(id);
        }

        object.setUser(user_key);

        database
                .child(getPath(object))
                .child(object.getId())
                .setValue(object);
    }

    public void delete(T object) {
        if (object == null || object.getId() == null || object.getId().trim().isEmpty()) {
            return;
        }

        database
                .child(getPath(object))
                .child(object.getId())
                .removeValue();
    }

    public void updateBalancesUI(final Callback<List<Balance>> callback) {
        query = getQuery(Table.BALANCES);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Balance> list = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Balance object = data.getValue(Balance.class);
                    if (object != null) {
                        list.add(object);
                    }
                }
                callback.updateUI(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void updateCategoriesUI(final Callback<List<Category>> callback) {
        query = getQuery(Table.CATEGORIES);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Category> list = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Category object = data.getValue(Category.class);
                    if (object != null) {
                        list.add(object);
                    }
                }
                callback.updateUI(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void updateTransactionsUI(final Callback<List<Transaction>> callback, DateDisplayType type, Date filter) {
        query = getQuery(Table.TRANSACTIONS);
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




    private Query getQuery(Table table) {
        return database
                .child(table.toString())
                .orderByChild(ATTRIBUTE_USER)
                .equalTo(user_key);
    }


}
