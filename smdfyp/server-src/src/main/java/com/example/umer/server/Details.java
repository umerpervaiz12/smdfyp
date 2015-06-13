package com.example.umer.server;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

@SuppressWarnings("ALL")
public class Details extends FragmentActivity{

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
        float c= (float) 0.5;
        float d= (float) 0.5;

        Intent intent=getIntent();

       String lat1= intent.getCharSequenceExtra("lat").toString();
      String  lon1= intent.getCharSequenceExtra("lon").toString();
     String Title1=   intent.getCharSequenceExtra("Title").toString();

     //   String lat1= "73.0842197";
      //  String  lon1= "31.7315192";
      //  String Title1=   "+923477884564@police";

        char[] phone = new char[30];

      phone=  Title1.toCharArray();
        char [] temp1=new char[13];
        for(int i=0;i<13;i++)
        {
            temp1[i]=phone[i];
        }


        final String phonenumber =String.valueOf(temp1);

        Double finallat= Double.valueOf(lat1);
        Double finallong= Double.valueOf(lon1);






        LatLng department=new LatLng(31.4315192,73.2842197 );
        mMap.addMarker(new MarkerOptions().position(department).title("Emeregency Department").anchor(c, d).icon(BitmapDescriptorFactory.fromResource(R.drawable.department)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(department, 10));

            String lat=lat1;
            String lan=lon1;

            //  double finallat=Double.parseDouble(lat);
            // double finallon=Double.parseDouble(lan);

            String Title=phonenumber;
            String  details=Title1.toLowerCase();


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

            LatLng temp=new LatLng(finallat,finallong);

            float distance=distanceBetween(department,temp);

            String finalmsg=Title +":-"+" => Distance:"+String.valueOf(distance/1000)+"km";

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

         /*   mMap.addPolyline(new PolylineOptions()
                    .add(department, temp)
                    .width(5)
                    .color(Color.rgb(255, 161, 74)));
                    */

            String url = getDirectionsUrl(department, temp);

            DownloadTask downloadTask = new DownloadTask();

            // Start downloading json data from Google Directions API
            downloadTask.execute(url);

        Button button = new Button(this);
        button.setText("Send Acknowledgment message");
        addContentView(button, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT));

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                String messagef="Your Request is acknowleged.Rescue team will be there soon";
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(phonenumber, null, messagef, null, null);


            }
        });



        }


      private String getDirectionsUrl(LatLng origin,LatLng dest){

        // Origin of route
        String str_origin = "origin="+origin.latitude+","+origin.longitude;

        // Destination of route
        String str_dest = "destination="+dest.latitude+","+dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin+"&"+str_dest+"&"+sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;

        return url;
    }
    /** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while( ( line = br.readLine()) != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d("Exception while downloading url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }





    // Fetches data from url passed
    private class DownloadTask extends AsyncTask<String, Void, String>{

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try{
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }

    /** A class to parse the Google Places in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();

            // Traversing through all the routes
            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(2);
                lineOptions.color(Color.RED);
            }

            // Drawing polyline in the Google Map for the i-th route
            mMap.addPolyline(lineOptions);
        }
    }



}
