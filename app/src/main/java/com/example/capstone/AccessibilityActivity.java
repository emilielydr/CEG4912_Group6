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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accessibility);
        Button btnBackSettings= findViewById(R.id.btnBackAccessibility);
        ImageButton btnColorScheme=findViewById(R.id.btnColorScheme);
        ImageButton btnTextSize=findViewById(R.id.btnTextSize);

        preferences = getSharedPreferences("AppSettings", MODE_PRIVATE);
        currentTextSize = preferences.getFloat("fontSize", 16f); // Load saved size

        btnBackSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to open SettingsActivity
                Intent intent = new Intent(AccessibilityActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        btnTextSize.setOnClickListener(v -> {
            currentTextSize += 2;
            if (currentTextSize > 24) currentTextSize = 16; // Reset cycle

            preferences.edit().putFloat("fontSize", currentTextSize).apply();

            // Apply new font size immediately
            applyTextSizeToAllViews(findViewById(android.R.id.content));

            // Restart the app to apply changes globally
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
        applyTextSizeToAllViews(findViewById(android.R.id.content));
        ImageButton btnBrightness = findViewById(R.id.btnBrightness);
        SeekBar brightnessSlider = findViewById(R.id.brightnessSlider);
        contentResolver=getContentResolver();
        window=getWindow();
        brightnessSlider.setMax(255);
        brightnessSlider.setKeyProgressIncrement(1);

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            if(Settings.System.canWrite((this))){
            }else{
                Intent intent=new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse(("package: "+getApplication().getPackageName())));
                startActivity(intent);
            }
        }
        try {
            brightness= Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS);
            brightnessSlider.setProgress(brightness);
        } catch (Settings.SettingNotFoundException e) {
            throw new RuntimeException(e);
        }
        brightnessSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar brightnessSlider, int progress, boolean fromUser){
                brightness=progress;
                Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, brightness);
                WindowManager.LayoutParams layoutParams=window.getAttributes();
                layoutParams.screenBrightness=brightness/(float)300;
                window.setAttributes(layoutParams);
            }
            @Override
            public void onStartTrackingTouch(SeekBar brightnessSlider){

            }
            @Override
            public void onStopTrackingTouch(SeekBar brightnessSlider){

            }

        });


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
        btnColorScheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle between light and dark mode
                int currentMode = AppCompatDelegate.getDefaultNightMode();
                if (currentMode == AppCompatDelegate.MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
            }
        });



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