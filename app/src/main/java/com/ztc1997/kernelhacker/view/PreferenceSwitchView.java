package com.ztc1997.kernelhacker.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gc.materialdesign.views.Switch;
import com.ztc1997.kernelhacker.R;

/**
 * Created by Alex on 2015/2/2.
 */
public class PreferenceSwitchView extends RelativeLayout {
    private String title = "", summary, key;
    private TextView titleView, summaryView;
    private Switch switchView;
    private SharedPreferences preferences;
    
    public PreferenceSwitchView(Context context) {
        this(context, null);
    }

    public PreferenceSwitchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PreferenceSwitchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        LayoutInflater.from(context).inflate(R.layout.view_preference_switch, this);
        switchView = (Switch) findViewById(R.id.preferencr_switch_switch);
        titleView = (TextView) findViewById(R.id.preferencr_swich_title);
        summaryView = (TextView) findViewById(R.id.preferencr_swich_summary);
        
        if (attrs != null) {
            TypedArray attr = context.obtainStyledAttributes(attrs, R.styleable.PreferenceSwitchView);
            title = attr.getString(R.styleable.PreferenceSwitchView_psv_title);
            summary = attr.getString(R.styleable.PreferenceSwitchView_psv_summary);
            key = attr.getString(R.styleable.PreferenceSwitchView_psv_key);
            switchView.setChecked(attr.getBoolean(R.styleable.PreferenceSwitchView_psv_defaultValue, false));
            attr.recycle();
            setTitle(title);
            setSummary(summary);
        }
        
        if (key != null){
            switchView.setChecked(preferences.getBoolean(key, switchView.isCheck()));
        }
        
        switchView.setOncheckListener(new Switch.OnCheckListener() {
            @Override
            public void onCheck(boolean check) {
                preferences.edit().putBoolean(key, check).apply();
            }
        });
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        titleView.setText(title);
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
        summaryView.setText(summary);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    public void setChecked(boolean checked){
        switchView.setChecked(checked);
    }
    
    public boolean isChecked(){
        return switchView.isCheck();
    }

    @Override
    public void setEnabled(boolean enabled) {
        titleView.setTextColor(getResources().getColor(enabled ? R.color.main_dark : R.color.main_light));
        super.setEnabled(enabled);
    }
}
