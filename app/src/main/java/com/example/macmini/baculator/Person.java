package com.example.macmini.baculator;


import android.os.Parcel;
import android.os.Parcelable;


public class Person implements Parcelable {


    private String mLabel;
    private int mRadio_visibility;
    private int mText_input_visibility;
    private int mWeight_Visibility;


    public Person(String mLabel, int mRadio_visibility, int mText_input_visibility, int mWeight_Visibility){

        this.mLabel = mLabel;
        this.mRadio_visibility = mRadio_visibility;
        this.mText_input_visibility = mText_input_visibility;
        this.mWeight_Visibility = mWeight_Visibility;

    }


    public String getmLabel() {
        return mLabel;
    }

    public void setmLabel(String mLabel) {
        this.mLabel = mLabel;
    }

    public int getmRadio_visibility() {
        return mRadio_visibility;
    }

    public void setmRadio_visibility(int mRadio_visibility) {
        this.mRadio_visibility = mRadio_visibility;
    }

    public int getmText_input_visibility() {
        return mText_input_visibility;
    }

    public void setmText_input_visibility(int mText_input_visibility) {
        this.mText_input_visibility = mText_input_visibility;
    }

    public int getmWeight_Visibility() {
        return mWeight_Visibility;
    }

    public void setmWeight_Visibility(int mWeight_Visibility) {
        this.mWeight_Visibility = mWeight_Visibility;
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
        parcel.writeString(mLabel);
    }

    private Person(Parcel in) {
        mLabel = in.readString();
    }


}