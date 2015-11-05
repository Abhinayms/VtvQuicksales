package com.sevya.vtvhmobile;

import com.sevya.vtvhmobile.models.SOAPServices;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;


/**
 * Created by abhinaym on 5/11/15.
 */
public class JAXBExample {

    public void xmlReader() {
        try {

            Reader reader = new InputStreamReader(this.getClass().getResourceAsStream("/SOAPServices.xml"));



            JAXBContext jaxbContext = JAXBContext.newInstance(SOAPServices.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            SOAPServices services = (SOAPServices) jaxbUnmarshaller.unmarshal(reader);
            System.out.println(services.getTargetNameSpace());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]){
        JAXBExample example = new JAXBExample();
        example.xmlReader();
    }
}