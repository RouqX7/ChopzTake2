package com.example.chops.DBHandler;

import com.example.chops.Interfaces.ICallback;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.ExecutionException;

public class FireAuth implements IAuth {
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    public Task<AuthResult> signUpWithEmailAndPassword(String email, String password) {

        return firebaseAuth.createUserWithEmailAndPassword(email, password);
    }

    @Override
    public void signUpWithEmailAndPassword(String email, String password, ICallback callback) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(
                (result) -> {
                    if (result.getUser() != null) {
                        String uid = result.getUser().getUid();
                        callback.execute(uid, true);
                    }

                }
        ).addOnFailureListener(
                (reason) -> {
                    callback.execute(null, false, reason.getLocalizedMessage());
                }
        );
    }

    @Override
    public Task<AuthResult> signInWithEmailAndPassword(String email, String password) {
        return firebaseAuth.signInWithEmailAndPassword(email, password);
    }

    @Override
    public void signInWithEmailAndPassword(String email, String password, ICallback callback) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(
                (result) -> {
                    if (result.getUser() != null) {
                        String uid = result.getUser().getUid();
                        callback.execute(uid, true);
                    }

                }
        ).addOnFailureListener(
                (reason) -> {
                    callback.execute(null, false, reason.getLocalizedMessage());
                }
        );
    }

    @Override
    public String getCurrentUser() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        return firebaseUser != null ? firebaseUser.getUid() : null;
    }

    @Override
    public void signOut() {
        firebaseAuth.signOut();
    }
}
