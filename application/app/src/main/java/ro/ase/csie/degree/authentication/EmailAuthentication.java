package ro.ase.csie.degree.authentication;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import ro.ase.csie.degree.MainActivity;
import ro.ase.csie.degree.authentication.user.User;
import ro.ase.csie.degree.firebase.FirebaseService;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.content.Intent.makeMainActivity;

public class EmailAuthentication {

    private final Context context;
    private final FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private User user;

    public EmailAuthentication(Context context) {
        this.context = context;
        this.mAuth = FirebaseAuth.getInstance();
        this.currentUser = null;
    }

    public void registerAccount(TextInputEditText name, TextInputEditText email, TextInputEditText password) {
        mAuth.createUserWithEmailAndPassword(email.getText().toString().trim(),
                password.getText().toString().trim())
                .addOnCompleteListener(completeRegisterEvent(name.getText().toString()));
    }

    private OnCompleteListener<AuthResult> completeRegisterEvent(String name) {
        return task -> {
            if (task.isSuccessful()) {
                createUserProfile(name);
            } else {
                Toast.makeText(context,
                        task.getException().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void createUserProfile(String name) {
        currentUser = mAuth.getCurrentUser();
        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();
        currentUser.updateProfile(profileUpdate)
            .addOnCompleteListener(task -> {
                setAccount();
                saveData();
                Toast.makeText(context,
                        user.toString(),
                        Toast.LENGTH_SHORT).show();
            });
    }

    private void saveData() {
        FirebaseService firebaseService = FirebaseService.getInstance(context);
        firebaseService.insertUserData(user);
    }

    public void loginAccount(TextInputEditText email, TextInputEditText password) {
        mAuth.signInWithEmailAndPassword(email.getText().toString().trim(),
                password.getText().toString())
                .addOnCompleteListener(completeLoginEvent());
    }

    private OnCompleteListener<AuthResult> completeLoginEvent() {
        return task -> {
            if (task.isSuccessful()) {
                Intent intent = new Intent(context, MainActivity.class);
                setAccount();
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        };
    }

    private User getUser() {
        currentUser = mAuth.getCurrentUser();
        return new User(currentUser.getUid(), currentUser.getDisplayName(), currentUser.getEmail());
    }

    public void setAccount() {
        user = getUser();
        user.setAccount(context);
    }

}
