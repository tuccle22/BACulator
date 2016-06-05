package com.example.macmini.baculator;

/**
 * Created by MacMini on 6/3/16.
 */
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class DrinkAdapter extends RecyclerView.Adapter<DrinkAdapter.MyViewHolder> {

    private List<Drinks> drinkList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView drink, alc_content;
        public EditText qty;

        public MyViewHolder(View view) {
            super(view);
            drink = (TextView) view.findViewById(R.id.drink);
            qty = (EditText) view.findViewById(R.id.qty);
            alc_content = (TextView) view.findViewById(R.id.alc_content);
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
        holder.alc_content.setText(String.valueOf(drinks.getmAlc_content()*100));
    }

    @Override
    public int getItemCount() {
        return drinkList.size();
    }
}