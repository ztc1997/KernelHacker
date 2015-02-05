package com.ztc1997.kernelhacker.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.ztc1997.kernelhacker.R;

/**
 * Created by Alex on 2015/2/2.
 */
public class PreferenceView extends RelativeLayout {
    protected String key, dependency;
    private TextView title, summary;
    private MaterialRippleLayout rootView;
    protected SharedPreferences preferences;

    public PreferenceView(Context context) {
        this(context, null);
    }

    public PreferenceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PreferenceView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, R.layout.view_preference);
    }
    
    public PreferenceView(Context context, AttributeSet attrs, int defStyleAttr, int layoutRes){
        super(context, attrs, defStyleAttr);
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        LayoutInflater.from(context).inflate(layoutRes, this);
        title = (TextView) findViewById(R.id.preferencr_title);
        summary = (TextView) findViewById(R.id.preferencr_summary);
        rootView = (MaterialRippleLayout) findViewById(R.id.preferencr_ripple);

        if (attrs != null) {
            TypedArray attr = context.obtainStyledAttributes(attrs, R.styleable.PreferenceView);
            key = attr.getString(R.styleable.PreferenceView_pv_key);
            dependency = attr.getString(R.styleable.PreferenceView_pv_dependency);
            setTitle(attr.getString(R.styleable.PreferenceView_pv_title));
            setSummary(attr.getString(R.styleable.PreferenceView_pv_summary));
            attr.recycle();
        }
        
    }

    public String getTitle() {
        return title.getText().toString();
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public String getSummary() {
        return summary.getText().toString();
    }

    public void setSummary(String summary) {
        if (summary == null || summary.equals("")) {
            this.summary.setVisibility(GONE);
        }else {
            this.summary.setText(summary);
            this.summary.setVisibility(VISIBLE);
        }
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDependency() {
        return dependency;
    }

    public void setDependency(String dependency) {
        this.dependency = dependency;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        rootView.setEnabled(enabled);
        title.setTextColor(getResources().getColor(enabled ? R.color.main_dark : R.color.main_light));
        summary.setTextColor(getResources().getColor(enabled ?
                R.color.secondary_text_default_material_light : R.color.secondary_text_disabled_material_light));
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        rootView.setOnClickListener(l);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        preferences.registerOnSharedPreferenceChangeListener(changeListener);
        if (dependency!= null && !dependency.equals(""))
            setEnabled(preferences.getBoolean(dependency, false));
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        preferences.unregisterOnSharedPreferenceChangeListener(changeListener);
    }

    SharedPreferences.OnSharedPreferenceChangeListener changeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (dependency!= null && key.equals(dependency)){
                if (!dependency.equals(""))
                    setEnabled(preferences.getBoolean(dependency, false));
            }
        }
    };
}
