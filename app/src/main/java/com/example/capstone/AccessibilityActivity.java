package com.example.capstone;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
public class AccessibilityActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accessibility);
        Button btnBackSettings= findViewById(R.id.btnBackAccessibility);
        btnBackSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to open SettingsActivity
                Intent intent = new Intent(AccessibilityActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }
}