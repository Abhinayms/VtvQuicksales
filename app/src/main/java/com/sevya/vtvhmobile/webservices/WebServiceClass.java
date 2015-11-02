package com.sevya.vtvhmobile.webservices;

import com.sevya.vtvhmobile.models.ResponseStatus;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by abhinaym on 31/10/15.
 */
public class WebServiceClass {

    private String SOAP_ACTION = "";
    private String METHOD_NAME = "";
    private String NAMESPACE = "http://tempuri.org/";
    private String URL="http://192.168.1.19:1024/VTVHQuickSaleService.asmx";
    public  String errorMessage;
    final int SESSION_TIMEOUT=750000;
    private ResponseStatus status=null;
    public WebServiceClass()
    {
        errorMessage="";
    }

    public ResponseStatus getDetailsByMobileNumber(String number) throws IOException, XmlPullParserException {
        status = new ResponseStatus();

        SOAP_ACTION="http://tempuri.org/GetAccountDetails";
        METHOD_NAME="GetAccountDetails";
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("MobileNo",number);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,SESSION_TIMEOUT);
        try {
            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION, envelope);
            // Get the response
            status.setStatusCode(200);
            Object response = (Object) envelope.getResponse();
            status.setStatusResponse(response.toString());

        } catch (Exception e) {
            e.printStackTrace();
            status.setStatusCode(500);
            status.setStatusResponse("Error occured");
        }
        return status;

    }



}
