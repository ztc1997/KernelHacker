package com.ztc1997.kernelhacker.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ztc1997.kernelhacker.R;

/**
 * Created by Alex on 2015/2/2.
 */
public class PreferenceView extends RelativeLayout {
    protected String key;
    private TextView title, summary;
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

        if (attrs != null) {
            TypedArray attr = context.obtainStyledAttributes(attrs, R.styleable.PreferenceView);
            key = attr.getString(R.styleable.PreferenceView_pv_key);
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
        this.summary.setText(summary);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public void setEnabled(boolean enabled) {
        title.setTextColor(getResources().getColor(enabled ? R.color.main_dark : R.color.main_light));
        super.setEnabled(enabled);
    }
}
