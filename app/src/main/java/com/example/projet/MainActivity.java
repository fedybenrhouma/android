package com.example.projet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuthHelper authHelper;
    private FirebaseUser currentUser;
    private TextView userEmail;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        authHelper = new FirebaseAuthHelper();
        currentUser = authHelper.getCurrentUser();

        // Check if user is logged in
        if (currentUser == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        userEmail = findViewById(R.id.userEmail);
        logoutButton = findViewById(R.id.logoutButton);

        userEmail.setText("Welcome, " + currentUser.getEmail());
        logoutButton.setOnClickListener(v -> logout());
    }

    private void logout() {
        authHelper.logoutUser();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    public void searchPlayers(View view) {
        startActivity(new Intent(this, SearchPlayerActivity.class));
    }
}