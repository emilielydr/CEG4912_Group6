package com.example.capstone;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;

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
        ImageButton btnBrightness = findViewById(R.id.btnBrightness);
        SeekBar brightnessSlider = findViewById(R.id.brightnessSlider);

        btnBrightness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle visibility of the brightness slider
                if (brightnessSlider.getVisibility() == View.GONE) {
                    brightnessSlider.setVisibility(View.VISIBLE);
                } else {
                    brightnessSlider.setVisibility(View.GONE);
                }
            }
        });
    }

}