package com.ztc1997.kernelhacker.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gc.materialdesign.views.ProgressBarDeterminate;
import com.ztc1997.kernelhacker.R;

/**
 * Created by Alex on 2015/2/1.
 */
public class InfoProgressBar extends LinearLayout {
    private String unit = "", name = "";
    private int min;
    private int max;
    private int color;
    private int value;
    private TextView infoText, minText, maxText;
    private ProgressBarDeterminate progressBar;
    
    public InfoProgressBar(Context context) {
        this(context, null);
    }

    public InfoProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public InfoProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }
    
    private void init(Context context, AttributeSet attributeSet){
        LayoutInflater.from(context).inflate(R.layout.view_info_progress_bar, this);
        progressBar = (ProgressBarDeterminate) findViewById(R.id.info_progress_bar);
        infoText = (TextView) findViewById(R.id.info_text_view);
        minText = (TextView) findViewById(R.id.info_min);
        maxText = (TextView) findViewById(R.id.info_max);
        color = getResources().getColor(R.color.main_dark);
        if (attributeSet != null) {
            TypedArray attr = context.obtainStyledAttributes(attributeSet, R.styleable.InfoProgressBar);
            unit = attr.getString(R.styleable.InfoProgressBar_ipb_unit);
            name = attr.getString(R.styleable.InfoProgressBar_ipb_name);
            min = attr.getInt(R.styleable.InfoProgressBar_ipb_minValue, 0);
            max = attr.getInt(R.styleable.InfoProgressBar_ipb_maxValue, 100);
            value =attr.getInt(R.styleable.InfoProgressBar_ipb_defaultValue, 0);
            color = attr.getColor(R.styleable.InfoProgressBar_ipb_barColor, getResources().getColor(R.color.main_dark));
        }
        setColor(color);
        updateText();
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        progressBar.setBackgroundColor(color);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value){
        this.value = value;
        progressBar.setProgress(value - min);
        updateText();
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
        progressBar.setMax(max - min);
        updateText();
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
        progressBar.setMax(max - min);
        updateText();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
        updateText();
    }
    
    private void updateText(){
        infoText.setText(name + value + unit);
        minText.setText(min + unit);
        maxText.setText(max + unit);
    }
}
