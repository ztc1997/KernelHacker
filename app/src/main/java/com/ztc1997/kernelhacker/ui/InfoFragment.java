package com.ztc1997.kernelhacker.ui;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.BatteryManager;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.XLabels;
import com.ztc1997.kernelhacker.R;
import com.ztc1997.kernelhacker.extra.Frequency;
import com.ztc1997.kernelhacker.extra.Paths;
import com.ztc1997.kernelhacker.extra.PrefKeys;
import com.ztc1997.kernelhacker.extra.Stats;
import com.ztc1997.kernelhacker.extra.Utils;
import com.ztc1997.kernelhacker.view.InfoProgressBar;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class InfoFragment extends Fragment {
    private View rootView;
    private TextView kernelName;
    private BarChart cpuChart;
    private InfoProgressBar cpuTemp, batteryTemp, cpuFreq;
    private SharedPreferences preferences;
    private Intent batteryIntent;
    private final Handler handler = new Handler(Looper.getMainLooper());

    public InfoFragment() {
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
        rootView = inflater.inflate(R.layout.fragment_info, container, false);
        cpuChart = (BarChart) rootView.findViewById(R.id.cpu_chart);
        kernelName = (TextView) rootView.findViewById(R.id.kernel_name);
        cpuTemp = (InfoProgressBar) rootView.findViewById(R.id.info_cpu_temp);
        cpuFreq = (InfoProgressBar) rootView.findViewById(R.id.info_cpu_freq);
        batteryTemp = (InfoProgressBar) rootView.findViewById(R.id.info_battery_temp);
        kernelName.setText(preferences.getString(PrefKeys.KERNEL_VERSION, getString(R.string.info_kernel_version_unable)));
        cpuChartSetup();
        deviceStateSetup();
        return rootView;
    }

    private void cpuChartSetup(){
        Stats stats = Utils.getFrequencyStats(true);
        stats.sort(Stats.SortMethod.Frequency);
        List<Frequency> frequencies = stats.getFrequencies();
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<Integer> colors = new ArrayList<Integer>();
        
        for (int i = 0 ; i < frequencies.size() ; i++) {
            Frequency frequency = frequencies.get(i);
            float percentage = stats.getPercentage(frequency).floatValue();
            if (percentage == 0)
                continue;
            xVals.add(frequency.getMHz() / 1000 + "");
            yVals1.add(new BarEntry(percentage * 100, i));
            if (i <= frequencies.size() / 3){
                colors.add(getResources().getColor(android.R.color.holo_green_dark));
            } else if (i <= frequencies.size() / 3 * 2){
                colors.add(getResources().getColor(android.R.color.holo_orange_dark));
            } else {
                colors.add(getResources().getColor(android.R.color.holo_red_dark));
            }
        }
        
        BarDataSet set = new BarDataSet(yVals1, getString(R.string.info_cpu_time_in_state));
        set.setColors(colors);
        BarData data = new BarData(xVals, set);
        cpuChart.setDescription("");
        cpuChart.setUnit("%");
        cpuChart.setData(data);
        cpuChart.setTouchEnabled(false);
        cpuChart.setDrawYValues(false);
        cpuChart.setDrawBorder(false);
        cpuChart.setDrawLegend(false);
        cpuChart.getXLabels().setPosition(XLabels.XLabelPosition.BOTTOM);
    }
    
    private void deviceStateSetup(){
        cpuFreq.setMin(Integer.parseInt(preferences.getString(PrefKeys.CPU_MIN_FREQ, "0")) / 1000);
        cpuFreq.setMax(Integer.parseInt(preferences.getString(PrefKeys.CPU_MAX_FREQ, "0")) / 1000);
    }

    @Override
    public void onResume() {
        super.onResume();
        batteryIntent = getActivity().registerReceiver(batteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        if (updateStatsThread.isInterrupted())
            updateStatsThread.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(batteryReceiver);
        updateStatsThread.interrupt();
    }
    
    private Thread updateStatsThread = new Thread(){
        @Override
        public void run() {
            super.run();
            while (true){
                final int cpuTmp = Integer.parseInt(Utils.readOneLine(Paths.INFO_CPU_TEMP));
                String cpufrqStr = Utils.readOneLine(Paths.SCALING_CUR_FREQ);
                final int cpufrq = Integer.parseInt(cpufrqStr.substring(0, cpufrqStr.length() - 3));
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        int batteryTmp = batteryIntent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,0) / 10;
                        batteryTemp.setValue(batteryTmp);
                        cpuTemp.setValue(cpuTmp);
                        cpuFreq.setValue(cpufrq);
                    }
                });
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private BroadcastReceiver batteryReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            batteryTemp.setValue(intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, batteryTemp.getValue() * 10) / 10);
        }
    };

}
