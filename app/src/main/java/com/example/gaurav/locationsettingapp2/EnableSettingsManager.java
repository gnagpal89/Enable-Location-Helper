package com.example.gaurav.locationsettingapp2;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class EnableSettingsManager {

    private EnableSettingsManager() {
    }

    private static List<IEnableSetting> sEnableSettingList = new ArrayList<>();

    static {
        sEnableSettingList.add(new EnableNetworkHelper());
        sEnableSettingList.add(new EnableLocationHelper());
    }

    public static boolean areSettingsEnabled(Activity activity) {
        for (IEnableSetting setting : sEnableSettingList) {
            if (!setting.isEnabled(activity)) {
                return false;
            }
        }
        return true;
    }

    public static void enableSettings(Activity activity) {
        for (IEnableSetting setting : sEnableSettingList) {
            if (!setting.isEnabled(activity)) {
                setting.enable(activity);
                break;
            }
        }
    }
}
