package com.sevya.vtvhmobile.models;



import android.content.Context;
import android.widget.Toast;

/**
 * Created by abhinaym on 28/9/15.
 */
public class Message {

    public static void message(Context context,String message)
    {
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }
}