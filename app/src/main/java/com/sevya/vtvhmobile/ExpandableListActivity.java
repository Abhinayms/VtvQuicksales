package com.sevya.vtvhmobile;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SimpleCursorTreeAdapter;
import android.widget.TextView;

import com.sevya.vtvhmobile.db.DataBaseAdapter;
import com.sevya.vtvhmobile.models.ResponseStatus;
import com.sevya.vtvhmobile.models.SalesListResponseModel;
import com.sevya.vtvhmobile.models.SalesmanCart;
import com.sevya.vtvhmobile.models.UserModel;
import com.sevya.vtvhmobile.util.SOAPServices;
import com.sevya.vtvhmobile.webservices.SOAPServiceClient;
import com.sevya.vtvhmobile.webservices.ServiceParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ExpandableListActivity extends AppCompatActivity{
    DataBaseAdapter dataBaseHelper;
    MyExpandableListAdapter mAdapter;
    TextView textView;
    Toolbar mToolbar;
    Cursor mGroupsCursor;
    Thread thread;
    ResponseStatus status;
    JSONArray array;
    SalesmanCart salesmanCart;
    String mDate;

    private ExpandableListView expandableListView;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        expandableListView = new ExpandableListView(this);
        setContentView(R.layout.activity_expandable_list);


        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mToolbar.setTitle("");


        setSupportActionBar(mToolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backarrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        dataBaseHelper=new DataBaseAdapter(this);
        i=getIntent();
          mDate=i.getStringExtra("Date");
          salesmanCart=new SalesmanCart();
        salesmanCart.setDate(mDate);
        salesmanCart.setSalesmanId(new Integer(76));
        thread=new Thread() {
            public void run() {

                dataBaseHelper=new DataBaseAdapter(ExpandableListActivity.this);

                SOAPServiceClient soapServiceClient=new SOAPServiceClient();
                ServiceParams modalParam = new ServiceParams(salesmanCart,"salesmanCart", SalesmanCart.class);
                // ServiceParams primitiveParam = new ServiceParams(new Integer(76), "UserId", Integer.class);
                {
                    try {
                        status = (ResponseStatus) soapServiceClient.callService(SOAPServices.getServices("getSalesmanListByDateService"),modalParam);
                        if(status.getStatusCode() == 200 ) {
                            array = new JSONArray(status.getStatusResponse());
                            for (int index = 0; index < array.length(); index++) {
                                try {
                                    JSONObject eachObject = (JSONObject) array.get(index);

                                    SalesListResponseModel salesListResponseModel=new SalesListResponseModel();

                                    salesListResponseModel.setCartId(eachObject.getInt("CartId"));
                                    salesListResponseModel.setCartModelId(eachObject.getInt("CartModelId"));
                                    salesListResponseModel.setQty(eachObject.getInt("Qty"));
                                    salesListResponseModel.setModelId(eachObject.getInt("ModalId"));
                                    salesListResponseModel.setModelName(eachObject.getString("Modal"));
                                    salesListResponseModel.setTotalPrice(eachObject.getString("TotalPrice"));
                                    salesListResponseModel.setSalePrice(eachObject.getString("SalePrice"));
                                    salesListResponseModel.setName(eachObject.getString("ActName"));
                                    salesListResponseModel.setMobileNumber(eachObject.getString("MobileNo"));
                                    salesListResponseModel.setSalesManId(76);
                                    salesListResponseModel.setDate(mDate);

                                    long id=dataBaseHelper.insertSalesListResponse(salesListResponseModel);

                                }catch (Exception e)
                                {
                                    // Long id=dataBaseHelper.insertSaleslist(eachObject.getString("MobileNo"), eachObject.getInt("ModalId"),eachObject.getInt("Qty"),eachObject.getString("ActName"),
                                    //      eachObject.getString("SalePrice"),eachObject.getInt("CartId"),eachObject.getString("Modal"),eachObject.getString("TotalPrice"),76,eachObject.getInt("CartModelId"));

                                    e.printStackTrace();
                                }


                            }


                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }

        };
        thread.start();



        fillData();
    }
    @SuppressWarnings("deprecation")
    private void fillData()
    {

        i=getIntent();
        String mDate=i.getStringExtra("Date");
        String salesmenId="76";
        mGroupsCursor = dataBaseHelper.getAllSalesList(mDate );
        this.startManagingCursor(mGroupsCursor);
        mGroupsCursor.moveToFirst();
        if(mGroupsCursor.getCount()==0)
        {
            textView=(TextView)findViewById(R.id.saletextview);
            textView.setVisibility(View.VISIBLE);

        }
        else {

            textView=(TextView)findViewById(R.id.saletextview);
            textView.setVisibility(View.INVISIBLE);

            ExpandableListView elv = (ExpandableListView)this.findViewById(android.R.id.list);

            mAdapter = new MyExpandableListAdapter(mGroupsCursor, this,
                    R.layout.rowlayout_expgroup,                     // Your row layout for a group
                    R.layout.rowlayout_itemlist_exp,                 // Your row layout for a child
                    new String[]{DataBaseAdapter.DataBaseHelper.NAME, DataBaseAdapter.DataBaseHelper.MOBILE_NUMBER},          // Field(s) to use from group cursor
                    new int[]{R.id.p_name, R.id.p_number},                 // Widget ids to put group data into
                    new String[]{DataBaseAdapter.DataBaseHelper.MODEL_NAME, DataBaseAdapter.DataBaseHelper.QUANTITY, DataBaseAdapter.DataBaseHelper.TOTAL_PRICE},  // Field(s) to use from child cursors
                    new int[]{R.id.p_model, R.id.p_qty, R.id.p_price});          // Widget ids to put child data into


            elv.addHeaderView(getLayoutInflater().inflate(R.layout.saleslistheader, null, false));
            elv.setAdapter(mAdapter);                         // set the list adapter.

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_expandable_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class MyExpandableListAdapter extends SimpleCursorTreeAdapter {
        public MyExpandableListAdapter(Cursor cursor, Context context,int groupLayout,
                                       int childLayout, String[] groupFrom, int[] groupTo, String[] childrenFrom,
                                       int[] childrenTo) {
            super(context, cursor, groupLayout, groupFrom, groupTo,
                    childLayout, childrenFrom, childrenTo);
        }



        @SuppressWarnings("deprecation")
        @Override
        protected Cursor getChildrenCursor(Cursor groupCursor) {
            String number=groupCursor.getString(groupCursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.MOBILE_NUMBER));
            String date=groupCursor.getString(groupCursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.DATE_SALESLIST));
            Cursor childCursor = dataBaseHelper.getItemSales(number, date);
            childCursor.moveToFirst();
            ExpandableListActivity.this.startManagingCursor(childCursor);
            childCursor.moveToFirst();
            return childCursor;
        }

    }

}
