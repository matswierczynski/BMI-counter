package com.example.matik.bmi;

/**
 * Created by matik on 10.03.2018.
 */

public final class MetricUnitFactory extends UnitFactory {
    private static MetricUnitFactory _instance=null;

    protected MetricUnitFactory(){};

    public static MetricUnitFactory Instance() {
        if (_instance == null)
            _instance = new MetricUnitFactory();
        return _instance;
    }

    @Override
    boolean isMassValid(float value) {
        return ( value> 45 && value<= 150);
    }

    @Override
    boolean isHeightValid(float value) {
        return (value > 140 && value <= 195);
    }


}
