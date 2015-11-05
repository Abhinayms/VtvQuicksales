package com.sevya.vtvhmobile.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.util.Log;

import com.sevya.vtvhmobile.models.Customer;
import com.sevya.vtvhmobile.models.Message;
import com.sevya.vtvhmobile.models.ProductsInfo;

import java.net.ContentHandler;


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
        long id=db.insert(dataBaseHelper.Table_NAME,null,contentValues);
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
        contentValues.put(dataBaseHelper.TOTAL_PRICE,productsInfo.getTotalPrice());


        contentValues.put(dataBaseHelper.QUANTITY,productsInfo.getQty());

        long id= db.insert(dataBaseHelper.Table_items,null,contentValues);


        return id;
    }



    public Cursor getALLItems(String date)
    {
        SQLiteDatabase db=dataBaseHelper.getWritableDatabase();
        String[] args={date};
        String where=dataBaseHelper.CREATED_DATE+"=?";
        String[] columns={dataBaseHelper.Item_id,dataBaseHelper.NAME,dataBaseHelper.MOBILE_NUMBER,dataBaseHelper.CREATED_DATE};
        String groupBy=dataBaseHelper.NAME+","+dataBaseHelper.MOBILE_NUMBER+","+dataBaseHelper.CREATED_DATE;
        cursor=db.query(dataBaseHelper.Table_items,columns,where,args,groupBy,null,null,null);
        return  cursor;
    }

    public Cursor getItem(String number,String date)
    {


        SQLiteDatabase db=dataBaseHelper.getWritableDatabase();

        String[] columns={dataBaseHelper.Item_id,dataBaseHelper.NAME,dataBaseHelper.MOBILE_NUMBER,dataBaseHelper.PRICE,
                dataBaseHelper.MANUFACTURER,dataBaseHelper.MODEL_ID,dataBaseHelper.QUANTITY,dataBaseHelper.TOTAL_PRICE};

        String where=dataBaseHelper.MOBILE_NUMBER + "=?" + " AND " + dataBaseHelper.CREATED_DATE +"=?" ;


        String[] args={number,date};

         cursor =db.query(dataBaseHelper.Table_items, columns,where,args, null, null, null);


        return cursor;
    }
    public Cursor getAllData()
    {

        SQLiteDatabase db=dataBaseHelper.getWritableDatabase();

        String[] columns={dataBaseHelper.UID,dataBaseHelper.NAME,dataBaseHelper.MOBILE_NUMBER,dataBaseHelper.COMPANY_NAME,dataBaseHelper.GENDER,dataBaseHelper.PROFESSION,
                dataBaseHelper.LANDLINE_NUMBER,dataBaseHelper.ADDRESS,dataBaseHelper.EMAIL,dataBaseHelper.CREATED_DATE};

        cursor=db.query(dataBaseHelper.Table_NAME, columns, null, null, null, null, null);

        return  cursor;
    }

    public Cursor getPerson(String number)
    {
        SQLiteDatabase db=dataBaseHelper.getWritableDatabase();

        String[] columns={dataBaseHelper.UID,dataBaseHelper.NAME,dataBaseHelper.MOBILE_NUMBER,dataBaseHelper.COMPANY_NAME,dataBaseHelper.GENDER,dataBaseHelper.PROFESSION,
                dataBaseHelper.LANDLINE_NUMBER,dataBaseHelper.ADDRESS,dataBaseHelper.EMAIL};

        String where=DataBaseHelper.MOBILE_NUMBER+"=?";
        String[] whereargs={number};

        cursor=db.query(dataBaseHelper.Table_NAME, columns,where,whereargs,null, null, null, null);
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

        int count=db.update(dataBaseHelper.Table_NAME,contentValues,where,whereargs);
        return count;



    }

    public int deletePerson(String number)
    {
        SQLiteDatabase db=dataBaseHelper.getWritableDatabase();
        String where=DataBaseHelper.MOBILE_NUMBER+"=?";
        String[] whereArgs={number};
        int count=db.delete(DataBaseHelper.Table_NAME,where,whereArgs);
        db.close();
        return count;


    }

    public class DataBaseHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "Vtvh_Database";
        private static final String Table_NAME = "Vtvh_Customer";
        private static final int DATABASE_VERSION =25;
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



        private static final String CREATE_TABLE = "CREATE TABLE " + Table_NAME + " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT," + NAME + " VARCHAR(255)," +
                "" + MOBILE_NUMBER + " INT ," + COMPANY_NAME + " VARCHAR(3)," + GENDER + " VARCHAR(10)," + PROFESSION + " VARCHAR(30)," +
                "" + LANDLINE_NUMBER + " VARCHAR(20)," + ADDRESS + " VARCHAR(255)," + EMAIL + " VARCHAR(30)," + CREATED_DATE + " DATE DEFAULT CURRENT_DATE);";

        private static final String DROP_TABLE = "DROP TABLE  IF EXISTS " + Table_NAME;
        private Context context;

        public static final String Table_items="vtvh_items";
        public static final String Item_id="_id";

        public static final String PRICE="PRICE";
        public static final String TOTAL_PRICE="TOTALPRICE";
        public static final String MANUFACTURER="MANUFACTURER";
        public static final String MODEL_ID="MODEL_ID";
        public static final String QUANTITY="QUANTITY";

        private static final String CREATE_TABLE_ITEMs = "CREATE TABLE " + Table_items + " (" + Item_id + " INTEGER PRIMARY KEY AUTOINCREMENT," +NAME+ " VARCHAR(255)," +
                "" + MOBILE_NUMBER + " INT," + PRICE + " VARCHAR(100)," +TOTAL_PRICE + " VARCHAR(255)," +MANUFACTURER+ " VARCHAR(100),"+ MODEL_ID + " VARCHAR(100)," +QUANTITY+ " VARCHAR(100)," + CREATED_DATE + " DATE DEFAULT CURRENT_DATE);";

        private static final String DROP_TABLE_ITEMS= "DROP TABLE IF EXISTS " + Table_items;


        public DataBaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {

                db.execSQL(CREATE_TABLE);
                db.execSQL(CREATE_TABLE_ITEMs);
            } catch (SQLException e) {
                Message.message(context, "" + e);
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            try {

                db.execSQL(DROP_TABLE);
                db.execSQL(DROP_TABLE_ITEMS);
                onCreate(db);
            } catch (SQLException e) {
                Message.message(context, "" + e);
            }

        }
    }
}
