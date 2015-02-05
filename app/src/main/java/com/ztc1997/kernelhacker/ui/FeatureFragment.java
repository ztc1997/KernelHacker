package com.ztc1997.kernelhacker.ui;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ztc1997.kernelhacker.R;
import com.ztc1997.kernelhacker.extra.PrefKeys;
import com.ztc1997.kernelhacker.view.PreferenceEditTextView;
import com.ztc1997.kernelhacker.view.PreferenceSwitchView;
import com.ztc1997.kernelhacker.view.PreferenceView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeatureFragment extends Fragment {
    private SharedPreferences preferences;
    
    private PreferenceView t2wRangeView;
    private PreferenceEditTextView t2wIntervalView;
    private PreferenceSwitchView t2wView, t2wAutoView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_feature, container, false);
        t2wView = (PreferenceSwitchView) rootView.findViewById(R.id.t2w_switch);
        t2wAutoView = (PreferenceSwitchView) rootView.findViewById(R.id.t2w_auto_switch);
        t2wIntervalView = (PreferenceEditTextView) rootView.findViewById(R.id.t2w_interval);
        t2wRangeView = (PreferenceView) rootView.findViewById(R.id.t2w_effective_range);
        
        t2wRangeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), T2wRangeSelector.class));
            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        preferences.registerOnSharedPreferenceChangeListener(changeListener);
        t2wIntervalView.setSummary(getString(R.string.feature_t2w_interval_summury, preferences.getInt(PrefKeys.T2W_INTERAL, 20) + ""));
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
                case PrefKeys.T2W_INTERAL:
                    t2wIntervalView.setSummary(getString(R.string.feature_t2w_interval_summury, preferences.getInt(key, 20) + ""));
                    break;
            }
        }
    };

}
