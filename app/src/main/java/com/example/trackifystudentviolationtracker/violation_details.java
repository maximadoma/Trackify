package com.example.trackifystudentviolationtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.trackifystudentviolationtracker.databinding.ActivityHistoryBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class violation_details extends AppCompatActivity {

    String stud_id, date_time, violation_type;
    private loading_prompt loading_prompt;


    TextView issued, idNumber, studentName, gender, year, course, department, violationType, processedBy;

    String getIssue, getStud_id, getStud_name, getStud_gender, getYear, getCourse, getDepartment,  getViolation, getProcessed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_violation_details);

        loading_prompt = new loading_prompt(violation_details.this);

        //initialize textViews
        issued = findViewById(R.id.issue_text);
        idNumber = findViewById(R.id.id_number_text);
        studentName = findViewById(R.id.name_text);
        gender = findViewById(R.id.gender_text);
        year = findViewById(R.id.yearlevel_text);
        course = findViewById(R.id.course_text);
        department = findViewById(R.id.department_text);
        violationType = findViewById(R.id.vio_text);
        processedBy = findViewById(R.id.processed_by_text);

        Intent intent = this.getIntent();

        if (intent != null){
            stud_id = intent.getStringExtra("id");
            date_time = intent.getStringExtra("date_time");
            violation_type = intent.getStringExtra("violation");

            // Call method to retrieve and display data
            violation_retrieve(stud_id, date_time, violation_type);
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
    }


    public void violation_retrieve(String stud_id, final String date_time, final String violation_type){


        //Start---------------
        loading_prompt.show();

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://trackify-student-violation-tracker.000webhostapp.com/trackify_folder/v_details.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // Check if the response is a JSON array

                       JSONArray jsonArray = new JSONArray(response);


                            for(int i= 0; i<jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            getIssue = jsonObject.getString("date_time");
                            getStud_id = jsonObject.getString("stud_id");
                            getStud_name =jsonObject.getString("full_name");
                            getStud_gender =jsonObject.getString("stud_gender");
                            getYear=jsonObject.getString("year_level");
                            getCourse =jsonObject.getString("course");
                            getDepartment=jsonObject.getString("department");
                            getViolation =jsonObject.getString("violation");
                            getProcessed =jsonObject.getString("processed_by");


                                if (stud_id != null) {
                                    issued.setText(getIssue);
                                    idNumber.setText(getStud_id);
                                    studentName.setText(getStud_name);
                                    gender.setText(getStud_gender);
                                    year.setText(getYear);
                                    course.setText(getCourse);
                                    department.setText(getDepartment);
                                    violationType.setText(getViolation);
                                    processedBy.setText(getProcessed);
                                }
                                loading_prompt.dismiss();
                        }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(violation_details.this, "Error parsing data", Toast.LENGTH_SHORT).show();
                    loading_prompt.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(violation_details.this, "Error", Toast.LENGTH_SHORT).show();
                loading_prompt.dismiss();
            }
        }){
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("stud_id", stud_id);
                params.put("date_time", date_time);
                params.put("violation_type", violation_type);
                return params;
            }
        };
        queue.add(stringRequest);

        //------------end--------------
    }



}