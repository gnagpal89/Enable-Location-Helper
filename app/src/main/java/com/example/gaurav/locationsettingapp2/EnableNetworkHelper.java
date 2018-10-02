package com.example.gaurav.locationsettingapp2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;


public class EnableNetworkHelper implements IEnableSetting {

    public static void showNetworkEnableDialog(final Activity activity) {
//        if (!ConnectionManager.isInternetConnected(activity)) {
//            enableNetworkService(activity);
//            return;
//        }
        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setMessage("Internet Not Working. Please enable it from Data Usage Settings.");
        dialog.setPositiveButton("Enable Internet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                if (activity == null) {
                    return;
                }
                Intent intent;
                if(BuildConfig.VERSION_CODE >= Build.VERSION_CODES.M){
                    intent = new Intent(Intent.ACTION_MAIN);
                    intent.setClassName(
                            "com.android.settings",
                            "com.android.settings.Settings$DataUsageSummaryActivity");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                } else {
                    intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                }
                activity.startActivity(intent);


            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                if (activity == null) {
                    return;
                }
                Toast.makeText(activity, "Enable network to continue", Toast.LENGTH_SHORT).show();
                showNetworkEnableDialog(activity);
            }
        });
        dialog.setCancelable(false);
        if (!(activity).isFinishing()) {
            dialog.show();

        }

    }

    static void enableNetworkService(Activity activity){
        if (!ConnectionManager.isInternetConnected(activity)) {
            EnableNetworkHelper.showNetworkEnableDialog(activity);
            return;
        }
    }

    public static void checkInternetConnection(Activity activity) {
        if (!ConnectionManager.isInternetConnected(activity)) {
            EnableNetworkHelper.showNetworkEnableDialog(activity);
            return;
        }
    }

    @Override
    public boolean isEnabled(Activity activity) {
        return ConnectionManager.isInternetConnected(activity);
    }

    @Override
    public void enable(Activity activity) {
        showNetworkEnableDialog(activity);
    }

}
