package com.example.trackifystudentviolationtracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;


public class customAdapter extends BaseAdapter {
    public Filter getFilter;
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



    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                ArrayList<String> filteredIds = new ArrayList<>();
                ArrayList<String> filteredDates = new ArrayList<>();
                ArrayList<String> filteredViolations = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    // No filter, return the original data
                    filteredIds.addAll(idList);
                    filteredDates.addAll(dateTimeList);
                    filteredViolations.addAll(violationTypeList);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (int i = 0; i < idList.size(); i++) {
                        if (idList.get(i).toLowerCase().contains(filterPattern) ||
                                dateTimeList.get(i).toLowerCase().contains(filterPattern) ||
                                violationTypeList.get(i).toLowerCase().contains(filterPattern)) {
                            filteredIds.add(idList.get(i));
                            filteredDates.add(dateTimeList.get(i));
                            filteredViolations.add(violationTypeList.get(i));
                        }
                    }
                }

                results.values = filteredIds;
                results.count = filteredIds.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                idList = (ArrayList<String>) results.values;
                notifyDataSetChanged();
            }
        };
    }



}
