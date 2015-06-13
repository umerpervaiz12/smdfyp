package com.example.umer.smd;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import com.google.android.gms.ads.*;
import com.google.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class adactivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adactivity);
        // values/strings.xml.
        AdView mAdView = (AdView) findViewById(R.id.adview1);

        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        com.google.android.gms.ads.AdRequest adRequest = new com.google.android.gms.ads.AdRequest.Builder().build();

        // Start loading the ad in the background.
        // AdSize adSize=new AdSize(10,10);
        // mAdView.setAdSize(adSize);
      //  mAdView.setAdUnitId("ca-app-pub-4457199248111480/8999794544");
        mAdView.loadAd(adRequest);
    }
}
