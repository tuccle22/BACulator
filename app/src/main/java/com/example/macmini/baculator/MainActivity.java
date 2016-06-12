package com.example.macmini.baculator;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;


import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    private FloatingActionMenu mMenu;
    private FloatingActionButton mBeer;
    private FloatingActionButton mShots;
    private FloatingActionButton mWine;
    private Button calc;
    private TextView result;
    private TextInputEditText mWeight;
    private RadioGroup mGender;
    private Spinner mWeightUnit;
    private TextInputEditText mTime;


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

        mMenu = (FloatingActionMenu) findViewById(R.id.menu);
        result = (TextView) findViewById(R.id.result);
        mWeight = (TextInputEditText) findViewById(R.id.weight);
        mGender = (RadioGroup) findViewById(R.id.gender);
        mWeightUnit = (Spinner) findViewById(R.id.weight_unit);
        mTime = (TextInputEditText) findViewById(R.id.time);



        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        //Shots FAB
        mShots = (FloatingActionButton) findViewById(R.id.shots);
        mShots.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Drinks drinks = new Drinks("Shot of Liquor", 1, 40,
                        getResources().getDrawable(R.drawable.ic_fab_shots, getTheme()));
                drinkList.add(drinks);
                mAdapter.notifyItemInserted(mAdapter.getItemCount());
                mMenu.close(true);
            }
        });

        //Wine FAB
        mWine = (FloatingActionButton) findViewById(R.id.wine);
        mWine.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Drinks drinks = new Drinks("Glass of Wine", 1, 15,
                        getResources().getDrawable(R.drawable.ic_fab_wine, getTheme()));
                drinkList.add(drinks);
                mAdapter.notifyItemInserted(mAdapter.getItemCount());
                mMenu.close(true);
            }
        });

        //Beer FAB
        mBeer = (FloatingActionButton) findViewById(R.id.beer);
        mBeer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Drinks drinks = new Drinks("12oz of Beer", 1, 5,
                        getResources().getDrawable(R.drawable.ic_beer, getTheme()));
                drinkList.add(drinks);
                mAdapter.notifyItemInserted(mAdapter.getItemCount());
                mMenu.close(true);
            }
        });

        //calculate button
        calc = (Button) findViewById(R.id.calculate);
        calc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try {
                    result.setText(calculateBAC());
                } catch (Exception e) {
                    result.setText("Fill out the above information.");
                    e.printStackTrace();
                }
            }
        });



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private String calculateBAC() throws Exception {

        BACalc calc = new BACalc();
        double bac = 0;

        for(int i=0 ; i<drinkList.size() ; i++){
            Drinks drinks = drinkList.get(i);

                bac += drinks.getmQty() *
                        calc.getBAC(
                            12,
                            drinks.getmAlc_content(),
                            Double.parseDouble(mWeight.getText().toString()),
                            mWeightUnit.getSelectedItem().toString(),
                            ((RadioButton)findViewById(mGender.getCheckedRadioButtonId())).getText().toString(),
                            Double.parseDouble(mTime.getText().toString())
                        );

        }
        return String.valueOf((double)Math.round(bac * 100d) / 100d);
    }

}
