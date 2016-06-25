package com.example.macmini.baculator;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    static final String GENDER = "gender";
    static final String WEIGHT = "weight";
    static final String WEIGHT_UNIT = "weightUnit";
    static final String WATER = "water";
    static final String TIME_DRINKING = "timeDrinking";

    private FloatingActionMenu mMenu;
    private FloatingActionButton mBeer;
    private FloatingActionButton mShots;
    private FloatingActionButton mWine;
    private Button calc;
    private TextView result;
    private TextInputEditText mWeight;
    private RadioGroup mGender;
    private Spinner mWeightUnit;
    private TextInputEditText mWater;
    private TextInputEditText mTime;

    private ArrayList<Drinks> drinkList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DrinkAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState != null){
            //populate saved instances
        }

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new DrinkAdapter(drinkList);

        mMenu = (FloatingActionMenu) findViewById(R.id.menu);
        result = (TextView) findViewById(R.id.result);
        mWeight = (TextInputEditText) findViewById(R.id.weight);
        mGender = (RadioGroup) findViewById(R.id.gender);
        mWeightUnit = (Spinner) findViewById(R.id.weight_unit);
        mWater = (TextInputEditText) findViewById(R.id.water);
        mTime = (TextInputEditText) findViewById(R.id.time);


        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        mMenu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            }
        });

        //Shots FAB
        mShots = (FloatingActionButton) findViewById(R.id.shots);
        mShots.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Drinks drinks = new Drinks(getResources().getDrawable(R.drawable.ic_shots_fab, getTheme()),
                        1, 1.5, "Shot of Liquor", 40);
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
                Drinks drinks = new Drinks(getResources().getDrawable(R.drawable.ic_wine_fab, getTheme()),
                        1, 6, "Glass of Wine", 12);
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
                Drinks drinks = new Drinks(getResources().getDrawable(R.drawable.ic_beer_fab, getTheme()),
                        1, 12, "12oz of Beer", 5);
                drinkList.add(drinks);
                mAdapter.notifyItemInserted(mAdapter.getItemCount());
                mMenu.close(true);
            }
        });

        //calculate button
        calc = (Button) findViewById(R.id.result);
        calc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try {
                    result.setText(calculateBAC());
                } catch (Exception e) {
                    result.setText("Missing Information");
                    e.printStackTrace();
                }
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Drinks drinks = drinkList.get(position);
                final int pos = position;

                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.list_item);
                dialog.setTitle(drinks.getmDrink());

                try {
                    final ImageView img = (ImageView) dialog.findViewById(R.id.ic_view);
                    img.setImageDrawable(drinks.getmImg());
                    final TextInputEditText qty = (TextInputEditText) dialog.findViewById(R.id.qty);
                    qty.setText(Integer.toString(drinks.getmQty()));
                    qty.setSelectAllOnFocus(true);
                    final TextInputEditText oz = (TextInputEditText) dialog.findViewById(R.id.oz);
                    oz.setText(Double.toString(drinks.getmOz()));
                    oz.setSelectAllOnFocus(true);
                    final TextView drinkDesc = (TextView) dialog.findViewById(R.id.drink);
                    drinkDesc.setText(drinks.getmDrink());
                    final TextInputEditText abv = (TextInputEditText) dialog.findViewById(R.id.alc_content);
                    abv.setText(Double.toString(drinks.getmAlc_content()));
                    abv.setSelectAllOnFocus(true);

//                    View v = ;
//
//                    dialog.addContentView(v, new RecyclerView.LayoutParams(
//                            ViewGroup.LayoutParams.MATCH_PARENT,
//                            ViewGroup.LayoutParams.MATCH_PARENT
//                    ));

                    dialog.getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            Drinks drinks = drinkList.get(pos);
                            drinks.setmQty(Integer.parseInt(qty.getText().toString()));
                            drinks.setmAlc_content(Double.parseDouble(abv.getText().toString()));
                            drinks.setmOz(Double.parseDouble(oz.getText().toString()));
                            mAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),
                            e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                dialog.show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    @Override
    public void onResume(){
        super.onResume();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
//        savedInstanceState.putInt(GENDER, mGender.getCheckedRadioButtonId());
//        savedInstanceState.putDouble(WEIGHT, Double.parseDouble(mWeight.getText().toString()));
//        savedInstanceState.putInt(WEIGHT_UNIT, mWeightUnit.getSelectedItemPosition());
//        savedInstanceState.putDouble(WATER, Double.parseDouble(mWater.getText().toString()));
//        savedInstanceState.putDouble(TIME_DRINKING, Double.parseDouble(mTime.getText().toString()));
//        savedInstanceState.putParcelable("DrinkArray", drinkList);
        super.onSaveInstanceState(savedInstanceState);
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
        switch (item.getItemId()) {
            case R.id.action_about:
                return true;
            case R.id.action_settings:
                startActivityForResult(new Intent(this, SettingsActivity.class), 0);
                return true;
        }
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
                            drinks.getmOz(),
                            drinks.getmAlc_content(),
                            Double.parseDouble(mWeight.getText().toString()),
                            mWeightUnit.getSelectedItem().toString(),
                            ((RadioButton)findViewById(mGender.getCheckedRadioButtonId())).getText().toString(),
                            Double.parseDouble(mTime.getText().toString())
                        );

        }
        return String.valueOf((double)Math.round(bac * 100d) / 100d);
    }

    public interface ClickListener {
        void onClick(View view, int position);
        void onLongClick(View view, int position);
    }
        public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

            private GestureDetector gestureDetector;
            private MainActivity.ClickListener clickListener;

            public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final MainActivity.ClickListener clickListener) {
                this.clickListener = clickListener;
                gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        return true;
                    }

                    @Override
                    public void onLongPress(MotionEvent e) {
                        View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                        if (child != null && clickListener != null) {
                            clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                        }
                    }
                });
            }
            @Override
            public boolean onInterceptTouchEvent (RecyclerView rv, MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                    clickListener.onClick(child, rv.getChildPosition(child));
                }
                return false;
            }
            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e){  }
            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {  }

        }


}
