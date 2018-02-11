package ru.spbau.mit.karvozavr.cityquest.quest;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Class to get needed services and permissions.
 * <p>
 * All permissions are must-have: if they are not granted, then app will not work properly.
 */
public class ServiceProvider {

    public static final int PERMISSION_REQUEST_INTERNET = 0;
    public static final int PERMISSION_REQUEST_LOCATION = 1;

    public static boolean getInternetAccess(Activity activity) {
        if (!getInternetPermission(activity))
            return false;

        return true;
    }

    public static boolean getLocationAccess(Activity activity) {
        // Ensure we have permissions
        if (!getLocationPermission(activity))
            return false;

        // Trying to get location manager
        LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);

        // Enable GPS provider
        boolean enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (!enabled) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            activity.startActivity(intent);
            return false;
        }

        return true;
    }

    private static boolean getInternetPermission(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.INTERNET}, 0);
            return false;
        }

        return true;
    }

    private static boolean getLocationPermission(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            return false;
        }

        return true;
    }
}
