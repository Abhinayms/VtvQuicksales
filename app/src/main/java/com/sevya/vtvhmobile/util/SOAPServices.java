package com.sevya.vtvhmobile.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by abhinaym on 5/11/15.
 */
public class SOAPServices {

    private static JSONArray servicesList = null;
    private static String targetNameSpace = "";
    private static String soapURL = "";

    public static void loadServices(String configData){
        try {
            JSONObject json = new JSONObject(configData);
            servicesList = (JSONArray)json.get("SOAPServices");
            targetNameSpace = json.get("targetNameSpace").toString();
            soapURL = json.get("soapUrl").toString();
        }catch(JSONException exception){
            exception.printStackTrace();
        }
    }

    public static JSONObject getServices(String serviceName){
        for(int index = 0; index < servicesList.length(); index++){
            try {
                JSONObject jsonObj = servicesList.getJSONObject(index);
                if (jsonObj.get("soapServiceName").equals(serviceName)){
                    jsonObj.put("targetNameSpace", targetNameSpace);
                    jsonObj.put("soapURL", soapURL);
                    return jsonObj;
                }
            }catch (JSONException exception){
                exception.printStackTrace();
            }
        }
        return null;
    }
}
