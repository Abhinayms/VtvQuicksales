package com.sevya.vtvhmobile.webservices;

import android.util.Log;
import android.widget.Toast;

import com.sevya.vtvhmobile.models.CartModel;
import com.sevya.vtvhmobile.models.ResponseStatus;


import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;
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

            for(ServiceParams param : params) {
                request.addProperty(param.getParamAlias(), param.getParams());
                envelope.addMapping(NAMESPACE, param.getParamType().getSimpleName(), param.getParamType());
            }
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
            status =  new ResponseStatus(500, ""+e.toString());
        }
        return status;
    }

    public ResponseStatus callCartService(JSONObject soapService, List<CartModel> params){
        try {
            NAMESPACE = soapService.get("targetNameSpace").toString();
            METHOD_NAME = soapService.get("method").toString();
            SOAP_ACTION = NAMESPACE+METHOD_NAME;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;

            SoapObject soapLogs = new SoapObject(NAMESPACE, "cartModelList");

            for(CartModel param : params) {
                soapLogs.addProperty(param.getClass().getSimpleName(), param);
            }

            request.addSoapObject(soapLogs);

            envelope.setOutputSoapObject(request);

            envelope.addMapping(NAMESPACE, "CartModel", new CartModel().getClass());

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


}
