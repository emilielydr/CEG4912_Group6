package com.example.capstone;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TemperatureActivity extends AppCompatActivity {

    private TextView tempTextView, postureTextView; // Add a new TextView for posture
    private DatabaseReference tempDatabaseRef, pressureDatabaseRef;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_temperature);

        // Apply insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize TextViews
        tempTextView = findViewById(R.id.tempTextView);
        postureTextView = findViewById(R.id.postureTextView); // New TextView for posture
        handler = new Handler(Looper.getMainLooper());

        // Firebase Database references
        tempDatabaseRef = FirebaseDatabase.getInstance().getReference("temperature_data");
        pressureDatabaseRef = FirebaseDatabase.getInstance().getReference("capteur_pression");

        // Fetch temperature and posture data
        fetchTemperature();
        fetchPosture();
    }

    private void fetchTemperature() {
        tempDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Double temperature = snapshot.child("temperature").getValue(Double.class);
                    if (temperature != null) {
                        tempTextView.setText("Temperature: " + temperature + " °C");
                    } else {
                        showToast("Temperature data not found");
                    }
                } else {
                    showToast("No temperature data available");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                showToast("Failed to read temperature: " + error.getMessage());
            }
        });
    }

    private void fetchPosture() {
        pressureDatabaseRef.child("voltage").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Double voltage = snapshot.getValue(Double.class);
                    if (voltage != null) {
                        // Determine posture based on voltage threshold
                        if (voltage > 0.8) {
                            postureTextView.setText("Good Posture");
                            postureTextView.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
                        } else {
                            postureTextView.setText("Bad Posture");
                            postureTextView.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                showToast("Failed to read posture: " + error.getMessage());
            }
        });
    }

    private void showToast(String message) {
        handler.post(() -> Toast.makeText(this, message, Toast.LENGTH_LONG).show());
    }
}
