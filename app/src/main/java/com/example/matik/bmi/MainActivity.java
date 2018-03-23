package com.example.matik.bmi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int    NUMBER_OF_UNITS = 2;
    private static boolean IS_FIRST_ACTIVITY_CREATION = false;
    final static String MASS_VALUE_KEY=
            "com.example.matik.bmi.ResultActivity.Mass_Value.Extra_Message";
    final static String HEIGHT_VALUE_KEY=
            "com.example.matik.bmiResultActivity.Height_Value.Extra_Message";
    private static final int DEFAULT_VALUE = 0;
    private static String massCurrentValue;
    private static String heightCurrentValue;
    private UnitFactory [] unitFactory;
    private UnitFactory currentUnits;
    private int unitFlag;

    public static void startAboutAuthor(Context context) {
        Intent starter = new Intent(context, AboutAuthorActivity.class);
        context.startActivity(starter);
    }

    public static void startResultActivity(Context context, double calculatedBMI) {
        Intent starter = new Intent(context, ResultActivity.class);
        starter.putExtra(Intent.EXTRA_TEXT, calculatedBMI);
        context.startActivity(starter);
    }

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

        initUnitFactory();

       final Button calculateButton = findViewById(R.id.countButton);
       calculateButton.setOnClickListener(onClickCalculateButtonListener);

       final Button unitButton = findViewById(R.id.changeUnitButton);
       unitButton.setOnClickListener(onClickUnitsButtonListener);

       if (!IS_FIRST_ACTIVITY_CREATION) {
           setFieldsFromSharedPreferences();
           IS_FIRST_ACTIVITY_CREATION = true;
       }

       if (savedInstanceState != null) {
           ((EditText)findViewById(R.id.massEditText)).setText(massCurrentValue);
           ((EditText)findViewById(R.id.heightEditText)).setText(heightCurrentValue);
       }
   }

   private View.OnClickListener onClickUnitsButtonListener = new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           changeUnits();
       }
   };

    private View.OnClickListener onClickCalculateButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            calculateBMI();
        }
    };

   @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        massCurrentValue = ((EditText)findViewById(R.id.massEditText)).getText().toString();
        heightCurrentValue = ((EditText)findViewById(R.id.heightEditText)).getText().toString();
    }

    @Override //creates new menu
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menuItemSave:
                onMenuOptionSaveClicked();
                return true;
            case R.id.menuItemAboutAuthor:
                startAboutAuthor(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initUnitFactory(){
        unitFactory = new UnitFactory[NUMBER_OF_UNITS];
        unitFactory[0]=MetricUnitFactory.Instance();
        unitFactory[1]=EnglishUnitFactory.Instance();
        currentUnits = unitFactory[0];

    }

    private void setFieldsFromSharedPreferences(){
        SharedPreferences sharedPref = this.getPreferences(
                                        Context.MODE_PRIVATE);

        int massCurrentValue = sharedPref.getInt(MASS_VALUE_KEY, DEFAULT_VALUE);
        int heightCurrentValue = sharedPref.getInt(HEIGHT_VALUE_KEY, DEFAULT_VALUE);
        ((EditText)findViewById(R.id.massEditText)).setText(
                String.valueOf(massCurrentValue));
        ((EditText)findViewById(R.id.heightEditText)).setText(
                String.valueOf(heightCurrentValue));
    }

    private void onMenuOptionSaveClicked() {
        saveToSharedPref(parseDataFromField(R.id.massEditText),
                parseDataFromField(R.id.heightEditText));
    }

    private int parseDataFromField(int fieldId){
        String valueFromEditText;
        int currentEditTextValue=0;
        valueFromEditText   = ((EditText)findViewById(fieldId)).getText().toString();
        if (!currentUnits.isStringEmpty(valueFromEditText)){
            try {
                 currentEditTextValue = Integer.parseInt(valueFromEditText);
            } catch (NumberFormatException nfe) {
                displayDefaultToast(getResources().getString(R.string.parsing_not_successful));
            }
        }
        else
            displayDefaultToast(getResources().getString(R.string.empty_field));
        return currentEditTextValue;
    }

    private boolean validateData(int mass, int height){
        if (currentUnits.isMassValid(mass)) {
            if (currentUnits.isHeightValid(height)) {
                return true;
            } else
                displayDefaultToast(getResources().getString(R.string.height_out_of_range));
        }
        else
            displayDefaultToast(getResources().getString(R.string.mass_out_of_range));
        return false;
    }

    private void saveToSharedPref(int mass, int height){
        if (validateData(mass, height)) {
                SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
                sharedPref.edit().putInt(MASS_VALUE_KEY, mass).apply();
                sharedPref.edit().putInt(HEIGHT_VALUE_KEY, height).apply();
                displayDefaultToast(getResources().getString(R.string.save_successful));
            }
    }

    private void calculateBMI(){
        double result;
        int mass   = parseDataFromField(R.id.massEditText);
        int height = parseDataFromField(R.id.heightEditText);
        if (validateData(mass, height)){
            try {
                result = currentUnits.countBMI(mass, height);
                startResultActivity(this, result);
            }catch (MassValueException massValExc){displayDefaultToast(
                    getResources().getString(R.string.mass_out_of_range));}
             catch (HeightValueException heightValExc){displayDefaultToast(
                    getResources().getString(R.string.height_out_of_range));}
        }
    }

    private void displayDefaultToast(String toastMessage){
        Toast.makeText(this,
                toastMessage,
                Toast.LENGTH_SHORT)
                .show();
    }

    private void changeUnits(){

        ((EditText)findViewById(R.id.massEditText)).setText("");
        ((EditText)findViewById(R.id.heightEditText)).setText("");

        unitFlag = (unitFlag+1) % NUMBER_OF_UNITS;

        if (unitFlag == 0)
            setMetricUnits();

        else if (unitFlag == 1)
            setEnglishUnits();

        currentUnits = unitFactory[unitFlag];
    }

    /*Function to set english units on massTextView & heightTextView*/
    private void setEnglishUnits(){
        ((TextView)findViewById(R.id.massTextView)).setText(
                getResources().getString(R.string.mass_textView_en));
        ((TextView)findViewById(R.id.heightTextView)).setText(
                getResources().getString(R.string.height_TextView_en));
    }

    /*Function to set metric units on massTextView & heightTextView*/
    private void setMetricUnits(){
        ((TextView)findViewById(R.id.massTextView)).setText(
                getResources().getString(R.string.mass_textView));
        ((TextView)findViewById(R.id.heightTextView)).setText(
                getResources().getString(R.string.height_TextView));
    }

}
