package com.sevya.vtvhmobile;

/**
 * Created by abhinaym on 8/10/15.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.sevya.vtvhmobile.db.DataBaseAdapter;
import com.sevya.vtvhmobile.util.SOAPServices;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class SplashScreen extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;
    private Context context;
    private static List soapServicesList;
    DataBaseAdapter databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        databaseHelper=new DataBaseAdapter(this);

        String url="http://103.42.248.146:2001/VTVHQuickSaleService.asmx";


        Cursor cursor=databaseHelper.getServerCredentials();
        if (cursor.getCount()==0)
        {
            databaseHelper.insertServerCredentials(url, "");

        }
        else{
            databaseHelper.updateServerCredentials(url, "");
        }

        cursor=databaseHelper.getServerCredentials();
        cursor.moveToFirst();
        String hostaddress=cursor.getString(cursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.HOSTNAME));



        Log.d("splash screen ip",""+hostaddress);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcomescreen);
        try {
            AssetManager assetManager = getApplicationContext().getAssets();
            BufferedReader reader = new BufferedReader(new InputStreamReader(assetManager.open("SOAPServices.json")));
            String s;
            StringBuffer buffer = new StringBuffer();
            while((s=reader.readLine())!=null){
                buffer.append(s);
            }

            String newBuffer=buffer.toString().replace("#SOAP_URL#",hostaddress);
            SOAPServices.loadServices(newBuffer);

        }catch(Exception e){
            e.printStackTrace();
        }
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    protected void getConfigInfo(){

    }

}