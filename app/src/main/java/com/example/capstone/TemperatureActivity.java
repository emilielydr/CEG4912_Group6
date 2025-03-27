package com.example.capstone;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
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


    private DatabaseReference tempDatabaseRef, pressureDatabaseRef;
    private Handler handler;
    private TextView tempTextView, tempStatusTextView;
    private ImageView coolingIcon, coolingOff;

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
        tempStatusTextView = findViewById(R.id.tempStatusTextView);
        coolingIcon = findViewById(R.id.coolingIcon);
        coolingOff = findViewById(R.id.coolingOff);
        //postureTextView = findViewById(R.id.postureTextView); // New TextView for posture
        handler = new Handler(Looper.getMainLooper());

        // Firebase Database references
        tempDatabaseRef = FirebaseDatabase.getInstance().getReference("temperature_data");

        // Fetch temperature
        fetchTemperature();

    }

    private void fetchTemperature() {
        tempDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Double temperature = snapshot.child("temperature").getValue(Double.class);
                    if (temperature != null) {
                        tempTextView.setText("Temperature: " + temperature + " °C");
                        tempStatusTextView.setText("Status: Cooling");
                        coolingIcon.setVisibility(ImageView.VISIBLE);
                        coolingOff.setVisibility(ImageView.GONE);
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

    private void showToast(String message) {
        handler.post(() -> Toast.makeText(this, message, Toast.LENGTH_LONG).show());
    }
}
