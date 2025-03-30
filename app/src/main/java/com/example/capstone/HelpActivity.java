package com.example.capstone;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class HelpActivity extends AppCompatActivity {
    private float currentTextSize;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_help);
        preferences = getSharedPreferences("AppSettings", MODE_PRIVATE);

        // Load saved text size (clamp between min and max to avoid extreme scaling)
        currentTextSize = Math.max(12f, Math.min(preferences.getFloat("fontSize", 16f), 22f));

        // Apply theme adjustments and text size
        applyTextSizeAndTheme(findViewById(R.id.rootLayout));
    }

    private void applyTextSizeAndTheme(View view) {
        int nightModeFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        boolean isDarkMode = nightModeFlags == Configuration.UI_MODE_NIGHT_YES;

        if (view instanceof TextView) {
            TextView textView = (TextView) view;

            // Prevent 'wheelChairTxt' from resizing and apply text color
            if (textView.getId() == R.id.wheelChairTxt) {
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24f); // Set fixed size
            } else {
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, currentTextSize);
            }

            if (isDarkMode) {
                textView.setTextColor(getResources().getColor(android.R.color.white));
            } else {
                textView.setTextColor(getResources().getColor(android.R.color.black));
            }
        } else if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            for (int i = 0; i < group.getChildCount(); i++) {
                applyTextSizeAndTheme(group.getChildAt(i));
            }
        }

        // Apply background color in dark mode
        View rootView = findViewById(R.id.rootLayout);
        if (isDarkMode && rootView != null) {
            rootView.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        }

        // Scroll to top to prevent layout shifting issues
        final ScrollView scrollView = findViewById(R.id.scrollView);
        if (scrollView != null) {
            scrollView.post(() -> scrollView.fullScroll(View.FOCUS_UP));
        }

        // Prevent wheelChairImage resizing by specifying fixed max size (done in XML)
        ImageView wheelChairImage = findViewById(R.id.wheelChairImage);
        if (wheelChairImage != null) {
            // You could also modify its layout properties programmatically, but XML settings are preferred for images.
        }
    }
}
