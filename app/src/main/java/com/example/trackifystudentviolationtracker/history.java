package com.example.trackifystudentviolationtracker;

import static com.example.trackifystudentviolationtracker.R.id.history_dataList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class history extends AppCompatActivity {

    //initialization
    String id, date_and_time, violation_type;
    private loading_prompt loading_prompt;
    public ArrayList<String> idList = new ArrayList<>();
    public ArrayList<String> dateTimeList = new ArrayList<>();
    public ArrayList<String> violationTypeList = new ArrayList<>();
    public ListView  history_list;
    public customAdapter adapter;

    public SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Toolbar toolbar = findViewById(R.id.toolbar_history);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        //Back Button
        ImageButton img_btn = (ImageButton) findViewById(R.id.back);
        img_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), dashboard.class);
                startActivity(intent);
            }
        });

        loading_prompt = new loading_prompt(history.this);
        history_list = (ListView) findViewById(history_dataList);
        adapter = new customAdapter(idList, dateTimeList, violationTypeList, this);
        history_list.setAdapter(adapter);
        history_list.setClickable(true);
        historyData();


        //brings the data to the violation details to view all the student details
        history_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), violation_details.class);
                intent.putExtra("id", idList.get(position));
                intent.putExtra("date_time", dateTimeList.get(position));
                intent.putExtra("violation", violationTypeList.get(position));
                startActivity(intent);
            }
        });


    }

    // Search and sort options
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.custom_menu_toolbar, menu);
        MenuItem searchItem = menu.findItem(R.id.nav_search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Perform search when user submits query
                historySearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Perform search as user types
                historySearch(newText);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    //history db retrieve
    private void historyData() {
        //-----start  retrieve----

        loading_prompt.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://trackify-student-violation-tracker.000webhostapp.com/trackify_folder/history_retrieve.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        id = jsonObject.getString("stud_id");
                        date_and_time = jsonObject.getString("date_time");
                        violation_type = jsonObject.getString("violation");

                        idList.add(id);
                        dateTimeList.add(date_and_time);
                        violationTypeList.add(violation_type);
                    }


                    // Notify adapter of data changes
                    adapter.notifyDataSetChanged();
                    loading_prompt.dismiss();

                } catch (JSONException e) {
                    e.printStackTrace();
                    loading_prompt.dismiss();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                loading_prompt.dismiss();
            }
        });

        queue.add(stringRequest);

        //------------end--------------
    }


    //history search retrieve
    private void historySearch(final String query) {
        //-----start  retrieve----

        searchView.getInputType();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://trackify-student-violation-tracker.000webhostapp.com/trackify_folder/search_retrieve.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    idList.clear();
                    dateTimeList.clear();
                    violationTypeList.clear();

                    if (response.startsWith("<")) {
                        // Response is not JSON, handle the error
                        Toast.makeText(history.this, "Error: Invalid Response", Toast.LENGTH_SHORT).show();
                    }

                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        id = jsonObject.getString("stud_id");
                        date_and_time = jsonObject.getString("date_time");
                        violation_type = jsonObject.getString("violation");

                        idList.add(id);
                        dateTimeList.add(date_and_time);
                        violationTypeList.add(violation_type);
                    }


                    // Notify adapter of data changes
                    adapter.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();


                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();

            }
        })
        {
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("search", query);
                return params;
            }
        };

        queue.add(stringRequest);

        //------------end--------------
    }


}
