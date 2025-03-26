package com.example.capstone;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
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

import java.util.List;






public class MainActivity extends AppCompatActivity {
    private static final String RPI_HOSTNAME = "raspberry5alex.duckdns.org"; // 🔹 Remplace par ton sous-domaine DuckDNS
    private static final String USERNAME = "pi"; // 🔹 Nom d’utilisateur Raspberry Pi
    private static final String PASSWORD = "raspberry"; // 🔹 Mot de passe Raspberry Pi
    private static final String STREAM_URL = "http://" + RPI_HOSTNAME + ":8080";
    private WebView webView;
    private Button btnCamera;

    private TextView postureText;
    private FrameLayout postureBox;




    private TextView bluetoothStatus;
    private BluetoothAdapter bluetoothAdapter;
    private static final int REQUEST_ENABLE_BT = 1;


    private ImageView imageView;
    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        checkAndRequestBluetoothPermissions(); // 🔹 Gestion des permissions Bluetooth
        postureText = findViewById(R.id.postureText);
        postureBox = findViewById(R.id.postureBox);
        //FrameLayout postureBox = findViewById(R.id.postureBox);
        // Call the updatePosture method to simulate posture change
        updatePosture(0.7); // Pass in a value to test; 0.7 would be "Bad Posture"

        // Initialisation de FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();

        ImageView wheelChairImg = findViewById(R.id.wheelChairImg);
        Button openGPSButton = findViewById(R.id.btnGPS);
        Button btnCamera = findViewById(R.id.btnCamera);
        Button btnTemperature = findViewById(R.id.btnTemperature);
        Button btnPosture = findViewById(R.id.btnPosture);
        ImageButton btnSettings = findViewById(R.id.btnSettings);

        // 🔹 Ajout de la WebView pour le stream Raspberry Pi
        WebView webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true); // Activer JavaScript pour le stream
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        webView.setVisibility(View.GONE);



        // Vérification et demande de permission pour la caméra
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        }

        // 🔹 Bouton pour ouvrir la caméra et afficher le stream
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.setVisibility(View.VISIBLE); // Toujours afficher WebView
                webView.loadUrl(STREAM_URL); // Charger le flux Raspberry Pi

                Toast.makeText(MainActivity.this, "Affichage du flux vidéo de la caméra Raspberry Pi", Toast.LENGTH_SHORT).show();
            }
        });


        // Bouton pour ouvrir Google Maps avec une recherche GPS
        openGPSButton.setOnClickListener(v -> {
            Log.d("MainActivity", "Bouton GPS cliqué !");
            openMaps();
        });

        // Bouton pour aller à TemperatureActivity
        btnTemperature.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TemperatureActivity.class);
            startActivity(intent);
        });
        // Bouton pour aller à PostureActivity
        btnPosture.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PostureActivity.class);
            startActivity(intent);
        });

        // Bouton pour aller à SettingsActivity
        btnSettings.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });
    }
    // Function to update posture status dynamically
    public void updatePosture(double voltage) {
        if (voltage > 0.8) {
            postureText.setText("Posture: Good");
            postureBox.setBackgroundResource(R.drawable.posture_box_background);
        } else {
            postureText.setText("Posture: Bad");
            postureBox.setBackgroundResource(R.drawable.posture_box_background_bad);

        }
    }


    private void checkAndRequestBluetoothPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) { // Android 12+ (API 31+)
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.BLUETOOTH_CONNECT,
                        Manifest.permission.BLUETOOTH_SCAN
                }, REQUEST_ENABLE_BT);
            }
        } else { // Android 6.0 - 11
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION
                }, REQUEST_ENABLE_BT);
            }
        }
    }



    private void enableBluetooth() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available on this device", Toast.LENGTH_SHORT).show();
        } else if (!bluetoothAdapter.isEnabled()) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions

                return;
            }
            bluetoothAdapter.enable();
            Toast.makeText(this, "Bluetooth enabled", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Bluetooth is already enabled", Toast.LENGTH_SHORT).show();
        }
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_ENABLE_BT) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableBluetooth();
            } else {
                Toast.makeText(this, "Bluetooth permission denied", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == 1) { // Permission pour la caméra
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 100);
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openMaps() {
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=restaurants"); // Changez "restaurants" par une autre requête si nécessaire
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            if (imageView != null) {
                imageView.setImageBitmap(imageBitmap);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Déconnecter l'utilisateur lorsqu'on ferme l'application
        firebaseAuth.signOut();
    }
}
