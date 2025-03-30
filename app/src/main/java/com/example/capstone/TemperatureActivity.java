package com.example.capstone;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
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
    private float currentTextSize;
    private SharedPreferences preferences;

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

        preferences = getSharedPreferences("AppSettings", MODE_PRIVATE);
        currentTextSize = preferences.getFloat("fontSize", 16f); // Load saved size

        // Apply the text size to all views in this activity
        applyTextSizeToAllViews(findViewById(android.R.id.content));

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
                        tempStatusTextView.setText("Status: Unknown");
                    }
                } else {
                    showToast("No temperature data available");
                    tempStatusTextView.setText("Status: Unavailable");
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
