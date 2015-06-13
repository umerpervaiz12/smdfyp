package com.example.umer.server;

import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private float distanceBetween(LatLng latLng1, LatLng latLng2) {

        Location loc1 = new Location(LocationManager.GPS_PROVIDER);
        Location loc2 = new Location(LocationManager.GPS_PROVIDER);
        loc1.setLatitude(latLng1.latitude);
        loc1.setLongitude(latLng1.longitude);

        loc2.setLatitude(latLng2.latitude);
        loc2.setLongitude(latLng2.longitude);


        return loc1.distanceTo(loc2);
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
       DatabaseOperations DB=new DatabaseOperations(this);
        List<TableData> Emergencylist=DB.getAllContacts();

        float c= (float) 0.5;
        float d= (float) 0.5;


        LatLng department=new LatLng(31.4315192,73.2842197 );
        mMap.addMarker(new MarkerOptions().position(department).title("Emeregency Department").anchor(c, d).icon(BitmapDescriptorFactory.fromResource(R.drawable.department)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(department, 1));
        for(int i=0;i<Emergencylist.size();i++)
        {
            TableData table=new TableData();
            table=Emergencylist.get(i);
            String lat=table.getlatitude();
            String lan=table.getlongitude();

          //  double finallat=Double.parseDouble(lat);
           // double finallon=Double.parseDouble(lan);

            String Title=table.getphonenumber();
          String  details=table.getdetails().toLowerCase();


            String message;

            int option=0;
            if(details.contains("ambulance"))
            {
                message="Ambulance";
                option=0;
            }
            else if(details.contains("police"))
            {
                message="Police";
                option=1;
            }
            else if(details.contains("firebrigade"))
            {
                message="Firebrigade";
                option=2;
            }
            else if(details.contains("immediate"))
            {
                message="Immidiate";
                option=3;
            }
            else
            {
                message="undefined";
                option=4;
            }






            Double finallat= Double.valueOf(lat);
            Double finallong= Double.valueOf(lan);

            LatLng temp=new LatLng(finallat,finallong);

            float distance=distanceBetween(department,temp);

            String finalmsg=Title +":-"+message+" => Distance :  "+String.valueOf(distance) ;

            if(option==0) {


                mMap.addMarker(new MarkerOptions().position(temp).title(finalmsg).anchor(c, d).icon(BitmapDescriptorFactory.fromResource(R.drawable.mapicon1)));
            }
            else if (option==1)
            {
                mMap.addMarker(new MarkerOptions().position(temp).title(finalmsg).anchor(c, d).icon(BitmapDescriptorFactory.fromResource(R.drawable.police)));
            }

            else if (option==2)
            {
                mMap.addMarker(new MarkerOptions().position(temp).title(finalmsg).anchor(c, d).icon(BitmapDescriptorFactory.fromResource(R.drawable.police)));
            }
            else if (option==3)
            {
                mMap.addMarker(new MarkerOptions().position(temp).title(finalmsg).anchor(c, d).icon(BitmapDescriptorFactory.fromResource(R.drawable.police)));
            }
            else if (option==4)
            {
                mMap.addMarker(new MarkerOptions().position(temp).title(finalmsg).anchor(c, d).icon(BitmapDescriptorFactory.fromResource(R.drawable.police)));
            }

            mMap.addPolyline(new PolylineOptions()
                    .add(department, temp)
                    .width(5)
                    .color(Color.rgb(255, 161, 74)));

        }


    }
}
