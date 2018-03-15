package com.example.piotrek.bmi238493;

/**
 * Created by Piotrek on 2018-03-10.
 */

public class BMIForKg extends BMI {
    public BMIForKg(double mass, double height) {
        super(mass, height);
        this.height /= 100;
    }

    @Override
    protected double calculate() {
        if (!dataIsValid()) throw new IllegalArgumentException();

        bmi = mass / (height * height);
        return bmi;
    }

    @Override
    protected boolean dataIsValid() {
        return mass > 0 && height > 0;
    }
}
