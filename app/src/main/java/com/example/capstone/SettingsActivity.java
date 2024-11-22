package com.example.capstone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Initialize button after setContentView()
        Button btnAccessibility= findViewById(R.id.btnAccessibility);


        btnAccessibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to open SettingsActivity
                Intent intent = new Intent(SettingsActivity.this, AccessibilityActivity.class);
                startActivity(intent);
            }
        });
    }
}
