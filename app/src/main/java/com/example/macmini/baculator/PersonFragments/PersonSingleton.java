package com.example.macmini.baculator.PersonFragments;


public class PersonSingleton {

    private String mSex;
    private String mWeight;
    private String mWeightUnit;
    private String mTime;

    private static PersonSingleton ourInstance = new PersonSingleton();

    public static PersonSingleton getInstance() {
        return ourInstance;
    }

    private PersonSingleton() { }

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

}
