package com.example.matik.bmi;

/**
 * Created by Mateusz Swierczynski
 */

public abstract class UnitFactory {

    private final int CENTIMETERS_TO_METERS_DIVIDER = 100;
    abstract  boolean isMassValid(int value);
    abstract  boolean isHeightValid(int value);

    boolean isStringEmpty(final String s) {
        return s == null || s.trim().isEmpty();
    }
    protected double countBMI(int mass, int height)
                    throws HeightValueException, MassValueException {
        if (isHeightValid(height)){
            if (isMassValid(mass)){
                double heightInMeters = (double)height/CENTIMETERS_TO_METERS_DIVIDER;
                return (double)mass/(heightInMeters*heightInMeters);
            }
            else
                throw new MassValueException();
        }
        else
            throw new HeightValueException();
    }

}
