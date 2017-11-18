package ru.spbau.mit.karvozavr.cityquest.quest;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Class to get needed permissions.
 *
 * All permissions are must-have: if they are not granted, then app will not work properly.
 */
public class PermissionsProvider {

    public static final int PERMISSION_REQUEST_INTERNET = 0;
    public static final int PERMISSION_REQUEST_LOCATION = 1;

    public static void getInternetPermission(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.INTERNET}, 0);
        }
    }

    public static void getLocationPermission(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }
}
