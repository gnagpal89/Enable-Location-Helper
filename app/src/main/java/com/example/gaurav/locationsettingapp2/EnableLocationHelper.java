package com.example.gaurav.locationsettingapp2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


/**
 * Helper class to prompt user to enable GPS and Location Services
 */
public class EnableLocationHelper implements IEnableSetting{
    private static final String TAG = EnableLocationHelper.class.getSimpleName();

    private static final int REQUEST_CHECK_SETTINGS = 1000;
    public static final int ACCESS_FINE_LOCATION_INTENT_ID = 3;
    private static boolean isPopUpShown = false;

    private static boolean isLocationEnabled;

    /**
     * Enable GPS and Location Services
     * and set the mode to High Accuracy
     *
     * @param activity
     */
    public static boolean switchOnGPSProgrammatically(final Activity activity) {
//        if (checkIfLocationIsEnabled(activity)) {
//            enableLocationService(activity);
//            return;
//        }

        // check whether current location settings are satisfied:
        Task<LocationSettingsResponse> task = LocationServices.getSettingsClient(activity).checkLocationSettings(buildLocationSettingsRequest());
        task.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {

            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    // All location settings are satisfied. The client can initialize location
                    // requests here.
                    LocationSettingsStates state = response.getLocationSettingsStates();
                    isLocationEnabled = state.isLocationUsable();
                } catch (ApiException e) {
                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // Cast to a resolvable exception.
                            ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                            // Location settings are not satisfied. But could be fixed by showing the
                            // user a dialog.
                            try {
                                resolvableApiException.startResolutionForResult(activity, REQUEST_CHECK_SETTINGS);

                            } catch (IntentSender.SendIntentException e1) {
                                e1.printStackTrace();
                            }
                            break;

                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            //open setting and switch on GPS manually
                            switchOnGPSManually(activity);
                            break;
                    }
                }
            }
        });
        return isLocationEnabled;
    }

    /**
     * Check Location permission granted or not
     *
     * @param activity
     */
    public static boolean checkPermission(Activity activity) {
            if (ContextCompat.checkSelfPermission(activity,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED)
                return requestLocationPermission(activity);
            else
                return enableLocation(activity);
    }

    /*  Show Popup to access User Permission  */
    private static boolean requestLocationPermission(Activity activity) {
//        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
//            ActivityCompat.requestPermissions(activity,
//                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
//                    ACCESS_FINE_LOCATION_INTENT_ID);
//            Toast.makeText(activity, "Enable location services.", Toast.LENGTH_SHORT).show();
//
//        } else {
//            ActivityCompat.requestPermissions(activity,
//                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                    ACCESS_FINE_LOCATION_INTENT_ID);
//        }

        ActivityCompat.requestPermissions(activity,
                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_FINE_LOCATION_INTENT_ID);
        return false;
    }

    /**
     * If Google play services are available, switch on GPS programmatically
     * else open the settings menu
     *
     * @param activity
     */
    public static boolean enableLocation(Activity activity) {
        //EnableNetworkHelper.checkInternetConnection(activity);

        if (isGooglePlayServicesAvailable(activity)) { // Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            return switchOnGPSProgrammatically(activity);
        } else {
            return switchOnGPSManually(activity);
        }
    }


    /**
     * Check if Google Play Services are available on the device
     *
     * @param context
     * @return
     */
    private static boolean isGooglePlayServicesAvailable(Context context) {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(context);
        return resultCode == ConnectionResult.SUCCESS;
    }

    /**
     * Prompts user to turn on location services and Switch to High Accuracy mode
     * from the settings menu
     *
     * @param activity
     */
    private static boolean switchOnGPSManually(final Activity activity) {
        if (checkIfLocationIsEnabled(activity)) {
            return true;
        }
        Log.i(TAG, "enable Location");
        if (!isPopUpShown) {
            final String enableLocationMsg = activity.getResources().getString(R.string.enable_location_high_acc_msg);
            AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
            dialog.setMessage(enableLocationMsg);
            dialog.setPositiveButton(activity.getResources().getString(R.string.enable_location_positive_btn),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            // TODO Auto-generated method stub
                            isPopUpShown = false;

                            Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            activity.startActivity(myIntent);
                            isLocationEnabled = checkIfLocationIsEnabled(activity);
                        }
                    });
            dialog.setNegativeButton(activity.getResources().getString(R.string.enable_location_negative_btn),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            //isHighAccuracyLocationEnabled = false;
                            isPopUpShown = false;
                            Toast.makeText(activity, enableLocationMsg, Toast.LENGTH_LONG).show();
                            isLocationEnabled = switchOnGPSManually(activity);
                        }
                    });
            dialog.setCancelable(false);
            if (!activity.isFinishing()) {
                dialog.show();
                isPopUpShown = true;
            }
        }
        return isLocationEnabled;
    }

    /**
     * Create a {@link LocationSettingsRequest.Builder} LocationSettingsRequest.Builder
     * and add all of the {@link LocationRequest} LocationRequests that the app will be using.
     * Return LocationSettingRequest
     */
    private static LocationSettingsRequest buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(new LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY));
        return builder.build();
    }

    public static boolean checkIfLocationIsEnabled(Activity activity) {
        LocationManager lm = null;
        boolean gps_enabled = false, network_enabled = false;
        if (lm == null)
            lm = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }
        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        return gps_enabled && network_enabled;
    }

    public static boolean enableLocationService(Activity activity) {

        if (!checkIfLocationIsEnabled(activity)) {
            return enableLocation(activity);
        }

//        if (!ConnectionManager.isInternetConnected(activity)) {
//            EnableNetworkHelper.showNetworkEnableDialog(activity);
//            return;
//        }

        //if (BaseActivity.this.isServiceRunning(GpsLocationService.class)) {
        //    return;
        //}

        //this.startLocationService(true);

        return true;
    }

    @Override
    public boolean isEnabled(Activity activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            return checkPermission(activity);
        } else {
            return enableLocation(activity);
        }
    }

    @Override
    public void enable(Activity activity) {
        checkPermission(activity);
    }
}
