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
    public static final String RESULT_EXTRA_MESSAGE = "com.example.matik.bmi.RESULT_MESSAGE";
    public static final int    NUMBER_OF_UNITS = 2;
    private String massEditTextValue;
    private String heightEditTextValue;
    private UnitFactory [] unitFactory;
    private UnitFactory currentUnits;
    private int unitFlag;
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

        unitFactory = new UnitFactory[NUMBER_OF_UNITS];
        unitFactory[0]=MetricUnitFactory.Instance();
        unitFactory[1]=EnglishUnitFactory.Instance();
        currentUnits = unitFactory[0];

       final Button countButton = findViewById(R.id.countButton);
       countButton.setOnClickListener(new View.OnClickListener() {
                                     public void onClick(View v) {CountResult();}
                                 });
       final Button unitButton = findViewById(R.id.changeUnitButton);
       unitButton.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v) {
               changeUnits();
           }
       });
        getSharedPreferencesData();
   }

   @Override
   protected void onPause(){
       super.onPause();
       massEditTextValue = getCurrentValue(R.id.massEditText);
       heightEditTextValue = getCurrentValue(R.id.heightEditText);
   }

   @Override
   protected void onResume(){
       super.onResume();
       setTextView(R.id.massEditText, massEditTextValue);
       setTextView(R.id.heightEditText, heightEditTextValue);
   }

   @Override
   protected void onStop(){
       super.onStop();
       setTextView(R.id.massEditText, massEditTextValue);
       setTextView(R.id.heightEditText, heightEditTextValue);
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
                onSaveMenuOption();
                return true;
            case R.id.menuItemAboutAuthor:
                onAboutAuthorMenuOption();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getSharedPreferencesData(){
        SharedPreferences sharedPref = this.getPreferences(
                Context.MODE_PRIVATE);
        String defaultMass = this.getString(R.string.saved_mass_default_key);
        String defaultHeight = this.getString(R.string.saved_height_default_value);
        String mass = sharedPref.getString(getString(R.string.mass_file_key), defaultMass);
        String height = sharedPref.getString(getString(R.string.height_file_key), defaultHeight);
        setTextView(R.id.heightEditText, height);
        setTextView(R.id.massEditText, mass);
        massEditTextValue = mass;
        heightEditTextValue = height;
    }

    private void onSaveMenuOption(){
        String mass, height;
        mass = getCurrentValue(R.id.massEditText);
        height = getCurrentValue(R.id.heightEditText);
        Context context = this;
        String toastMessage=context.getString(R.string.save_error);
        if (currentUnits.isMassValid(Integer.parseInt(mass)) &&
                currentUnits.isHeightValid(Float.parseFloat(height))){
            SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
            sharedPref.edit().putString(context.getString(R.string.mass_file_key),mass).apply();
            sharedPref.edit().putString(context.getString(R.string.height_file_key),height).apply();
            toastMessage=context.getString(R.string.save_successful);
        }
        displayDefaultToast(toastMessage);
    }

    private void CountResult(){
        float result;
        float mass=0;
        float height=0;

        try {
            mass = parseNumber(getCurrentValue(R.id.massEditText));
        }catch (NumberFormatException nfe){displayDefaultToast("Enter mass value");}

        try {
            height = parseNumber(getCurrentValue(R.id.heightEditText));
        }catch (NumberFormatException nfe){displayDefaultToast("Enter height value");}

        try{
            result = currentUnits.countBMI(mass, height);
            onCountBMIButtonClicked(result);
        }catch
                (heightValueException exc){displayDefaultToast("Height out of range");}
        catch
                (massValueException exc){displayDefaultToast("Mass out of range");}

    }

    String getCurrentValue(int id){
        final EditText editText;
        String value;
        editText = findViewById(id);
        value = editText.getText().toString();
        return value;
    }

    void setTextView(int id, String text){
        EditText editText;
        editText = findViewById(id);
        editText.setText(text);
    }

    private void displayDefaultToast(String toastMessage){
        Toast.makeText(this,
                toastMessage,
                Toast.LENGTH_SHORT)
                .show();
    }


    private float parseNumber(String stringToParse) throws NumberFormatException {
        return Float.parseFloat(stringToParse);
    }



    private void onAboutAuthorMenuOption()
    {
        Intent intent = new Intent(MainActivity.this, AboutAuthor.class);
        startActivity(intent);
    }

    private void onCountBMIButtonClicked(float bmi)
    {
        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
        intent.putExtra(RESULT_EXTRA_MESSAGE,bmi);
        startActivity(intent);
    }
    private void changeUnits(){
        //default units are metric, flag = true

        unitFlag = (unitFlag+1) % NUMBER_OF_UNITS;

        if (unitFlag == 0)
            setMetricUnits();

        else if (unitFlag == 1)
            setEnglishUnits();

        currentUnits = unitFactory[unitFlag];
    }

    private void setEnglishUnits(){
        TextView mTextView = this.findViewById(R.id.massTextView);
        TextView hTextView = this.findViewById(R.id.heightTextView);
        mTextView.setText(R.string.mass_textView_en);
        hTextView.setText(R.string.height_TextView_en);
    }

    private void setMetricUnits(){
        TextView mTextView = this.findViewById(R.id.massTextView);
        TextView hTextView = this.findViewById(R.id.heightTextView);
        mTextView.setText(R.string.mass_textView);
        hTextView.setText(R.string.height_TextView);
    }

}
