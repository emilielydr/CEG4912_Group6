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

public class PostureActivity extends AppCompatActivity {

    private TextView postureTextView;// Add a  TextView for posture
    private DatabaseReference  pressureDatabaseRef;
    private ImageView imgGoodPosture, imgBadPosture;
    private Handler handler;
    private float currentTextSize;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_posture);

        // Apply insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize TextViews
        postureTextView = findViewById(R.id.postureTextView); // New TextView for posture
        handler = new Handler(Looper.getMainLooper());
        imgGoodPosture = findViewById(R.id.img_good_posture);
        imgBadPosture = findViewById(R.id.img_bad_posture);

        pressureDatabaseRef = FirebaseDatabase.getInstance().getReference("capteur_pression");
        fetchPosture();

        preferences = getSharedPreferences("AppSettings", MODE_PRIVATE);
        currentTextSize = preferences.getFloat("fontSize", 16f); // Load saved size

        // Apply the text size to all views in this activity
        applyTextSizeToAllViews(findViewById(android.R.id.content));
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
                            postureTextView.setTextColor(getResources().getColor(android.R.color.white));
                            postureTextView.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
                            imgGoodPosture.setVisibility(View.VISIBLE);
                            imgBadPosture.setVisibility(View.GONE);
                        } else {
                            postureTextView.setText("Bad Posture");
                            postureTextView.setTextColor(getResources().getColor(android.R.color.white));
                            postureTextView.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
                            imgGoodPosture.setVisibility(View.GONE);
                            imgBadPosture.setVisibility(View.VISIBLE);

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
