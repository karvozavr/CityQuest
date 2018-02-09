package ru.spbau.mit.karvozavr.cityquest.quest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

public class GeoQuestStep extends AbstractQuestStep implements Serializable {

    private static final double targetMaximalDistance = 25.0;

    private final double targetLocationLatitude;
    private final double targetLocationLongitude;

    public GeoQuestStep(String title,
                        String description,
                        String goal,
                        double targetLatitude,
                        double targetLongitude,
                        String image) {
        super(title, description, goal, "geo_quest_step_label", image);
        targetLocationLatitude = targetLatitude;
        targetLocationLongitude = targetLongitude;
    }

    @Override
    public void check(Activity context) {
        final ProgressDialog progressDialog = ProgressDialog.show(context, "", "Checking position");

        new Handler().postDelayed(() -> {
            LocationManager locationManager =
                    (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

            if (ServiceProvider.getLocationAccess(context)) {
                Location location = getLastKnownLocation(locationManager);

                if (location == null) {
                    Toast.makeText(context, "Failed to get location. Check GPS settings.", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    return;
                }

                Location targetLocation = new Location("target");
                targetLocation.setLatitude(targetLocationLatitude);
                targetLocation.setLongitude(targetLocationLongitude);
                double distance = targetLocation.distanceTo(location);

                if (distance < targetMaximalDistance) {
                    Toast.makeText(context, "Yes!" + Double.valueOf(distance).toString(), Toast.LENGTH_LONG).show();
                    QuestController.proceedToNextStep(context);
                } else {
                    Toast.makeText(context, "No" + Double.valueOf(distance).toString(), Toast.LENGTH_LONG).show();
                }
            }

            progressDialog.dismiss();
        }, 100);
    }

    /**
     * Returns last known location with highest possible accuracy.
     */
    @SuppressLint("MissingPermission")
    private Location getLastKnownLocation(LocationManager locationManager) {
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;

        for (String provider : providers) {

            locationManager.requestSingleUpdate(provider, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            }, null);

            @SuppressLint("MissingPermission")
            Location knownLocation = locationManager.getLastKnownLocation(provider);
            if (knownLocation == null) {
                continue;
            }

            if (bestLocation == null || knownLocation.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = knownLocation;
            }
        }

        return bestLocation;
    }
}
