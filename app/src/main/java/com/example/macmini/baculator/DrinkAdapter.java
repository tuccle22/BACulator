package com.example.macmini.baculator;

/**
 * Created by MacMini on 6/3/16.
 */
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.util.List;

public class DrinkAdapter extends RecyclerView.Adapter<DrinkAdapter.MyViewHolder> {


    public List<Drinks> drinkList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView drink;
        public TextInputEditText qty, alc_content;
        public ImageView ic_view;


        public MyViewHolder(View view) {
            super(view);
            drink = (TextView) view.findViewById(R.id.drink);
            qty = (TextInputEditText) view.findViewById(R.id.qty);
            alc_content = (TextInputEditText) view.findViewById(R.id.alc_content);
            ic_view = (ImageView) view.findViewById(R.id.ic_view);

        }
    }

    public DrinkAdapter(List<Drinks> drinkList) {
        this.drinkList = drinkList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Drinks drinks = drinkList.get(position);
        holder.drink.setText(drinks.getmDrink());
        holder.qty.setText(String.valueOf(drinks.getmQty()));
        holder.alc_content.setText(String.valueOf(drinks.getmAlc_content()));
        holder.ic_view.setImageDrawable(drinks.getmImg());

    }

    @Override
    public int getItemCount() {
        return drinkList.size();
    }

    public double calculateBAC(View v) {

        TextInputEditText weight = (TextInputEditText) v.findViewById(R.id.weight);
        Spinner weightUnit = (Spinner) v.findViewById(R.id.weight_unit);
        RadioGroup gender = (RadioGroup) v.findViewById(R.id.gender);
        TextInputEditText time = (TextInputEditText) v.findViewById(R.id.time);

        BACalc calc = new BACalc();

        double bac = 0;

        for(int i=0 ; i<drinkList.size() ; i++){
            Drinks drinks = drinkList.get(i);
            bac += drinks.getmQty() * calc.getBAC(
                    12,
                    drinks.getmAlc_content(),
                    Double.parseDouble(weight.getText().toString()),
                    weightUnit.getSelectedItem().toString(),
                    ((RadioButton)v.findViewById(gender.getCheckedRadioButtonId())).getText().toString(),
                    Double.parseDouble(time.getText().toString())
                    );
        }
        return bac;
    }

}