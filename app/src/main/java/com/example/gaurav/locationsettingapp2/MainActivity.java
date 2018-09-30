package com.example.gaurav.locationsettingapp2;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.location.LocationSettingsRequest;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private LocationSettingsRequest mLocationSettingsRequest;
    private GoogleApiClient mGoogleApiClient;
    private LocationSettingsRequest.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "In onResume");
        EnableLocationHelper.checkPermission(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.e("Req Code", "" + requestCode);
        if (requestCode == EnableLocationHelper.ACCESS_FINE_LOCATION_INTENT_ID) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED ) {

                // Success Stuff here
                Toast.makeText(this, "Location permission granted", Toast.LENGTH_SHORT).show();

            }
            else{
                // Failure Stuff
                Toast.makeText(this, "Permission required", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
