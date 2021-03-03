package ro.ase.csie.degree.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ro.ase.csie.degree.authentication.user.User;
import ro.ase.csie.degree.model.Category;

public class FirebaseService {

    public static final String TABLE_CATEGORIES = "categories";
    public static final String TABLE_USERS = "users";
    public static final String ATTRIBUTE_USER = "user";

    private DatabaseReference database;
    private static FirebaseService firebaseService;

    private FirebaseService(String table) {
        database = FirebaseDatabase.getInstance().getReference(table);
    }

    public static FirebaseService getInstance(String table) {
        if (firebaseService == null) {
            synchronized (FirebaseService.class) {
                if (firebaseService == null) {
                    firebaseService = new FirebaseService(table);
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
            database.child(category.getId()).setValue(category);
        }
    }

    public void updateCategoriesUI(final Callback<List<Category>> callback, String user) {
        Query userCategories = database
                .orderByChild(ATTRIBUTE_USER)
                .equalTo(user);

        userCategories.addValueEventListener(new ValueEventListener() {
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
}
