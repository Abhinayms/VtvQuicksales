package com.sevya.vtvhmobile.models;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by abhinaym on 12/11/15.
 */
public class GetModelsForList implements KvmSerializable {

    private String ModelPrefix;

    public String getPrefix() {
        return ModelPrefix;
    }

    public void setPrefix(String prefix) {
        this.ModelPrefix = prefix;
    }


    @Override
    public Object getProperty(int i) {
        switch (i) {
            case 0:
                return ModelPrefix;
        }
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 1;
    }

    @Override
    public void setProperty(int i, Object o) {

        switch (i) {
            case 0:
                ModelPrefix = o.toString();
                break;
            default:
                break;
        }
    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {
        switch (i) {
            case 0:
                propertyInfo.type = propertyInfo.STRING_CLASS;
                propertyInfo.name = "ModelPrefix";
                break;
            default:
                break;
        }

    }
}
