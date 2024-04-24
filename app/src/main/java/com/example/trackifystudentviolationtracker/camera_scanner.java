package com.example.trackifystudentviolationtracker;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.ViewfinderView;

import java.lang.reflect.Field;

public class camera_scanner extends AppCompatActivity {
    private CaptureManager captureManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_scanner);

        //initialize the camera scanner
        DecoratedBarcodeView barcodeView = (DecoratedBarcodeView) findViewById(R.id.barcode_View);
        captureManager = new CaptureManager(this, barcodeView);
        barcodeView.setStatusText(null);
        captureManager.initializeFromIntent(getIntent(), savedInstanceState);
        captureManager.decode();

        ViewfinderView viewFinder = barcodeView.getViewFinder();

        //remove the laser
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

    @Override
    protected void onPause() {
        super.onPause();
        captureManager.onPause();
    }



}