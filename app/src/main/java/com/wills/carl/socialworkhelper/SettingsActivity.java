package com.wills.carl.socialworkhelper;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        /*
            TODO: For settings, maybe have an "Edit CEU Types" and "Edit Supervision Types" which opens
            a dialog where you can modify the text for each of the 5 types.
            The types are then stored in shared preferences and can be read out from wherever.
         */

    }
}
