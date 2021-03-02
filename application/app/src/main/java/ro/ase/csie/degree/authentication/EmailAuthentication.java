package ro.ase.csie.degree.authentication;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import ro.ase.csie.degree.MainActivity;
import ro.ase.csie.degree.authentication.user.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class EmailAuthentication {

    private final Context context;
    private final FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    public EmailAuthentication(Context context) {
        this.context = context;
        this.mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
    }

    public void registerAccount(TextInputEditText name, TextInputEditText email, TextInputEditText password) {
        if (currentUser != null) {
            currentUser.reload();
            FirebaseAuth.getInstance().signOut();
        } else {
            mAuth.createUserWithEmailAndPassword(email.getText().toString().trim(),
                    password.getText().toString().trim())
                    .addOnCompleteListener(completeRegisterEvent());
            currentUser = mAuth.getCurrentUser();
        }
    }

    private OnCompleteListener<AuthResult> completeRegisterEvent() {
        return task -> {
            if (task.isSuccessful()) {
                Toast.makeText(context,
                        "Account created successfully.",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context,
                        task.getException().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        };
    }

    public void loginAccount(TextInputEditText email, TextInputEditText password) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email.getText().toString().trim(),
                password.getText().toString())
                .addOnCompleteListener(completeLoginEvent());

    }

    private OnCompleteListener<AuthResult> completeLoginEvent() {
        return task -> {
            if (task.isSuccessful()) {
                Intent intent = new Intent(context, MainActivity.class);
                FirebaseUser user = mAuth.getCurrentUser();
                assert user != null;
                setAccount();
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        };
    }

    public void setAccount() {
        User user = new User(currentUser.getUid(), currentUser.getDisplayName(), currentUser.getEmail());
        user.setAccount(context, user);
    }

}
