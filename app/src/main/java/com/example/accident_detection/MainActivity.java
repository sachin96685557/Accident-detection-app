package com.example.accident_detection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    public static final String FILE_NAME = "example.txt";
    String filepath;
     String firstNum,bloodGrp,secondNum , thirdNum ;
     public static String no1,no2,no3,bgrp;
    EditText txt1,txt2 ,txt3;
    EditText txtO;
    Button btsave ,btlod;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt1 = findViewById(R.id.num1);
        txt2 = findViewById(R.id.num2);
        txt3 = findViewById(R.id.num3);
        txtO = findViewById(R.id.bloodgroup);
        btsave =findViewById(R.id.save);
        btlod = findViewById(R.id.load);
        filepath = "MyFileDir";
            Intent i = new Intent(this,accService.class);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            startForegroundService(i);
        }else {
            startService(i);
        }
            btsave.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {
                    if(txt1.getText()!=null)
                        firstNum=txt1.getText().toString();
                    if(txt2.getText()!=null)
                        secondNum=txt2.getText().toString();
                    if(txt3.getText()!=null)
                        thirdNum=txt3.getText().toString();
                    if(txtO.getText()!=null)
                        bloodGrp=txtO.getText().toString();
                    FileOutputStream fos = null;
                    try {
                        fos = openFileOutput(FILE_NAME,MODE_PRIVATE);
                        OutputStreamWriter osw = new OutputStreamWriter(fos);
                        osw.append(firstNum);
                        osw.append("\n");
                        osw.append(secondNum);
                        osw.append("\n");
                        osw.append(thirdNum);
                        osw.append("\n");
                        osw.append(bloodGrp);
                        osw.close();
                        fos.close();
                        Toast.makeText(MainActivity.this, "saved" , Toast.LENGTH_LONG).show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

        btlod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileInputStream fis = null ;
                try {
                    fis = openFileInput(FILE_NAME);
                    InputStreamReader isr = new InputStreamReader(fis);
                    BufferedReader br = new BufferedReader(isr);
                    no1 = br.readLine();
                    no2 = br.readLine();
                    no3 = br.readLine();
                    bgrp = br.readLine();

                    br.close();
                    txt1.setText(no1.toString());
                    txt2.setText(no2.toString());
                    txt3.setText(no3.toString());
                    txtO.setText(bgrp.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menuu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.about:
                    Intent io =new Intent(this,about.class);
                    startActivity(io);
                //Toast.makeText(this, "about selected", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.edit:

                Toast.makeText(this, "edit selected", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.servicestop:
                Intent i = new Intent(this,accService.class);
                stopService(i);
                Toast.makeText(this, "stoping services", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.exit:

                System.exit(1);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }
}