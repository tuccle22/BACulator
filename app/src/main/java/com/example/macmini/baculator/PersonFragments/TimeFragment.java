package com.example.macmini.baculator.PersonFragments;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.macmini.baculator.R;

public class TimeFragment extends Fragment {
    // Store instance variables
    private String title;
    private int page;
    private TextInputEditText time_view;

    // newInstance constructor for creating fragment with arguments
    public static TimeFragment newInstance(int page, String title) {
        TimeFragment timeFragment = new TimeFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        timeFragment.setArguments(args);
        return timeFragment;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_time, container, false);
        setTime((TextInputEditText) view.findViewById(R.id.time));
        return view;
    }

    public void setTime(TextInputEditText time_view){
        this.time_view = time_view;
    }

    public Double getTime(){
        return Double.parseDouble(time_view.getText().toString());
    }
}