package ro.ase.csie.degree.firebase;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ro.ase.csie.degree.authentication.user.User;
import ro.ase.csie.degree.model.Balance;
import ro.ase.csie.degree.model.Category;
import ro.ase.csie.degree.model.Transaction;

public class FirebaseService {

    public static final String TABLE_CATEGORIES = "categories";
    public static final String TABLE_BALANCES = "balances";
    public static final String TABLE_USERS = "users";
    public static final String ATTRIBUTE_USER = "user";
    public static final String TABLE_BUDGET = "budget";
    public static final String TABLE_TRANSACTIONS = "transactions";

    private DatabaseReference database;
    private static FirebaseService firebaseService;
    private String user_key;
    private Query query;


    private FirebaseService(Context context) {
    database = FirebaseDatabase.getInstance().getReference(TABLE_BUDGET);
        user_key = context.getSharedPreferences(User.USER_PREFS, Context.MODE_PRIVATE).getString(User.USER_KEY, null);


    }

    public static FirebaseService getInstance(Context context) {
        if (firebaseService == null) {
            synchronized (FirebaseService.class) {
                if (firebaseService == null) {
                    firebaseService = new FirebaseService(context);
                }
            }
        }
        return firebaseService;
    }

    public void insertUserData(User user) {
        if (user == null) {
            return;
        } else {
            database.push();
            database.child(user.getKey()).setValue(user);
        }
    }

    public void insertCategory(Category category) {
        if (category == null) {
            return;
        } else {
            String id = database.push().getKey();
            category.setId(id);
            database.child(TABLE_CATEGORIES).child(category.getId()).setValue(category);
        }
    }

    public void insertTransaction(Transaction transaction) {
        if (transaction == null) {
            return;
        } else {
            String id = database
                    .child(TABLE_TRANSACTIONS)
                    .push()
                    .getKey();
            transaction.setId(id);
            database
                    .child(TABLE_TRANSACTIONS)
                    .child(transaction.getId())
                    .setValue(transaction);
        }
    }

    public void deleteCategory(Category category) {
        if (category == null || category.getId() == null || category.getId().trim().isEmpty()) {
            return;
        } else {
            database
                    .child(TABLE_CATEGORIES)
                    .child(category.getId())
                    .removeValue();

        }
    }

    public void updateTransactionsUI(final Callback<List<Transaction>> callback) {
        query = database
                .child(TABLE_TRANSACTIONS)
                .orderByChild(ATTRIBUTE_USER)
                .equalTo(user_key);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Transaction> transactions = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Transaction transaction = data.getValue(Transaction.class);
                    if (transaction != null) {
                        transactions.add(transaction);
                    }
                }
                callback.updateUI(transactions);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void updateCategoriesUI(final Callback<List<Category>> callback) {
        query = database
                .child(TABLE_CATEGORIES)
                .orderByChild(ATTRIBUTE_USER)
                .equalTo(user_key);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Category> categories = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Category category = data.getValue(Category.class);
                    if (category != null) {
                        categories.add(category);
                    }
                }
                callback.updateUI(categories);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("updateCategoriesUI", error.getMessage());
            }
        });
    }

    public void upsertBalance(Balance balance) {
        if (balance == null) {
            return;
        } else {
            if (balance.getId() == null || balance.getId().trim().isEmpty()) {
                String id = database.push().getKey();
                balance.setId(id);
            }
            database.child(TABLE_BALANCES).child(balance.getId()).setValue(balance);
        }
    }

    public void deleteBalance(Balance balance) {
        if (balance == null || balance.getId() == null || balance.getId().trim().isEmpty()) {
            return;
        } else {
            database
                    .child(TABLE_BALANCES)
                    .child(balance.getId())
                    .removeValue();
        }
    }

    public void updateBalancesUI(final Callback<List<Balance>> callback) {
        query = database
                .child(TABLE_BALANCES)
                .orderByChild(ATTRIBUTE_USER)
                .equalTo(user_key);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Balance> balances = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Balance balance = data.getValue(Balance.class);
                    if (balance != null) {
                        balances.add(balance);
                    }
                }
                callback.updateUI(balances);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("updateBalancesUI", error.getMessage());
            }
        });
    }
}
