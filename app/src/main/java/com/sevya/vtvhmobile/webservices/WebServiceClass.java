package com.sevya.vtvhmobile.webservices;

import android.util.Log;

import com.sevya.vtvhmobile.models.ResponseStatus;
import com.sevya.vtvhmobile.models.UserModel;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
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
    private String URL="http://192.168.1.19:2001/VTVHQuickSaleService.asmx";
    public  String errorMessage;
    final int SESSION_TIMEOUT=750000;
    private ResponseStatus status=null;
    public WebServiceClass()
    {
        errorMessage="";
    }

    public ResponseStatus getDetailsByMobileNumber(String number) throws IOException, XmlPullParserException {

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
            Object response = (Object) envelope.getResponse();
            Log.d("",""+response);
            if(!response.toString().equals("null")){
                status =  new ResponseStatus(200, response.toString());
            }else{
                status = new ResponseStatus(201,"couldn't find data" );
            }

        } catch (Exception e) {
            e.printStackTrace();
            status =  new ResponseStatus(500, "Error occured");
        }
        return status;

    }
    public ResponseStatus insertCustomerDetails(UserModel usermodel)
    {
        SOAP_ACTION="http://tempuri.org/insertdetails";
        METHOD_NAME="insertdetails";
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        /*request.addProperty("Name",name);
        request.addProperty("SurName",surName);
        request.addProperty("EmailId",emialId);
        request.addProperty("Flat",flat);
        request.addProperty("CompanyName",companyName);
        request.addProperty("TINNo",tinno);
        request.addProperty("Street",street);
        request.addProperty("City",city);
        request.addProperty("Mandal",mandal);
        request.addProperty("District",district);
        request.addProperty("State",state);
        request.addProperty("Mobile",mobile);
        request.addProperty("Phone",phone);
        request.addProperty("Country",country);
        request.addProperty("Gender",gender);
        request.addProperty("UserId",userId);
        request.addProperty("PrimaryActID",primaryactId);
        request.addProperty("DuplicateIds",duplicateIds);*/

        /*PropertyInfo pi=new PropertyInfo();
        pi.setName("userModel");
        pi.setValue(usermodel);
        //  Log.d("actid",""+usermodel.getActID());

        pi.setType(usermodel.getClass());*/
        request.addProperty("userModel", usermodel);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);
        envelope.addMapping(NAMESPACE, "UserModel", new UserModel().getClass());
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,SESSION_TIMEOUT);
        try {
            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            Log.d("",""+response);
           /* if(!response.toString().equals("null")){
                status =  new ResponseStatus(200, response.toString());
            }else{
                status = new ResponseStatus(201,"couldn't find data" );
            }*/

        } catch (Exception e) {
            e.printStackTrace();
            status =  new ResponseStatus(500, "Error occured");
        }
        return status;
    }

    public ResponseStatus updateCustomer(String name,String mobile,String companyName,String gender,String landline,String add1,String add2,
                                         String emailId,int actId)
    {
        SOAP_ACTION="http://tempuri.org/UpdateCustomer";
        METHOD_NAME="UpdateCustomer";
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("ActName",name);
        request.addProperty("MobileNo",mobile);
        request.addProperty("CompanyName",companyName);
        request.addProperty("Gender",gender);
        request.addProperty("PhoneNo",landline);
        request.addProperty("Address1",add1);
        request.addProperty("Address2",add2);
        request.addProperty("EmailId",emailId);
        request.addProperty("Actid",actId);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,SESSION_TIMEOUT);
        try {
            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION, envelope);
            // Get the response
            Object response = (Object) envelope.getResponse();
            Log.d("",""+response);
            if(!response.toString().equals("null")){
                status =  new ResponseStatus(200, response.toString());
            }else{
                status = new ResponseStatus(201,"couldn't find data" );
            }

        } catch (Exception e) {
            e.printStackTrace();
            status =  new ResponseStatus(500, "Error occured");
        }
        return status;
    }

}
