package com.example.trackifystudentviolationtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.badge.BadgeUtils;

import java.util.Arrays;
import java.util.List;

public class edit_details extends AppCompatActivity {
    EditText scan_output ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_details);


        String result_code = getIntent().getStringExtra("");
        scan_output = findViewById(R.id.scan_output);

        if (scan_output != null) {
            scan_output.setText(result_code);
        }


        //Back Button
        ImageButton img_btn = (ImageButton) findViewById(R.id.back);
        img_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), dashboard.class);
                startActivity(intent);
            }
        });


        final List<String> violations = Arrays.asList("Select a violation*","Not wearing uniform", "Not wearing ID", "Loitering","Vandalism");


        AppCompatSpinner spinner = findViewById(R.id.violation_spinner);

        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), R.layout.custom_spinner_layout, violations );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);



        //for Preview Button

        Button preview_Btn = (Button) findViewById(R.id.preview_Btn);

        preview_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String selectedViolation = (String) spinner.getSelectedItem();

                if (selectedViolation.equals(violations.get(0)))
                {
//                    Toast.makeText(getApplicationContext(), "Please select a violation", Toast.LENGTH_SHORT).show();

                    AlertDialog.Builder builder = new AlertDialog.Builder(edit_details.this, R.style.CustomAlertDialogTheme);
                    builder.setTitle("")
                            .setMessage("Please select a violation")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
//                                    Toast.makeText(edit_details.this,"Selected Option: YES",Toast.LENGTH_SHORT).show();
                                }
                            });

                    //Creating dialog box
                    AlertDialog dialog  = builder.create();
                    dialog.show();

                }else{
                   Intent intent = new Intent(getApplicationContext(), final_review.class);
                   startActivity(intent);
                }

            }
        });



    }


}