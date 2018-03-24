package com.example.piotrek.bmi238493;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class BMIUnitTests {
    @Test
    public void for_valid_mass_and_height_in_SI_should_return_correct_value() {
        BMI bmiCounter = new BMIForKg(60, 170);
        double bmi = bmiCounter.calculate();
        assertEquals(bmi, 20.761, 0.001);
    }

    @Test (expected = IllegalArgumentException.class)
    public void for_zero_mass_and_height_in_SI_should_throw_exception() {
        BMI bmiCounter = new BMIForKg(0, 0);
        bmiCounter.calculate();
    }

    @Test (expected = IllegalArgumentException.class)
    public void for_negative_mass_and_height_in_SI_should_throw_exception() {
        BMI bmiCounter = new BMIForKg(-1, -1.9);
        bmiCounter.calculate();
    }

    @Test
    public void for_valid_mass_and_height_in_IMP_should_return_correct_value() {
        BMI bmiCounter = new BMIForImperial(132, 72);
        double bmi = bmiCounter.calculate();
        assertEquals(bmi, 17.90, 0.001);
    }

    @Test (expected = IllegalArgumentException.class)
    public void for_zero_mass_and_height_in_IMP_should_throw_exception() {
        BMI bmiCounter = new BMIForImperial(0, 0);
        bmiCounter.calculate();
    }

    @Test (expected = IllegalArgumentException.class)
    public void for_negative_mass_and_height_in_IMP_should_throw_exception() {
        BMI bmiCounter = new BMIForImperial(-1, -1.9);
        bmiCounter.calculate();
    }

    @Test (expected = IllegalArgumentException.class)
    public void for_incorrect_data_category_should_throw_exception() {
        BMI bmiCounter = new BMIForKg(0, 0);
        bmiCounter.category();
    }

    @Test
    public void for_correct_underweight_BMI_shoud_return_correct_category() {
        BMI bmiCounter = new BMIForKg(61, 183);
        assertEquals(bmiCounter.category(), BMICategory.UNDERWEIGHT);
    }

    @Test
    public void for_correct_normal_BMI_shoud_return_correct_category() {
        BMI bmiCounter = new BMIForKg(70, 183);
        assertEquals(bmiCounter.category(), BMICategory.NORMAL);
    }

    @Test
    public void for_correct_overweight_BMI_shoud_return_correct_category() {
        BMI bmiCounter = new BMIForKg(70, 160);
        assertEquals(bmiCounter.category(), BMICategory.OVERWEIGHT);
    }

    @Test
    public void for_correct_obesity_BMI_shoud_return_correct_category() {
        BMI bmiCounter = new BMIForKg(80, 160);
        assertEquals(bmiCounter.category(), BMICategory.OBESITY);
    }

    @Test
    public void for_correct_heavy_obesity_BMI_shoud_return_correct_category() {
        BMI bmiCounter = new BMIForKg(120, 160);
        assertEquals(bmiCounter.category(), BMICategory.HEAVY_OBESITY);
    }
}