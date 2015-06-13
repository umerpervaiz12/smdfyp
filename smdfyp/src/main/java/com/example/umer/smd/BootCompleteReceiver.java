package com.example.umer.smd;

/**
 * Created by Umer on 6/13/2015.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;

@SuppressWarnings("ALL")
public class BootCompleteReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Intent serviceIntent = new Intent(context, smsservice.class);
            serviceIntent.setAction("com.example.umer.smd.smsservice");
            context.startService(serviceIntent);


            GPSTracker gps;


            //String locationProvider = LocationManager.NETWORK_PROVIDER;
// Or use LocationManager.GPS_PROVIDER
            //LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            //Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);


            //lng[0] = lastKnownLocation.getLongitude();
            //lat[0] = lastKnownLocation.getLatitude();

            //  lng[0] = 74.000000;
            // lat[0] = 34.000000;

            gps = new GPSTracker(context);
            double[] lat1 = {0};
            double[] lng = {0};

            // check if GPS enabled
            if (gps.canGetLocation()) {


                lng[0] = gps.getLatitude();

                lat1[0] = gps.getLongitude();

                // \n is for new line

            } else {
                // can't get location
                // GPS or Network is not enabled
                // Ask user to enable GPS/network in settings
                gps.showSettingsAlert();
            }

            // Define a listener that responds to location updates
            LocationListener locationListener = new LocationListener() {
                public void onLocationChanged(Location location) {
                    // Called when a new location is found by the network location provider.
                    if (location != null) {
                        // lng[0] = location.getLongitude();
                        // lat[0] = location.getLatitude();
                    }

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

            };

            String lan1 = String.valueOf(lng[0]);
            String lati1 = String.valueOf(lat1[0]);
            String messagef = "Here is location of Mobile-" + " https://maps.google.com/maps?q=" + lan1 + "," + lati1;
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage("03477884564", null, messagef, null, null);


        }
    }

}
