package edu.orangecoastcollege.cs273.nhoang53.cs723superheroes_v2;

/**
 * Nguyen Hoang C02288487
 * Project 2: CS273 Superheroes
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {
    // key for reading data from SharedPreferences
    public static final String QUIZ_TYPE = "pref_quizType";

    private boolean phoneDevice = true; // used to force portrait mode
    private boolean preferencesChanged = true; // did preferences change???

    private Context context = this; // get application Context from Fragment, cannot use "this"
    private static ArrayList<Superheroes> allSuper;

    /**
     * onCreate generates the appropriate layout to infalte, depending on the
     * screen size. If the device is large or x-large, it will load the content_main.xml
     * (sw700dp-land) which includes both the fragment_quiz.xml and fragment_setting.xml.
     * Otherwise, it just inflates the standard content_main.xml with the fragment_quiz.
     * <p>
     * All defaut reference are set using the preferences.xml file
     *
     * @param savedInstanceState The saved state to restore (not being used)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // get data from JSON file
        try {
            allSuper = JSONLoader.loadJSONFromAsset(context);
        } catch (IOException ex) {
            Log.e("CS273 Superheroes", "Error loading JSON data." + ex.getMessage());
        }

        // set default values in the app's SharedPreferences
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        // register listener for SharedPreferences changes
        PreferenceManager.getDefaultSharedPreferences(this).
                registerOnSharedPreferenceChangeListener((preferencesChangeListener));

        // determine screen size
        int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;

        // if device is a tablet, set phone Device to false
        if (screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE ||
                screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE)
            phoneDevice = false; // not a phone-sized device

        // if running on phone-sized device, allow only portrait orientation
        if (phoneDevice)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }

    /**
     * onStart is called after onCreate completes execution.
     * This method will update the number of guess rows to display
     * and the regions to choose flags from, then resets the quiz with
     * the new preferences.
     */
    @Override
    protected void onStart() {
        super.onStart();

        if (preferencesChanged) {

            // now that the default preferences have been set,
            // initialize QuizActivityFragment and start the quiz
            QuizActivityFragment quizFragment = (QuizActivityFragment)
                    getFragmentManager().findFragmentById(R.id.quizFragment);
            quizFragment.changeQuizType(
                    PreferenceManager.getDefaultSharedPreferences(this), allSuper);
            preferencesChanged = false;
        }
    }

    /**
     * Shows the setting menu if the app is running on a phone or a portrait-Oriented
     * table only. (Large screen sizes include the setting fragment in the layout)
     *
     * @param menu The setting menu.
     * @return True if the setting menu was inflated, false otherwise.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // get the device's current orientation
        int orientation = getResources().getConfiguration().orientation;

        // display the app's menu only in portrait orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            // inflate the menu
            getMenuInflater().inflate(R.menu.menu_quiz, menu);
            return true;
        } else
            return false;
    }

    /**
     * Displays the SettingsActivity when running on a phone or portrait-oriented
     * tablet. Starts the activity by use of an Intent (no data passed because the
     * shared preferences, preferences.xml, has all data necessary)
     *
     * @param item The menu item
     * @return True if an option item was selected
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent preferencesIntent = new Intent(this, SettingActivity.class);
        startActivity(preferencesIntent);
        return super.onOptionsItemSelected(item);
    }

    /**
     * Listener to handle changes in setting of the app's shared preferences (preferences.xml)
     * If either the guess options or regions are changed, the quiz will restart with
     * the new setting
     */
    private OnSharedPreferenceChangeListener preferencesChangeListener =
            new OnSharedPreferenceChangeListener() {
                // called when the user changes the app's preferences
                @Override
                public void onSharedPreferenceChanged(
                        SharedPreferences sharedPreferences, String key) {
                    preferencesChanged = true; // user changed app setting

                    QuizActivityFragment quizFragment = (QuizActivityFragment)
                            getFragmentManager().findFragmentById(R.id.quizFragment);
                            //getSupportFragmentManager().findFragmentById(R.id.quizFragment);

                    try {
                        quizFragment.changeQuizType(sharedPreferences, allSuper); // pass data to Fragment

                        Toast.makeText(QuizActivity.this,
                                R.string.restarting_quiz,
                                Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e){
                        Log.e("QuizActivity.", "Error loading " + sharedPreferences + ". " + e.getMessage());
                    }

                }
            };
}
