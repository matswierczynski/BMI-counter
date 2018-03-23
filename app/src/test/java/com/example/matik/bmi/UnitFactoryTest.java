package com.example.matik.bmi;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created by Mateusz Swierczynski
 */
@RunWith(Parameterized.class)
public class UnitFactoryTest {

    private int currentMass;
    private int currentHeight;
    private double expected;
    private static MetricUnitFactory metricUnitFactory;


    @Parameterized.Parameters
    public static Collection<Object[]> data(){
        return Arrays.asList(new Object[][] {
                {46, 141, 23.14}, {60, 150, 26.67}, {70, 150, 31.11}, {70, 160, 27.34},
                {80, 160, 31.25}, {70, 170, 24.22}, {80, 170, 27.68}, {80, 180, 24.69},
                {90, 180, 27.78}, {100, 180, 30.86}, {120, 160, 46.88 }, {140, 190, 38.78 },
                {150, 195, 39.45}
        });
    }

    public UnitFactoryTest(int mass, int height, double exp){
        currentMass=mass;
        currentHeight=height;
        expected=exp;
    }


    @BeforeClass
    public static void onlyOnce(){
        metricUnitFactory = MetricUnitFactory.Instance();
    }


    @Test
    public void countBMI() throws Exception {
        assertEquals(expected,metricUnitFactory.countBMI(currentMass, currentHeight),0.01);
    }

    @Test(expected = HeightValueException.class)
    public void HeightOutOfRangecountBMI() throws Exception {
        assertEquals(-2000, metricUnitFactory.countBMI(-10, -20), 0.01);
        assertEquals(2000,  metricUnitFactory.countBMI(0,   0),   0.01);
        assertEquals(22.96, metricUnitFactory.countBMI(45,  140), 0.01);
        assertEquals(40,    metricUnitFactory.countBMI(160, 200), 0.01);
    }

    @Test(expected = MassValueException.class)
    public void MassOutOfRangecountBMI() throws Exception {
        assertEquals(-2000, metricUnitFactory.countBMI(-10, 150), 0.01);
        assertEquals(2000,  metricUnitFactory.countBMI(0,   150), 0.01);
        assertEquals(22.96, metricUnitFactory.countBMI(45,  150), 0.01);
        assertEquals(40,    metricUnitFactory.countBMI(151, 150), 0.01);
    }

}