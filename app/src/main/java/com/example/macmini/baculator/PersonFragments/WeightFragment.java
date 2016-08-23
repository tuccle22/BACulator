package com.example.macmini.baculator.PersonFragments;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.example.macmini.baculator.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;
import butterknife.OnTextChanged;
import butterknife.Unbinder;

public class WeightFragment extends Fragment {

    private Unbinder unbinder;

    @OnTextChanged(R.id.weight_input)
    public void onTextChanged(CharSequence text) {
        PersonSingleton.getInstance().setmWeight(text.toString());
    }

    @OnItemSelected(R.id.spinner)
    public void onItemSelected(Spinner spinner, int position) {
        PersonSingleton.getInstance().setmWeightUnit(spinner.getSelectedItem().toString());
    }

    // newInstance constructor for creating fragment with arguments
    public static WeightFragment newInstance() {
        return new WeightFragment();
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
        View view = inflater.inflate(R.layout.frag_weight, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}