package com.example.matik.bmi;

/**
 * Created by Mateusz Swierczynski
 */

final class MetricUnitFactory extends UnitFactory {
    private static final int LOWER_BOUNDARY_OF_CORRECT_MASS = 45;
    private static final int UPPER_BOUNDARY_OF_CORRECT_MASS = 150;
    private static final int LOWER_BOUNDARY_OF_CORRECT_HEIGHT = 140;
    private static final int UPPER_BOUNDARY_OF_CORRECT_HEIGHT = 195;
    private static MetricUnitFactory _instance=null;

    private MetricUnitFactory(){}

    static MetricUnitFactory Instance() {
        if (_instance == null)
            _instance = new MetricUnitFactory();
        return _instance;
    }

    @Override
    boolean isMassValid(int value) {
        return ( value> LOWER_BOUNDARY_OF_CORRECT_MASS && value<= UPPER_BOUNDARY_OF_CORRECT_MASS);
    }

    @Override
    boolean isHeightValid(int value) {
        return (value > LOWER_BOUNDARY_OF_CORRECT_HEIGHT &&
                value <= UPPER_BOUNDARY_OF_CORRECT_HEIGHT);
    }

}
