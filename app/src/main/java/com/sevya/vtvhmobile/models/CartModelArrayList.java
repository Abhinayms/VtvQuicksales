package com.sevya.vtvhmobile.models;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;
import java.util.List;
import java.util.ArrayList;
/**
 * Created by abhinaym on 7/11/15.
 */
public class CartModelArrayList<T> extends ArrayList<T> implements KvmSerializable{

    private static final long serialVersionUID = -1166006770093411055L;

    @Override
    public Object getProperty(int arg0) {
        // TODO Auto-generated method stub
        return this.get(arg0);
    }

    @Override
    public int getPropertyCount() {
        // TODO Auto-generated method stub
        return this.size();

    }

    @Override
    public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
        // TODO Auto-generated method stub
        arg2.name = "cartModelList";
        arg2.type = this.getClass();

    }

    @Override
    public void setProperty(int arg0, Object arg1) {
        // TODO Auto-generated method stub
        this.add((T)arg1);
    }
}
