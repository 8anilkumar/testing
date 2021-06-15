package com.eminence.drive13;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.eminence.drive13.utils.GPSTracker;
import com.eminence.drive13.utils.GpsUtils;
import com.eminence.drive13.utils.YourPreference;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.List;
import java.util.Locale;

public class SplashScreen extends AppCompatActivity {
    Double lat,lng;
    private static final int REQUEST_LOCATION_PERMISSION = 100;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        // permission is granted, open the camera
                        turngpspon();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        // check for permanent denial of permission
                        if (response.isPermanentlyDenied()) {
                            Intent a = new Intent(Intent.ACTION_MAIN);
                            a.addCategory(Intent.CATEGORY_HOME);
                            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(a);
                            finishAffinity();
                            return;
                            // navigate user to app settings
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();




    }



    private void redirectionScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                GPSTracker mGPS = new GPSTracker(getApplicationContext());
                Double lat,lng;

                if (mGPS.canGetLocation) {
                    mGPS.getLocation();
                    lat=mGPS.getLatitude();
                    lng=mGPS.getLongitude();
                    //Toast.makeText(GetDocument.this, ""+address, Toast.LENGTH_SHORT).show();
                    getCompleteAddressString(lat,lng);
                }
                else
                {
                    Toast.makeText(mGPS, "location not Available", Toast.LENGTH_SHORT).show();
                }

                YourPreference yourPrefrence = YourPreference.getInstance(getApplicationContext());
                String id = yourPrefrence.getData("id");

                if (id.equalsIgnoreCase("")||id.equalsIgnoreCase(null))
                {
                    startActivity(new Intent(getApplicationContext(), Getstarted.class));
                    finish();
                }
                else {
                    startActivity(new Intent(getApplicationContext(), HotelMain.class));
                    finish();
                }


            }
        }, 2000);
    }

    private void getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            String cityName = addresses.get(0).getLocality();
            String subLocality = addresses.get(0).getSubLocality();
            String countryName = addresses.get(0).getAddressLine(2);
            YourPreference yourPrefrence = YourPreference.getInstance(getApplicationContext());
            yourPrefrence.saveData("cityname",cityName);
            yourPrefrence.saveData("sublocality",subLocality);
            // Toast.makeText(this, ""+cityName+stateName, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private  void  turngpspon()
    {

        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && hasGPSDevice(this)) {
           //  Toast.makeText(getApplicationContext(), "Gps already enabled", Toast.LENGTH_SHORT).show();
            redirectionScreen();
        } else {
            if (!hasGPSDevice(this)) {
             //   Toast.makeText(getApplicationContext(), "Gps not Supported", Toast.LENGTH_SHORT).show();
                finish();
            }
            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && hasGPSDevice(this)) {
            //     Toast.makeText(getApplicationContext(), "Gps not enabled", Toast.LENGTH_SHORT).show();
                new GpsUtils(this).turnGPSOn(new GpsUtils.OnGpsListener() {
                    @Override
                    public void gpsStatus(boolean isGPSEnable) {
                        if (isGPSEnable) {
                            redirectionScreen();
                         }
                    }
                });
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001) {
            if (resultCode == Activity.RESULT_OK) {
                redirectionScreen();
            } else {
                finish();
            }
        }
    }

    private boolean hasGPSDevice(Context context) {
        final LocationManager mgr = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        if (mgr == null)
            return false;
        final List<String> providers = mgr.getAllProviders();
        return providers.contains(LocationManager.GPS_PROVIDER);
    }
}