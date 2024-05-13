package com.example.trackifystudentviolationtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
public class final_review extends AppCompatActivity {

    TextView stud_id, stud_name, violation_type, time, date, processed_by;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_review);



        //Back Button
        ImageButton img_btn = (ImageButton) findViewById(R.id.back);
        img_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        //initialize the textBox
        stud_id = findViewById(R.id.id_number_text);
        stud_name = findViewById(R.id.student_name_text);
        violation_type = findViewById(R.id.violaion_text);
        time = findViewById(R.id.time_text);
        date= findViewById(R.id.date_text);
        processed_by = findViewById(R.id.processed_by_text);


        //get the details

        String studentId = getIntent().getStringExtra("studentID");
        String studentName = getIntent().getStringExtra("studentName");
        String violation = getIntent().getStringExtra("violation");
        String processed = getIntent().getStringExtra("username");


        // Get current date and time
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
        SimpleDateFormat timeFormat_db = new SimpleDateFormat("HH:mm:ss");

        String currentDate = dateFormat.format(calendar.getTime());
        String currentTime = timeFormat.format(calendar.getTime());

        // Retrieve the username from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
        String username = prefs.getString("username", "");


        //set the details to the text box
        stud_id.setText(studentId);
        stud_name.setText(studentName);
        violation_type.setText(violation);
        time.setText(currentTime);
        date.setText(currentDate);
        processed_by.setText(username);



    }
}