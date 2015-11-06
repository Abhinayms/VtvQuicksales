package com.sevya.vtvhmobile;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;


import com.sevya.vtvhmobile.Adapters.CustomCartListViewAdapter;
import com.sevya.vtvhmobile.db.DataBaseAdapter;
import com.sevya.vtvhmobile.models.CartModel;
import com.sevya.vtvhmobile.models.ResponseStatus;
import com.sevya.vtvhmobile.models.UserModel;
import com.sevya.vtvhmobile.util.SOAPServices;
import com.sevya.vtvhmobile.webservices.SOAPServiceClient;
import com.sevya.vtvhmobile.webservices.ServiceParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class CartActivity extends AppCompatActivity {

    TextView cname;
    TextView cnum;
    Intent intent;
    Toolbar mToolbar;
    Button tickButton;
    ImageButton plusButton;
    TextView textView;
    Button continueshopping;
    TextView totalPrice;
    TextView textTotalPrice;

    Thread thread;
    ResponseStatus status;
    JSONArray array;
    CartModel cartModel;

    DataBaseAdapter dataBaseHelper;
    SimpleCursorAdapter simpleCursorAdapter;
    Cursor cursor;
    ListView listView;
    String cartid;
    int pstn;
    CustomCartListViewAdapter customCartListViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        /*getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu);*/
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backarrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        dataBaseHelper=new DataBaseAdapter(this);
         cname=(TextView)findViewById(R.id.cname);
        cnum=(TextView)findViewById(R.id.cnum);
        totalPrice=(TextView)findViewById(R.id.totalprice);

        intent=getIntent();

        String name=intent.getStringExtra("cname");
        String num=intent.getStringExtra("cnum");

        cname.setText(name);
        cnum.setText(num);


        onTickButtonClick();
        onPlusButtonClick();

       populateItemsListFromDB();


    }



    @SuppressWarnings("deprecation")
    private void populateItemsListFromDB()
    {   int sum=0;
        String number=cnum.getText().toString();

        String date=intent.getStringExtra("Date");
        listView = (ListView) findViewById(R.id.cartitemview);
        cursor = dataBaseHelper.getItem(number, date);
        startManagingCursor(cursor);
        if (cursor.getCount()==0)
        {
            textTotalPrice=(TextView)findViewById(R.id.texttotalprice);
            textTotalPrice.setVisibility(View.INVISIBLE);
            textView = (TextView)findViewById(R.id.cartitemtextview);
            textView.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
            continueshopping=(Button)findViewById(R.id.continueshopping);
            continueshopping.setVisibility(View.VISIBLE);
            tickButton.setVisibility(View.INVISIBLE);
            plusButton.setVisibility(View.INVISIBLE);


            continueshopping.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        ButtonAnimation.animation(v);
                                                        Intent intent=new Intent(CartActivity.this,BuyProducts.class);
                                                        intent.putExtra("cname",cname.getText().toString());
                                                        intent.putExtra("cnum",cnum.getText().toString());
                                                        startActivity(intent);
                                                    }
                                                }

            );


        }
        else {
            textView=(TextView)findViewById(R.id.cartitemtextview);
            continueshopping=(Button)findViewById(R.id.continueshopping);
            textView.setVisibility(View.INVISIBLE);
            continueshopping.setVisibility(View.INVISIBLE);




            customCartListViewAdapter=new CustomCartListViewAdapter(this,cursor,0);
            listView.addHeaderView(getLayoutInflater().inflate(R.layout.header, null, false));
            listView.setAdapter(customCartListViewAdapter);
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                // setting onItemLongClickListener and passing the position to the function
                @Override
                public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                               int position, long arg3) {
                    Cursor selectedFromList = (Cursor) (listView.getItemAtPosition(position));//.toString();
                    String cart_id = selectedFromList.getString(selectedFromList.getColumnIndex(DataBaseAdapter.DataBaseHelper.CART_ID));

                    removeItemFromList(cart_id,position);

                    return true;
                }
            });



            cursor.moveToFirst();
            for(int i=0;i<cursor.getCount();i++) {
                int index = cursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.TOTAL_PRICE);
                String tprice = cursor.getString(index);
                int convertedPrice=Integer.parseInt(tprice);
                sum+=convertedPrice;
                cursor.moveToNext();
            }
            String tcost=NumberFormat.getNumberInstance(Locale.US).format(sum);
            totalPrice.setText(tcost);
        }

    }
    // method to remove list item
    protected void removeItemFromList(String cart_id,int position) {
        cartid=cart_id;
        pstn=position;

        AlertDialog.Builder alert = new AlertDialog.Builder(
                CartActivity.this);

        alert.setTitle("Choose Action");
        alert.setMessage("Do you want delete this item?");
        alert.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TOD O Auto-generated method stub


               // simpleCursorAdapter.notifyDataSetChanged();
                //simpleCursorAdapter.notifyDataSetInvalidated();

            }
        });
        alert.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TOD O Auto-generated method stub
                AlertDialog.Builder alert = new AlertDialog.Builder(
                        CartActivity.this);

                alert.setTitle("Delete this Item");
                alert.setMessage("Do you want delete this item?");
                alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TOD O Auto-generated method stub

                        dataBaseHelper.deleteItem(cartid);

                        customCartListViewAdapter.notifyDataSetChanged();
                        customCartListViewAdapter.notifyDataSetInvalidated();
                        listView.setAdapter(customCartListViewAdapter);





                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TOD O Auto-generated method stub
                        dialog.dismiss();
                    }
                });

                alert.show();

            }

    });

        alert.show();

    }

    public void onTickButtonClick()
    {
        tickButton=(Button) findViewById(R.id.float_button_tick);
        tickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ButtonAnimation.animation(v);

                 cartModel = new CartModel();

                final ArrayList<CartModel> cartModelArrayList=new ArrayList<CartModel>();
                for(int i=0;i<cursor.getCount();i++)
                {


                }


                thread = new Thread() {
                    public void run() {
                        SOAPServiceClient soapServiceClient = new SOAPServiceClient();
                        ServiceParams modalParam = new ServiceParams(cartModelArrayList, "userModel", UserModel.class);
                        // ServiceParams primitiveParam = new ServiceParams(new Integer(76), "UserId", Integer.class);

                        try {
                            status = (ResponseStatus) soapServiceClient.callService(SOAPServices.getServices("insertCustomerDetailsService"), modalParam);
                            if (status.getStatusCode() == 200) {
                                array = new JSONArray(status.getStatusResponse());
                                for (int index = 0; index < array.length(); index++) {
                                    try {
                                        JSONObject eachObject = (JSONObject) array.get(index);


                                    }catch (Exception e)
                                    {
                                        e.printStackTrace();
                                    }

                                    }
                                }
                                }catch (Exception e)
                        {
                            e.printStackTrace();;
                        }



                        }

                };
                thread.start();
            }

            });

                Intent intent =new Intent(CartActivity.this,SurveyActivity.class);
        startActivity(intent);


            }


    public void onPlusButtonClick()
    {
        plusButton=(ImageButton) findViewById(R.id.float_button_plus);
        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ButtonAnimation.animation(v);

                Intent intent=new Intent(CartActivity.this,BuyProducts.class);
                intent.putExtra("cname",cname.getText().toString());
                intent.putExtra("cnum",cnum.getText().toString());
                //intent.putExtra("listsize",((Integer)listView.getScrollBarSize()).toString());
                intent.putExtra("listsize", listView.getCount());
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cart, menu);
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
        if(id==R.id.home)
        {
            Intent intent=new Intent(CartActivity.this,BuyProducts.class);
            intent.putExtra("cname",cname.getText().toString());
            intent.putExtra("cnum",cnum.getText().toString());
            startActivity(intent);
        }
        if(id==R.id.homeicon)
        {
            Intent intent=new Intent(CartActivity.this,MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}


            /*String[] fromFieldsNames = new String[]{DataBaseAdapter.DataBaseHelper.MODEL_ID, DataBaseAdapter.DataBaseHelper.QUANTITY, DataBaseAdapter.DataBaseHelper.PRICE, DataBaseAdapter.DataBaseHelper.TOTAL_PRICE};
            int[] toViewIDs = new int[]
                    {R.id.p_model, R.id.p_qty, R.id.p_price,R.id.p_totalprice};

            simpleCursorAdapter = new SimpleCursorAdapter(

                    this,
                    R.layout.cartitemlayout,
                    cursor,
                    fromFieldsNames,
                    toViewIDs,
                    0
            );


            *//*ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) listView.getLayoutParams();
            lp.height = 300;
            listView.setLayoutParams(lp);*//*
            listView.addHeaderView(getLayoutInflater().inflate(R.layout.header, null, false));
            listView.setAdapter(simpleCursorAdapter);



*/
