package com.sevya.vtvhmobile.models;



import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.io.Serializable;
import java.util.Hashtable;

/**
 * Created by abhinaym on 4/11/15.
 */
public class UserModel implements KvmSerializable {

    private int PrimaryActID;
    private String SurName;
    private String CompanyName;
    private String ActName;
    private String Address1;
    private String Street;
    private String Gender;
    private String Phone;
    private String MobileNo;
    private String City;
    private String State;
    private String District;
    private String Mandal;
    private String Country;
    private String Pin;
    private String TinNo;
    private String IsPrimaryAct;
    private String Email;
    private String FlatNo;
    private String DuplicateIds;


    public int getPrimaryActID() {
        return PrimaryActID;
    }

    public void setPrimaryActID(int primaryActID) {
        PrimaryActID = primaryActID;
    }

    public String getSurName() {
        return SurName;
    }

    public void setSurName(String surName) {
        SurName = surName;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getActName() {
        return ActName;
    }

    public void setActName(String actName) {
        ActName = actName;
    }

    public String getAddress1() {
        return Address1;
    }

    public void setAddress1(String address1) {
        Address1 = address1;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getMandal() {
        return Mandal;
    }

    public void setMandal(String mandal) {
        Mandal = mandal;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getPin() {
        return Pin;
    }

    public void setPin(String pin) {
        Pin = pin;
    }

    public String getTinNo() {
        return TinNo;
    }

    public void setTinNo(String tinNo) {
        TinNo = tinNo;
    }

    public String getIsPrimaryAct() {
        return IsPrimaryAct;
    }

    public void setIsPrimaryAct(String isPrimaryAct) {
        IsPrimaryAct = isPrimaryAct;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFlatNo() {
        return FlatNo;
    }

    public void setFlatNo(String flatNo) {
        FlatNo = flatNo;
    }

    public String getDuplicateIds() {
        return DuplicateIds;
    }

    public void setDuplicateIds(String duplicateIds) {
        DuplicateIds = duplicateIds;
    }

    @Override
    public Object getProperty(int i) {
        switch (i) {
            case 0:
                return PrimaryActID;
            case 1:
                return SurName;
            case 2:
                return CompanyName;
            case 3:
                return ActName;
            case 4:
                return Address1;
            case 5:
                return Street;
            case 6:
                return Gender;
            case 7:
                return Phone;
            case 8:
                return MobileNo;
            case 9:
                return City;
            case 10:
                return State;
            case 11:
                return District;
            case 12:
                return Mandal;
            case 13:
                return Country;
            case 14:
                return Pin;
            case 15:
                return TinNo;
            case 16:
                return IsPrimaryAct;
            case 17:
                return Email;
            case 18:
                return FlatNo;
            case 19:
                return DuplicateIds;
        }
        return null;
     }

    @Override
    public int getPropertyCount() {
        return 20;
    }

    @Override
    public void setProperty(int i, Object o) {
        switch (i) {
            case 0:
                PrimaryActID = Integer.parseInt(o.toString());
                break;
            case 1:
                SurName = o.toString();
                break;
            case 2:
                CompanyName = o.toString();
                break;
            case 3:
                ActName = o.toString();
                break;
            case 4:
                Address1 = o.toString();
                break;
            case 5:
                Street = o.toString();
                break;
            case 6:
                Gender = o.toString();
                break;
            case 7:
                Phone = o.toString();
                break;
            case 8:
                MobileNo = o.toString();
                break;
            case 9:
                City = o.toString();
                break;
            case 10:
                State = o.toString();
                break;
            case 11:
                District = o.toString();
                break;
            case 12:
                Mandal = o.toString();
                break;
            case 13:
                Country = o.toString();
                break;
            case 14:
                Pin = o.toString();
                break;
            case 15:
                TinNo = o.toString();
                break;
            case 16:
                IsPrimaryAct = o.toString();
                break;
            case 17:
                Email = o.toString();
                break;
            case 18:
                FlatNo = o.toString();
                break;
            case 19:
                DuplicateIds = o.toString();
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
                propertyInfo.name = "PrimaryActID";
                break;
            case 1:
                propertyInfo.type = propertyInfo.STRING_CLASS;
                propertyInfo.name = "SurName";

                break;
            case 2:
                propertyInfo.type = propertyInfo.STRING_CLASS;
                propertyInfo.name = "CompanyName";

                break;
            case 3:
                propertyInfo.type = propertyInfo.STRING_CLASS;
                propertyInfo.name = "ActName";

                break;
            case 4:
                propertyInfo.type = propertyInfo.STRING_CLASS;
                propertyInfo.name = "Address1";

                break;
            case 5:
                propertyInfo.type = propertyInfo.STRING_CLASS;
                propertyInfo.name = "Street";

                break;
            case 6:
                propertyInfo.type = propertyInfo.STRING_CLASS;
                propertyInfo.name = "Gender";

                break;
            case 7:
                propertyInfo.type = propertyInfo.STRING_CLASS;
                propertyInfo.name = "Phone";

                break;
            case 8:
                propertyInfo.type = propertyInfo.STRING_CLASS;
                propertyInfo.name = "MobileNo";
                break;
            case 9:
                propertyInfo.type = propertyInfo.STRING_CLASS;
                propertyInfo.name = "City";

                break;
            case 10:
                propertyInfo.type = propertyInfo.STRING_CLASS;
                propertyInfo.name = "State";

                break;
            case 11:
                propertyInfo.type = propertyInfo.STRING_CLASS;
                propertyInfo.name = "District";

                break;
            case 12:
                propertyInfo.type = propertyInfo.STRING_CLASS;
                propertyInfo.name = "Mandal";

                break;
            case 13:
                propertyInfo.type = propertyInfo.STRING_CLASS;
                propertyInfo.name = "Country";

                break;
            case 14:
                propertyInfo.type = propertyInfo.STRING_CLASS;
                propertyInfo.name = "Pin";

                break;
            case 15:
                propertyInfo.type = propertyInfo.STRING_CLASS;
                propertyInfo.name = "TinNo";

                break;
            case 16:
                propertyInfo.type = propertyInfo.STRING_CLASS;
                propertyInfo.name = "IsPrimaryAct";

                break;
            case 17:
                propertyInfo.type = propertyInfo.STRING_CLASS;
                propertyInfo.name = "Email";

                break;
            case 18:
                propertyInfo.type = propertyInfo.STRING_CLASS;
                propertyInfo.name = "FlatNo";
                break;
            case 19:
                propertyInfo.type = propertyInfo.STRING_CLASS;
                propertyInfo.name = "DuplicateIds";
                break;
            default:
                break;
        }
    }
}
