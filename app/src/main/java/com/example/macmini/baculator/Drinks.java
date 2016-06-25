package com.example.macmini.baculator;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

/**
 * Created by MacMini on 6/3/16.
 */
public class Drinks implements Parcelable {

    private String mDrink;
    private int mQty;
    private double mAlc_content;
    private double mOz;
    private Drawable mImg;

    public Drinks(Drawable mImg, int mQty, double mOz, String mDrink, double mAlc_content){

        this.mImg = mImg;
        this.mQty = mQty;
        this.mDrink = mDrink;
        this.mOz = mOz;
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

    public Drawable getmImg() {
        return mImg;
    }

    public void setmImg(Drawable mImg) {
        this.mImg = mImg;
    }

    public double getmOz() {
        return mOz;
    }

    public void setmOz(double mOz) {
        this.mOz = mOz;
    }

    public int describeContents() {
        return 0;
    }


    //Implements parceable for screen rotation.  I'm not
    //sure why we would use these methods.
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mDrink);
    }

    public static final Parcelable.Creator<Drinks> CREATOR
            = new Parcelable.Creator<Drinks>() {
        public Drinks createFromParcel(Parcel in) {
            return new Drinks(in);
        }
        public Drinks[] newArray(int size) {
            return new Drinks[size];
        }
    };

    private Drinks(Parcel in) {
        mDrink = in.readString();
    }
}