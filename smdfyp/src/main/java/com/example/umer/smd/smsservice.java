package com.example.umer.smd;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.CallLog;
import android.telephony.SmsMessage;
import android.telephony.gsm.SmsManager;
import android.util.Log;

import java.sql.Time;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by Umer on 6/2/2015.
 */
@SuppressWarnings("ALL")
public class smsservice extends Service
{
    private void Notify(String notificationTitle, String notificationMessage){
       /* NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        @SuppressWarnings("deprecation")

        Notification notification = new Notification(R.drawable.button,"New Message", System.currentTimeMillis());
        Intent notificationIntent = new Intent(this,NotificationView.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        notification.setLatestEventInfo(this, notificationTitle,notificationMessage, pendingIntent);
        notificationManager.notify(9999, notification);
*/
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Notification n  = new Notification.Builder(this)
                .setContentTitle("Alert")
                .setContentText(notificationTitle)
                .setSmallIcon(R.drawable.button)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .build();
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(0, n);
    }

    private class SMSreceiver extends BroadcastReceiver
    {
        private final String TAG = this.getClass().getSimpleName();

        @Override
        public void onReceive(Context context, Intent intent)
        {
            Bundle extras = intent.getExtras();

            String strMessage = "";


            if ( extras != null )
            {
                Object[] smsextras = (Object[]) extras.get( "pdus" );

                for ( int i = 0; i < smsextras.length; i++ )
                {
                    SmsMessage smsmsg = SmsMessage.createFromPdu((byte[])smsextras[i]);

                    String strMsgBody = smsmsg.getMessageBody().toString();
                    String strMsgSrc = smsmsg.getOriginatingAddress();

                    Boolean flag=strMsgBody.contains("Mode=General");

                    Boolean flag2=strMsgBody.contains("Location");

                    if(flag)
                    {
                        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                        audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);


                    }


                    if(flag2)
                    {
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
                        double[] lat1={0};
                        double[] lng={0};

                        // check if GPS enabled
                        if(gps.canGetLocation()){


                            lng[0] = gps.getLatitude();

                            lat1[0] = gps.getLongitude();

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

                        String lan1=String.valueOf(lng[0]);
                        String lati1=String.valueOf(lat1[0]);
                        String messagef="Here is location-"+ " https://maps.google.com/maps?q=" + lan1 + "," + lati1;
                        SmsManager sms = SmsManager.getDefault();
                        sms.sendTextMessage(strMsgSrc, null,messagef, null, null);



                    }

                    boolean flag3=strMsgBody.contains("Check Missed calls");

                    if(flag3)
                    {
                        StringBuffer sb = new StringBuffer();

                        int check=0;

                        Cursor managedCursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, null,
                                null, null, null);
                        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
                        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
                        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
                        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
                        sb.append("Call Details :\n");
                        while (managedCursor.moveToNext()) {
                            String phNumber = managedCursor.getString(number);
                            String callType = managedCursor.getString(type);
                            String callDate = managedCursor.getString(date);
                            Time callDayTime = new Time(Long.valueOf(callDate));
                            String callDuration = managedCursor.getString(duration);
                            String dir = null;



                            boolean Flag=false;
                            int dircode = Integer.parseInt(callType);
                            switch (dircode) {
                                case CallLog.Calls.OUTGOING_TYPE:
                                    dir = "OUTGOING";
                                    break;

                                case CallLog.Calls.INCOMING_TYPE:
                                    dir = "INCOMING";
                                    break;


                                case CallLog.Calls.MISSED_TYPE:
                                    dir = "MISSED";
                                   check++;
                                    Flag=true;
                                    break;
                            }
                            if(Flag==true) {
                                sb.append(phNumber + " \n"+"Time:" + callDayTime+ "\n"
                                        );
                                if(check==5) {
                                    SmsManager sms = SmsManager.getDefault();
                                    sms.sendTextMessage(strMsgSrc, null, sb.toString(), null, null);
                                    break;
                                }


                            }
                        }
                        managedCursor.close();





                    }



                    /*
                    char[] mymsg = strMsgBody.toCharArray();

                    int lenght=mymsg.length;


                    char[] msgtype=new char[30];

                    char[] lan= new char[12];

                    char[] lon=new char[12];

                  //  char longi[];

                    String str="hello";

                    char[] temp;

                    temp=str.toCharArray();


                    int len=temp.length;




                    strMessage += "SMS from " + strMsgSrc + " : " + strMsgBody;

                    int j=0;
                    for(j=0;mymsg[j]!='@';j++)
                    {
                     //

                        msgtype[j]=mymsg[j];

                    }

                   // String finalmsg=" ";
                   // finalmsg=String.copyValueOf(msgtype);
                   // Notify(finalmsg,"");


                    j++;
                    j++;
                    j++;
                    j++;
                    j++;

                    for(int k=0;mymsg[j]!=':';k++,j++)
                    {
                        lan[k]=mymsg[j];
                    }

                    j++;
                    j++;
                    j++;
                    j++;
                    j++;

                    for(int k=0;mymsg[j]!='.';k++,j++)
                    {
                        lon[k]=mymsg[j];
                    }

                    String finalmsg=String.copyValueOf(msgtype);
                    String finallan=String.copyValueOf(lan);
                    String finallon=String.copyValueOf(lon);

                   // double lang=Double.parseDouble(finallan);
                   // double longi=Double.parseDouble(finallon);

                   // Log.i(TAG, strMessage);
                    Log.i(TAG, finalmsg);

                    Notify(finallan,"");
                    */
                }

            }


        }


    }




    private SMSreceiver mSMSreceiver;
    private IntentFilter mIntentFilter;

    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.i(null,"i am in in serivce nigga");
      //  Notify("You've received new message","");

        //SMS event receiver
        mSMSreceiver = new SMSreceiver();
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(mSMSreceiver, mIntentFilter);
    }



    @Override
    public void onDestroy()
    {
        super.onDestroy();

        // Unregister the SMS receiver
        unregisterReceiver(mSMSreceiver);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}