package com.example.umer.server;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.umer.server.CustomListAdapter;
import com.example.umer.server.DatabaseOperations;
import com.example.umer.server.R;
import com.example.umer.server.TableData;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {


    private SwipeRefreshLayout swipeContainer;
    Context con=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity3);

        Intent serviceIntent = new Intent(this,incomingmessage.class);
        serviceIntent.setAction("com.example.umer.smd.incomingmessage");
        startService(serviceIntent);
        final DatabaseOperations dbo=new DatabaseOperations(this);
        swipeContainer= (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        ArrayList data = getListData();
        final ListView lv1 = (ListView) findViewById(R.id.mainListView);
        lv1.setAdapter(new CustomListAdapter(con, data));
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                TableData table=(TableData) parent.getItemAtPosition(position);
                String data=table.getdetails();
                String lat=table.getlatitude();
                String lon=table.getlongitude();

                String phone=table.getphonenumber();

                Intent i= new Intent(MainActivity.this,Details.class);
                // startActivity(i);
                // i.putExtra("ph",ph);
                String Title;
                if(data.contains("police"))
                {
                    Title=phone + "@"+"police";
                }

                else if(data.contains("ambulance"))
                {
                    Title=phone + "@"+"ambulance";
                }
                else if(data.contains("firebrigade"))
                {
                    Title=phone + "@"+"firebrigade";
                }
                else
                {
                    Title=phone + "@"+"immediate";
                }

                i.putExtra("Title",Title);
                i.putExtra("lat",lat);
                i.putExtra("lon",lon);
                startActivity(i);
            }
        });
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList data1 = getListData();
                        final ListView lv2 = (ListView) findViewById(R.id.mainListView);
                        lv2.setAdapter(new CustomListAdapter(con, data1));
                        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view,
                                                    int position, long id) {


                                TableData table=(TableData) parent.getItemAtPosition(position);
                                String data=table.getdetails();
                                String lat=table.getlatitude();
                                String lon=table.getlongitude();

                                String phone=table.getphonenumber();

                                Intent i= new Intent(MainActivity.this,Details.class);

                                String Title;
                                if(data.contains("police"))
                                {
                                    Title=phone + "@"+"police";
                                }

                                else if(data.contains("ambulance"))
                                {
                                    Title=phone + "@"+"ambulance";
                                }
                                else if(data.contains("firebrigade"))
                                {
                                    Title=phone + "@"+"firebrigade";
                                }
                                else
                                {
                                    Title=phone + "@"+"immediate";
                                }

                                i.putExtra("Title",Title);
                                i.putExtra("lat",lat);
                                i.putExtra("lon",lon);
                                startActivity(i);

                                Toast.makeText(getApplicationContext(),
                                        "data " + data, Toast.LENGTH_LONG).show();


                            }
                        });
                        swipeContainer.setRefreshing(false);
                    }
                }, 5000);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity3, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private ArrayList<TableData> getListData() {
        DatabaseOperations dbo=new DatabaseOperations(this);
        return dbo.getAllContacts();
    }
}
