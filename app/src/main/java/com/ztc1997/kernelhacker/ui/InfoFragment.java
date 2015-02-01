package com.ztc1997.kernelhacker.ui;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.Legend;
import com.github.mikephil.charting.utils.XLabels;
import com.ztc1997.kernelhacker.R;
import com.ztc1997.kernelhacker.extra.Frequency;
import com.ztc1997.kernelhacker.extra.Stats;
import com.ztc1997.kernelhacker.extra.Utils;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class InfoFragment extends Fragment {
    private View rootView;
    private BarChart cpuChart;

    public InfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_info, container, false);
        cpuChart = (BarChart) rootView.findViewById(R.id.cpu_chart);
        cpuChartSetup();
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
        cpuChart.animateY(900);
    }

}
