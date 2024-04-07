package com.example.trackifystudentviolationtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class login_screen extends AppCompatActivity {

    //validate if the user data and password data matches the database
    private EditText username, password;
    private Button login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        username = findViewById(R.id.username_field);
        password = findViewById(R.id.password_field);
        login_btn = findViewById(R.id.login_Btn);



        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goto_dashboard();
            }
        });

    }

    public boolean goto_dashboard(){
        Intent intent = new Intent(getApplicationContext(), dashboard.class);
        startActivity(intent);
        return true;
    }

}