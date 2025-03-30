package com.example.capstone;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private static final String STREAM_URL = "http://raspberry5alex.duckdns.org:8080";
    private WebView webView;
    private TextView postureText, tempTextView;
    private FrameLayout postureBox;
    private FirebaseAuth firebaseAuth;
    private BluetoothAdapter bluetoothAdapter;
    private static final int REQUEST_ENABLE_BT = 1;
    private DatabaseReference tempDatabaseRef, postureDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        checkAndRequestBluetoothPermissions();
        postureText = findViewById(R.id.postureText);
        postureBox = findViewById(R.id.postureBox);
        tempTextView = findViewById(R.id.tempText); // Ajout du TextView pour la température
        firebaseAuth = FirebaseAuth.getInstance();

        String username = firebaseAuth.getCurrentUser() != null ? firebaseAuth.getCurrentUser().getDisplayName() : "User";
        TextView welcomeTxt = findViewById(R.id.welcomeTxt);
        welcomeTxt.setText("Welcome, " + username);

        ImageView wheelChairImg = findViewById(R.id.wheelChairImg);
        Button openGPSButton = findViewById(R.id.btnGPS);
        Button btnCamera = findViewById(R.id.btnCamera);
        Button btnTemperature = findViewById(R.id.btnTemperature);
        Button btnPosture = findViewById(R.id.btnPosture);
        ImageButton btnSettings = findViewById(R.id.btnSettings);

        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setVisibility(View.GONE);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        }

        btnCamera.setOnClickListener(v -> {
            webView.setVisibility(View.VISIBLE);
            webView.loadUrl(STREAM_URL);
            Toast.makeText(MainActivity.this, "Affichage du flux vidéo", Toast.LENGTH_SHORT).show();
        });

        openGPSButton.setOnClickListener(v -> openMaps());
        btnTemperature.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, TemperatureActivity.class)));
        btnPosture.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, PostureActivity.class)));
        btnSettings.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SettingsActivity.class)));

        tempDatabaseRef = FirebaseDatabase.getInstance().getReference("temperature_data");
        postureDatabaseRef = FirebaseDatabase.getInstance().getReference("capteur_pression");
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
                        tempTextView.setText("Temperature: Unknown");
                    }
                } else {
                    tempTextView.setText("Temperature: Unavailable");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(MainActivity.this, "Failed to read temperature", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void checkAndRequestBluetoothPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.BLUETOOTH_CONNECT,
                        Manifest.permission.BLUETOOTH_SCAN
                }, REQUEST_ENABLE_BT);
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_ENABLE_BT);
            }
        }
    }

    private void openMaps() {
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=restaurants");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        firebaseAuth.signOut();
    }
    // Function to update posture status dynamically
    private void fetchPosture() {
        postureDatabaseRef.child("voltage").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Double voltage = snapshot.getValue(Double.class);
                    if (voltage != null) {
                        // Determine posture based on voltage threshold
                        String postureStatus;
                        if (voltage > 0.8) {
                            postureStatus = "Good Posture";
                            postureText.setText("Posture Status : Good");
                            postureText.setTextColor(getResources().getColor(android.R.color.white));
                            postureText.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));

                        } else {
                            postureStatus = "Bad Posture";
                            postureText.setText("Posture Status : Bad");
                            postureText.setTextColor(getResources().getColor(android.R.color.white));
                            postureText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));

                        }

                        // Save the posture status to Firebase for MainActivity to read
                        FirebaseDatabase.getInstance().getReference("posture_data").child("posture").setValue(postureStatus);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(MainActivity.this, "Failed to read posture", Toast.LENGTH_LONG).show();
            }
        });
    }

}
