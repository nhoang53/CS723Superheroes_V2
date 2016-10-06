package edu.orangecoastcollege.cs273.nhoang53.cs723superheroes_v2;

/**
 * Nguyen Hoang C02288487
 * Project 2: CS273 Superheroes
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * SettingsActivity is the activity to display a SettingsActivityFragment
 * on a landscape-oriented table
 */
public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
