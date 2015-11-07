package com.sevya.vtvhmobile.models;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;
import java.util.List;
import java.util.ArrayList;
/**
 * Created by abhinaym on 7/11/15.
 */
public class CartModelList<E> extends ArrayList<E> implements KvmSerializable{


    @Override
    public Object getProperty(int i) {
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 0;
    }

    @Override
    public void setProperty(int i, Object o) {

    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {

    }
}
