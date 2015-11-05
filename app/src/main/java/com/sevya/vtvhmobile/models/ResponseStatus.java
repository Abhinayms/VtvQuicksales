package com.sevya.vtvhmobile.models;

/**
 * Created by abhinaym on 2/11/15.
 */
public class ResponseStatus {

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getStatusResponse() {
        return statusResponse;
    }

    private Integer statusCode;
    private String statusResponse;

    public ResponseStatus(int statusCode, String statusResponse){
        this.statusCode = statusCode;
        this.statusResponse = statusResponse;
    }


}
