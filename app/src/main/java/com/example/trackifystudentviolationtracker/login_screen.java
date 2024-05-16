package com.example.trackifystudentviolationtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
    private loading_prompt loading_prompt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        EditText username_txt = findViewById(R.id.username_field);
        EditText password_txt = findViewById(R.id.password_field);
        CheckBox checkBox = findViewById(R.id.login_checkbox);
        Button login_btn = findViewById(R.id.login_Btn);

        String url = "https://trackify-student-violation-tracker.000webhostapp.com/trackify_folder/login.php";
        loading_prompt = new loading_prompt(login_screen.this);



        login_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (username_txt.equals("")) {
                    Toast.makeText(login_screen.this, "Enter your username", Toast.LENGTH_SHORT).show();
                } else if (password_txt.equals("")) {
                    Toast.makeText(login_screen.this, "Enter your password", Toast.LENGTH_SHORT).show();
                } else {
                    loading_prompt.show();

                    String username = username_txt.getText().toString().trim();
                    String password = password_txt.getText().toString().trim();

                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            if(response.equalsIgnoreCase("logged in successfully")){

                                // Save username to SharedPreferences
                                SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();
                                editor.putString("username", username);
                                editor.apply();

                                Intent intent = new Intent(getApplicationContext(), dashboard.class);
                                startActivity(intent);

                                Toast.makeText(login_screen.this, response, Toast.LENGTH_SHORT).show();
                                loading_prompt.dismiss();
                            }
                            else{
                                Toast.makeText(login_screen.this, response, Toast.LENGTH_SHORT).show();
                                loading_prompt.dismiss();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(login_screen.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                            System.out.println(error.getMessage().toString());
                            loading_prompt.dismiss();
                        }
                    }) {
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("username", username);
                            params.put("password", password);
                            return params;
                        }
                    };

                    queue.add(stringRequest);

                }
            }
        });

        //checkbox validation so that the user keeps logged in until user is logged out


        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("remember","");

        if (checkbox.equals("true")){
            Intent intent = new Intent(getApplicationContext(), dashboard.class);
            startActivity(intent);
        }else if (checkbox.equals("false")){
            //the user still needs to sign in
        }
        

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "true");
                    editor.apply();
                }else if (!buttonView.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "false");
                    editor.apply();
                }
            }
        });









        //---- end ----

    }

}








