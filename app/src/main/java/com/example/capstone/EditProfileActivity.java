package com.example.capstone;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;


import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {

    private EditText emailEditText;
    private SwitchCompat toggleSwitch;
    private FusedLocationProviderClient fusedLocationClient;
    private Button saveButton;

    private SharedPreferences sharedPreferences;
    private Handler handler = new Handler(Looper.getMainLooper());
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    private static final long UPDATE_INTERVAL = 25 * 60 * 1000; // 25 minutes

    private Runnable locationUpdater = new Runnable() {
        @Override
        public void run() {
            if (toggleSwitch.isChecked()) {
                sendLocationToEmail();
                handler.postDelayed(this, UPDATE_INTERVAL);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_editprofile);

        emailEditText = findViewById(R.id.inputEmail);
        toggleSwitch = findViewById(R.id.toggleSwitch);
        saveButton = findViewById(R.id.btnSave);

        sharedPreferences = getSharedPreferences("HelpPrefs", MODE_PRIVATE);

        // Initialiser le FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Load saved email and switch state
        emailEditText.setText(sharedPreferences.getString("emergency_email", ""));
        toggleSwitch.setChecked(sharedPreferences.getBoolean("location_updates", false));

        saveButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            if (!TextUtils.isEmpty(email)) {
                sharedPreferences.edit()
                        .putString("emergency_email", email)
                        .putBoolean("location_updates", toggleSwitch.isChecked())
                        .apply();
                Toast.makeText(this, "Settings saved", Toast.LENGTH_SHORT).show();
                if (toggleSwitch.isChecked()) {
                    handler.post(locationUpdater);
                } else {
                    handler.removeCallbacks(locationUpdater);
                }
            } else {
                Toast.makeText(this, "Enter a valid email", Toast.LENGTH_SHORT).show();
            }
        });

        toggleSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            sharedPreferences.edit().putBoolean("location_updates", isChecked).apply();
            if (isChecked) {
                handler.post(locationUpdater);
            } else {
                handler.removeCallbacks(locationUpdater);
            }
        });

        if (toggleSwitch.isChecked()) {
            handler.post(locationUpdater);
        }
    }


    private void sendLocationToEmail() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();

                            String email = sharedPreferences.getString("emergency_email", "");
                            if (!email.isEmpty()) {
                                // Lancer la tâche d'envoi d'email
                                new SendEmailTask(email, latitude, longitude).execute();
                                Toast.makeText(EditProfileActivity.this, "Location sent to email", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(EditProfileActivity.this, "Could not retrieve location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
