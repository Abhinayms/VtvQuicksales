package com.sevya.vtvhmobile.webservices;

/**
 * Created by abhinaym on 5/11/15.
 */
public class ServiceParams {

    private Object params;
    private String paramAlias;
    private Class paramType;

    public ServiceParams(Object params, String paramAlias, Class paramType){
        this.params = params;
        this.paramAlias = paramAlias;
        this.paramType = paramType;
    }

    public Object getParams() {
        return params;
    }

    public String getParamAlias() {
        return paramAlias;
    }

    public Class getParamType() {
        return paramType;
    }

}
