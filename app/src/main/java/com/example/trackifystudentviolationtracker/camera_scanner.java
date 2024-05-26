package com.example.trackifystudentviolationtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.ImageView;

import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.ViewfinderView;

import java.lang.reflect.Field;
import java.util.List;

public class camera_scanner extends AppCompatActivity {
    private CaptureManager captureManager;
    private boolean isFlashOn = false;

    private DecoratedBarcodeView barcodeView;

    private ImageView flashButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_scanner);

        barcodeView = findViewById(R.id.barcode_View);
        captureManager = new CaptureManager(this, barcodeView);
        barcodeView.setStatusText(null);
        captureManager.initializeFromIntent(getIntent(), savedInstanceState);
        captureManager.decode();


        // Flash button
        flashButton = findViewById(R.id.flash);
        flashButton.setOnClickListener(v -> toggleFlash());

        //remove the laser

        ViewfinderView viewFinder = barcodeView.getViewFinder();

        Field scannerAlphaField = null;

        try {
            scannerAlphaField = viewFinder.getClass().getDeclaredField("SCANNER_ALPHA");
            scannerAlphaField.setAccessible(true);
            scannerAlphaField.set(viewFinder, new int[1]);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        captureManager.onResume();
    }


    private void handleResult(String result) {
        // Handle the scan result (e.g., pass it back to the calling activity)
    }

    @Override
    protected void onPause() {
        super.onPause();
        captureManager.onPause();
    }

    private void toggleFlash() {
        if (isFlashOn) {
            barcodeView.setTorchOff();
            isFlashOn = false;
        } else {
            barcodeView.setTorchOn();
            isFlashOn = true;
        }
        updateFlashIcon();
    }

    private void updateFlashIcon() {
        if (isFlashOn) {
            flashButton.setImageResource(R.drawable.flash);
        } else {
            flashButton.setImageResource(R.drawable.flash_off);
        }
    }


}