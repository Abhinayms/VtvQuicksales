package com.sevya.vtvhmobile.models;

import java.util.List;

/**
 * Created by abhinaym on 5/11/15.
 */
public class SOAPServices {

    private List<SoapService> soapServices;
    private String targetNameSpace;

    public List<SoapService> getSoapServices() {
        return soapServices;
    }

    public void setSoapServices(List<SoapService> soapServices) {
        this.soapServices = soapServices;
    }

    public String getTargetNameSpace() {
        return targetNameSpace;
    }

    public void setTargetNameSpace(String targetNameSpace) {
        this.targetNameSpace = targetNameSpace;
    }
}
