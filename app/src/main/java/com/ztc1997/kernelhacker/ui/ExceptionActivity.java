package com.ztc1997.kernelhacker.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import com.gc.materialdesign.widgets.Dialog;
import com.ztc1997.kernelhacker.R;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;

/**
 * Created by Alex on 2015/2/5.
 */
public class ExceptionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Dialog dialog = new Dialog(this, getString(R.string.dailog_excption_title), getString(R.string.dailog_excption_msg));
        dialog.addCancelButton(getString(android.R.string.cancel), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        dialog.setOnAcceptButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bmob.initialize(getApplicationContext(), "ba9bd8dcb177f47278656662c57a5074");
                BmobObject bmobObject = new BmobObject();
                bmobObject.setTableName("Exception");
                bmobObject.add("exception", getIntent().getStringExtra("exception"));
                int ver = 0;
                try {
                    PackageManager manager = getApplicationContext().getPackageManager();
                    PackageInfo info = manager.getPackageInfo(getApplicationContext().getPackageName(), 0);
                    ver = info.versionCode;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                bmobObject.add("version", ver);
                bmobObject.update(getApplicationContext());
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
