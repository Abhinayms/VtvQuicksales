package com.sevya.vtvhmobile;

/**
 * Created by abhinaym on 8/10/15.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;

import com.sevya.vtvhmobile.util.SOAPServices;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class SplashScreen extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;
    private Context context;
    private static List soapServicesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

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

            String newBuffer=buffer.toString().replace("#SOAP_URL#","http://192.168.1.19:2006/VTVHQuickSaleService.asmx");
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