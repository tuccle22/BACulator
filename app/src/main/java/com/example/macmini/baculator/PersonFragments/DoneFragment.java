package com.example.macmini.baculator.PersonFragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.macmini.baculator.AgreeActivity;
import com.example.macmini.baculator.DrinkAdapter;
import com.example.macmini.baculator.Drinks;
import com.example.macmini.baculator.MainActivity;
import com.example.macmini.baculator.R;
import com.example.macmini.baculator.SplashActivity;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.relex.circleindicator.CircleIndicator;

public class DoneFragment extends Fragment {

    private Unbinder unbinder;
    private ViewPager view_pager;
    @BindView(R.id.done_card) CardView done_card;

    @OnClick(R.id.reset)
    public void onResetClick(View view) {
        DrinkAdapter.drinkList.clear();
        MainActivity.mAdapter.notifyDataSetChanged();


        SharedPreferences prefs = getActivity().getSharedPreferences("something", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(SplashActivity.AGREEMENT, false);
        editor.commit();
        view_pager.setCurrentItem(0);
        Intent intent = new Intent(getContext(), AgreeActivity.class);
        startActivity(intent);
    }

    @OnClick({R.id.done_sex, R.id.done_weight, R.id.done_weight_measure, R.id.done_time})
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.done_sex:
                view_pager.setCurrentItem(MainActivity.GENDER, true);
                break;
            case R.id.done_weight:
            case R.id.done_weight_measure:
                view_pager.setCurrentItem(MainActivity.WEIGHT, true);
                break;
            case R.id.done_time:
                view_pager.setCurrentItem(MainActivity.TIME, true);
                break;
        }
    }


    // newInstance constructor for creating fragment with arguments
    public static DoneFragment newInstance() {
        return new DoneFragment();
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_done, container, false);
        view_pager = (ViewPager) getActivity().findViewById(R.id.view_pager);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}