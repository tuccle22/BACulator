package com.example.macmini.baculator.PersonFragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;


import com.example.macmini.baculator.R;

public class DoneFragment extends Fragment {
    // Store instance variables
    private String title;
    private int page;

    // newInstance constructor for creating fragment with arguments
    public static DoneFragment newInstance(int page, String title) {
        DoneFragment doneFragment = new DoneFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        doneFragment.setArguments(args);
        return doneFragment;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");

//        final android.support.design.widget.FloatingActionButton mDoneFab = (android.support.design.widget.FloatingActionButton) findViewById(R.id.done_fab);
//        mDoneFab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                TranslateAnimation animation = new TranslateAnimation(0, mMenu.getX()-mDoneFab.getX(),0 , mMenu.getY()-mDoneFab.getY());
//                animation.setRepeatMode(0);
//                animation.setDuration(3000);
//                animation.setFillAfter(true);
//                mDoneFab.startAnimation(animation);
//            }
//        });

    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_done, container, false);
//        final FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.done_fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                TranslateAnimation animation = new TranslateAnimation(0, 215,0 , 110);
//                animation.setRepeatMode(0);
//                animation.setDuration(3000);
//                animation.setFillAfter(true);
//                fab.startAnimation(animation);
//            }
//        });
        return inflater.inflate(R.layout.frag_done, container, false);
    }
}