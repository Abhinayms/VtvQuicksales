package com.sevya.vtvhmobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.sevya.vtvhmobile.db.DataBaseAdapter;
import com.sevya.vtvhmobile.models.ProductsInfo;
import com.sevya.vtvhmobile.models.ResponseStatus;
import com.sevya.vtvhmobile.util.SOAPServices;
import com.sevya.vtvhmobile.webservices.SOAPServiceClient;
import com.sevya.vtvhmobile.webservices.ServiceParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;

public class ReceiveDetails extends AppCompatActivity {


    TextView name;
    TextView numm;
    TextView CompName;
    TextView pro;
    TextView email;
    TextView add;
    TextView add1;
    TextView add2;
    TextView add3;
    TextView gen;
    TextView ln;
    ImageButton rcontinue;
    String date;
    String actid;
    private Toolbar mToolbar;
    Thread thread;
    String mobile;
    String customerName;
    ResponseStatus status;
    JSONArray array;
    DataBaseAdapter dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer);

        dataBaseHelper=new DataBaseAdapter(this);

        name=(TextView)findViewById(R.id.ename);
        numm=(TextView)findViewById(R.id.enumm);
        CompName=(TextView)findViewById(R.id.custCompName);
        pro=(TextView)findViewById(R.id.epro);
        email=(TextView)findViewById(R.id.email);
        add=(TextView)findViewById(R.id.eadd);
        add1=(TextView)findViewById(R.id.eadd1);
        add2=(TextView)findViewById(R.id.eadd2);
        add3=(TextView)findViewById(R.id.eadd3);
        gen=(TextView) findViewById(R.id.egen);
        ln=(TextView) findViewById(R.id.eln);

        Intent i=getIntent();

        String cname=i.getStringExtra("cname");
        String cage=i.getStringExtra("compName");
        String cnum=i.getStringExtra("cnum");
        String cpro=i.getStringExtra("cpro");
        String cmail=i.getStringExtra("cmail");
        String cadd=i.getStringExtra("cadd");
        String cadd1=i.getStringExtra("cadd1");
        String cadd2=i.getStringExtra("cadd2");
        String cadd3=i.getStringExtra("cadd3");
        String cgen=i.getStringExtra("rb");
        String cln=i.getStringExtra("cln");
        actid=i.getStringExtra("actId");

        name.setText(cname);
        numm.setText(cnum);
        CompName.setText(cage);
        pro.setText(cpro);
        email.setText(cmail);
        add.setText(cadd);
        add1.setText(cadd1);
        add2.setText(cadd2);
        add3.setText(cadd3);
        gen.setText(cgen);
        ln.setText(cln);

        mobile=numm.getText().toString();
        customerName=name.getText().toString();

        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH)+1;
        int year = c.get(Calendar.YEAR);
        date ="" + year + "-" + "" + month + "-" + ""+ day;


        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        /*getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu);*/
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backarrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        onButtonClick();

    }

    public void onButtonClick()
    {

        rcontinue=(ImageButton) findViewById(R.id.Rcontinue);
        rcontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ButtonAnimation.animation(v);

                Intent in =new Intent(ReceiveDetails.this,CartActivity.class);
                in.putExtra("cname",name.getText().toString());
                in.putExtra("cnum", numm.getText().toString());
                in.putExtra("actId", actid);
                in.putExtra("Date", date);
                startActivity(in);



            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_receive_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id==R.id.action_cart)
        {


            thread = new Thread() {
                public void run() {
                    SOAPServiceClient soapServiceClient = new SOAPServiceClient();

                    try {
                        status = (ResponseStatus) soapServiceClient.callServiceUsingPrimitives(SOAPServices.getServices("getGetCartItemsBasedOnMobileNoService"), new ServiceParams(mobile, "mobileNo", String.class),new ServiceParams(Integer.parseInt(actid),"Actid",Integer.class));
                        if (status.getStatusCode() == 200) {
                            JSONObject obj=new JSONObject(status.getStatusResponse());
                            array=obj.getJSONArray("Details");

                            for (int index = 0; index < array.length(); index++) {
                                try {
                                    JSONObject eachObject = (JSONObject) array.get(index);
                                    ProductsInfo productsInfo=new ProductsInfo();
                                    productsInfo.setName(customerName);
                                    productsInfo.setNumber(mobile);
                                    productsInfo.setActId(actid);
                                    productsInfo.setPrice(eachObject.getString("SalePrice"));
                                    productsInfo.setTotalPrice(eachObject.getString("TotalPrice"));
                                    productsInfo.setModalId(eachObject.getString("ModalId"));
                                    productsInfo.setModelNo(eachObject.getString("Model"));
                                    productsInfo.setStockPoint(eachObject.getString("SpId"));
                                    productsInfo.setQty(eachObject.getString("Qty"));
                                    productsInfo.setInstall(eachObject.getBoolean("IsInstallationReq"));
                                    productsInfo.setDemo(eachObject.getBoolean("IsDemoReq"));



                                     dataBaseHelper.insertDataItems(productsInfo);


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                            ReceiveDetails.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent=new Intent(ReceiveDetails.this,CartActivity.class);
                                    intent.putExtra("cname",name.getText().toString());
                                    intent.putExtra("cnum",numm.getText().toString());
                                    intent.putExtra("Date",date);
                                    intent.putExtra("actId",actid);
                                    startActivity(intent);
                                }
                            });


                        }
                        else if(status.getStatusCode()==500){
                            ReceiveDetails.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ReceiveDetails.this, "" + status.getStatusResponse(), Toast.LENGTH_SHORT).show();
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



        }
        if(id==R.id.action_edit)
        {
            Intent i=new Intent(this,EditCustomer.class);


            i.putExtra("cname", name.getText().toString());
            i.putExtra("cnum", numm.getText().toString());
            i.putExtra("cpro", pro.getText().toString());
            i.putExtra("rb", gen.getText().toString());
            i.putExtra("compName", CompName.getText().toString());
            i.putExtra("cmail", email.getText().toString());
            i.putExtra("cadd",add.getText().toString());
            i.putExtra("cln", ln.getText().toString());
            i.putExtra("cadd1", add1.getText().toString());
            i.putExtra("cadd2", add2.getText().toString());
            i.putExtra("cadd3", add3.getText().toString());
            i.putExtra("actId",actid);

            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(ReceiveDetails.this,MainActivity.class);
        startActivity(i);
    }
}
