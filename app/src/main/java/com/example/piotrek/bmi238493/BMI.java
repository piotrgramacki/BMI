package com.example.piotrek.bmi238493;

/**
 * Created by Piotrek on 2018-03-10.
 */

public abstract class BMI {
    private static final double NORMAL = 18.5;
    private static final double OVERWEIGHT = 25;
    private static final double OBESITY = 30;
    private static final double H_OBESITY = 40;

    protected double mass;
    protected double height;
    protected double bmi;

    public BMI(double mass, double height) {
        this.mass = mass;
        this.height = height;
        this.bmi = -1;
    }

    protected abstract double calculate();

    protected abstract boolean dataIsValid();

    protected BMICategory category() {
        if(bmi == -1)
            calculate();

        if (bmi > H_OBESITY)
            return BMICategory.HEAVY_OBESITY;
        else if (bmi > OBESITY)
            return BMICategory.OBESITY;
        else if (bmi > OVERWEIGHT)
            return BMICategory.OVERWEIGHT;
        else if (bmi > NORMAL)
            return  BMICategory.NORMAL;
        else return BMICategory.UNDERWEIGHT;
    }
}
