package com.example.chops.DBHandler;

import com.example.chops.Interfaces.ICallback;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public interface IAuth {
    public Task<AuthResult> signUpWithEmailAndPassword(String email, String password);
    public void signUpWithEmailAndPassword(String email, String password, ICallback callback);

    public Task<AuthResult> signInWithEmailAndPassword(String email, String password);
    public void signInWithEmailAndPassword(String email, String password, ICallback callback);
    public String getCurrentUser();
    public void signOut();

}

