package com.example.macmini.baculator.PersonFragments;


import android.os.Parcel;
import android.os.Parcelable;


public class Person implements Parcelable {

    private String mSex;
    private String mWeight;
    private String mWeightUnit;
    private String mTime;


    public Person(String mSex, String mWeight, String mWeightUnit, String mTime){

        this.mSex = mSex;
        this.mWeight = mWeight;
        this.mWeightUnit = mWeightUnit;
        this.mTime = mTime;

    }

    public String getmSex() {
        return mSex;
    }

    public void setmSex(String mSex) {
        this.mSex = mSex;
    }

    public String getmWeight() {
        return mWeight;
    }

    public void setmWeight(String mWeight) {
        this.mWeight = mWeight;
    }

    public String getmWeightUnit() {
        return mWeightUnit;
    }

    public void setmWeightUnit(String mWeightUnit) {
        this.mWeightUnit = mWeightUnit;
    }

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }




    //******* PARCELABLE STUFF BELOW *******//

    public static final Parcelable.Creator<Person> CREATOR
            = new Parcelable.Creator<Person>() {
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mTime);
    }

    private Person(Parcel in) {
        mTime = in.readString();
    }



}