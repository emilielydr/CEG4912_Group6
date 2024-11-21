package com.example.capstone;
import android.content.Intent;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText, confirmPasswordEditText;
    private Button btnSignIn, btnCreateAcct;
    private TextView noAccountTxt;
    private boolean isRegistering = false; // Mode Inscription ou Connexion
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialiser les éléments de l'interface
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnCreateAcct = findViewById(R.id.btnCreateAcct);
        noAccountTxt = findViewById(R.id.noAccountTxt);

        // Initialisation de Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Vérifier si l'utilisateur est déjà connecté
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // L'utilisateur est connecté, donc rediriger vers l'écran principal
            goToHome();
        }

        // Gérer le bouton Sign In ou Register selon le mode
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRegistering) {
                    // Si en mode inscription, vérifier si les mots de passe correspondent
                    String password = passwordEditText.getText().toString();
                    String confirmPassword = confirmPasswordEditText.getText().toString();
                    if (password.equals(confirmPassword)) {
                        registerUser(usernameEditText.getText().toString(), password);
                    } else {
                        Toast.makeText(LoginActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Si en mode connexion, se connecter
                    signInUser(usernameEditText.getText().toString(), passwordEditText.getText().toString());
                }
            }
        });

        // Gérer le bouton "Create Account" pour changer le mode en inscription
        btnCreateAcct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleRegistrationMode();
            }
        });

        // Gérer le texte "Don't have an account?" pour changer le mode en inscription
        noAccountTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleRegistrationMode();
            }
        });
    }

    private void toggleRegistrationMode() {
        isRegistering = !isRegistering;

        if (isRegistering) {
            // Passer en mode inscription
            btnSignIn.setText("Register");
            confirmPasswordEditText.setVisibility(View.VISIBLE); // Afficher le champ de confirmation de mot de passe
            noAccountTxt.setVisibility(View.GONE); // Cacher le texte "Don't have an account?"
        } else {
            // Passer en mode connexion
            btnSignIn.setText("Sign In");
            confirmPasswordEditText.setVisibility(View.GONE); // Cacher le champ de confirmation de mot de passe
            noAccountTxt.setVisibility(View.VISIBLE); // Afficher le texte "Don't have an account?"
        }
    }

    // Fonction pour enregistrer un nouvel utilisateur
    private void registerUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(LoginActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                        goToHome();
                    } else {
                        Toast.makeText(LoginActivity.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Fonction pour se connecter avec un utilisateur existant
    private void signInUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                        goToHome();
                    } else {
                        Toast.makeText(LoginActivity.this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Fonction pour rediriger vers l'écran principal de l'application
    private void goToHome() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class); // Remplacez HomeActivity par votre activité principale
        startActivity(intent);
        finish();
    }
}