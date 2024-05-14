package com.example.trackifystudentviolationtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class final_review extends AppCompatActivity {
    private loading_prompt loading_prompt;
    TextView stud_id, stud_name, violation_type, time, date, processed_by;
    Button submit_final;


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

        loading_prompt = new loading_prompt(final_review.this);

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


        // Retrieve the username from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
        String username = prefs.getString("username", "");


        // Get current date and time
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
        SimpleDateFormat timeFormat_db = new SimpleDateFormat("HH:mm:ss");

        String currentDate = dateFormat.format(calendar.getTime());
        String currentTime = timeFormat.format(calendar.getTime()); //holds the format in displaying time in a coherent manner
        String db_Time = timeFormat_db.format(calendar.getTime()); //holds the proper time format to be thrown to the db



        //set the details to the text box
        stud_id.setText(studentId);
        stud_name.setText(studentName);
        violation_type.setText(violation);
        time.setText(currentTime);
        date.setText(currentDate);
        processed_by.setText(username);


        //put the fetched details to the database

        String getID = stud_id.getText().toString().trim();
        String getViolation = violation_type.getText().toString().trim();
        String getDate = date.getText().toString().trim();
        String getTime = db_Time;
        String getProcessed = processed_by.getText().toString().trim();

        submit_final = findViewById(R.id.submit_Btn);
        submit_final.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading_prompt.show();

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "https://trackify-student-violation-tracker.000webhostapp.com/trackify_folder/final_review.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.equals("Success")){
                            loading_prompt.dismiss();

                            //go back to dashboard
                            AlertDialog.Builder builder = new AlertDialog.Builder(final_review.this, R.style.CustomAlertDialogTheme);
                            builder.setTitle("Form successfully submitted")
                                    .setIcon(R.drawable.check_icon)
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(getApplicationContext(), dashboard.class);
                                            startActivity(intent);
                                        }
                                    });

                            //Creating dialog box
                            AlertDialog dialog  = builder.create();
                            dialog.show();


                        }else{
                            loading_prompt.dismiss();
                            Toast.makeText(final_review.this, "Data Failed to Add to Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading_prompt.dismiss();
                        Toast.makeText(final_review.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }){
                    protected Map<String, String> getParams(){
                        Map<String, String> params = new HashMap<>();
                        params.put("stud_id", getID);
                        params.put("violation", getViolation);
                        params.put("processed_by", getProcessed);
                        params.put("vr_date", getDate);
                        params.put("vr_time", getTime);
                        return params;
                    }
                };
                queue.add(stringRequest);
            }
        });




    }
}