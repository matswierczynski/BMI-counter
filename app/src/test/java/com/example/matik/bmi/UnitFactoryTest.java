package com.example.matik.bmi;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created by Mateusz Swierczynski
 */
@RunWith(Parameterized.class)
public class UnitFactoryTest {

    private float currentMass;
    private float currentHeight;
    private float expected;
    private static MetricUnitFactory metricUnitFactory;


    @Parameterized.Parameters
    public static Collection<Object[]> data(){
        return Arrays.asList(new Object[][] {
                {46f, 141f, 23.14f}, {60f, 150f, 26.67f}, {70f, 150f, 31.11f}, {70f, 160f, 27.34f},
                {80f, 160f, 31.25f}, {70f, 170f, 24.22f}, {80f, 170f, 27.68f}, {80f, 180f, 24.69f},
                {90f, 180f, 27.78f}, {100f, 180f, 30.86f}, {120f, 160f, 46.88f }, {140, 190, 38.78f },
                {150, 195f, 39.45f}
        });
    }

    public UnitFactoryTest(float mass, float height, float exp){
        currentMass=mass;
        currentHeight=height;
        expected=exp;
    }


    @BeforeClass
    public static void onlyOnce(){
        metricUnitFactory = new MetricUnitFactory();
    }


    @Test
    public void countBMI() throws Exception {
        assertEquals(expected,metricUnitFactory.countBMI(currentMass, currentHeight),0.01f);
    }

    @Test(expected = heightValueException.class)
    public void HeightOutOfRangecountBMI() throws Exception {
        assertEquals(-2000, metricUnitFactory.countBMI(-10, -20), 0.01);
        assertEquals(2000,  metricUnitFactory.countBMI(0,   0),   0.01);
        assertEquals(22.96, metricUnitFactory.countBMI(45,  140), 0.01);
        assertEquals(40,    metricUnitFactory.countBMI(160, 200), 0.01);
    }

    @Test(expected = massValueException.class)
    public void MassOutOfRangecountBMI() throws Exception {
        assertEquals(-2000, metricUnitFactory.countBMI(-10, 150), 0.01);
        assertEquals(2000,  metricUnitFactory.countBMI(0,   150), 0.01);
        assertEquals(22.96, metricUnitFactory.countBMI(45,  150), 0.01);
        assertEquals(40,    metricUnitFactory.countBMI(151, 150), 0.01);
    }

}