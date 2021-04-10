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
import ro.ase.csie.degree.model.Transaction;

public class TemplateService {

    private FirebaseService firebaseService;

    public TemplateService() {
        firebaseService = FirebaseService.getInstance();
    }


    public Transaction upsert(Transaction template) {
        if (template == null) {
            return null;
        }

        if (template.getId() == null || template.getId().trim().isEmpty()) {
            String id = firebaseService.getDatabase().push().getKey();
            template.setId(id);
        }

        template.setUser(SplashActivity.KEY);

        firebaseService
                .getDatabase()
                .child(Table.TEMPLATES.toString())
                .child(template.getId())
                .setValue(template);

        return template;
    }


    public void delete(Transaction template) {
        if (template == null || template.getId() == null || template.getId().trim().isEmpty()) {
            return;
        }

        firebaseService
                .getDatabase()
                .child(Table.TEMPLATES.toString())
                .child(template.getId())
                .removeValue();
    }

    public void updateTemplatesUI(final Callback<List<Transaction>> callback) {
        Query query = firebaseService.getQuery(Table.TEMPLATES);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Transaction> list = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Transaction template = data.getValue(Transaction.class);
                    if (template != null) {
                        list.add(template);
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
