package com.sevya.vtvhmobile;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Rect;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.sevya.vtvhmobile.Adapters.CustomCartListViewAdapter;
import com.sevya.vtvhmobile.db.DataBaseAdapter;
import com.sevya.vtvhmobile.models.CartModel;
import com.sevya.vtvhmobile.models.ResponseStatus;
import com.sevya.vtvhmobile.util.SOAPServices;
import com.sevya.vtvhmobile.webservices.SOAPServiceClient;
import com.sevya.vtvhmobile.webservices.ServiceParams;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;


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
    Cursor cursor;
    ListView listView;
    String cartid;
    String accountId;
    int pstn;
    CustomCartListViewAdapter customCartListViewAdapter;
    private Integer Actid;
    String number;
    String date;
    String num;
    int count;
    List<CartModel> cartModelArrayList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);


        dataBaseHelper=new DataBaseAdapter(this);

         cname=(TextView)findViewById(R.id.cname);
        cnum=(TextView)findViewById(R.id.cnum);
        totalPrice=(TextView)findViewById(R.id.totalprice);

        intent=getIntent();

        String name=intent.getStringExtra("cname");
         num=intent.getStringExtra("cnum");
        accountId=intent.getStringExtra("actId");
        date=intent.getStringExtra("Date");


        Log.d("acct", "" + accountId);


       Actid=Integer.parseInt(accountId);

        cname.setText(name);
        cnum.setText(num);
       populateItemsListFromDB();

    }
    @SuppressWarnings("deprecation")
    private void populateItemsListFromDB()
    {
        listView = (ListView) findViewById(R.id.cartitemview);

        tickButton=(Button)findViewById(R.id.float_button_tick);
        plusButton=(ImageButton)findViewById(R.id.float_button_plus);

        double sum=0.00;
        number=cnum.getText().toString();


        cursor = dataBaseHelper.getItem(accountId,number);
        startManagingCursor(cursor);
        if (cursor.getCount()==0)
        {
            textTotalPrice=(TextView)findViewById(R.id.texttotalprice);
            textTotalPrice.setVisibility(View.INVISIBLE);
            TextView totalprice=(TextView)findViewById(R.id.totalprice);
            totalprice.setVisibility(View.INVISIBLE);
            textView = (TextView)findViewById(R.id.cartitemtextview);
            textView.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
            LinearLayout relativeLayout=(LinearLayout)findViewById(R.id.deliverychargelayout);
            relativeLayout.setVisibility(View.GONE);

            continueshopping=(Button)findViewById(R.id.continueshopping);
            continueshopping.setVisibility(View.VISIBLE);
            tickButton.setVisibility(View.INVISIBLE);
            plusButton.setVisibility(View.INVISIBLE);
            LinearLayout cartHeader=(LinearLayout)findViewById(R.id.cart_header);
            cartHeader.setVisibility(View.GONE);


            continueshopping.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        ButtonAnimation.animation(v);
                                                        Intent intent=new Intent(CartActivity.this,BuyProducts.class);
                                                        intent.putExtra("cname",cname.getText().toString());
                                                        intent.putExtra("cnum",cnum.getText().toString());
                                                        intent.putExtra("actId",accountId);
                                                        startActivity(intent);
                                                    }
                                                }

            );


        }
        else {
            textView=(TextView)findViewById(R.id.cartitemtextview);
            continueshopping=(Button)findViewById(R.id.continueshopping);
            textView.setVisibility(View.GONE);
            continueshopping.setVisibility(View.GONE);

            customCartListViewAdapter=new CustomCartListViewAdapter(this,cursor);

            listView.setAdapter(customCartListViewAdapter);
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                               int position, long arg3) {
                    Cursor selectedFromList = (Cursor) (listView.getItemAtPosition(position));
                    String cart_id = selectedFromList.getString(selectedFromList.getColumnIndex(DataBaseAdapter.DataBaseHelper.CART_ID));

                    removeItemFromList(cart_id,position);

                    return true;
                }
            });



            cursor.moveToFirst();
            for(int i=0;i<cursor.getCount();i++) {
                int index = cursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.TOTAL_PRICE);
                String tprice = cursor.getString(index);
                double convertedPrice=Double.parseDouble(tprice);
                sum+=convertedPrice;
                cursor.moveToNext();
            }
           // String tcost=NumberFormat.getNumberInstance(Locale.US).format(sum);
            totalPrice.setText(String.format("%.2f",sum)); //((Object) sum).toString())
        }

    }
    // method to remove list item
    protected void removeItemFromList(final String cart_id,int position) {
        cartid=cart_id;
        pstn=position;

        AlertDialog.Builder alert = new AlertDialog.Builder(
                CartActivity.this);

        alert.setTitle("Do you want to Delete this Item?");
        /*alert.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TOD O Auto-generated method stub
                AlertDialog.Builder alert = new AlertDialog.Builder(
                        CartActivity.this);

                alert.setTitle("Edit this Item");
                alert.setMessage("Do you want Edit this item?");
                alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TOD O Auto-generated method stub

                        cursor=dataBaseHelper.getProduct(cart_id);
                        cursor.moveToFirst();
                        Intent intent=new Intent(CartActivity.this,BuyProducts.class);
                        intent.putExtra("cname",cname.getText().toString());
                        intent.putExtra("cnum",cnum.getText().toString());
                        intent.putExtra("actId",accountId);
                        intent.putExtra("listsize", listView.getCount());
                        intent.putExtra("modelNo",cursor.getString(cursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.MODEL_No)));
                        intent.putExtra("quantity",cursor.getString(cursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.QUANTITY)));
                        intent.putExtra("unitprice",cursor.getString(cursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.PRICE)));

                        startActivity(intent);
                        dataBaseHelper.deleteItem(cartid);


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
        });*/
        alert.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TOD O Auto-generated method stub

                dataBaseHelper.deleteItem(cartid);
                populateItemsListFromDB();
                customCartListViewAdapter.notifyDataSetChanged();
                customCartListViewAdapter.notifyDataSetInvalidated();

            }

        });
       alert.show();

    }

    public void submit(View v)
    {
                ButtonAnimation.animation(v);
                EditText deliveryCharges=(EditText)findViewById(R.id.textdeliverycharges);

                cartModelArrayList = new ArrayList<CartModel>();
                cursor.moveToFirst();
               for (int i = 0; i < cursor.getCount(); i++) {
                    cartModel = new CartModel();
                    cartModel.setActid(Actid);
                    cartModel.setSalesmanId(new Integer(76));

                    String unitPrice = cursor.getString(cursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.PRICE));
                    cartModel.setSalePrice(unitPrice);
                    String totalPrice = cursor.getString(cursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.TOTAL_PRICE));
                    cartModel.setTotalPrice(totalPrice);
                    cartModel.setModalId(cursor.getInt(cursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.MODEL_ID)));
                    cartModel.setCartModelId(new Integer(0));
                    cartModel.setCartId(new Integer(0));
                    String qty = cursor.getString(cursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.QUANTITY));
                    cartModel.setQty(Integer.parseInt(qty));
                    cartModel.setSpId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.STOCKPOINT_ID))));
                    cartModel.setModel(cursor.getString(cursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.MODEL_No)));
                    cartModel.setDeliveryCharges(deliveryCharges.getText().toString());
                    String isDemoReq=cursor.getString(cursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.DEMO));
                    String isInstallReq=cursor.getString(cursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.INSTALL));
                    cartModel.setIsDemoReq(Boolean.parseBoolean(isDemoReq));
                    cartModel.setIsInstallationReq(Boolean.parseBoolean(isInstallReq));

                   cartModelArrayList.add(cartModel);
                   cursor.moveToNext();

                }

                thread = new Thread() {
                    public void run() {
                        SOAPServiceClient soapServiceClient = new SOAPServiceClient();
                        ServiceParams modalParam = new ServiceParams(cartModelArrayList, "cartModelList",cartModelArrayList.getClass());
                        try {
                            status = (ResponseStatus) soapServiceClient.callCartService(SOAPServices.getServices("insertCartDetailsService"), cartModelArrayList);
                            if (status.getStatusCode() == 200) {
                                array = new JSONArray(status.getStatusResponse());
                                for (int index = 0; index < array.length(); index++) {
                                    try {
                                        JSONObject eachObject = (JSONObject) array.get(index);


                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                            }
                            else if(status.getStatusCode()==500){
                                CartActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(CartActivity.this, "" + status.getStatusResponse(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            ;
                        }


                    }

                };
                thread.start();


        Intent i=new Intent(CartActivity.this,SurveyActivity.class);
        startActivity(i);
       dataBaseHelper.deleteCartTable();

    }


    public void plusButton(View v)
    {

                ButtonAnimation.animation(v);

                Intent intent=new Intent(CartActivity.this,BuyProducts.class);
                intent.putExtra("cname", cname.getText().toString());
                intent.putExtra("cnum", cnum.getText().toString());
                intent.putExtra("actId", accountId);
                intent.putExtra("listsize", listView.getCount());
                startActivity(intent);

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

        if(id==R.id.trash)
        {

             count=listView.getCount();
            if(count==0){
                Log.d("second time ",""+listView.getCount());
                Toast.makeText(CartActivity.this," There are no Items in the Cart to delete ",Toast.LENGTH_LONG).show();
            }
            else {
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(
                        CartActivity.this);

                // set title
                alertDialogBuilder.setTitle("Alert");

                // set dialog message
                alertDialogBuilder
                        .setMessage("Do you want to delete the entire cart ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                int i = dataBaseHelper.deleteAllCartItems(num);
                                populateItemsListFromDB();



                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });
                // create alert dialog
                android.app.AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }

        }
        if(id==R.id.homeicon)
        {
            android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(
                    CartActivity.this);

            // set title
            alertDialogBuilder.setTitle("Alert");

            // set dialog message
            alertDialogBuilder
                    .setMessage("Return to Home Screen ?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent=new Intent(CartActivity.this,MainActivity.class);
                            startActivity(intent);
                            dataBaseHelper.deleteCartTable();


                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing
                            dialog.cancel();
                        }
                    });
            // create alert dialog
            android.app.AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();

        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(CartActivity.this,BuyProducts.class);
        startActivity(i);
    }
}

