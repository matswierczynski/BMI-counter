package com.example.matik.bmi;


/**
 * Created by Mateusz Swierczynski
 */

class BmiResultInterpreter {
    private BmiResultCategory bmiResultCategory;
    BmiResultInterpreter(BmiResultCategory bmiResultCategory){
        this.bmiResultCategory = bmiResultCategory;
    }

    int interpretBmiResult(){
        switch (bmiResultCategory){
            case UNDERWEIGHT:{
                //TODO: Additional activities when the bmi result is underweight
                return R.color.colorUnderweight;
            }
            case BALANCEDWEIGHT:{
                //TODO: Additional activities when the bmi result is balanced
                return R.color.colorBalancedWeight;
            }
            case OVERWEIGHT:{
                //TODO: Additional activities when the bmi result is overweight
                return R.color.colorOverweight;
            }

            default:
                return R.color.colorPrimary;
        }
    }
}
