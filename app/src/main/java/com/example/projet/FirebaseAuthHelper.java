package com.example.projet;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseAuthHelper {
    private static FirebaseAuth mAuth;

    public FirebaseAuthHelper() {
        mAuth = FirebaseAuth.getInstance();
    }

    /**
     * Register a new user with email and password
     */
    public void registerUser(String email, String password, AuthCallback callback) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    callback.onSuccess(user);
                } else {
                    callback.onFailure(task.getException());
                }
            });
    }

    /**
     * Login user with email and password
     */
    public void loginUser(String email, String password, AuthCallback callback) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    callback.onSuccess(user);
                } else {
                    callback.onFailure(task.getException());
                }
            });
    }

    /**
     * Logout current user
     */
    public void logoutUser() {
        mAuth.signOut();
    }

    /**
     * Get current logged in user
     */
    public FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }

    /**
     * Check if user is logged in
     */
    public boolean isUserLoggedIn() {
        return mAuth.getCurrentUser() != null;
    }

    /**
     * Callback interface for authentication operations
     */
    public interface AuthCallback {
        void onSuccess(FirebaseUser user);
        void onFailure(Exception exception);
    }
}
