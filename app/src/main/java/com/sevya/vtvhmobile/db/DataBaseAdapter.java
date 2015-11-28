package com.sevya.vtvhmobile.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.sevya.vtvhmobile.models.Customer;
import com.sevya.vtvhmobile.models.MergeCustomer;
import com.sevya.vtvhmobile.models.Message;
import com.sevya.vtvhmobile.models.ProductsInfo;
import com.sevya.vtvhmobile.models.SalesListResponseModel;

/**
 * Created by abhinaym on 28/9/15.
 */

public class
        DataBaseAdapter {

    DataBaseHelper dataBaseHelper;
    private Cursor cursor;
    public DataBaseAdapter(Context context)
    {
        dataBaseHelper=new DataBaseHelper(context);
    }

    public long insertCustomer(Customer customer)
    {
        SQLiteDatabase db=dataBaseHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(dataBaseHelper.NAME,customer.getName());
        contentValues.put(dataBaseHelper.MOBILE_NUMBER,customer.getMobileNumber());
        contentValues.put(dataBaseHelper.COMPANY_NAME,customer.getAge());
        contentValues.put(dataBaseHelper.GENDER,customer.getGender());
        contentValues.put(dataBaseHelper.PROFESSION,customer.getProfession());
        contentValues.put(dataBaseHelper.LANDLINE_NUMBER,customer.getLandlineNumber());
        contentValues.put(dataBaseHelper.ADDRESS,customer.getAddress());
        contentValues.put(dataBaseHelper.EMAIL,customer.getEmail());
        long id=db.insert(dataBaseHelper.Table_CUSTOMER,null,contentValues);
        return id;
    }

    public void insertMergeDetails(MergeCustomer mergeCustomer)
    {
        SQLiteDatabase db=dataBaseHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(dataBaseHelper.NAME,mergeCustomer.getName());
        contentValues.put(dataBaseHelper.MOBILE_NUMBER,mergeCustomer.getMobileNumber());
        contentValues.put(dataBaseHelper.COMPANY_NAME,mergeCustomer.getCompany());
        contentValues.put(dataBaseHelper.GENDER,mergeCustomer.getGender());
        contentValues.put(dataBaseHelper.PROFESSION,mergeCustomer.getProfession());
        contentValues.put(dataBaseHelper.LANDLINE_NUMBER,mergeCustomer.getLandlineNumber());
        contentValues.put(dataBaseHelper.ADDRESS,mergeCustomer.getAddress());
        contentValues.put(dataBaseHelper.EMAIL,mergeCustomer.getEmail());
        contentValues.put(dataBaseHelper.ACTID,mergeCustomer.getActid());
        contentValues.put(dataBaseHelper.CITY,mergeCustomer.getCity());
        contentValues.put(dataBaseHelper.STREET,mergeCustomer.getStreet());
        contentValues.put(dataBaseHelper.MANDAL,mergeCustomer.getMandal());
        contentValues.put(dataBaseHelper.DISTRICT,mergeCustomer.getDistrict());
        contentValues.put(dataBaseHelper.COUNTRY,mergeCustomer.getCountry());
        contentValues.put(dataBaseHelper.PRIMARYACT,mergeCustomer.getIsPrimaryact());
        contentValues.put(dataBaseHelper.DUPLICATEID,mergeCustomer.getDuplicateIds());
        contentValues.put(dataBaseHelper.PIN,mergeCustomer.getPin());
        contentValues.put(dataBaseHelper.STATE,mergeCustomer.getState());
        contentValues.put(dataBaseHelper.SALESMAN_ID,mergeCustomer.getSalesmanId());
        contentValues.put(dataBaseHelper.TINNO,mergeCustomer.getTinno());
        contentValues.put(dataBaseHelper.FLATNO,mergeCustomer.getFlatNo());

        db.insert(dataBaseHelper.Table_MERGE,null,contentValues);

    }

    public long insertSalesListResponse(SalesListResponseModel salesListResponseModel)
    {
        SQLiteDatabase db=dataBaseHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(dataBaseHelper.MOBILE_NUMBER,salesListResponseModel.getMobileNumber());
        contentValues.put(dataBaseHelper.MODEL_ID,salesListResponseModel.getModelId());
        contentValues.put(dataBaseHelper.QUANTITY,salesListResponseModel.getQty());
        contentValues.put(dataBaseHelper.NAME,salesListResponseModel.getName());
        contentValues.put(dataBaseHelper.PRICE,salesListResponseModel.getSalePrice());
        contentValues.put(dataBaseHelper.CART_SALE_ID,salesListResponseModel.getCartId());
        contentValues.put(dataBaseHelper.MODEL_NAME,salesListResponseModel.getModelName());
        contentValues.put(dataBaseHelper.TOTAL_PRICE,salesListResponseModel.getTotalPrice());
        contentValues.put(dataBaseHelper.SALESMAN_ID, salesListResponseModel.getSalesManId());
        contentValues.put(dataBaseHelper.CARTMODEL_ID, salesListResponseModel.getCartModelId());
        contentValues.put(dataBaseHelper.DATE_SALESLIST,salesListResponseModel.getDate());

        long id=db.insert(dataBaseHelper.Table_SALES,null,contentValues);

        return id;
    }
  
    public long insertDataItems(ProductsInfo productsInfo)
    {
        SQLiteDatabase db=dataBaseHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(dataBaseHelper.NAME,productsInfo.getName());
        contentValues.put(dataBaseHelper.MOBILE_NUMBER,productsInfo.getNumber());
        contentValues.put(dataBaseHelper.MODEL_No,productsInfo.getModelNo());
        contentValues.put(dataBaseHelper.PRICE, productsInfo.getPrice());
        contentValues.put(dataBaseHelper.TOTAL_PRICE, productsInfo.getTotalPrice());
        contentValues.put(dataBaseHelper.DEMO,productsInfo.isDemo());
        contentValues.put(dataBaseHelper.INSTALL,productsInfo.isInstall());
        contentValues.put(dataBaseHelper.MODEL_ID,productsInfo.getModalId());
        contentValues.put(dataBaseHelper.QUANTITY,productsInfo.getQty());

        long id= db.insert(dataBaseHelper.Table_CART,null,contentValues);


        return id;
    }

    public void insertServerCredentials(String hostName,String portNumber)
    {
        SQLiteDatabase db=dataBaseHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(dataBaseHelper.HOSTNAME,hostName);
        contentValues.put(dataBaseHelper.PORTNUMBER,portNumber);

        db.insert(dataBaseHelper.Table_CREDENTIALS, null, contentValues);


    }

    public Cursor getServerCredentials()
    {
        SQLiteDatabase db=dataBaseHelper.getWritableDatabase();
        String[] columns={dataBaseHelper.HOSTNAME,dataBaseHelper.PORTNUMBER};
        cursor=db.query(dataBaseHelper.Table_CREDENTIALS,columns,null,null,null,null,null);

        return cursor;
    }

    public void updateServerCredentials(String hostName,String portNumber)
    {
        SQLiteDatabase db=dataBaseHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(dataBaseHelper.HOSTNAME,hostName);
        contentValues.put(dataBaseHelper.PORTNUMBER,portNumber);


        db.update(dataBaseHelper.Table_CREDENTIALS, contentValues, null, null);


    }


    public Cursor getALLItems(String date)
    {
        SQLiteDatabase db=dataBaseHelper.getWritableDatabase();
        String[] args={date};
        String where=dataBaseHelper.CREATED_DATE+"=?";
        String[] columns={dataBaseHelper.CART_ID,dataBaseHelper.NAME,dataBaseHelper.MOBILE_NUMBER,dataBaseHelper.CREATED_DATE};
        String groupBy=dataBaseHelper.NAME+","+dataBaseHelper.MOBILE_NUMBER+","+dataBaseHelper.CREATED_DATE;
        cursor=db.query(dataBaseHelper.Table_CART,columns,where,args,groupBy,null,null,null);
        return  cursor;
    }
    public Cursor getAllSalesList(String date)
    {

        SQLiteDatabase db=dataBaseHelper.getWritableDatabase();
        String[] columns={dataBaseHelper.SALES_LIST_ID,dataBaseHelper.NAME,dataBaseHelper.MOBILE_NUMBER,dataBaseHelper.SALESMAN_ID,dataBaseHelper.DATE_SALESLIST};
        String where=dataBaseHelper.DATE_SALESLIST +  "=?"; //+ " AND " + dataBaseHelper.SALESMAN_ID + "=?" ;
        String[] args={date};
        String groupBy=dataBaseHelper.NAME+","+dataBaseHelper.MOBILE_NUMBER+","+dataBaseHelper.DATE_SALESLIST;
        cursor=db.query(dataBaseHelper.Table_SALES,columns,where,args,groupBy,null,null);
        return cursor;

    }

    public Cursor getItem(String number,String date)
    {


        SQLiteDatabase db=dataBaseHelper.getWritableDatabase();

        String[] columns={dataBaseHelper.CART_ID,dataBaseHelper.NAME,dataBaseHelper.MOBILE_NUMBER,dataBaseHelper.PRICE,dataBaseHelper.MODEL_ID,
                dataBaseHelper.STOCKPOINT_ID,dataBaseHelper.MODEL_No,dataBaseHelper.QUANTITY,dataBaseHelper.TOTAL_PRICE,dataBaseHelper.DEMO,dataBaseHelper.INSTALL};

        String where=dataBaseHelper.MOBILE_NUMBER + "=?" + " AND " + dataBaseHelper.CREATED_DATE +"=?" ;


        String[] args={number,date};

        cursor =db.query(dataBaseHelper.Table_CART, columns,where,args, null, null, null);


        return cursor;
    }

    public Cursor getProduct(String cartId)
    {
        SQLiteDatabase db=dataBaseHelper.getWritableDatabase();
        String[] columns={dataBaseHelper.PRICE,
                dataBaseHelper.MODEL_No,dataBaseHelper.MODEL_ID,dataBaseHelper.QUANTITY,dataBaseHelper.TOTAL_PRICE};
        String where=dataBaseHelper.CART_ID + "=?";

        String[] args={cartId};

        cursor=db.query(dataBaseHelper.Table_CART,columns,where,args,null,null,null);
        return cursor;

    }


    public Cursor getItemSales(String number,String date)
    {

        SQLiteDatabase db=dataBaseHelper.getWritableDatabase();

        String[] columns={dataBaseHelper.SALES_LIST_ID,dataBaseHelper.NAME,dataBaseHelper.MOBILE_NUMBER,dataBaseHelper.PRICE,
                dataBaseHelper.MODEL_NAME,dataBaseHelper.MODEL_ID,dataBaseHelper.QUANTITY,dataBaseHelper.TOTAL_PRICE};

        String where=dataBaseHelper.MOBILE_NUMBER + "=?" + " AND " + dataBaseHelper.DATE_SALESLIST +"=?" ;


        String[] args={number,date};

         cursor =db.query(dataBaseHelper.Table_SALES, columns,where,args, null, null, null);


        return cursor;
    }


    public Cursor getAllData(String actid)
    {

        SQLiteDatabase db=dataBaseHelper.getWritableDatabase();

        String[] columns={dataBaseHelper.UID,dataBaseHelper.NAME,dataBaseHelper.MOBILE_NUMBER,dataBaseHelper.PRIMARYACT,dataBaseHelper.COMPANY_NAME,dataBaseHelper.ADDRESS,dataBaseHelper.STREET,
                            dataBaseHelper.GENDER,dataBaseHelper.DUPLICATEID,dataBaseHelper.SALESMAN_ID,dataBaseHelper.PROFESSION,dataBaseHelper.LANDLINE_NUMBER,dataBaseHelper.CITY,dataBaseHelper.STATE,
                            dataBaseHelper.DISTRICT,dataBaseHelper.MANDAL,dataBaseHelper.COUNTRY,dataBaseHelper.PIN,dataBaseHelper.TINNO,dataBaseHelper.PRIMARYACT,dataBaseHelper.EMAIL,dataBaseHelper.FLATNO};
        String where=DataBaseHelper.ACTID+"=?";
        String[] whereargs={actid};

        cursor=db.query(dataBaseHelper.Table_MERGE, columns,where,whereargs,null, null, null, null);



        return  cursor;
    }

    public Cursor getPerson(String number)
    {
        SQLiteDatabase db=dataBaseHelper.getWritableDatabase();

        String[] columns={dataBaseHelper.UID,dataBaseHelper.NAME,dataBaseHelper.MOBILE_NUMBER,dataBaseHelper.COMPANY_NAME,dataBaseHelper.GENDER,dataBaseHelper.PROFESSION,
                dataBaseHelper.LANDLINE_NUMBER,dataBaseHelper.ADDRESS,dataBaseHelper.EMAIL,dataBaseHelper.ACTID,dataBaseHelper.PRIMARYACT};

        String where=DataBaseHelper.MOBILE_NUMBER+"=?";
        String[] whereargs={number};

        cursor=db.query(dataBaseHelper.Table_MERGE, columns,where,whereargs,null, null, null, null);
        return cursor;

    }

    public int update(String name,String number,String age,String gen,String profession,String lln,String address,String email)
    {
        SQLiteDatabase db=dataBaseHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(dataBaseHelper.NAME,name);
        contentValues.put(dataBaseHelper.MOBILE_NUMBER,number);
        contentValues.put(dataBaseHelper.COMPANY_NAME,age);
        contentValues.put(dataBaseHelper.GENDER,gen);
        contentValues.put(dataBaseHelper.PROFESSION,profession);
        contentValues.put(dataBaseHelper.LANDLINE_NUMBER,lln);
        contentValues.put(dataBaseHelper.ADDRESS,address);
        contentValues.put(dataBaseHelper.EMAIL, email);

        String where=dataBaseHelper.MOBILE_NUMBER+"=?";
        String[] whereargs={number};

        int count=db.update(dataBaseHelper.Table_CUSTOMER,contentValues,where,whereargs);
        return count;



    }

    public int deletePerson(String number)
    {
        SQLiteDatabase db=dataBaseHelper.getWritableDatabase();
        String where=DataBaseHelper.MOBILE_NUMBER+"=?";
        String[] whereArgs={number};
        int count=db.delete(DataBaseHelper.Table_CUSTOMER, where, whereArgs);
        db.close();
        return count;


    }

    public int deleteItem(String cartId)
    {
        SQLiteDatabase db=dataBaseHelper.getWritableDatabase();
        String where=DataBaseHelper.CART_ID+"=?";
        String[] whereArgs={cartId};
        int count=db.delete(DataBaseHelper.Table_CART,where,whereArgs);
        return count;
    }

    public int deleteAllCartItems(String mobileNumber)
    {
        SQLiteDatabase db=dataBaseHelper.getWritableDatabase();
        String where=DataBaseHelper.MOBILE_NUMBER+"=?";
        String[] whereArgs={mobileNumber};
        int count=db.delete(DataBaseHelper.Table_CART,where,whereArgs);
        return  count;
    }

    public void deleteSalesTable()
    {
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        db.execSQL("DELETE FROM Sales_Table"); //delete all rows in a table
        db.close();
    }

    public void deleteMergeTable()
    {
        SQLiteDatabase db=dataBaseHelper.getWritableDatabase();
        db.execSQL("DELETE FROM Merge_Table");
        db.close();
    }

    public void deleteCartTable()
    {
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        db.execSQL("DELETE FROM Cart_Table"); //delete all rows in a table
        db.close();
    }

    public class DataBaseHelper extends SQLiteOpenHelper {

        private Context context;
        private static final int DATABASE_VERSION =56;
        private static final String DATABASE_NAME = "Vtvh_Database";
        private static final String Table_CUSTOMER = "Customer_table";
        public static final String Table_CART="Cart_Table";
        public static final String Table_SALES="Sales_Table";
        public static final String Table_MERGE="Merge_Table";
        public static final String Table_CREDENTIALS="Credentials_Table";
        public static final String UID = "_id";
        public static final String NAME = "NAME";
        public static final String MOBILE_NUMBER = "MOBILE_NUMBER";
        public static final String COMPANY_NAME = "COMPANYNAME";
        public static final String GENDER = "GENDER";
        public static final String PROFESSION = "PROFESSION";
        public static final String LANDLINE_NUMBER = "LANDLINE_NUMBER";
        public static final String ADDRESS = "ADDRESS";
        public static final String EMAIL = "EMAIL";
        public static final String DEMO="DEMO";
        public static final String INSTALL="INSTALL";
        public static final String CREATED_DATE="CREATED_DATE";

        public static final String MODEL_NAME="MODEL_NAME";
        public static final String PRICE="PRICE";
        public static final String TOTAL_PRICE="TOTALPRICE";
        public static final String STOCKPOINT_ID="STOCKPOINT_ID";
        public static final String MODEL_ID="MODEL_ID";
        public static final String MODEL_No="MODEL_No";
        public static final String QUANTITY="QUANTITY";
        public static final String CART_ID="_id";
        public static final String SALES_LIST_ID="_id";
        public static final String CART_SALE_ID="id";
        public static final String SALESMAN_ID="SALESMAN_ID";
        public static final String CARTMODEL_ID="CARTMODEL_ID";
        public static final String DATE_SALESLIST="DATE";
        public static final String STATUS="STATUS";
        public static final String ACTID="ACTID";
        public static final String PRIMARYACT="PRIMARYACT";
        public static final String STREET="STREET";
        public static final String CITY="CITY";
        public static final String DISTRICT="DISTRICT";
        public static final String STATE="STATE";
        public static final String DUPLICATEID="DUPLICATEID";
        public static final String COUNTRY="COUNTRY";
        public static final String PIN="PIN";
        public static final String TINNO="TINNO";
        public static final String MANDAL="MANDAL";
        public static final String FLATNO="FLATNO";
        public static final String CREDENTIALS_ID="_id";
        public static final String HOSTNAME="HOSTNAME";
        public static final String PORTNUMBER="PORTNUMBER";






        private static final String DROP_TABLE_CUSTOMER = "DROP TABLE  IF EXISTS " + Table_CUSTOMER;
        private static final String DROP_TABLE_CART = "DROP TABLE IF EXISTS " + Table_CART;
        private static final String DROP_TABLE_SALES_LIST = "DROP TABLE IF EXISTS " + Table_SALES;
        private static final String DROP_TABLE_MERGE = "DROP TABLE IF EXISTS " + Table_MERGE;
        private static final String DROP_TABLE_CREDENTIALS = "DROP TABLE IF EXISTS " + Table_CREDENTIALS;




        private static final String CREATE_TABLE_CUSTOMER = "CREATE TABLE " + Table_CUSTOMER + " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT," + NAME + " VARCHAR(255)," +
                "" + MOBILE_NUMBER + " INT ," + COMPANY_NAME + " VARCHAR(3)," + GENDER + " VARCHAR(10)," + PROFESSION + " VARCHAR(30)," +
                "" + LANDLINE_NUMBER + " VARCHAR(20)," + ADDRESS + " VARCHAR(255)," + EMAIL + " VARCHAR(30)," + CREATED_DATE + " DATE DEFAULT CURRENT_DATE);";



        private static final String CREATE_TABLE_CART = "CREATE TABLE " + Table_CART + " (" + CART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +NAME+ " VARCHAR(255)," +
                "" + MOBILE_NUMBER + " INT," + PRICE + " VARCHAR(100)," + MODEL_No + " VARCHAR(100)," +TOTAL_PRICE + " VARCHAR(255)," +STOCKPOINT_ID+ " VARCHAR(100),"+ MODEL_ID + " VARCHAR(255),"+ MODEL_NAME + " VARCHAR(255)," +QUANTITY+ " VARCHAR(100),"+ DEMO + " VARCHAR(100),"+ INSTALL + " VARCHAR(100)," + CREATED_DATE + " DATE DEFAULT CURRENT_DATE);";


        private static final String CREATE_TABLE_SALES_LIST = " CREATE TABLE " + Table_SALES + " (" + SALES_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + MOBILE_NUMBER + " VARCHAR(100)," + MODEL_ID + " VARCHAR(255)," + QUANTITY + " VARCHAR(100)," + NAME + " VARCHAR(255)," + PRICE + " VARCHAR(100), " + CART_SALE_ID + " VARCHAR(100)," + MODEL_NAME + " VARCHAR(255)," +
                "" + TOTAL_PRICE + " VARCHAR(255)," + SALESMAN_ID + " VARCHAR(100)," + CARTMODEL_ID + " VARCHAR(100)," + STATUS + " VARCHAR(100)," + DATE_SALESLIST + " VARCHAR(100));";


        private static final String CREATE_TABLE_MERGE = "CREATE TABLE " + Table_MERGE + " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT," + NAME + " VARCHAR(255)," + ACTID + " VARCHAR(255)," +
                "" + MOBILE_NUMBER + " VARCHAR(100)," + COMPANY_NAME + " VARCHAR(100)," + GENDER + " VARCHAR(10)," + PROFESSION + " VARCHAR(30)," +
                "" + LANDLINE_NUMBER + " VARCHAR(20)," + PRIMARYACT + " VARCHAR(255)," + ADDRESS + " VARCHAR(255)," + STREET + " VARCHAR(255)," + CITY + " VARCHAR(255)," + MANDAL + " VARCHAR(255)," + DISTRICT + " VARCHAR(255)," +
                "" + EMAIL + " VARCHAR(100)," + STATE + " VARCHAR(255)," + COUNTRY + " VARCHAR(255)," + FLATNO + " VARCHAR(255)," + DUPLICATEID + " VARCHAR(100)," + PIN + " VARCHAR(100)," + TINNO + " VARCHAR(100)," + SALESMAN_ID + " VARCHAR(30));";

        private static final String CREATE_TABLE_CREDENTIALS= " CREATE TABLE " + Table_CREDENTIALS + " (" + CREDENTIALS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + HOSTNAME + " VARCHAR(255)," + PORTNUMBER + " VARCHAR(255)); ";


        public DataBaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_TABLE_SALES_LIST);
                db.execSQL(CREATE_TABLE_CUSTOMER);
                db.execSQL(CREATE_TABLE_CART);
                db.execSQL(CREATE_TABLE_MERGE);
                db.execSQL(CREATE_TABLE_CREDENTIALS);

            } catch (SQLException e) {
                Message.message(context, "" + e);
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            try {

                db.execSQL(DROP_TABLE_CUSTOMER);
                db.execSQL(DROP_TABLE_CART);
                db.execSQL(DROP_TABLE_SALES_LIST);
                db.execSQL(DROP_TABLE_MERGE);
                db.execSQL(DROP_TABLE_CREDENTIALS);
                onCreate(db);
            } catch (SQLException e) {
                Message.message(context, "" + e);
            }

        }
    }
}
