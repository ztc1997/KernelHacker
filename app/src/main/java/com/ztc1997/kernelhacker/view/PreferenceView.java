package com.ztc1997.kernelhacker.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ztc1997.kernelhacker.R;

/**
 * Created by Alex on 2015/2/2.
 */
public class PreferenceView extends RelativeLayout {
    protected String key, dependency;
    private TextView title, summary;
    protected SharedPreferences preferences;
    private Point downPoint;
    private OnClickListener onClickListener;

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
        title.setTextColor(getResources().getColor(enabled ? R.color.main_dark : R.color.main_light));
        summary.setTextColor(getResources().getColor(enabled ?
                R.color.secondary_text_default_material_light : R.color.secondary_text_disabled_material_light));
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        onClickListener = l;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isEnabled())
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    getParent().requestDisallowInterceptTouchEvent(true);
                    downPoint = new Point((int) event.getX(), (int) event.getY());
                    setBackgroundResource(R.color.dim_lighter_gray);
                    break;
                
                case MotionEvent.ACTION_MOVE:
                    if (Math.abs(event.getX() - downPoint.x) >= 5 && Math.abs(event.getY() - downPoint.y) >= 5) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                        setBackgroundResource(R.color.transparent);
                    }
                    break;
                
                case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        setBackgroundResource(R.color.transparent);
                        if (Math.abs(event.getX() - downPoint.x) <= 5 && Math.abs(event.getY() - downPoint.y) <= 5 && onClickListener != null) {
                            onClickListener.onClick(this);
                        }
                        break;
            }
        super.onTouchEvent(event);
        return true;
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
