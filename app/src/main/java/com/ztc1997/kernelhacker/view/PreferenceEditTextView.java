package com.ztc1997.kernelhacker.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.ztc1997.kernelhacker.R;

/**
 * Created by Alex on 2015/2/3.
 */
public class PreferenceEditTextView extends PreferenceView {
    private int min = 0, max = 100, value = min, interval = 1;
    private EditTextDialog.OnEditTextSetListener onEditTextSetListener;
    
    public PreferenceEditTextView(Context context) {
        this(context, null);
    }

    public PreferenceEditTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PreferenceEditTextView(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        
        if (attrs != null){
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PreferenceEditTextView);
            min = array.getInt(R.styleable.PreferenceEditTextView_petv_minValue, 0);
            max = array.getInt(R.styleable.PreferenceEditTextView_petv_maxValue, 100);
            value = array.getInt(R.styleable.PreferenceEditTextView_petv_defaultValue, min);
            interval = array.getInt(R.styleable.PreferenceEditTextView_petv_interval, 1);
            array.recycle();
        }
        
        if (key != null)
            value = preferences.getInt(key, min);
        
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new EditTextDialog(context, getTitle())
                        .setOnEditTextSetListener(new EditTextDialog.OnEditTextSetListener() {
                            @Override
                            public void onEditTextSet(int edit) {
                                if (key != null)
                                    preferences.edit().putInt(key, edit).apply();
                                if (onEditTextSetListener != null)
                                    onEditTextSetListener.onEditTextSet(edit);
                            }
                        }).setMax(max)
                        .setMin(min)
                        .setValue(value)
                        .setInterval(interval)
                        .show();
            }
        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        preferences.registerOnSharedPreferenceChangeListener(changeListener);
        value = preferences.getInt(key, value);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        preferences.unregisterOnSharedPreferenceChangeListener(changeListener);
    }

    private SharedPreferences.OnSharedPreferenceChangeListener changeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (key.equals(PreferenceEditTextView.this.key)){
                value = preferences.getInt(key, value);
            }
        }
    };

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public EditTextDialog.OnEditTextSetListener getOnEditTextSetListener() {
        return onEditTextSetListener;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public void setOnEditTextSetListener(EditTextDialog.OnEditTextSetListener onEditTextSetListener) {
        this.onEditTextSetListener = onEditTextSetListener;
    }
}
