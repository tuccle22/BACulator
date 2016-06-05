package com.example.macmini.baculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private FloatingActionMenu mMenu;
    private FloatingActionButton mBeer;
    private FloatingActionButton mShots;
    private FloatingActionButton mWine;

    private List<Drinks> drinkList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DrinkAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new DrinkAdapter(drinkList);

        mBeer = (FloatingActionButton) findViewById(R.id.beer);
        mShots = (FloatingActionButton) findViewById(R.id.shots);
        mWine = (FloatingActionButton) findViewById(R.id.wine);
        mMenu = (FloatingActionMenu) findViewById(R.id.menu);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        //Beer FAB
        mBeer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Drinks drinks = new Drinks("12oz of Beer", 1, .05);
                drinkList.add(drinks);
                mAdapter.notifyItemInserted(mAdapter.getItemCount());
                mMenu.close(true);
            }
        });
        //Wine FAB
        mWine.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Drinks drinks = new Drinks("Glass of Wine", 1, .14);
                drinkList.add(drinks);
                mAdapter.notifyItemInserted(mAdapter.getItemCount());
                mMenu.close(true);
            }
        });
        //Shots FAB
        mShots.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Drinks drinks = new Drinks("Shot of Liquor", 1, .40);
                drinkList.add(drinks);
                mAdapter.notifyItemInserted(mAdapter.getItemCount());
                mMenu.close(true);
            }
        });


    }
}
