package com.example.trackifystudentviolationtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;

public class settings extends AppCompatActivity {

    private CheckBox beepCheckbox;
    private CheckBox vibrateCheckbox;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //Back Button
        ImageButton img_btn = (ImageButton) findViewById(R.id.back);
        img_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), dashboard.class);
                startActivity(intent);
            }
        });


        sharedPreferences = getSharedPreferences("ScanSettings", MODE_PRIVATE);

        beepCheckbox = findViewById(R.id.beep_checkbox);
        vibrateCheckbox = findViewById(R.id.vibrate_checkbox);

        // Load saved preferences
        beepCheckbox.setChecked(sharedPreferences.getBoolean("beep", false));
        vibrateCheckbox.setChecked(sharedPreferences.getBoolean("vibrate", false));

        // Save preferences when changed
        beepCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("beep", isChecked);
            editor.apply();
        });

        vibrateCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("vibrate", isChecked);
            editor.apply();
        });





    }
}