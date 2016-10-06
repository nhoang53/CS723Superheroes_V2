package edu.orangecoastcollege.cs273.nhoang53.cs723superheroes_v2;

/**
 * Nguyen Hoang C02288487
 * Project 2: CS273 Superheroes
 */

import android.preference.PreferenceFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class SettingActivityFragment extends PreferenceFragment {

    // create preferences GUI from preferences.xml file in res/xml
    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);

        addPreferencesFromResource(R.xml.preferences); // load from XML
    }
}
