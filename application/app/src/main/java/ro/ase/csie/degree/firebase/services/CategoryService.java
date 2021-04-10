package ro.ase.csie.degree.firebase.services;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ro.ase.csie.degree.SplashActivity;
import ro.ase.csie.degree.async.Callback;
import ro.ase.csie.degree.firebase.FirebaseService;
import ro.ase.csie.degree.firebase.Table;
import ro.ase.csie.degree.model.Category;

public class CategoryService {

    private FirebaseService firebaseService;

    public CategoryService() {
        firebaseService = FirebaseService.getInstance();
    }


    public void upsert(Category category) {
        if (category == null) {
            return;
        }

        if (category.getId() == null || category.getId().trim().isEmpty()) {
            String id = firebaseService.getDatabase().push().getKey();
            category.setId(id);
        }

        category.setUser(SplashActivity.KEY);

        firebaseService
                .getDatabase()
                .child(Table.CATEGORIES.toString())
                .child(category.getId())
                .setValue(category);

    }

    public void delete(Category category) {
        if (category == null || category.getId() == null || category.getId().trim().isEmpty()) {
            return;
        }

        firebaseService
                .getDatabase()
                .child(Table.CATEGORIES.toString())
                .child(category.getId())
                .removeValue();
    }

    public void updateCategoriesUI(final Callback<List<Category>> callback) {
        Query query = firebaseService.getQuery(Table.CATEGORIES);
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
}
