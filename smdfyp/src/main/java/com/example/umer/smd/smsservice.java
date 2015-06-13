package com.example.umer.smd;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsMessage;
import android.util.Log;

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
                .setContentText("New Emergency message Recieved")
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

                    strMessage += "SMS from " + strMsgSrc + " : " + strMsgBody;

                   // Log.i(TAG, strMessage);
                    Log.i(TAG, "i am in in serivce nigga");
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
        Notify("You've received new message","");

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