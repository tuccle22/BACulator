package com.example.macmini.baculator;

import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * Created by MacMini on 6/3/16.
 */
public class Drinks {

    private String mDrink;
    private int mQty;
    private double mAlc_content;
//    private View mImg_view;
    private Drawable mImg;


    public Drinks(){ }

    public Drinks(String mDrink, int mQty, double mAlc_content, Drawable mImg){

        this.mDrink = mDrink;
        this.mQty = mQty;
        this.mAlc_content = mAlc_content;
//        this.mImg_view = mImg_view;
        this.mImg = mImg;

    }

    public String getmDrink() {
        return mDrink;
    }

    public void setmDrink(String mDrink) {
        this.mDrink = mDrink;
    }

    public double getmAlc_content() {
        return mAlc_content;
    }

    public void setmAlc_content(double mAlc_content) {
        this.mAlc_content = mAlc_content;
    }

    public int getmQty() {
        return mQty;
    }

    public void setmQty(int mQty) {
        this.mQty = mQty;
    }


//    public View getmImg_view() {
//        return mImg_view;
//    }
//
//    public void setmImg_view(View mImg_view) {
//        this.mImg_view = mImg_view;
//    }

    public Drawable getmImg() {
        return mImg;
    }

    public void setmImg(Drawable mImg) {
        this.mImg = mImg;
    }
}