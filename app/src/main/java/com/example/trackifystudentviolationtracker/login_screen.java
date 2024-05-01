package com.example.trackifystudentviolationtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class login_screen extends AppCompatActivity {

    //validate if the user data and password data matches the database
    private Button login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        EditText username = findViewById(R.id.username_field);
        EditText password = findViewById(R.id.password_field);
        login_btn = findViewById(R.id.login_Btn);


//
//        login_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                goto_dashboard();
//            }
//        });
//
//    }
//
//    public boolean goto_dashboard(){
//        Intent intent = new Intent(getApplicationContext(), dashboard.class);
//        startActivity(intent);
//        return true;



//        login_btn.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                String username_text = username.getText().toString();
//                String password_text = password.getText().toString();
//
//                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
//                String url = "https://trackify-student-violation-tracker.000webhostapp.com/trackify_folder/login.php";
//
//                JSONObject requestData = new JSONObject();
//                try {
//                    requestData.put("username", username);
//                    requestData.put("password", password);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            // Convert the response string to a JSONObject
//                            JSONObject jsonResponse = new JSONObject(response);
//
//                            // Extract the 'success' boolean and 'message' string
//                            boolean success = jsonResponse.getBoolean("success");
//                            String message = jsonResponse.getString("message");
//
//                            // Check if the login was successful
//                            if (success) {
//                                // Show a success message
//                                Toast.makeText(login_screen.this, message, Toast.LENGTH_SHORT).show();
//
//                                // Navigate to the dashboard activity
//                                Intent intent = new Intent(login_screen.this, dashboard.class);
//                                startActivity(intent);
//                            } else {
//                                // Show an error message
//                                Toast.makeText(login_screen.this, message, Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (JSONException e) {
//                            // Handle JSON parsing exception
//                            e.printStackTrace();
//                            Toast.makeText(login_screen.this, "Error parsing response", Toast.LENGTH_SHORT).show();
//                        }
//
//
//                    }
//                }, error -> Toast.makeText(login_screen.this, "Network error: " + error.getMessage(), Toast.LENGTH_SHORT).show());
//
//
//                final Response.ErrorListener error = new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(login_screen.this, "Error", Toast.LENGTH_SHORT).show();
//                    }
//                };
//                queue.add(stringRequest);
//
//            }
//        });



        login_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String username_txt = username.getText().toString().trim();
                String password_txt = password.getText().toString().trim();

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "https://trackify-student-violation-tracker.000webhostapp.com/trackify_folder/login.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.startsWith("<")) {
                            // Response is not JSON, handle the error
                            Toast.makeText(login_screen.this, "Error: Invalid Response", Toast.LENGTH_SHORT).show();
                        }

                        try {
                            // Convert the response string to a JSONObject
                            JSONObject jsonResponse = new JSONObject(response);

                            // Check for a 'success' boolean key in the response
                            if (jsonResponse.has("success")) {
                                boolean success = jsonResponse.getBoolean("success");
                                String message = jsonResponse.getString("message");

                                // Check if the login was successful
                                Toast.makeText(login_screen.this, message, Toast.LENGTH_SHORT).show();
                                if (success) {
                                    // Show a success message and navigate to the dashboard activity
                                    Intent intent = new Intent(login_screen.this, dashboard.class);
                                    startActivity(intent);
                                } else {
                                    // Show an error message
                                }
                            } else {
                                // If the response does not contain 'success', handle it as an error
                                Toast.makeText(login_screen.this, "Error: Unexpected response format", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            // Handle JSON parsing exception
                            e.printStackTrace();
                            Toast.makeText(login_screen.this, "Error parsing response", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(login_screen.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }){
                    protected Map<String, String> getParams(){
                        Map<String, String> params = new HashMap<>();
                        params.put("username_txt", username_txt);
                        params.put("password_txt", password_txt);
                        return params;
                    }
                };
                queue.add(stringRequest);

            }
        });


    }

}