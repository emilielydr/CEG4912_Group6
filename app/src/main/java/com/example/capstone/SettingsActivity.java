package com.example.capstone;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private Button btnSignOut, btnHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Button btnBackSettings= findViewById(R.id.btnBackSettings);
        Button btnHelp=findViewById(R.id.btnHelp);
        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to open SettingsActivity
                Intent intent = new Intent(SettingsActivity.this, HelpActivity.class);
                startActivity(intent);
            }
        });

        btnBackSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to open SettingsActivity
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        Button btnAccessibility= findViewById(R.id.btnAccessibility);
        btnAccessibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to open SettingsActivity
                Intent intent = new Intent(SettingsActivity.this, AccessibilityActivity.class);
                startActivity(intent);
            }
        });

        // Initialisation de FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();

        // Lien avec le bouton Sign Out
        btnSignOut = findViewById(R.id.btnSignOut);

        // Ajouter un écouteur de clic
        btnSignOut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //////////////////////////////////////////
                Log.d("SettingsActivity", "Sign Out button clicked!");
                if (firebaseAuth.getCurrentUser() != null) {
                    Log.d("SettingsActivity", "User is signed in: " + firebaseAuth.getCurrentUser().getEmail());
                    firebaseAuth.signOut();
                    Log.d("SettingsActivity", "User signed out.");
                } else {
                    Log.d("SettingsActivity", "No user signed in.");
                }
                //////////////////////////////


                // Déconnecter l'utilisateur de Firebase
                firebaseAuth.signOut();

                // Rediriger vers LoginActivity
                Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Effacer la pile des activités
                startActivity(intent);
                finish();
                Log.d("SettingsActivity", "Redirected to LoginActivity.");// Terminer SettingsActivity
            }
        });
    }
}

