package com.example.capstone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText, confirmPasswordEditText;
    private Button btnSignIn, btnCreateAcct;
    private TextView noAccountTxt, confirmPasswordTxt;
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
        confirmPasswordTxt= findViewById(R.id.confirmPasswordTxt);

        // Initialisation de Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Vérifier si l'utilisateur est déjà connecté
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // L'utilisateur est connecté, donc rediriger vers l'écran principal
            goToHome();
        }

        // Initialiser l'interface en mode connexion, donc masquer "Confirm Password"
        confirmPasswordEditText.setVisibility(View.GONE);
        confirmPasswordTxt.setVisibility(View.GONE);

        // Gérer le bouton Sign In ou Register selon le mode
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (isRegistering) {
                    // Vérification des champs vides en mode inscription
                    String confirmPassword = confirmPasswordEditText.getText().toString().trim();
                    if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                        Toast.makeText(LoginActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (password.equals(confirmPassword)) {
                        registerUser(username, password);
                    } else {
                        Toast.makeText(LoginActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Vérification des champs vides en mode connexion
                    if (username.isEmpty() || password.isEmpty()) {
                        Toast.makeText(LoginActivity.this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    signInUser(username, password);
                }
            }
        });

        // Gérer le bouton "Create Account" pour changer le mode en inscription
        btnCreateAcct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleRegistrationMode();
                btnCreateAcct.setVisibility(View.GONE);
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
            confirmPasswordEditText.setVisibility(View.GONE);
            confirmPasswordTxt.setVisibility(View.VISIBLE);// Cacher le champ de confirmation de mot de passe
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
        Intent intent = new Intent(LoginActivity.this, MainActivity.class); // Remplacez MainActivity par votre activité principale
        startActivity(intent);
        finish();
    }
    @Override
    public void onBackPressed() {
        if (isRegistering) {
            // Si nous sommes en mode inscription, revenir au mode connexion
            toggleRegistrationMode();
            btnCreateAcct.setVisibility(View.VISIBLE);
            confirmPasswordEditText.setVisibility(View.GONE);
            confirmPasswordTxt.setVisibility(View.GONE);
            // Afficher à nouveau le bouton "Create Account"
        } else {
            // Si nous sommes en mode connexion, garder le comportement par défaut (retour à l'activité précédente)
            super.onBackPressed();
        }
    }

}
