package com.example.umer.smd;

/**
 * Created by Umer on 6/12/2015.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;

import com.splunk.mint.Mint;

public class FirstActivity extends Activity {

    ImageButton imgButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstactivity);
        Intent serviceIntent = new Intent(this,smsservice.class);
        serviceIntent.setAction("com.example.umer.smd.smsservice");
        startService(serviceIntent);

        addButtonListener();
        addbutton2listerner();
        Mint.initAndStartSession(FirstActivity.this, "e02095fa");
        Button button = (Button) findViewById(R.id.button1);

        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {

                char[] a=new char[5];

                for(int i=0;i<6;i++)
                {
                    char b=a[i];
                }
            }

        });


    }
    public void addButtonListener() {

        imgButton = (ImageButton) findViewById(R.id.imageButton);

        imgButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent=new Intent(view.getContext(),MainActivity.class);

                startActivity(intent);


            }

        });
    }

    public void addbutton2listerner() {

        imgButton = (ImageButton) findViewById(R.id.imageButton1);

        imgButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent=new Intent(view.getContext(),contactActivity.class);

                startActivity(intent);


            }

        });
    }
}