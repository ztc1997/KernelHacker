package com.ztc1997.kernelhacker.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import com.gc.materialdesign.widgets.Dialog;
import com.ztc1997.kernelhacker.extra.Utils;

import java.util.Arrays;

import uk.me.lewisdeane.ldialogs.CustomListDialog;

/**
 * Created by Alex on 2015/2/2.
 */
public class PreferenceListView extends PreferenceView {
    private OnClickListener onClickListener;
    private CustomListDialog.ListClickListener listClickListener;
    private String[] options = {"No available options"};
    
    public PreferenceListView(Context context) {
        this(context, null);
    }

    public PreferenceListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PreferenceListView(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        super.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null)
                    onClickListener.onClick(PreferenceListView.this);
                showDialog();
            }
        });
    }
    
    private void showDialog(){
        CustomListDialog dialog = new CustomListDialog.Builder(getContext(), getTitle(), options).build();
        dialog.setListClickListener(new CustomListDialog.ListClickListener(){
            @Override
            public void onListItemSelected(int position, String[] items, String item) {
                preferences.edit().putString(key, item).apply();
                if (listClickListener != null)
                    listClickListener.onListItemSelected(position, items, item);
            }
            
        });
        dialog.show();
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    @Override
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setListClickListener(CustomListDialog.ListClickListener listClickListener) {
        this.listClickListener = listClickListener;
    }
}
