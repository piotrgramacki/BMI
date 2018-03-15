package com.example.piotrek.bmi238493;

/**
 * Created by Piotrek on 2018-03-10.
 */

public class BMIForImperial extends BMI {
    public BMIForImperial(double mass, double height) {
        super(mass, height);
    }

    @Override
    protected double calculate() {
        if (!dataIsValid()) throw new IllegalArgumentException();

        bmi = mass / (height * height) * 703;
        return bmi;
    }

    @Override
    protected boolean dataIsValid() {
        return mass > 0 && height > 0;
    }
}
