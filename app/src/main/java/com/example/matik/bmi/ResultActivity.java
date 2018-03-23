package com.example.matik.bmi;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    private final static double LOWER_BOUNDARY_OF_BALANCED_BMI = 18;
    private final static double UPPER_BOUNDARY_OF_BALANCED_BMI = 24;
    private ConstraintLayout constraintLayout;
    private final static double DEFAULT_VALUE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        constraintLayout = findViewById(R.id.resultLayout);
        showResult();
    }

   @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    private void showResult(){
        double bmiResult   = getIntent().getDoubleExtra(Intent.EXTRA_TEXT,DEFAULT_VALUE);
        if (bmiResult < LOWER_BOUNDARY_OF_BALANCED_BMI)
            constraintLayout.setBackgroundResource(
                    new BmiResultInterpreter(BmiResultCategory.UNDERWEIGHT).interpretBmiResult());
        else if (bmiResult >= LOWER_BOUNDARY_OF_BALANCED_BMI &&
                 bmiResult <=UPPER_BOUNDARY_OF_BALANCED_BMI)
            constraintLayout.setBackgroundResource(
                    new BmiResultInterpreter(BmiResultCategory.BALANCEDWEIGHT).
                                                                interpretBmiResult());
        else if (bmiResult > UPPER_BOUNDARY_OF_BALANCED_BMI)
            constraintLayout.setBackgroundResource(
                    new BmiResultInterpreter(BmiResultCategory.OVERWEIGHT).interpretBmiResult()
            );
        ((TextView)findViewById(R.id.BMITextView)).setText(String.format("%2.2f",bmiResult));
    }


}
