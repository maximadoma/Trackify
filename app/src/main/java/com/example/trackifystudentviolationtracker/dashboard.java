package com.example.trackifystudentviolationtracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);



        //Initializing the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);


        //Initializing the Drawer Layout
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        //Initializing the Side Drawer Navigation View
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new home_fragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

    }



    //Method for opening the side drawer navigation view
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        navigationView = findViewById(R.id.nav_view);

        if (itemId == R.id.nav_home) {
            replaceFragment(new home_fragment());
            drawerLayout.closeDrawer(GravityCompat.START);

        } else if (itemId == R.id.nav_history) {
            Intent intent = new Intent(dashboard.this, history.class );
            startActivity(intent);
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else if (itemId == R.id.nav_settings) {
            Intent intent = new Intent(dashboard.this, settings.class );
            startActivity(intent);
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else if (itemId == R.id.nav_aboutapp) {
            Intent intent = new Intent(dashboard.this, about_app.class );
            startActivity(intent);
            drawerLayout.closeDrawer(GravityCompat.START);
        }


        return true;
    }

    //method in replacing the fragment after selection
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }



}