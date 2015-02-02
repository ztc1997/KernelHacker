package com.ztc1997.kernelhacker.ui;

import java.util.Locale;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gc.materialdesign.widgets.Dialog;
import com.gc.materialdesign.widgets.ProgressDialog;
import com.gc.materialdesign.widgets.SnackBar;
import com.ztc1997.kernelhacker.AntiFalseWakeService;
import com.ztc1997.kernelhacker.MyApplication;
import com.ztc1997.kernelhacker.extra.Commands;
import com.ztc1997.kernelhacker.extra.PrefKeys;
import com.ztc1997.kernelhacker.extra.Paths;
import com.ztc1997.kernelhacker.R;
import com.ztc1997.kernelhacker.extra.Utils;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.update.BmobUpdateAgent;


public class MainActivity extends ActionBarActivity implements ActionBar.TabListener {
    
    private SharedPreferences preferences;
    //private Fragment featuresFragment = new FeaturesFragment();

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        getRoot();
        Bmob.initialize(this, "ba9bd8dcb177f47278656662c57a5074");
        BmobUpdateAgent.update(this);

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            switch (position){
                case 0:
                    return new InfoFragment();
                case 1:
                    return new CommonFragment();
                case 2:
                    return new FeaturesFragment();
                default:
                    return PlaceholderFragment.newInstance(position);
            }
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_info).toUpperCase(l);
                case 1:
                    return getString(R.string.title_common).toUpperCase(l);
                case 2:
                    return getString(R.string.title_features).toUpperCase(l);
                case 3:
                    return getString(R.string.title_settings).toUpperCase(l);
            }
            return null;
        }
    }

 /*   *//**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /* 
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /*
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_blank, container, false);
            return rootView;
        }
    }

    private void getRoot(){
        final ProgressDialog dialog = new ProgressDialog(this,getString(R.string.dialog_getting_root_title));
        dialog.setCancelable(false);
        dialog.show();
        new Thread(){
            @Override
            public void run() {
                super.run();
                final boolean unableRoot = MyApplication.getRootUtil() == null;
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    public void run() {
                        if (unableRoot) {
                            Dialog dialog1 = new Dialog(MainActivity.this, getString(R.string.dialog_getting_root_failed_title), getString(R.string.dialog_getting_root_failed_msg));
                            dialog1.setOnAcceptButtonClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();
                                }
                            });
                            dialog1.show();
                        }
                        dialog.dismiss();
                    }
                });
            }
        }.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        preferences.registerOnSharedPreferenceChangeListener(changeListener);
        initSysValues();
    }

    private void initSysValues(){
        preferences.edit()
                .putString(PrefKeys.KERNEL_VERSION, Utils.readOneLine(Paths.INFO_KERNEL_VERSION))
                .putString(PrefKeys.T2W_INTERAL, Utils.readOneLine(Paths.T2W_INTERVAL))
                .putString(PrefKeys.T2W_RANGE_X_FROM, Utils.readOneLine(Paths.T2W_X_FROM))
                .putString(PrefKeys.T2W_RANGE_X_TO, Utils.readOneLine(Paths.T2W_X_TO))
                .putString(PrefKeys.T2W_RANGE_Y_FROM, Utils.readOneLine(Paths.T2W_Y_FROM))
                .putString(PrefKeys.T2W_RANGE_Y_TO,Utils.readOneLine(Paths.T2W_Y_TO))
                .putBoolean(PrefKeys.T2W, !Utils.readOneLine(Paths.T2W_PREVENT_SLEEP).equals("0"))
                .putString(PrefKeys.CPU_MAX_FREQ, Utils.readOneLine(Paths.CPUINFO_MAX_FREQ))
                .putString(PrefKeys.CPU_MIN_FREQ, Utils.readOneLine(Paths.CPUINFO_MIN_FREQ))
                .putBoolean(PrefKeys.ZRAM, Utils.readTextLines(Paths.SWAP_STATE).contains("/dev/block/zram0"))
                .apply();
    }

    @Override
    protected void onPause() {
        super.onPause();
        preferences.unregisterOnSharedPreferenceChangeListener(changeListener);
    }

    private SharedPreferences.OnSharedPreferenceChangeListener changeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences preferences, String s) {
            if (s.equals(PrefKeys.T2W_AUTO)){
                Intent intent = new Intent(MainActivity.this, AntiFalseWakeService.class);
                if (preferences.getBoolean(s, false))
                    MainActivity.this.startService(intent);
                else
                    MainActivity.this.stopService(intent);
            }else if (s.equals(PrefKeys.T2W)){
                String i = (preferences.getBoolean(s, false) ? "1" : "0");
                Utils.writeFileWithRoot(Paths.T2W_PREVENT_SLEEP, i);
                Utils.writeFileWithRoot(Paths.T2W_ENABLE, i);
            }else if (s.equals(PrefKeys.T2W_INTERAL)){
                String delay = preferences.getString(s, "20");
                Utils.writeFileWithRoot(Paths.T2W_INTERVAL, delay);
            }else if (s.equals(PrefKeys.ZRAM)){
                if (preferences.getBoolean(s, false))
                    MyApplication.getRootUtil().execute(Commands.ENABLE_ZRAM, null);
                else {
                    MyApplication.getRootUtil().execute(Commands.DISABLE_ZRAM, null);
                    new SnackBar(MainActivity.this, getString(R.string.common_zram_disable_hint)).show();
                }
            }
        }
    };
}
