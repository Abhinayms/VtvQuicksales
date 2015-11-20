package com.sevya.vtvhmobile.webservices;

import android.util.Log;

import com.sevya.vtvhmobile.models.ResponseStatus;


import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Objects;

/**
 * Created by abhinaym on 5/11/15.
 */
public class SOAPServiceClient {

    private ResponseStatus status;
    private String SOAP_ACTION = "";
    private String METHOD_NAME = "";
    private String NAMESPACE = "";
    private final int SESSION_TIMEOUT = 750000;

    public ResponseStatus callService(JSONObject soapService, ServiceParams... params){
        try {
            NAMESPACE = soapService.get("targetNameSpace").toString();
            METHOD_NAME = soapService.get("method").toString();
            SOAP_ACTION = NAMESPACE+METHOD_NAME;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;

            /*HttpURLConnection connection = new HttpURLConnection() {
                @Override
                public void disconnect() {

                }

                @Override
                public boolean usingProxy() {
                    return false;
                }

                @Override
                public void connect() throws IOException {

                }
            }*/

            for(ServiceParams param : params) {
                request.addProperty(param.getParamAlias(), param.getParams());
                envelope.addMapping(NAMESPACE, param.getParamType().getSimpleName(), param.getParamType());
            }
//          Log.d("request",""+(request.getProperty("GetModelsForList")).toString());
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(soapService.get("soapURL").toString(), SESSION_TIMEOUT);

            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            Log.d("response", "" + response);
            if(!response.toString().equals("null")){
                status =  new ResponseStatus(200, response.toString());
            }else{
                status = new ResponseStatus(201,"couldn't find data" );
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("*****","Error Occured");
            status =  new ResponseStatus(500, "Error occured");
        }
        return status;
    }

    public ResponseStatus callServiceUsingPrimitives(JSONObject soapService, ServiceParams... params){
        try {
            NAMESPACE = soapService.get("targetNameSpace").toString();
            METHOD_NAME = soapService.get("method").toString();
            SOAP_ACTION = NAMESPACE+METHOD_NAME;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;

            for(ServiceParams param : params) {
                request.addProperty(param.getParamAlias(), param.getParams());

            }
//          Log.d("request",""+(request.getProperty("GetModelsForList")).toString());
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(soapService.get("soapURL").toString(), SESSION_TIMEOUT);

            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION, envelope);
            // Get the response
            Object response = (Object) envelope.getResponse();
            Log.d("response", "" + response);
            if(!response.toString().equals("null")){
                status =  new ResponseStatus(200, response.toString());
            }else{
                status = new ResponseStatus(201,"couldn't find data" );
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("*****","Error Occured");
            status =  new ResponseStatus(500, "Error occured");
        }
        return status;
    }

}
