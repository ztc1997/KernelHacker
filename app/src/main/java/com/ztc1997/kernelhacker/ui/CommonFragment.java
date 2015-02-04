package com.ztc1997.kernelhacker.ui;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ztc1997.kernelhacker.R;
import com.ztc1997.kernelhacker.extra.Paths;
import com.ztc1997.kernelhacker.extra.PrefKeys;
import com.ztc1997.kernelhacker.extra.Utils;
import com.ztc1997.kernelhacker.view.PreferenceEditTextView;
import com.ztc1997.kernelhacker.view.PreferenceListView;
import com.ztc1997.kernelhacker.view.PreferenceSwitchView;

import uk.me.lewisdeane.ldialogs.CustomListDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommonFragment extends Fragment {

    private PreferenceSwitchView zramView, cpuLockView;
    private PreferenceListView cpuMinView,cpuMaxView, cpuGovView;
    private PreferenceEditTextView zramSizeView, zramSwappinessView;
    private SharedPreferences preferences;

    public CommonFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_common, container, false);
        zramView = (PreferenceSwitchView) rootView.findViewById(R.id.zram_switch);
        zramSizeView = (PreferenceEditTextView) rootView.findViewById(R.id.zram_disksize);
        zramSwappinessView = (PreferenceEditTextView) rootView.findViewById(R.id.zram_swappiness);
        cpuMinView = (PreferenceListView) rootView.findViewById(R.id.common_cpu_min);
        cpuMaxView = (PreferenceListView) rootView.findViewById(R.id.common_cpu_max);
        cpuGovView = (PreferenceListView) rootView.findViewById(R.id.common_cpu_gov);
        cpuLockView = (PreferenceSwitchView) rootView.findViewById(R.id.common_cpu_lock);
        zramSetup();
        cpuSetup();
        return rootView;
    }

    private void zramSetup(){
        
    }

    private void cpuSetup(){
        String[] frequencies = Utils.getAvailableFrequencies();
        cpuMinView.setOptions(frequencies);
        cpuMaxView.setOptions(frequencies);
        
        String[] govs = Utils.readStringArray(Paths.SCALING_AVAILABLE_GOVERNORS);
        cpuGovView.setOptions(govs);
    }

    @Override
    public void onResume() {
        super.onResume();
        preferences.registerOnSharedPreferenceChangeListener(changeListener);

        cpuLockView.setChecked(preferences.getBoolean(PrefKeys.CPU_LOCK_FREQ, false));
        String minFreq = Utils.khzToMhzString(preferences.getString(PrefKeys.CPU_MIN_FREQ, "-1"));
        cpuMinView.setSummary(getString(R.string.common_cpu_min_summary, minFreq));
        String maxFreq = Utils.khzToMhzString(preferences.getString(PrefKeys.CPU_MAX_FREQ, "-1"));
        cpuMaxView.setSummary(getString(R.string.common_cpu_min_summary, maxFreq));
        cpuGovView.setSummary(getString(R.string.common_cpu_gov_summary, preferences.getString(PrefKeys.CPU_GOV, "-1")));
        
        zramView.setChecked(preferences.getBoolean(PrefKeys.ZRAM, false));
        zramSizeView.setSummary(getString(R.string.common_zram_disksize_summary, preferences.getInt(PrefKeys.ZRAM_DISKSIZE, 0) + ""));
        zramSwappinessView.setSummary(getString(R.string.common_zram_swappiness_summary, preferences.getInt(PrefKeys.ZRAM_SWAPPINESS, 18) + ""));
    }

    @Override
    public void onPause() {
        super.onPause();
        preferences.unregisterOnSharedPreferenceChangeListener(changeListener);
    }

    SharedPreferences.OnSharedPreferenceChangeListener changeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            switch (key){
                case PrefKeys.CPU_MIN_FREQ:
                    String minFreq = Utils.khzToMhzString(sharedPreferences.getString(key, "-1"));
                    cpuMinView.setSummary(getString(R.string.common_cpu_min_summary, minFreq));
                    break;
                case PrefKeys.CPU_MAX_FREQ:
                    String maxFreq = Utils.khzToMhzString(sharedPreferences.getString(key, "-1"));
                    cpuMaxView.setSummary(getString(R.string.common_cpu_max_summary, maxFreq));
                    break;
                case PrefKeys.CPU_GOV:
                    cpuGovView.setSummary(getString(R.string.common_cpu_gov_summary, preferences.getString(PrefKeys.CPU_GOV, "-1")));
                    break;
                case PrefKeys.ZRAM_DISKSIZE:
                    zramSizeView.setSummary(getString(R.string.common_zram_disksize_summary, preferences.getInt(PrefKeys.ZRAM_DISKSIZE, 0) + ""));
                    break;
                case PrefKeys.ZRAM_SWAPPINESS:
                    zramSwappinessView.setSummary(getString(R.string.common_zram_swappiness_summary, preferences.getInt(PrefKeys.ZRAM_SWAPPINESS, 18) + ""));
                    break;
            }
        }
    };
}
