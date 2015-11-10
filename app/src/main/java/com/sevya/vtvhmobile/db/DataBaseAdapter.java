package com.sevya.vtvhmobile.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.sevya.vtvhmobile.models.Customer;
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
        contentValues.put(dataBaseHelper.MODEL_ID,productsInfo.getModelNo());
        contentValues.put(dataBaseHelper.PRICE, productsInfo.getPrice());
        contentValues.put(dataBaseHelper.TOTAL_PRICE, productsInfo.getTotalPrice());


        contentValues.put(dataBaseHelper.QUANTITY,productsInfo.getQty());

        long id= db.insert(dataBaseHelper.Table_CART,null,contentValues);


        return id;
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


    public Cursor getItem(String number,String date)
    {
        SQLiteDatabase db=dataBaseHelper.getWritableDatabase();

        String[] columns={dataBaseHelper.CART_SALE_ID,dataBaseHelper.NAME,dataBaseHelper.MOBILE_NUMBER,dataBaseHelper.PRICE,
                dataBaseHelper.MODEL_ID,dataBaseHelper.QUANTITY,dataBaseHelper.TOTAL_PRICE};

        String where=dataBaseHelper.MOBILE_NUMBER + "=?" + " AND " + dataBaseHelper.DATE_SALESLIST +"=?" ;


        String[] args={number,date};

         cursor =db.query(dataBaseHelper.Table_SALES, columns,where,args, null, null, null);


        return cursor;
    }

    public Cursor getAllSalesList(String date)
    {

        SQLiteDatabase db=dataBaseHelper.getWritableDatabase();
        String[] columns={dataBaseHelper.CART_SALE_ID,dataBaseHelper.CARTMODEL_ID,dataBaseHelper.QUANTITY,dataBaseHelper.MODEL_ID,dataBaseHelper.MODEL_NAME,
                            dataBaseHelper.TOTAL_PRICE,dataBaseHelper.PRICE,dataBaseHelper.NAME,dataBaseHelper.MOBILE_NUMBER,dataBaseHelper.SALESMAN_ID};
        String where=dataBaseHelper.DATE_SALESLIST +  "=?"; //+ " AND " + dataBaseHelper.SALESMAN_ID + "=?" ;
        String[] args={date};
        String groupBy=dataBaseHelper.NAME+","+dataBaseHelper.MOBILE_NUMBER+","+dataBaseHelper.DATE_SALESLIST;
        cursor=db.query(dataBaseHelper.Table_SALES,columns,where,args,groupBy,null,null);
        return cursor;

    }
    public Cursor getAllData()
    {

        SQLiteDatabase db=dataBaseHelper.getWritableDatabase();

        String[] columns={dataBaseHelper.UID,dataBaseHelper.NAME,dataBaseHelper.MOBILE_NUMBER,dataBaseHelper.COMPANY_NAME,dataBaseHelper.GENDER,dataBaseHelper.PROFESSION,
                dataBaseHelper.LANDLINE_NUMBER,dataBaseHelper.ADDRESS,dataBaseHelper.EMAIL,dataBaseHelper.CREATED_DATE};

        cursor=db.query(dataBaseHelper.Table_CUSTOMER, columns, null, null, null, null, null);

        return  cursor;
    }

    public Cursor getPerson(String number)
    {
        SQLiteDatabase db=dataBaseHelper.getWritableDatabase();

        String[] columns={dataBaseHelper.UID,dataBaseHelper.NAME,dataBaseHelper.MOBILE_NUMBER,dataBaseHelper.COMPANY_NAME,dataBaseHelper.GENDER,dataBaseHelper.PROFESSION,
                dataBaseHelper.LANDLINE_NUMBER,dataBaseHelper.ADDRESS,dataBaseHelper.EMAIL};

        String where=DataBaseHelper.MOBILE_NUMBER+"=?";
        String[] whereargs={number};

        cursor=db.query(dataBaseHelper.Table_CUSTOMER, columns,where,whereargs,null, null, null, null);
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

    public class DataBaseHelper extends SQLiteOpenHelper {

        private Context context;
        private static final int DATABASE_VERSION =44;
        private static final String DATABASE_NAME = "Vtvh_Database";
        private static final String Table_CUSTOMER = "Customer_table";
        public static final String Table_CART="Cart_Table";
        public static final String Table_SALES="Sales_Table";
        public static final String UID = "_id";
        public static final String NAME = "NAME";
        public static final String MOBILE_NUMBER = "MOBILE_NUMBER";
        public static final String COMPANY_NAME = "COMPANYNAME";
        public static final String GENDER = "GENDER";
        public static final String PROFESSION = "PROFESSION";
        public static final String LANDLINE_NUMBER = "LANDLINE_NUMBER";
        public static final String ADDRESS = "ADDRESS";
        public static final String EMAIL = "EMAIL";
        public static final String CREATED_DATE="CREATED_DATE";

        public static final String MODEL_NAME="MODEL_NAME";
        public static final String PRICE="PRICE";
        public static final String TOTAL_PRICE="TOTALPRICE";
        public static final String STOCKPOINT_ID="STOCKPOINT_ID";
        public static final String MODEL_ID="MODEL_ID";
        public static final String QUANTITY="QUANTITY";
        public static final String CART_ID="_id";
        public static final String SALES_LIST_ID="_id";
        public static final String CART_SALE_ID="id";
        public static final String SALESMAN_ID="SALESMAN_ID";
        public static final String CARTMODEL_ID="CARTMODEL_ID";
        public static final String DATE_SALESLIST="DATE";




        private static final String DROP_TABLE_CUSTOMER = "DROP TABLE  IF EXISTS " + Table_CUSTOMER;
        private static final String DROP_TABLE_CART = "DROP TABLE IF EXISTS " + Table_CART;
        private static final String DROP_TABLE_SALES_LIST = "DROP TABLE IF EXISTS " + Table_SALES;



        private static final String CREATE_TABLE_CUSTOMER = "CREATE TABLE " + Table_CUSTOMER + " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT," + NAME + " VARCHAR(255)," +
                "" + MOBILE_NUMBER + " INT ," + COMPANY_NAME + " VARCHAR(3)," + GENDER + " VARCHAR(10)," + PROFESSION + " VARCHAR(30)," +
                "" + LANDLINE_NUMBER + " VARCHAR(20)," + ADDRESS + " VARCHAR(255)," + EMAIL + " VARCHAR(30)," + CREATED_DATE + " DATE DEFAULT CURRENT_DATE);";



        private static final String CREATE_TABLE_CART = "CREATE TABLE " + Table_CART + " (" + CART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +NAME+ " VARCHAR(255)," +
                "" + MOBILE_NUMBER + " INT," + PRICE + " VARCHAR(100)," +TOTAL_PRICE + " VARCHAR(255)," +STOCKPOINT_ID+ " VARCHAR(100),"+ MODEL_ID + " VARCHAR(255),"+ MODEL_NAME + " VARCHAR(255)," +QUANTITY+ " VARCHAR(100)," + CREATED_DATE + " DATE DEFAULT CURRENT_DATE);";


        private static final String CREATE_TABLE_SALES_LIST = " CREATE TABLE " + Table_SALES + " (" + SALES_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + MOBILE_NUMBER + " VARCHAR(100)," + MODEL_ID + " VARCHAR(255)," + QUANTITY + " VARCHAR(100)," + NAME + " VARCHAR(255)," + PRICE + " VARCHAR(100), " + CART_SALE_ID + " VARCHAR(100)," + MODEL_NAME + " VARCHAR(255)," +
                "" + TOTAL_PRICE + " VARCHAR(255)," + SALESMAN_ID + " VARCHAR(100)," + CARTMODEL_ID + " VARCHAR(100)," + DATE_SALESLIST + " VARCHAR(100));";

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
                onCreate(db);
            } catch (SQLException e) {
                Message.message(context, "" + e);
            }

        }
    }
}
