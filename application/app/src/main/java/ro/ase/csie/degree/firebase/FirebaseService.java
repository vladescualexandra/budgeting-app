package ro.ase.csie.degree.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ro.ase.csie.degree.SplashActivity;
import ro.ase.csie.degree.async.Callback;
import ro.ase.csie.degree.model.Account;
import ro.ase.csie.degree.model.Balance;
import ro.ase.csie.degree.model.Category;
import ro.ase.csie.degree.model.Transaction;
import ro.ase.csie.degree.util.DateConverter;

public class FirebaseService<T extends FirebaseObject> {


    public static final String ATTRIBUTE_USER = "user";
    public static final String ATTRIBUTE_EMAIL = "email";

    private final DatabaseReference database;
    private static FirebaseService firebaseService;
    private Query query;

    public DatabaseReference getDatabase() {
        return database;
    }


    private FirebaseService() {
        database = FirebaseDatabase.getInstance().getReference();
    }

    public static FirebaseService getInstance() {
        if (firebaseService == null) {
            synchronized (FirebaseService.class) {
                if (firebaseService == null) {
                    firebaseService = new FirebaseService();
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
        } else if (object instanceof Transaction) {
            return Table.TRANSACTIONS.toString();
        } else if (object instanceof Account) {
            return Table.USERS.toString();
        }
        return null;
    }

    public T upsert(T object) {
        if (object == null) {
            return null;
        }

        if (object.getId() == null || object.getId().trim().isEmpty()) {
            String id = database.push().getKey();
            object.setId(id);
        }

        object.setUser(SplashActivity.KEY);

        database
                .child(getPath(object))
                .child(object.getId())
                .setValue(object);

        return object;
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

    public void getAccount(final Callback<Account> callback, String email) {
        Log.e("FirebaseService", "getAccount: " + email);
        query = database
                .child(Table.USERS.toString())
                .orderByChild(ATTRIBUTE_EMAIL)
                .equalTo(email);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() == null) {
                    callback.updateUI(null);
                } else {
                    for (DataSnapshot item : snapshot.getChildren()) {
                        Account account = item.getValue(Account.class);
                        if (account != null) {
                            callback.updateUI(account);
                        } else {
                            Log.e("FirebaseService", "ACCOUNT IS NULL!");
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("cancel", error.getMessage());
            }
        });
    }

    public void getAccount(final Callback<Account> callback) {
        query = database
                .child(Table.USERS.toString())
                .child(SplashActivity.KEY);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() == null) {
                    callback.updateUI(null);
                } else {
                    Account account = snapshot.getValue(Account.class);
                    if (account != null) {
                        callback.updateUI(account);
                    } else {
                        Log.e("FirebaseService", "ACCOUNT IS NULL!");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("cancel", error.getMessage());
            }
        });
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
                .equalTo(SplashActivity.KEY);
    }

}
