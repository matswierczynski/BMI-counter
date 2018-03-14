package com.example.matik.bmi;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ViewResult();
    }

   @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    private void ViewResult(){
        Float result = getIntent().getFloatExtra(MainActivity.RESULT_EXTRA_MESSAGE, 0.00f);
        TextView textView = findViewById(R.id.BMITextView);
        textView.setText(String.format("%2.2f",result));
        setBackColor(result);
    }

    private void setBackColor(float result){
        ConstraintLayout constraintLayout = findViewById(R.id.resultLayout);
        if (result < 18)
            constraintLayout.setBackgroundColor(Color.rgb(29, 202, 255));
        else {
            if (result <= 24)
                constraintLayout.setBackgroundColor(Color.rgb(	164, 198, 57));
            else
                constraintLayout.setBackgroundColor(Color.rgb(255,99,71));
            }
        }
}
