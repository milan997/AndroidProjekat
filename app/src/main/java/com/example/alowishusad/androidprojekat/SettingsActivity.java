package com.example.alowishusad.androidprojekat;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {
    SettingsFragment sf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_settings);
        sf = new SettingsFragment();
        getFragmentManager().beginTransaction()
                            .replace(android.R.id.content, sf)
                            .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String adminName = sp.getString("adminName", "defaultValue");

        //Toast.makeText(this, adminName, Toast.LENGTH_LONG).show();

        EditTextPreference etp = (EditTextPreference) sf.getPref("adminName");

        etp.setSummary(adminName);

    }
}