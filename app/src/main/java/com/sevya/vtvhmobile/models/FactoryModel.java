package com.sevya.vtvhmobile.models;

/**
 * Created by abhinaym on 28/10/15.
 */
public class FactoryModel {

    public static Customer customer;
    public static ProductsInfo productsInfo;

    public static Customer getInstanceCustomer()
    {
        if(customer == null)
            return  new Customer();
            else
            return null;

    }
    public static ProductsInfo getInstanceProductsInfo()
    {
        if(productsInfo == null)
            return  new ProductsInfo();
        else
            return null;

    }
}
