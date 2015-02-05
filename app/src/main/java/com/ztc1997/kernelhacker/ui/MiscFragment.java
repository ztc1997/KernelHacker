package com.ztc1997.kernelhacker.ui;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ztc1997.kernelhacker.R;
import com.ztc1997.kernelhacker.view.PreferenceView;

import de.psdev.licensesdialog.LicensesDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class MiscFragment extends Fragment {
    private PreferenceView licensesView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_misc, container, false);
        licensesView = (PreferenceView) rootView.findViewById(R.id.misc_about_licenses);
        licensesView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LicensesDialog.Builder(getActivity()).setNotices(R.raw.notices).setIncludeOwnLicense(true).build().show();
            }
        });
        return rootView;
    }


}
