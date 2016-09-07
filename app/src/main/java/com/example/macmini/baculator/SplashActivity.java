package com.example.macmini.baculator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;


public class SplashActivity extends AppCompatActivity {

    public static final String AGREEMENT= "AGREED-TO-TERMS";
    private static boolean HASAGREED = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        SharedPreferences prefs = this.getSharedPreferences(AGREEMENT, Context.MODE_PRIVATE);
        HASAGREED = prefs.getBoolean(AGREEMENT, HASAGREED);

        if (HASAGREED) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        } else {
            Intent i = new Intent(this, AgreeActivity.class);
            startActivity(i);
            finish();
        }

    }
}
