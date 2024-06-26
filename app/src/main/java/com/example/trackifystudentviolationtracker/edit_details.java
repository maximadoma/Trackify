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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.badge.BadgeUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class edit_details extends AppCompatActivity {
    EditText scan_output;

    String student_name,student_gender, student_yr_level,student_course,student_college;
    private loading_prompt loading_prompt;
    TextView stud_studentname,stud_gender, stud_yearlevel,stud_course,stud_college;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_details);


        //initialization
        loading_prompt = new loading_prompt(edit_details.this);

        stud_studentname = findViewById(R.id.student_name);
        stud_gender = findViewById(R.id.student_gender);
        stud_yearlevel = findViewById(R.id.year_level);
        stud_course = findViewById(R.id.course);
        stud_college = findViewById(R.id.college);



        //Carries the result
        String result_code = getIntent().getStringExtra("");
        scan_output = findViewById(R.id.scan_output);



        //--------------------start retrieve to database---------------------------


        String stud_id = result_code.trim();
        loading_prompt.show();

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://trackify-student-violation-tracker.000webhostapp.com/trackify_folder/edit_page.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try{
                    JSONArray jsonArray = new JSONArray(response);

                    for(int i= 0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        student_name = jsonObject.getString("full_name");
                        student_gender= jsonObject.getString("stud_gender");
                        student_yr_level = jsonObject.getString("year_level");
                        student_course = jsonObject.getString("course");
                        student_college = jsonObject.getString("department");


                        if (scan_output != null) {
                            scan_output.setText(stud_id);
                            stud_studentname.setText(student_name);
                            stud_gender.setText(student_gender);
                            stud_yearlevel.setText(student_yr_level);
                            stud_course.setText(student_course);
                            stud_college.setText(student_college);
                        }


                        loading_prompt.dismiss();
                    }
                }catch(JSONException e){
                    e.printStackTrace();
                    loading_prompt.dismiss();

                    //do something to get back to dashboard and try input the ID number again
                    //Validates whether the ID number exists in the database

                    AlertDialog.Builder builder = new AlertDialog.Builder(edit_details.this, R.style.CustomAlertDialogTheme);
                    builder.setTitle("Student ID not found")
                            .setMessage("Please return to the dashboard and try again.")
                            .setCancelable(false)
                            .setPositiveButton("GO BACK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(getApplicationContext(), dashboard.class);
                                    startActivity(intent);
                                }
                            });

                    //Creating dialog box
                    AlertDialog dialog  = builder.create();
                    dialog.show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(edit_details.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }){
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("stud_id", stud_id);
                return params;
            }
        };
        queue.add(stringRequest);

        //------------end--------------



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

                    //I want the details from edit page to bring into the final page


//                   Intent intent = new Intent(getApplicationContext(), final_review.class);
//                   startActivity(intent);

                    Intent resultIntent = new Intent(getApplicationContext(), final_review.class);
                    resultIntent.putExtra("studentID", stud_id);
                    resultIntent.putExtra("studentName", student_name);
                    resultIntent.putExtra("violation", selectedViolation);

                    startActivity(resultIntent);




                }

            }
        });



    }


}