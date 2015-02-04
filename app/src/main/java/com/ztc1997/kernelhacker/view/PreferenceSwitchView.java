package com.ztc1997.kernelhacker.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gc.materialdesign.views.Switch;
import com.ztc1997.kernelhacker.R;

/**
 * Created by Alex on 2015/2/2.
 */
public class PreferenceSwitchView extends PreferenceView {
    private Switch switchView;
    private SharedPreferences preferences;
    
    public PreferenceSwitchView(Context context) {
        this(context, null);
    }

    public PreferenceSwitchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PreferenceSwitchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr, R.layout.view_preference_switch);
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        switchView = (Switch) findViewById(R.id.preferencr_switch_switch);
        
        if (attrs != null) {
            TypedArray attr = context.obtainStyledAttributes(attrs, R.styleable.PreferenceSwitchView);
            switchView.setChecked(attr.getBoolean(R.styleable.PreferenceSwitchView_psv_defaultValue, false));
            attr.recycle();
        }
        
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                switchView.setChecked(!switchView.isCheck());
            }
        });
        
        switchView.setOncheckListener(new Switch.OnCheckListener() {
            @Override
            public void onCheck(boolean check) {
                if (key != null)
                    preferences.edit().putBoolean(key, check).apply();
            }
        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        preferences.registerOnSharedPreferenceChangeListener(changeListener);
        setChecked(preferences.getBoolean(key, isChecked()));
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        preferences.unregisterOnSharedPreferenceChangeListener(changeListener);
    }

    private SharedPreferences.OnSharedPreferenceChangeListener changeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (key.equals(PreferenceSwitchView.this.key)){
                setChecked(preferences.getBoolean(key, isChecked()));
            }
        }
    };
    
    public void setChecked(boolean checked){
        switchView.setChecked(checked);
    }
    
    public boolean isChecked(){
        return switchView.isCheck();
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        switchView.setEnabled(enabled);
        switchView.setAlpha(enabled ? 1f : 0.5f);
    }
}
