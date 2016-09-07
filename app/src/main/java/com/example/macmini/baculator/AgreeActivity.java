package com.example.macmini.baculator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;


import com.daimajia.androidanimations.library.Techniques;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AgreeActivity extends AppCompatActivity {

    @OnClick(R.id.agree_button)
    public void agreedClick(View view) {
        SharedPreferences prefs = this.getSharedPreferences(SplashActivity.AGREEMENT, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putBoolean(SplashActivity.AGREEMENT, true);
        edit.apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_agree);
        ButterKnife.bind(this);
        SharedPreferences prefs = this.getSharedPreferences(SplashActivity.AGREEMENT, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putBoolean(SplashActivity.AGREEMENT, false);
        edit.apply();


    }
}
