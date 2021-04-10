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

    private final DatabaseReference database;
    private static FirebaseService firebaseService;

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

    public Query getQuery(Table table) {
        return database
                .child(table.toString())
                .orderByChild(ATTRIBUTE_USER)
                .equalTo(SplashActivity.KEY);
    }

}
