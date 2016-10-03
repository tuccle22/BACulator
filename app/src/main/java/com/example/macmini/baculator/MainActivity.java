package com.example.macmini.baculator;

import android.app.ActionBar;
import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
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
    public static final int WEIGHT_SPINNGER = 4;

    // --> Floating Action Menu/Button <-- //
    @BindView(R.id.menu) FloatingActionMenu mMenu;

    @BindView(R.id.card) CardView mCard;
    @BindView(R.id.toolbar) Toolbar toolbar;

    // --> ViewPager Person Information <-- //
    @BindView(R.id.indicator) CircleIndicator indicator;
    @BindView(R.id.view_pager) ViewPager view_pager;

    @BindView(R.id.clear) Button clear;
    @BindView(R.id.row_container) LinearLayout linear_layout;


    @BindView(R.id.fab_next) android.support.design.widget.FloatingActionButton fab_next;

    @OnClick(R.id.fab_next)
    public void onNextClick (android.support.design.widget.FloatingActionButton fab) {
        if (view_pager.getCurrentItem() != TIME) {
            fabNext(Techniques.SlideOutDown, fab, 150);
        } else {
            fabNext(Techniques.RollOut, fab, 250);
        }
    }

    @OnClick(R.id.clear)
    public void clearClick (final Button clearButton) {

        if (drinkList.size() > 0) {
            final View viewItem = recyclerView.getLayoutManager().findViewByPosition(DrinkAdapter.drinkList.size() - 1);
            YoYo.with(Techniques.SlideOutLeft).duration(150).interpolate(new LinearInterpolator()).withListener(new com.nineoldandroids.animation.Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(com.nineoldandroids.animation.Animator animation) {  }

                @Override
                public void onAnimationEnd(com.nineoldandroids.animation.Animator animation) {
                    clearClick(clearButton);
                }

                @Override
                public void onAnimationCancel(com.nineoldandroids.animation.Animator animation) {  }

                @Override
                public void onAnimationRepeat(com.nineoldandroids.animation.Animator animation) {  }
            }).playOn(viewItem);
            try {
                drinkList.remove(DrinkAdapter.drinkList.size() - 1);
            } catch (Exception e) {
                Toast.makeText(this, "Whoops! This app is drunk.", Toast.LENGTH_SHORT).show();
            }
        } else {
            mAdapter.notifyDataSetChanged();
            recyclerView.removeAllViews();
            YoYo.with(Techniques.SlideOutLeft).playOn(clearButton);
            clearButton.setVisibility(View.GONE);
        }
    }

    @OnPageChange(R.id.view_pager)
    public void onPageSelected (int position) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Service.INPUT_METHOD_SERVICE);
        View view = view_pager.getRootView();
        switch (position) {
            case GENDER:
                mAppBarLayout.setExpanded(true, true);
                mCard.setVisibility(View.GONE);
                fab_next.setVisibility(View.VISIBLE);
                mMenu.setVisibility(View.GONE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                break;
            case WEIGHT:
                mCard.setVisibility(View.GONE);
                YoYo.with(Techniques.SlideInUp).duration(150).playOn(fab_next);
                fab_next.setVisibility(View.VISIBLE);
                mMenu.setVisibility(View.GONE);
                TextInputEditText weight = (TextInputEditText) view.findViewById(R.id.weight_input);
                weight.requestFocus();
                weight.setSelectAllOnFocus(true);
                imm.showSoftInput(weight, InputMethodManager.SHOW_IMPLICIT);
                break;
            case TIME:
                mCard.setVisibility(View.GONE);
                mAppBarLayout.setExpanded(true, true);
                YoYo.with(Techniques.SlideInUp).duration(150).playOn(fab_next);
                fab_next.setVisibility(View.VISIBLE);
                mCard.setVisibility(View.GONE);
                mMenu.close(true);
                mMenu.setVisibility(View.GONE);
                TextInputEditText time = (TextInputEditText) view.findViewById(R.id.time_input);
                time.requestFocus();
                time.setSelectAllOnFocus(true);
                imm.showSoftInput(time, InputMethodManager.SHOW_IMPLICIT);
                break;
            case DONE:
                //TODO figure out if possible to attach done to toolbar.
                fab_next.setVisibility(View.GONE);
                mMenu.setVisibility(View.VISIBLE);
                addFab(Techniques.RollIn, mMenu);
                if (mAdapter.getItemCount() > 0) {
                    mCard.setVisibility(View.VISIBLE);
                    YoYo.with(Techniques.SlideInUp).playOn(mCard);
                    YoYo.with(Techniques.FadeInDown).playOn(clear);
                }
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

    @OnClick(R.id.calculate)
    public void calculate(Button mCalculate) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mCalculate.getWindowToken(), 0);

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
                1, mOz, mDesc, mAlcContent);
        mAdapter.notifyItemInserted(mAdapter.getItemCount());
        drinkList.add(drink);
        if (mCard.getVisibility() != View.VISIBLE) {
            mCard.setVisibility(View.VISIBLE);
            YoYo.with(Techniques.SlideInUp).playOn(mCard);
        }
        clear.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeInDown).playOn(clear);
        mMenu.close(true);
    }

    private ArrayList<Drinks> drinkList = new ArrayList<>();
    public static DrinkAdapter mAdapter;

    static final String DRINK_LIST = "drinkList";


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
        FragmentPagerAdapter adapterViewPager = new PersonAdapter(getSupportFragmentManager());
        view_pager.setAdapter(adapterViewPager);
        view_pager.setOffscreenPageLimit(3);
        view_pager.setClipToPadding(false);
        view_pager.setPageMargin(24);
        indicator.setViewPager(view_pager);

        SharedPreferences prefs = this.getSharedPreferences("person_info", Context.MODE_PRIVATE);
        PersonSingleton.getInstance().setmSex(prefs.getString(Integer.toString(GENDER), null));
        PersonSingleton.getInstance().setmWeight(prefs.getString(Integer.toString(WEIGHT), null));
        PersonSingleton.getInstance().setmWeightUnit(prefs.getString(Integer.toString(WEIGHT_SPINNGER), null));
        PersonSingleton.getInstance().setmTime(prefs.getString(Integer.toString(TIME), null));

        // --> Swipe to Dismiss Drink Items <-- //
        ItemTouchHelper swipeToDismissTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

                return false;
            }
            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                final Drinks removedDrink = drinkList.get(viewHolder.getAdapterPosition());
                final Snackbar snackbar = Snackbar
                        .make(recyclerView, "DRINK REMOVED", Snackbar.LENGTH_LONG)
                        .setAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                drinkList.add(viewHolder.getAdapterPosition(), removedDrink);
                                mAdapter.notifyItemInserted(viewHolder.getAdapterPosition());
                            }
                        });
                snackbar.setCallback(new Snackbar.Callback() {
                   @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                       try {
                           drinkList.remove(viewHolder.getAdapterPosition());
                           mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                           YoYo.with(Techniques.FadeInUp).duration(200).playOn(clear);
                           if (drinkList.isEmpty()) {
                               YoYo.with(Techniques.FadeOut).playOn(clear);
                               clear.setVisibility(View.INVISIBLE);
                           }
                       } catch(Exception e) {
                           final Snackbar errorSnack = Snackbar
                                   .make(recyclerView, "DELETING TOO FAST!! RESETTING!", Snackbar.LENGTH_SHORT);
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

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                Button button = new Button(MainActivity.this);
                button.setText(getText(R.string.done));
                button.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                button.setTextColor(getResources().getColor(android.R.color.white));
                button.setGravity(Gravity.END);
                lp.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 72, getResources().getDisplayMetrics());
                lp.setMarginEnd((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));

                dialog.setContentView(R.layout.list_item);
                dialog.addContentView(button, lp);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

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
                            try {
                                Drinks drinks = drinkList.get(pos);
                                drinks.setmQty(Integer.parseInt(qty.getText().toString()));
                                drinks.setmAlc_content(Double.parseDouble(abv.getText().toString()));
                                drinks.setmOz(Double.parseDouble(oz.getText().toString()));
                                mAdapter.notifyDataSetChanged();
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Not a valid value, you are drunk.", Toast.LENGTH_SHORT).show();
                            }
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
                    person.getmSex()
            );

        }
        bac = bac - (.015 * Double.parseDouble(person.getmTime()));
        return String.valueOf((double)Math.round(bac * 100d) / 100d);
    }

    private interface ClickListener {
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

    private void addFab(final Techniques technique, final View view) {
        mAppBarLayout.setExpanded(false, true);
        YoYo.with(technique).duration(250).interpolate(new LinearInterpolator()).withListener(new com.nineoldandroids.animation.Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(com.nineoldandroids.animation.Animator animation) {  }

            @Override
            public void onAnimationEnd(com.nineoldandroids.animation.Animator animation) {
                mMenu.open(true);
            }

            @Override
            public void onAnimationCancel(com.nineoldandroids.animation.Animator animation) {  }
            @Override
            public void onAnimationRepeat(com.nineoldandroids.animation.Animator animation) {  }
        }).playOn(view);
    }

    private void fabNext(final Techniques technique, android.support.design.widget.FloatingActionButton fab, int duration) {
        YoYo.with(technique).duration(duration).interpolate(new LinearInterpolator()).withListener(new com.nineoldandroids.animation.Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(com.nineoldandroids.animation.Animator animation) {
            }

            @Override
            public void onAnimationEnd(com.nineoldandroids.animation.Animator animation) {
                view_pager.setCurrentItem(view_pager.getCurrentItem() + 1);
            }

            @Override
            public void onAnimationCancel(com.nineoldandroids.animation.Animator animation) {
            }

            @Override
            public void onAnimationRepeat(com.nineoldandroids.animation.Animator animation) {
            }
        }).playOn(fab);
    }

}
