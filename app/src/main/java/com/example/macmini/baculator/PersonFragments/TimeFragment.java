package com.example.macmini.baculator.PersonFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
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
        final PersonSingleton person = PersonSingleton.getInstance();
        final TextInputEditText time = (TextInputEditText) view.findViewById(R.id.time_input);
        time.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                person.setmTime(time.getText().toString());
            }
        });

        return view;
    }

}