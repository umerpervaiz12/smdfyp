package com.example.umer.smd;


import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;
import android.telephony.gsm.SmsManager;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.location.Location;

/**
 * Implementation of App Widget functionality.
 */
@SuppressWarnings("ALL")
public class NewAppWidget extends AppWidgetProvider {

    private GoogleMap mMap;
    public static String ACTION_WIDGET_RECEIVER1 = "ActionReceiverWidget1";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int i = 0; i < appWidgetIds.length; i++) {
            int currentWidgetId = appWidgetIds[i];
            SmsManager sms = SmsManager.getDefault();

            final double[] lng = {0};
            final double[] lat = {0};
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

            // check if GPS enabled
            if(gps.canGetLocation()){

                lng[0] = gps.getLatitude();
                lat[0] = gps.getLongitude();

                // \n is for new line

            }else{
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
            String lon1=String.valueOf(lng[0]);
            String lat1=String.valueOf(lat[0]);
            String messagef="I need immidiate help" + "-" +lon1 + "-" + lat1;
            //sms.sendTextMessage("03477884564", null,messagef, null, null);

            Toast.makeText(context, "widget added", Toast.LENGTH_SHORT).show();
            RemoteViews views = new RemoteViews(context.getPackageName(),
                    R.layout.new_app_widget);
            Intent mIntent1 = new Intent(context, NewAppWidget.class);
            mIntent1.setAction(ACTION_WIDGET_RECEIVER1);
            //mIntent1.putExtra("msg", "Uw locatie is opgeslagen met long "+ lng +" en lat " + lat);
            mIntent1.putExtra("msg", messagef);
            PendingIntent mPendingIntent1 = PendingIntent.getBroadcast(context,
                    0, mIntent1, 0);

            views.setOnClickPendingIntent(R.id.button2, mPendingIntent1);

            // Update the widget
            appWidgetManager.updateAppWidget(appWidgetIds, views);

        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        // v1.5 fix that doesn't call onDelete Action
        final String action = intent.getAction();


        // check, if our Action was called
        // Save current location in database
        if (intent.getAction().equals(ACTION_WIDGET_RECEIVER1)) {
            String msg = "null";
            try {
                msg = intent.getStringExtra("msg");
            } catch (NullPointerException e) {
                Log.e("Error", "msg = null");
            }
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage("03477884564", null,msg, null, null);
        }
        super.onReceive(context, intent);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);
        // Create an Intent to launch MainActivity
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.appwidget_text,pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);

    }


}


