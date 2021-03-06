package com.example.macmini.baculator;


/*********************    W-I-D-M-A-R-K---F-O-R-M-U-L-A     ***********************
 *                                                                               **
 *          (     Alcohol in grams     )                                         **
 *  BAC % = ( ------------------------ ) * 100 - (.015 * elapsed time in hours)  **
 *          (  Weight in lbs * gender  )                                         **
 *                                                                               **
/**********************************************************************************/

public class Calculate {

    private final double MALE = 0.73;
    private final double FEMALE = 0.66;

    public double getBAC (double oz, double abv, double weight, String weight_unit, String gender) {

        double alcohol_val = getAlcoholContent(oz, abv);
        double weight_val = getBodyWeightGrams(weight, weight_unit);
        double gender_val = getGender(gender);
        double blood_alcohol_content = alcohol_val / (weight_val * gender_val);

        return blood_alcohol_content;
    }

    private double getBodyWeightGrams (double weight, String weight_unit) {
        return weight_unit.equals("kilograms") ? weight * 0.453592 : weight;
    }

    private double getAlcoholContent (double oz, double abv) {
        return oz * abv/100 * 5.14;
    }

    private double getGender (String gender) {
        return gender.equals("female") ? FEMALE : MALE;
    }

}
