package com.example.capstone;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class AccessibilityActivity extends AppCompatActivity {
    private SeekBar brightnessSlider;
    private int brightness;
    private ContentResolver contentResolver;
    private Window window;
    private float currentTextSize;
    private SharedPreferences preferences;
    private Button btnIncreaseTextSize, btnDecreaseTextSize, btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accessibility);

        Button btnBackSettings = findViewById(R.id.btnBackAccessibility);
        ImageButton btnColorScheme = findViewById(R.id.btnColorScheme);
        ImageButton btnTextSize = findViewById(R.id.btnTextSize);
        btnIncreaseTextSize = findViewById(R.id.btnIncreaseTextSize);
        btnDecreaseTextSize = findViewById(R.id.btnDecreaseTextSize);
        btnReset = findViewById(R.id.btnReset);

        preferences = getSharedPreferences("AppSettings", MODE_PRIVATE);
        currentTextSize = preferences.getFloat("fontSize", 16f); // Load saved size

        // Set up the back button
        btnBackSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccessibilityActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        // Text size toggle buttons
        btnTextSize.setOnClickListener(v -> toggleTextSizeButtons());
        btnIncreaseTextSize.setOnClickListener(v -> changeTextSize(2));
        btnDecreaseTextSize.setOnClickListener(v -> changeTextSize(-2));

        // Reset button functionality
        btnReset.setOnClickListener(v -> resetSettings());

        // Apply text size
        applyTextSizeToAllViews(findViewById(android.R.id.content));

        // Setup brightness slider
        ImageButton btnBrightness = findViewById(R.id.btnBrightness);
        brightnessSlider = findViewById(R.id.brightnessSlider);
        contentResolver = getContentResolver();
        window = getWindow();
        brightnessSlider.setMax(255);
        brightnessSlider.setKeyProgressIncrement(1);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.System.canWrite(this)) {
                // Already have permission to change brightness
            } else {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + getApplication().getPackageName()));
                startActivity(intent);
            }
        }

        // Set current brightness slider position
        try {
            brightness = Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS);
            brightnessSlider.setProgress(brightness);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        brightnessSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar brightnessSlider, int progress, boolean fromUser) {
                brightness = progress;
                Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, brightness);
                WindowManager.LayoutParams layoutParams = window.getAttributes();
                layoutParams.screenBrightness = brightness / (float) 300;
                window.setAttributes(layoutParams);
            }

            @Override
            public void onStartTrackingTouch(SeekBar brightnessSlider) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar brightnessSlider) {
            }
        });

        // Toggle brightness slider visibility
        btnBrightness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (brightnessSlider.getVisibility() == View.GONE) {
                    brightnessSlider.setVisibility(View.VISIBLE);
                } else {
                    brightnessSlider.setVisibility(View.GONE);
                }
            }
        });

        // Toggle between light and dark modes
        btnColorScheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentMode = AppCompatDelegate.getDefaultNightMode();
                if (currentMode == AppCompatDelegate.MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
            }
        });
    }

    // Increase or decrease text size
    private void changeTextSize(int change) {
        currentTextSize += change;
        if (currentTextSize < 12) currentTextSize = 12; // Set a minimum size
        if (currentTextSize > 30) currentTextSize = 30; // Set a maximum size

        preferences.edit().putFloat("fontSize", currentTextSize).apply();
        applyTextSizeToAllViews(findViewById(android.R.id.content));
    }

    // Reset settings to original values (font size, brightness, etc.)
    private void resetSettings() {
        // Reset font size to default
        currentTextSize = 16f;

        // Reset brightness to a default value (middle point)
        brightness = 128;

        // Reset text size in shared preferences
        preferences.edit().putFloat("fontSize", currentTextSize).apply();

        // Reset screen brightness
        Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, brightness);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.screenBrightness = brightness / (float) 300;
        window.setAttributes(layoutParams);

        // Reset to light mode (AppCompatDelegate.MODE_NIGHT_NO)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // Apply changes
        applyTextSizeToAllViews(findViewById(android.R.id.content)); // Reapply font size
    }


    // Apply text size to all views
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

    // Toggle visibility of text size buttons
    private void toggleTextSizeButtons() {
        if (btnIncreaseTextSize.getVisibility() == View.GONE) {
            btnIncreaseTextSize.setVisibility(View.VISIBLE);
            btnDecreaseTextSize.setVisibility(View.VISIBLE);
        } else {
            btnIncreaseTextSize.setVisibility(View.GONE);
            btnDecreaseTextSize.setVisibility(View.GONE);
        }
    }
}
