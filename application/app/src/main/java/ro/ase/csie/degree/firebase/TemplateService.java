package ro.ase.csie.degree.firebase;

import ro.ase.csie.degree.SplashActivity;
import ro.ase.csie.degree.model.Transaction;

public class TemplateService {

    private FirebaseService firebaseService;

    public TemplateService() {
        firebaseService = FirebaseService.getInstance();
    }


    public Transaction upsertTemplate(Transaction template) {
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

}
