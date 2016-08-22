package com.example.macmini.baculator;

import android.app.ActionBar;
import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.macmini.baculator.PersonFragments.PersonAdapter;
import com.example.macmini.baculator.PersonFragments.PersonSingleton;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnPageChange;
import me.relex.circleindicator.CircleIndicator;


public class MainActivity extends AppCompatActivity {

    public static final int GENDER = 0;
    public static final int WEIGHT = 1;
    public static final int TIME = 2;
    public static final int DONE = 3;

    // --> Floating Action Menu/Button <-- //
    @BindView(R.id.menu) FloatingActionMenu mMenu;

    @BindView(R.id.toolbar) Toolbar toolbar;

    // --> ViewPager Person Information <-- //
    @BindView(R.id.indicator) CircleIndicator indicator;
    @BindView(R.id.view_pager) ViewPager view_pager;


    @OnPageChange(R.id.view_pager)
    public void onPageSelected (int position) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Service.INPUT_METHOD_SERVICE);
        View view = view_pager.getRootView();
        switch (position) {
            case GENDER:
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                break;
            case WEIGHT:
                TextInputEditText weight = (TextInputEditText) view.findViewById(R.id.weight_input);
                weight.requestFocus();
                imm.showSoftInput(weight, InputMethodManager.SHOW_IMPLICIT);
                break;
            case TIME:
                TextInputEditText time = (TextInputEditText) view.findViewById(R.id.time_input);
                time.requestFocus();
                imm.showSoftInput(time, InputMethodManager.SHOW_IMPLICIT);
                break;
            case DONE:
                // DONE WEIGHT //
                TextView done_weight = (TextView) view.findViewById(R.id.done_weight);
                done_weight.setText(PersonSingleton.getInstance().getmWeight());
                // DONE WEIGHT MEASUREMENT //
                TextView done_weight_measure = (TextView) view.findViewById(R.id.done_weight_measure);
                done_weight_measure.setText(PersonSingleton.getInstance().getmWeightUnit());
                // DONE GENDER IMAGE //
                ImageView done_gender = (ImageView) view.findViewById(R.id.done_sex);
                if (Objects.equals(PersonSingleton.getInstance().getmSex(), "male")) {
                    done_gender.setImageDrawable(view.getResources().getDrawable(R.mipmap.ic_male, getTheme()));
                } else if (Objects.equals(PersonSingleton.getInstance().getmSex(), "female")){
                    done_gender.setImageDrawable(view.getResources().getDrawable(R.mipmap.ic_female, getTheme()));
                }
                // DONE TIME //
                TextView done_time = (TextView) view.findViewById(R.id.done_time);
                done_time.setText(String.format("Drinking for %s %s", PersonSingleton.getInstance().getmTime(), "hours"));

                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                break;
        }
    }

    // --> Miscellaneous Layouts <-- //
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.main_collapsing) CollapsingToolbarLayout mCollapse;
    @BindView(R.id.app_bar_layout) AppBarLayout mAppBarLayout;

//    @OnClick({R.id.app_bar_layout, R.id.main_collapsing, R.id.toolbar})
//    public void hideKeyboard(View view) {
//        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//    }

    @OnClick(R.id.calculate)
    public void calculate(Button mCalculate) {
        try {
            mCalculate.setText(calculateBAC());
        } catch (Exception e) {
            mCalculate.setText("Missing Information");
            e.printStackTrace();
        }
    }

    // FAB Button OnClickListeners
    @OnClick({R.id.shots, R.id.beer, R.id.wine})
    public void addDrink(FloatingActionButton fab) {
        int mImg;
        double mOz, mAlcContent;
        String mDesc;
        switch (fab.getId()) {
            case R.id.shots :
                mImg = R.drawable.ic_shots_fab;
                mOz = 1.5;
                mDesc = getResources().getString(R.string.shots_desc);
                mAlcContent = 40;
                break;
            case R.id.wine :
                mImg = R.drawable.ic_wine_fab;
                mOz = 6;
                mDesc = getResources().getString(R.string.wine_desc);
                mAlcContent = 12;
                break;
            default :
                mImg = R.drawable.ic_beer_fab;
                mOz = 12;
                mDesc = getResources().getString(R.string.beer_desc);
                mAlcContent = 5;
                break;
        }
        Drinks drink = new Drinks(getResources().getDrawable(mImg, getTheme()),
                1, mOz, mDesc, mAlcContent, View.GONE);
        mAdapter.notifyItemInserted(mAdapter.getItemCount());
        drinkList.add(drink);
        mMenu.close(true);
    }


    private ArrayList<Drinks> drinkList = new ArrayList<>();
    private DrinkAdapter mAdapter;

    static final String DRINK_LIST = "drinkList";


    private FragmentPagerAdapter adapterViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            drinkList = savedInstanceState.getParcelableArrayList(DRINK_LIST);
        }
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);


        // --> RecyclerView Stuff Here <-- //
        mAdapter = new DrinkAdapter(drinkList);
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);

        // --> Person Information ViewPager <-- //
        adapterViewPager = new PersonAdapter(getSupportFragmentManager());
        view_pager.setAdapter(adapterViewPager);
        view_pager.setOffscreenPageLimit(3);
        view_pager.setClipToPadding(false);
        view_pager.setPageMargin(24);
        indicator.setViewPager(view_pager);


        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Service.INPUT_METHOD_SERVICE);
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        imm.showSoftInput(view_pager,0);
                        break;
                    case 2:
                        imm.showSoftInput(view_pager,0);
                        break;
                    case 3:
                        View view = view_pager.getRootView();
                        TextView done_weight = (TextView) view.findViewById(R.id.done_weight);
                        TextView done_weight_measure = (TextView) view.findViewById(R.id.done_weight_measure);
                        ImageView done_gender = (ImageView) view.findViewById(R.id.done_sex);
                        TextView done_time = (TextView) view.findViewById(R.id.done_time);

                        done_weight.setText(PersonSingleton.getInstance().getmWeight());
                        done_weight_measure.setText(PersonSingleton.getInstance().getmWeightUnit());

                        if (Objects.equals(PersonSingleton.getInstance().getmSex(), "male")) {
                            done_gender.setImageDrawable(view.getResources().getDrawable(R.mipmap.ic_male, getTheme()));
                        } else {
                            done_gender.setImageDrawable(view.getResources().getDrawable(R.mipmap.ic_female, getTheme()));
                        }
                        done_time.setText(String.format("Drinking for %s %s", PersonSingleton.getInstance().getmTime(), "hours"));

                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


//        // Checks for AppBar close/open event
//        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
//                offset = Math.abs(offset); //makes positive offset values - easier to work with
//                if (offset <= appBarLayout.getTotalScrollRange()/2) {
////                    mMenu.hideMenu(true);
//                    mCard.setVisibility(View.GONE);
//                }
//                else if (offset > appBarLayout.getTotalScrollRange()/2) {
////                    mMenu.showMenu(true);
//                    mCard.setVisibility(View.VISIBLE);
//                }
//            }
//        });

        // --> Swipe to Dismiss Drink Items <-- //
        ItemTouchHelper swipeToDismissTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

                return false;
            }
            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                drinkList.get(viewHolder.getAdapterPosition()).setmVisibility(View.VISIBLE);
                final Drinks removedDrink = drinkList.get(viewHolder.getAdapterPosition());
                final Snackbar snackbar = Snackbar
                        .make(recyclerView, "DRINK REMOVED", Snackbar.LENGTH_LONG)
                        .setAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                drinkList.add(viewHolder.getAdapterPosition(), removedDrink);
                                drinkList.get(viewHolder.getAdapterPosition()).setmVisibility(View.GONE);
                                mAdapter.notifyItemInserted(viewHolder.getAdapterPosition());
                            }
                        });
                snackbar.setCallback(new Snackbar.Callback() {
                   @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                       try {
                           drinkList.remove(viewHolder.getAdapterPosition());
                           mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                       } catch(Exception e) {
                           final Snackbar errorSnack = Snackbar
                                   .make(recyclerView, "DELETING TOO FAST!!!", Snackbar.LENGTH_SHORT);
                           errorSnack.show();
                       }
                   }
                    @Override
                    public void onShown(Snackbar snackbar) {

                    }
                });
                snackbar.show();
            }
        });
        swipeToDismissTouchHelper.attachToRecyclerView(recyclerView);

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
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelableArrayList(DRINK_LIST, drinkList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                return true;
            case R.id.action_settings:
                startActivityForResult(new Intent(this, SettingsActivity.class), 0);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private String calculateBAC() throws Exception {

        PersonSingleton person = PersonSingleton.getInstance();

        Toast.makeText(this, "Sex: " + person.getmSex() +
                            " Weight: " + person.getmWeight() +
                            " Unit: " + person.getmWeightUnit() +
                            " Time: " + person.getmTime(), Toast.LENGTH_LONG).show();



        Calculate calc = new Calculate();
        double bac = 0;


        for(int i=0 ; i<drinkList.size() ; i++){
            Drinks drinks = drinkList.get(i);

            int qty = drinks.getmQty();
            double oz = drinks.getmOz();
            double alc_content = drinks.getmAlc_content();
            bac += calc.getBAC(
                    oz*qty,
                    alc_content,
                    Double.parseDouble(person.getmWeight()),
                    person.getmWeightUnit(),
                    person.getmSex(),
                    Double.parseDouble(person.getmTime()));

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
