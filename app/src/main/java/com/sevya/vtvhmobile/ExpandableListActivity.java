package com.sevya.vtvhmobile;


import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.sevya.vtvhmobile.Adapters.CustomSaleListViewAdapter;
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
    CustomSaleListViewAdapter customSaleListViewAdapter;
    TextView textView;
    Toolbar mToolbar;
    Cursor mGroupsCursor;
    Thread thread;
    ResponseStatus status;
    JSONArray array;
    SalesmanCart salesmanCart;
    String mDate;
    ListView lv;

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
        salesmanCart.setSalesmanId(new Integer(100));
        thread=new Thread() {
            public void run() {

                dataBaseHelper=new DataBaseAdapter(ExpandableListActivity.this);

                SOAPServiceClient soapServiceClient=new SOAPServiceClient();
                ServiceParams modalParam = new ServiceParams(salesmanCart,"salesmanCart", SalesmanCart.class);
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
                                    e.printStackTrace();
                                }


                            }
                            ExpandableListActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    fillData();
                                }
                            });


                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }

        };
        thread.start();

    }

    @SuppressWarnings("deprecation")
    private void fillData()
    {

        i=getIntent();
         mDate=i.getStringExtra("Date");
        String salesmenId="100";
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

            lv = (ListView)this.findViewById(android.R.id.list);

            customSaleListViewAdapter=new CustomSaleListViewAdapter(this,mGroupsCursor,0);


            lv.addHeaderView(getLayoutInflater().inflate(R.layout.saleslistheader, null, false));
            lv.setAdapter(customSaleListViewAdapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                                        int position, long arg3) {
                    Cursor selectedFromList = (Cursor) (lv.getItemAtPosition(position));//.toString();
                    String name = selectedFromList.getString(selectedFromList.getColumnIndex(DataBaseAdapter.DataBaseHelper.NAME));
                    String mobile = selectedFromList.getString(selectedFromList.getColumnIndex(DataBaseAdapter.DataBaseHelper.MOBILE_NUMBER));
                    Log.d("num",""+name+mobile);

                   /* String unitPrice = selectedFromList.getString(selectedFromList.getColumnIndex(DataBaseAdapter.DataBaseHelper.PRICE));
                    String totalPrice = selectedFromList.getString(selectedFromList.getColumnIndex(DataBaseAdapter.DataBaseHelper.TOTAL_PRICE));
                    String modelId = selectedFromList.getString(selectedFromList.getColumnIndex(DataBaseAdapter.DataBaseHelper.MODEL_ID));
                    String modelName = selectedFromList.getString(selectedFromList.getColumnIndex(DataBaseAdapter.DataBaseHelper.MODEL_NAME));
                    String qty = selectedFromList.getString(selectedFromList.getColumnIndex(DataBaseAdapter.DataBaseHelper.QUANTITY));*/

                    Intent intent = new Intent(ExpandableListActivity.this, PurchaseInfo.class);
                    intent.putExtra("name", name);
                    intent.putExtra("mobile", mobile);
                    intent.putExtra("date",mDate);
                    /*intent.putExtra("totalprice", totalPrice);
                    intent.putExtra("unitPrice", unitPrice);
                    intent.putExtra("modelId", modelId);
                    intent.putExtra("modelName", modelName);
                    intent.putExtra("qty", qty);*/
                    intent.putExtra("status", "not purchased");
                    startActivity(intent);


                }
            });
// set the list adapter.

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



    @Override
    protected void onStop() {
        super.onStop();
        dataBaseHelper.deleteSalesTable();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataBaseHelper.deleteSalesTable();
    }
}
