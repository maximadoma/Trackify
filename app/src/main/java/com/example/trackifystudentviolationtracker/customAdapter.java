package com.example.trackifystudentviolationtracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class customAdapter extends BaseAdapter {
    private ArrayList<String> idList;
    private ArrayList<String> dateTimeList;
    private ArrayList<String> violationTypeList;
    private Context context;

    public customAdapter(ArrayList<String> idList, ArrayList<String> dateTimeList, ArrayList<String> violationTypeList, Context context) {
        this.idList = idList;
        this.dateTimeList = dateTimeList;
        this.violationTypeList = violationTypeList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return idList.size(); // Use size of any list since all should have same size
    }

    @Override
    public Object getItem(int position) {
        return null; // Not needed for this implementation
    }

    @Override
    public long getItemId(int position) {
        return 0; // Just return the position as ID
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.history_listview_layout, null);

        //Initialize textViews
        TextView idField = view.findViewById(R.id.student_ID);
        TextView dateTimeView = view.findViewById(R.id.date_and_time);
        TextView violationTypeView = view.findViewById(R.id.violation_type);

        // Set data to views
        idField.setText(idList.get(position));
        dateTimeView.setText(dateTimeList.get(position));
        violationTypeView.setText(violationTypeList.get(position));

        return view;
    }
}
