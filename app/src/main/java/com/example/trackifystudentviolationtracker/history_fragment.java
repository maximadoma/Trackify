package com.example.trackifystudentviolationtracker;

import static com.example.trackifystudentviolationtracker.R.id.history_dataList;
import static com.example.trackifystudentviolationtracker.customAdapter.*;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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

public class history_fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public history_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment home_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static home_fragment newInstance(String param1, String param2) {
        home_fragment fragment = new home_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }





    //initialization
    String id, date_and_time, violation_type;
    private loading_prompt loading_prompt;
    public ArrayList<String> idList = new ArrayList<>();
    public ArrayList<String> dateTimeList = new ArrayList<>();
    public ArrayList<String> violationTypeList = new ArrayList<>();
    public customAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history_fragment, container, false);
        loading_prompt = new loading_prompt(getContext());



        ListView  history_list = (ListView) view.findViewById(history_dataList);
        adapter = new customAdapter(idList, dateTimeList, violationTypeList, getContext());
        history_list.setAdapter(adapter);


        historyData();


        return view;

    }

    private void historyData() {
        //-----start  retrieve----

        loading_prompt.show();
        RequestQueue queue = Volley.newRequestQueue(getActivity());
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
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                loading_prompt.dismiss();
            }
        });

        queue.add(stringRequest);

        //------------end--------------
    }

}