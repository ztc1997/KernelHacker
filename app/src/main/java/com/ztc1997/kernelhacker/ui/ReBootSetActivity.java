package com.ztc1997.kernelhacker.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import com.gc.materialdesign.widgets.Dialog;
import com.ztc1997.kernelhacker.R;
import com.ztc1997.kernelhacker.receiver.BootReceiver;

/**
 * Created by Alex on 2015/2/2.
 */
public class ReBootSetActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Dialog dialog = new Dialog(this,getString(R.string.re_boot_set_title), getString(R.string.re_boot_set_content));
        dialog.setOnAcceptButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BootReceiver.bootSetup(ReBootSetActivity.this, PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
                finish();
            }
        });
        dialog.addCancelButton(getString(android.R.string.cancel), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });
        dialog.show();
    }
}
