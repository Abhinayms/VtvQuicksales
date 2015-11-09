package com.sevya.vtvhmobile.models;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by abhinaym on 9/11/15.
 */
public class SalesmanCart implements KvmSerializable {

    private Integer SalesmanId;
    private  String Date;

    public Integer getSalesmanId() {
        return SalesmanId;
    }

    public void setSalesmanId(Integer salesmanId) {
        SalesmanId = salesmanId;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    @Override
    public Object getProperty(int i) {
        switch (i) {
            case 0:
                return SalesmanId;
            case 1:
                return Date;
        }
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 2;
    }

    @Override
    public void setProperty(int i, Object o) {

        switch (i) {
            case 0:
                SalesmanId = Integer.parseInt(o.toString());
                break;
            case 1:
                Date = o.toString();
                break;
            default:
                break;
        }

    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {

        switch (i) {
            case 0:
                propertyInfo.type = propertyInfo.INTEGER_CLASS;
                propertyInfo.name = "SalesmanId";
                break;
            case 1:
                propertyInfo.type = propertyInfo.STRING_CLASS;
                propertyInfo.name = "Date";

                break;
            default:
                break;
        }

    }
}
