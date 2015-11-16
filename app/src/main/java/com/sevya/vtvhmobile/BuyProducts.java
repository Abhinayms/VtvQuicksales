package com.sevya.vtvhmobile;

/**
 * Created by abhinaym on 24/10/15.
 */

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.sevya.vtvhmobile.db.DataBaseAdapter;
import com.sevya.vtvhmobile.models.GetModelsForList;
import com.sevya.vtvhmobile.models.ProductsInfo;
import com.sevya.vtvhmobile.models.ResponseStatus;
import com.sevya.vtvhmobile.models.UserModel;
import com.sevya.vtvhmobile.util.SOAPServices;
import com.sevya.vtvhmobile.webservices.SOAPServiceClient;
import com.sevya.vtvhmobile.webservices.ServiceParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BuyProducts extends Activity  implements OnTouchListener {

    private Spinner spinner1;
    TextView dname;
    TextView dnum;
    EditText qty;
    EditText cprice;
    TextView totalPrice;
    AutoCompleteTextView autotv;
    int i;
    String date;
    ImageButton modelimagebutton;
    ImageButton qtyimagebutton;
    ImageButton priceimagebutton;
    Thread thread;
    ResponseStatus status;
    JSONArray array;
    String actId;
    String prefix;
    DataBaseAdapter dataBaseHelper;
    GetModelsForList getModelsForList;
    String modelName;
    String modelId;
    String modelDescription;
    List<String> modelList;
    HashMap<String,String> modelMap;
    String selectedModelName;
    String selectedModelId;
    List<String> stockPointList;
    HashMap<String,String> stockpointMap;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dropdown);

        Date pdate=new Date();
        date = new SimpleDateFormat("yyyy-MM-dd").format(pdate);
        dataBaseHelper=new DataBaseAdapter(this);

        Intent intent=getIntent();

        String name=intent.getStringExtra("cname");
        String num=intent.getStringExtra("cnum");
        actId=intent.getStringExtra("actId");
        i=intent.getIntExtra("listsize",0);

        Log.d("bac",""+actId);

        dname=(TextView)findViewById(R.id.dname);
        dnum=(TextView)findViewById(R.id.dnum);
        qty=(EditText)findViewById(R.id.edit_text);
        cprice=(EditText)findViewById(R.id.price);
        totalPrice=(TextView)findViewById(R.id.totalprice);
        autotv=(AutoCompleteTextView)findViewById(R.id.autoTv);
        autotv.requestFocus();
        modelimagebutton=(ImageButton)findViewById(R.id.modelimagebutton);
        qtyimagebutton=(ImageButton)findViewById(R.id.cbqty);
        priceimagebutton=(ImageButton)findViewById(R.id.cbup);

        modelimagebutton.setOnTouchListener(this);
        qtyimagebutton.setOnTouchListener(this);
        priceimagebutton.setOnTouchListener(this);
        autotv.setOnTouchListener(this);
        qty.setOnTouchListener(this);
        cprice.setOnTouchListener(this);


        dname.setText(name);
        dnum.setText(num);

     autotv.addTextChangedListener(new TextWatcher() {

         @Override
         public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {

             prefix = cs.toString();

             modelList = new ArrayList<String>();
             modelMap=new HashMap<String, String>();


             thread = new Thread() {
                 public void run() {
                     SOAPServiceClient soapServiceClient = new SOAPServiceClient();

                     try {

                         status = (ResponseStatus) soapServiceClient.callServiceUsingPrimitives(SOAPServices.getServices("getModelsForListService"), new ServiceParams(prefix, "ModelPrefix", String.class));

                         if (status.getStatusCode() == 200) {
                             array = new JSONArray(status.getStatusResponse());
                             for (int index = 0; index < array.length(); index++) {
                                 try {
                                     JSONObject eachObject = (JSONObject) array.get(index);

                                     modelName = eachObject.getString("ModelName");
                                     modelId=eachObject.getString("ModelId");
                                     modelList.add(modelName);
                                     modelMap.put(modelName,modelId);

                                 } catch (Exception e) {
                                     e.printStackTrace();
                                 }
                             }
                         }
                     } catch (Exception e) {
                         e.printStackTrace();
                     }
                     BuyProducts.this.runOnUiThread(new Runnable() {
                         @Override
                         public void run() {


                             ArrayAdapter<String> adapter = new ArrayAdapter<>(BuyProducts.this, android.R.layout.simple_dropdown_item_1line, modelList);
                             autotv.setAdapter(adapter);
                             autotv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                 Map mp=new HashMap(modelMap);

                                 @Override
                                 public void onItemClick(AdapterView<?> p, View v, int pos, long id) {

                                     selectedModelName=autotv.getText().toString();
                                      selectedModelId=(String)mp.get(selectedModelName);


                                     qty.setFocusableInTouchMode(true);
                                     qty.requestFocus();

                                     addItemsOnSpinner1();

                                 }

                             });
                         }
                     });

                 }
             };
                thread.start();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int arg1, int arg2,
                                          int arg3) {

            }
            @Override
            public void afterTextChanged(Editable arg0) {


            }
        });



    }

    public void cancel(View v)
    {
        ButtonAnimation.animation(v);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                BuyProducts.this);

        alertDialogBuilder.setTitle("Alert");

        alertDialogBuilder
                .setMessage("Do you want to cancel ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (i != 0) {
                            Intent intent = new Intent(BuyProducts.this, CartActivity.class);
                            intent.putExtra("cname", dname.getText().toString());
                            intent.putExtra("cnum", dnum.getText().toString());
                            intent.putExtra("Date",date);
                            startActivity(intent);
                        } else {
                            Intent intent=new Intent(BuyProducts.this,MainActivity.class);
                            startActivity(intent);
                        }

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();


    }

    public void addItemsOnSpinner1() {
        spinner1 = (Spinner) findViewById(R.id.stock_spinner);
    stockPointList=new ArrayList<String>();
        stockPointList.add("--Select StockPoint--");
        stockpointMap=new HashMap<>();

        thread=new Thread() {
            public void run() {
                SOAPServiceClient soapServiceClient=new SOAPServiceClient();

                {
                    try {
                        status = (ResponseStatus) soapServiceClient.callServiceUsingPrimitives(SOAPServices.getServices("getModelDetailsService"), new ServiceParams(new Integer(100), "UserId", Integer.class), new ServiceParams(Integer.parseInt(selectedModelId), "ModelId", Integer.class));
                        if (status.getStatusCode() == 200) {
                            array = new JSONArray(status.getStatusResponse());
                            for (int index = 0; index < array.length(); index++) {
                                try {
                                    JSONObject eachObject = (JSONObject) array.get(index);
                                    String spid=eachObject.getString("SPID");
                                    String spName=eachObject.getString("SpName");

                                    stockPointList.add(spName);
                                    stockpointMap.put(spName,spid);


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    BuyProducts.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(BuyProducts.this,
                                    android.R.layout.simple_spinner_item, stockPointList);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner1.setAdapter(dataAdapter);

                        }
                    });


                }
            }

        };
        thread.start();

    }
    public void doneClick(View v) {
                                             ButtonAnimation.animation(v);
                                             ProductsInfo productsInfo=new ProductsInfo();
                                             productsInfo.setName(dname.getText().toString());
                                             productsInfo.setNumber(dnum.getText().toString());
                                             productsInfo.setModelNo(autotv.getText().toString());
                                             productsInfo.setPrice(cprice.getText().toString());
                                             productsInfo.setQty(qty.getText().toString());


                                             if((autotv.getText().toString().length()==0))
                                                 autotv.setError("Please enter model no");
                                             else if(qty.getText().toString().length()==0)
                                                 qty.setError("Please enter Qty");
                                             else if(cprice.getText().toString().length()==0)
                                                 cprice.setError("Please enter UnitPrice");
                                             else {
                                                 int p=Integer.parseInt(cprice.getText().toString());
                                                 int q=Integer.parseInt(qty.getText().toString());
                                                 String tp=""+(p*q);
                                                 productsInfo.setTotalPrice(tp);


                                                long id = dataBaseHelper.insertDataItems(productsInfo);

                                                 Intent intent = new Intent(BuyProducts.this, CartActivity.class);
                                                 intent.putExtra("cname", dname.getText().toString());
                                                 intent.putExtra("cnum", dnum.getText().toString());
                                                 intent.putExtra("Date", date);
                                                 intent.putExtra("actId",actId);
                                                 startActivity(intent);


                                             }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (v.getId()){
            case R.id.autoTv:
                modelimagebutton.setVisibility(View.VISIBLE);
                qtyimagebutton.setVisibility(View.INVISIBLE);
                priceimagebutton.setVisibility(View.INVISIBLE);
                break;

            case R.id.modelimagebutton:
                autotv.setText("");
                break;
            case R.id.edit_text:
                modelimagebutton.setVisibility(View.INVISIBLE);
                qtyimagebutton.setVisibility(View.VISIBLE);
                priceimagebutton.setVisibility(View.INVISIBLE);
                break;

            case R.id.cbqty:
                qty.setText("");
                break;

            case R.id.price:
                modelimagebutton.setVisibility(View.INVISIBLE);
                qtyimagebutton.setVisibility(View.INVISIBLE);
                priceimagebutton.setVisibility(View.VISIBLE);
                break;

            case R.id.cbup:
                cprice.setText("");
                break;


        }
      return false;

    }
}