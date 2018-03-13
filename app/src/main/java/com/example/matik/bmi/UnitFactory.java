package com.example.matik.bmi;

/**
 * Created by matik on 10.03.2018.
 */

public abstract class UnitFactory {

    abstract boolean isMassValid(float value);
    abstract boolean isHeightValid(float value);
    public float countBMI(float mass, float height)
                    throws heightValueException, massValueException{
        if (isHeightValid(height)){
            if (isMassValid(mass)){
                //change from cm to m
                height/=100;
                return mass/(height*height);
            }
            else
                throw new massValueException();
        }
        else
            throw new heightValueException();
    }

}
