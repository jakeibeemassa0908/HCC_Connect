package com.hccs.app.studentIn;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by theotherside on 02/04/15.
 */
public class SettingsActivity extends PreferenceActivity {

    public static final String PREF_USERNAME= "pref_key_username";
    public static final String PREF_PWD = "pref_key_password";
    public static final String PREF_AUTOFILL = "pref_autofill";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}