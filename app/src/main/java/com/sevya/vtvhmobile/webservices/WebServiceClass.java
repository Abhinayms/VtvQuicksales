package com.sevya.vtvhmobile.webservices;

/**
 * Created by abhinaym on 31/10/15.
 */
public class WebServiceClass {

    private String SOAP_ACTION = "";
    private String METHOD_NAME = "";
    private String NAMESPACE = "http://tempuri.org/";
    private String URL="192.168.1.144:478";
    public  String errorMessage;
    final int SESSION_TIMEOUT=750000;
    public WebServiceClass()
    {
        errorMessage="";
    }




}
