package com.example.accident_detection;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import android.location.Location;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;




/**
 *
 CREATED BY --SACHIN MAURYA -----30-3-21
 */

public class abort extends AppCompatActivity  {

    Location location; // location
    double latitude; // latitude
    double longitude; // longitude

    //////////////
    Button abortBt;
    //int chk=0,chk1=1;
    LocationFinder finder;

   //double longitude = 0.0, latitude = 0.0;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.abort);
        abortBt =findViewById(R.id.abort1);
////////////////////////////////////////////////////////////////extracting no//
        FileInputStream fis = null ;
        try {
            fis = openFileInput(MainActivity.FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            MainActivity.no1 = br.readLine();
            MainActivity.no2 = br.readLine();
            MainActivity.no3 = br.readLine();
            MainActivity.bgrp = br.readLine();
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

////////
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(Build.VERSION.SDK_INT >=26){
                    ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(400, VibrationEffect.DEFAULT_AMPLITUDE));
                    Toast.makeText(abort.this,"shaking",Toast.LENGTH_SHORT).show();
                    //Intent ii = new Intent(abort.this,LocationFinder.class);
                   // ii.setClass(this,LocationFinder.class);
                   // ii.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   // startActivity(ii);


                } else {
                    ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(150);
                }
            }
        }, 5000);

        finder = new LocationFinder(this);
        if (finder.canGetLocation()) {
            latitude = finder.getLatitude();
            longitude = finder.getLongitude();

        } else {
            finder.showSettingsAlert();
        }
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                Toast.makeText(abort.this,"lat"+latitude+"--long"+longitude,Toast.LENGTH_LONG).show();
                    SmsManager sm = SmsManager.getDefault();
                    sm.sendTextMessage(MainActivity.no1, null, "Help! I've met with an accident at http://maps.google.com/?q="+String.valueOf(latitude)+","+String.valueOf(longitude) +"my blood grp="+MainActivity.bgrp , null, null);
                    sm.sendTextMessage(MainActivity.no1, null, "Nearby Hospitals http://maps.google.com/maps?q=hospital&mrt=yp&sll="+String.valueOf(latitude)+","+String.valueOf(longitude)+"&output=kml", null, null);
                    sm.sendTextMessage(MainActivity.no2, null, "Help! I've met with an accident at http://maps.google.com/?q="+String.valueOf(latitude)+","+String.valueOf(longitude) +"my blood grp="+MainActivity.bgrp , null, null);
                    sm.sendTextMessage(MainActivity.no2, null, "Nearby Hospitals http://maps.google.com/maps?q=hospital&mrt=yp&sll="+String.valueOf(latitude)+","+String.valueOf(longitude)+"&output=kml", null, null);
                    sm.sendTextMessage(MainActivity.no3, null, "Help! I've met with an accident at http://maps.google.com/?q="+String.valueOf(latitude)+","+String.valueOf(longitude) +"my blood grp="+MainActivity.bgrp , null, null);
                    sm.sendTextMessage(MainActivity.no3, null, "Nearby Hospitals http://maps.google.com/maps?q=hospital&mrt=yp&sll="+String.valueOf(latitude)+","+String.valueOf(longitude)+"&output=kml", null, null);

                    Toast.makeText(abort.this,"#send",Toast.LENGTH_SHORT).show();



                }
            },5000);

        abortBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(1);
            }
        });



    }
}
