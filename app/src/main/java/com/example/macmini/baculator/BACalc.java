package com.example.macmini.baculator;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by MacMini on 6/4/16.
 */

//BAC % = ([Alcohol consumed in grams / (Body weight in grams x r)] x 100) || BAC as a percentage – (elapsed time in hours x 0. 015)

public class BACalc {

    private final double male = 0.68;
    private final double female = 0.55;
    private final double ml = 0.0338140225589;
    private final double gram = 453.592;

    public double getBAC (double oz, double abv, double weight, String weightUnit, String gender, double hours){

        double genderVal = male;

        if (gender.equals("female")){
            genderVal = female;
        }

        return ((getAlcoholContentML(oz, abv) / ((getBodyWeightGrams(weight, weightUnit) * genderVal))) * 100) - (hours * 0.015);
    }

    private double getBodyWeightGrams (double weight, String weightUnit){

        switch (weightUnit){
            case "pounds" :
                return weight * gram;
            case "kilograms" :
                return weight/1000;
        }
        return 0;
    }

    private double getAlcoholContentML (double oz, double abv){

        return 8 * (oz * ml) * abv;
    }

}
