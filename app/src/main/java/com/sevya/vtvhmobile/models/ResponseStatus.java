package com.sevya.vtvhmobile.models;

/**
 * Created by abhinaym on 2/11/15.
 */
public class ResponseStatus {

    public Integer getStatusCode() {
        return statusCode;
    }
    //set 200 for success callbacks
    //set 500 on error callback
    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusResponse() {
        return statusResponse;
    }

    public void setStatusResponse(String statusResponse) {
        this.statusResponse = statusResponse;
    }

    private Integer statusCode;
    private String statusResponse;


}
