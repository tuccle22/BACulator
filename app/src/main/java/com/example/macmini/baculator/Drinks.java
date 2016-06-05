package com.example.macmini.baculator;

/**
 * Created by MacMini on 6/3/16.
 */
public class Drinks {

    private String mDrink;
    private int mQty;
    private double mAlc_content;


    public Drinks(){  }

    public Drinks(String mDrink, int mQty, double mAlc_content){

        this.mDrink = mDrink;
        this.mQty = mQty;
        this.mAlc_content = mAlc_content;
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


}