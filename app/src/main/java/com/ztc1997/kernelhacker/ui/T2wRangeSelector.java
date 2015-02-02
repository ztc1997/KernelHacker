package com.ztc1997.kernelhacker.ui;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;
import com.ztc1997.kernelhacker.R;
import com.ztc1997.kernelhacker.extra.PrefKeys;
import com.ztc1997.kernelhacker.extra.Paths;
import com.ztc1997.kernelhacker.extra.Utils;
import com.ztc1997.kernelhacker.viwe.DrawRectView;

/**
 * Created by Alex on 2015/1/31.
 */
public class T2wRangeSelector extends Activity {
    private DrawRectView drv;
    private TextView info;
    private FloatingActionButton positiveBtn, negativeBtn;
    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_range_selector);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        drv = (DrawRectView) findViewById(R.id.range_selector);
        info = (TextView) findViewById(R.id.range_info);
        positiveBtn = (FloatingActionButton) findViewById(R.id.positiveBtn);
        negativeBtn = (FloatingActionButton) findViewById(R.id.negativeBtn);
        drv.left = Integer.parseInt(preferences.getString(PrefKeys.T2W_RANGE_X_FROM, "0"));
        drv.right = Integer.parseInt(preferences.getString(PrefKeys.T2W_RANGE_X_TO, "719"));
        drv.top = Integer.parseInt(preferences.getString(PrefKeys.T2W_RANGE_Y_FROM, "0"));
        drv.buttom = Integer.parseInt(preferences.getString(PrefKeys.T2W_RANGE_Y_TO, "1327"));
        info.setText(getString(R.string.info_t2w_effective_range, drv.left, drv.right, drv.top, drv.buttom));
        drv.setOnRectDrawingListener(new DrawRectView.OnRectDrawListener() {
            @Override
            public void onRectDrawing(int left, int top, int right, int buttom) {
                info.setText(getString(R.string.info_t2w_effective_range, left, right, top, buttom));
                positiveBtn.hide();
                negativeBtn.hide();
            }

            @Override
            public void onRectDrawed(int left, int top, int right, int buttom) {
                negativeBtn.show();
                positiveBtn.show();
            }
        });
        negativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences.edit()
                        .putString(PrefKeys.T2W_RANGE_X_FROM, drv.left+"")
                        .putString(PrefKeys.T2W_RANGE_X_TO, drv.right+"")
                        .putString(PrefKeys.T2W_RANGE_Y_FROM, drv.top+"")
                        .putString(PrefKeys.T2W_RANGE_Y_TO, drv.buttom+"")
                        .apply();
                Utils.writeFileWithRoot(Paths.T2W_X_FROM,drv.left+"");
                Utils.writeFileWithRoot(Paths.T2W_X_TO,drv.right+"");
                Utils.writeFileWithRoot(Paths.T2W_Y_FROM,drv.top+"");
                Utils.writeFileWithRoot(Paths.T2W_Y_TO,drv.buttom+"");
                finish();
            }
        });
    }
}
