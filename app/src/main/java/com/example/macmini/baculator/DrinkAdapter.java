package com.example.macmini.baculator;

import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DrinkAdapter extends RecyclerView.Adapter<DrinkAdapter.MyViewHolder> {


    public ArrayList<Drinks> drinkList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.drink) TextView drink;
        @BindView(R.id.qty) TextInputEditText qty;
        @BindView(R.id.alc_content) TextInputEditText alc_content;
        @BindView(R.id.oz) TextInputEditText oz;
        @BindView(R.id.ic_view) ImageView ic_view;


        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public DrinkAdapter(ArrayList<Drinks> drinkList) {
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
        holder.oz.setText(String.valueOf(drinks.getmOz()));
        holder.ic_view.setImageDrawable(drinks.getmImg());
        holder.alc_content.setText(String.valueOf(drinks.getmAlc_content()));
    }

    @Override
    public int getItemCount() {
        return drinkList.size();
    }


}