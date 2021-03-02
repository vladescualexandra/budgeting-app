package ro.ase.csie.degree.firebase;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ro.ase.csie.degree.authentication.user.User;

public class FirebaseService {

    public String USER_TABLE;
    private DatabaseReference database;
    private static FirebaseService firebaseService;

    private FirebaseService(Context context) {
        USER_TABLE = getUID(context);
        if (USER_TABLE != null) {
            database = FirebaseDatabase.getInstance().getReference(USER_TABLE);
        } else {
            Toast.makeText(context,
                    "Something went wrong - Firebase",
                    Toast.LENGTH_LONG).show();
        }
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

    private String getUID(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(User.USER_PREFS, Context.MODE_PRIVATE);
        return prefs.getString(User.USER_KEY, null);
    }

    public void insertUserData(User user) {
        if (user == null) {
            return;
        } else {
            database.child(user.getKey()).setValue(user);
        }
    }
}
