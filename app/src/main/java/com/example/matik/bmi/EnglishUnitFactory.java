package com.example.matik.bmi;

/**
 * Created by Mateusz Swierczynski
 */

public final class EnglishUnitFactory extends UnitFactory {
    private static final int LOWER_BOUNDARY_OF_CORRECT_MASS = 100;
    private static final int UPPER_BOUNDARY_OF_CORRECT_MASS = 215;
    private static final int LOWER_BOUNDARY_OF_CORRECT_HEIGHT = 50;
    private static final int UPPER_BOUNDARY_OF_CORRECT_HEIGHT = 80;
    private static final double ENGLISH_UNITS_COEFFICIENT = 0.0703;
    private static EnglishUnitFactory _instance=null;

    private EnglishUnitFactory(){}

    static EnglishUnitFactory Instance() {
        if (_instance == null)
            _instance = new EnglishUnitFactory();
        return _instance;
    }

    @Override
    boolean isMassValid(int value) {
        return ( value >= LOWER_BOUNDARY_OF_CORRECT_MASS &&
                 value <= UPPER_BOUNDARY_OF_CORRECT_MASS);
    }

    @Override
    boolean isHeightValid(int value) {
        return (value > LOWER_BOUNDARY_OF_CORRECT_HEIGHT &&
                value <= UPPER_BOUNDARY_OF_CORRECT_HEIGHT);
    }

    @Override
    public double countBMI(int mass, int height)
            throws HeightValueException, MassValueException {
        double result = super.countBMI(mass, height);
        return result*ENGLISH_UNITS_COEFFICIENT;
    }
}
