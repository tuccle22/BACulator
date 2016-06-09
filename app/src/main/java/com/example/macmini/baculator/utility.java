package com.example.macmini.baculator;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by MacMini on 6/4/16.
 */
public class Utility {

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static void calculateBAC () {


    }

}
