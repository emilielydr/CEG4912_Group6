package com.example.capstone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private float currentTextSize;
    private SharedPreferences preferences;
    private Button btnSignOut, btnHelp, btnContact, btnAccessibility, btnBackSettings, btnEditProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Initialize buttons
        btnAccessibility = findViewById(R.id.btnAccessibility);
        btnBackSettings = findViewById(R.id.btnBackSettings);
        btnHelp = findViewById(R.id.btnHelp);
        btnContact = findViewById(R.id.btnContact);
        btnSignOut = findViewById(R.id.btnSignOut);
        btnEditProfile = findViewById(R.id.btnEditProfile);

        // Initialize FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();

        // Button to navigate to AccessibilityActivity
        btnAccessibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, AccessibilityActivity.class);
                startActivity(intent);
            }
        });

        // Button to navigate to HelpActivity
        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, HelpActivity.class);
                startActivity(intent);
            }
        });
        // Button to navigate to EditProfile
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });

        // Button to navigate to ContactActivity
        btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, ContactActivity.class);
                startActivity(intent);
            }
        });

        // Button to navigate back to MainActivity
        btnBackSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Sign out button logic
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("SettingsActivity", "Sign Out button clicked!");

                if (firebaseAuth.getCurrentUser() != null) {
                    Log.d("SettingsActivity", "User is signed in: " + firebaseAuth.getCurrentUser().getEmail());
                    firebaseAuth.signOut();
                    Log.d("SettingsActivity", "User signed out.");
                } else {
                    Log.d("SettingsActivity", "No user signed in.");
                }

                // Sign out the user and redirect to LoginActivity
                firebaseAuth.signOut();
                Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                Log.d("SettingsActivity", "Redirected to LoginActivity.");
            }
        });
        preferences = getSharedPreferences("AppSettings", MODE_PRIVATE);
        currentTextSize = preferences.getFloat("fontSize", 16f); // Load saved size

        // Apply the text size to all views in this activity
        applyTextSizeToAllViews(findViewById(android.R.id.content));
    }
    private void applyTextSizeToAllViews(View view) {
        if (view instanceof TextView) {
            ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_SP, currentTextSize);
        } else if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            for (int i = 0; i < group.getChildCount(); i++) {
                applyTextSizeToAllViews(group.getChildAt(i));
            }
        }
    }
}
