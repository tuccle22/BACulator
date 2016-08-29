package com.example.macmini.baculator.PersonFragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.macmini.baculator.R;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class GenderFragment extends Fragment {

    private Unbinder unbinder;

    @OnClick({R.id.radio_male, R.id.radio_female})
    public void onRadioButtonClicked(RadioButton radioButton) {
        boolean checked = radioButton.isChecked();
        YoYo.with(Techniques.Pulse).duration(250).playOn(radioButton);
        View view = radioButton.getRootView();
        FloatingActionButton fab_next = (FloatingActionButton) view.findViewById(R.id.fab_next);
        YoYo.with(Techniques.Shake).playOn(fab_next);
        switch (radioButton.getId()) {
            case R.id.radio_male:
                if (checked) {
                    PersonSingleton.getInstance().setmSex("male");
                }
                break;
            case R.id.radio_female:
                if (checked) {
                    PersonSingleton.getInstance().setmSex("female");
                }
                break;
        }
    }

    // newInstance constructor for creating fragment with arguments
    public static GenderFragment newInstance() {
        return new GenderFragment();
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
        final View view = inflater.inflate(R.layout.frag_gender, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}