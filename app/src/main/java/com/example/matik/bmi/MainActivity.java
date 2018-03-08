package com.example.matik.bmi;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    private void getSharedPreferencesData(){
        SharedPreferences sharedPref = this.getPreferences(
                Context.MODE_PRIVATE);
        int defaultMass = getResources().getInteger(R.integer.saved_mass_default_key);
        TypedValue outValue = new TypedValue();
        getResources().getValue(
                                R.dimen.saved_height_default_value,
                                outValue,
                                true);
        float defaultHeight = outValue.getFloat();
        int mass = sharedPref.getInt(getString(R.string.mass_file_key), defaultMass);
        float height = sharedPref.getFloat(getString(R.string.height_file_key), defaultHeight);
    }

    private void onSaveMenuOption(int mass, int height){
        Context context = this;
        String toastMessage=context.getString(R.string.save_error);
        if (isMassValid(mass) && isHeightValid(height)){
            SharedPreferences sharedPref = this.getPreferences(
                                Context.MODE_PRIVATE);
            sharedPref.edit().putFloat(context.getString(R.string.mass_file_key),mass).apply();
            sharedPref.edit().putFloat(context.getString(R.string.height_file_key),height).apply();
            toastMessage=context.getString(R.string.save_successful);
        }
        Toast.makeText(context,
                toastMessage,
                Toast.LENGTH_SHORT)
                .show();
    }

    private boolean isMassValid(int mass){
        //TODO:implement mass constraints
        return true;
    }

    private boolean isHeightValid(int height){
        //TODO: immplement height constraints
        return true;
    }

}
