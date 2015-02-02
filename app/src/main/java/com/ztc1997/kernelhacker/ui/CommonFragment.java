package com.ztc1997.kernelhacker.ui;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ztc1997.kernelhacker.R;
import com.ztc1997.kernelhacker.extra.PrefKeys;
import com.ztc1997.kernelhacker.view.PreferenceSwitchView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommonFragment extends Fragment {

    private PreferenceSwitchView zramView;
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
        return rootView;
    }

    private void zramSetup(){
        zramView.setChecked(preferences.getBoolean(PrefKeys.ZRAM, false));
    }

}
