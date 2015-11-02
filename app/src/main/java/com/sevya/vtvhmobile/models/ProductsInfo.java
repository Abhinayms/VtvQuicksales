package com.sevya.vtvhmobile.models;

/**
 * Created by abhinaym on 24/10/15.
 */
public class ProductsInfo {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public String getStockPoint() {
        return stockPoint;
    }

    public void setStockPoint(String stockPoint) {
        this.stockPoint = stockPoint;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String totalPrice;

    private String name;
    private String number;
    private String modelNo;
    private String stockPoint;
    private String qty;
    private String price;
}
