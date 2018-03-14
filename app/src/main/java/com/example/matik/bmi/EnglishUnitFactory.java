package com.example.matik.bmi;

/**
 * Created by Mateusz Swierczynski
 */

public final class EnglishUnitFactory extends UnitFactory {

    private static EnglishUnitFactory _instance=null;
    private static float converter = 0.0703f;

    private EnglishUnitFactory(){}

    static EnglishUnitFactory Instance() {
        if (_instance == null)
            _instance = new EnglishUnitFactory();
        return _instance;
    }

    @Override
    boolean isMassValid(float value) {
        return ( value >= 100 && value <=215);
    }

    @Override
    boolean isHeightValid(float value) {
        return (value > 50 && value <= 80);
    }

    @Override
    public float countBMI(float mass, float height)
            throws heightValueException, massValueException {
        float result = super.countBMI(mass, height);
        return result*converter;
    }
}
