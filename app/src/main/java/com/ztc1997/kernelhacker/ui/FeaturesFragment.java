package com.ztc1997.kernelhacker.ui;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ztc1997.kernelhacker.R;
import com.ztc1997.kernelhacker.extra.PrefKeys;


/**
 * A simple {@link android.app.Fragment} subclass.
 */
public class FeaturesFragment extends PreferenceFragment {
    
    private SharedPreferences preferences;
    private EditTextPreference delayPrefs;
    private CheckBoxPreference t2wPrefs, autoPrefs;
    private Preference eRPrefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.features);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        delayPrefs = (EditTextPreference) findPreference(PrefKeys.T2W_INTERAL);
        autoPrefs = (CheckBoxPreference) findPreference(PrefKeys.T2W_AUTO);
        t2wPrefs = (CheckBoxPreference) findPreference(PrefKeys.T2W);
        eRPrefs = findPreference(PrefKeys.T2W_EFFECTIVE_RANGE);
        eRPrefs.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivity(new Intent(getActivity(), T2wRangeSelector.class));
                return true;
            }
        });
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        preferences.registerOnSharedPreferenceChangeListener(changeListener);
        delayPrefs.setSummary(getString(R.string.pref_t2w_delay_summury, preferences.getString(PrefKeys.T2W_INTERAL, "20")));
    }

    @Override
    public void onPause() {
        super.onPause();
        preferences.unregisterOnSharedPreferenceChangeListener(changeListener);
    }

    private SharedPreferences.OnSharedPreferenceChangeListener changeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences preferences, String s) {
            if (s.equals(PrefKeys.T2W_INTERAL)){
                String delay = preferences.getString(s, "20");
                delayPrefs.setSummary(getString(R.string.pref_t2w_delay_summury, delay));
            }
        }
    };
}
