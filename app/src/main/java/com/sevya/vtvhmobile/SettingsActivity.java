package com.sevya.vtvhmobile;

import android.content.res.AssetManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.sevya.vtvhmobile.db.DataBaseAdapter;
import com.sevya.vtvhmobile.models.ResponseStatus;
import com.sevya.vtvhmobile.models.SalesmanCart;
import com.sevya.vtvhmobile.util.SOAPServices;
import com.sevya.vtvhmobile.webservices.SOAPServiceClient;
import com.sevya.vtvhmobile.webservices.ServiceParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;

/**
 * Created by abhinaym on 8/10/15.
 */
public class SettingsActivity extends AppCompatActivity {
    CheckBox sslcheckbox, nonsslcheckbox;
    Toolbar mToolbar;
    TextView textPort;
    TextView textHost;
    EditText portNum;
    EditText hostName;
    String url;
    String port;
    String hostAddress;
    String hostPort;
    ResponseStatus status;
    Thread thread;
    Switch sslSwitch;
    DataBaseAdapter databasehelper;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backarrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        databasehelper=new DataBaseAdapter(this);



        textPort = (TextView) findViewById(R.id.textPortName);
        textHost=(TextView)findViewById(R.id.textHostName);
        textPort.setVisibility(View.INVISIBLE);
        portNum = (EditText) findViewById(R.id.portno);
        hostName=(EditText)findViewById(R.id.hostname);
        portNum.setVisibility(View.INVISIBLE);
        hostName.setText("http://192.168.1.19:2006/VTVHQuickSaleService.asmx");

        sslSwitch = (Switch) findViewById(R.id.mySslSwitch);
        sslSwitch.setChecked(true);
        sslSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (sslSwitch.isChecked()) {
                    hostName.setText("http://192.168.1.19:2006/VTVHQuickSaleService.asmx");
                    textPort.setVisibility(View.INVISIBLE);
                    portNum.setVisibility(View.INVISIBLE);
                } else {
                    hostName.setText("");
                    textPort.setVisibility(View.VISIBLE);
                    portNum.setVisibility(View.VISIBLE);



                }

            }
        });
}

    public void authenticate(View v)
    {
        ButtonAnimation.animation(v);

        if(sslSwitch.isChecked())
        {
           url= hostName.getText().toString();
           // port=portNum.getText().toString();

            try {
                AssetManager assetManager = getApplicationContext().getAssets();
                BufferedReader reader = new BufferedReader(new InputStreamReader(assetManager.open("SOAPServices.json")));
                String s;
                StringBuffer buffer = new StringBuffer();
                while((s=reader.readLine())!=null){
                    buffer.append(s);
                }


                String newBuffer=buffer.toString().replace("#SOAP_URL#",url);
                SOAPServices.loadServices(newBuffer);

                Log.d("new buffer",""+newBuffer);
            }catch(Exception e){
                e.printStackTrace();
            }

            Toast.makeText(SettingsActivity.this, "Updated successfully", Toast.LENGTH_SHORT).show();
        }
        else {

            url=hostName.getText().toString();
            port=portNum.getText().toString();

            databasehelper.insertServerCredentials(url,port);

            cursor=databasehelper.getServerCredentials();
            cursor.moveToFirst();
            hostAddress=cursor.getString(cursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.HOSTNAME));
            hostPort=cursor.getString(cursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.PORTNUMBER));

            try {
                AssetManager assetManager = getApplicationContext().getAssets();
                BufferedReader reader = new BufferedReader(new InputStreamReader(assetManager.open("SOAPServices.json")));
                String s;
                StringBuffer buffer = new StringBuffer();
                while((s=reader.readLine())!=null) {
                    buffer.append(s);


                    StringBuffer addressBuffer=new StringBuffer();
                    addressBuffer.append("http://"+url+":"+port+"/"+"VTVHQuickSaleService.asmx");

                    Log.d("AddressBuffer",""+addressBuffer);

                    String newBuffer=buffer.toString().replace("#SOAP_URL#",addressBuffer);
                    SOAPServices.loadServices(newBuffer);

                    Log.d("new buffer", "" + newBuffer);

                }
            }catch(Exception e){
                e.printStackTrace();
            }

            Toast.makeText(SettingsActivity.this, "Updated successfully", Toast.LENGTH_SHORT).show();
        }



    }

    public void test(View v){
        ButtonAnimation.animation(v);

        thread=new Thread() {
            public void run() {
                SalesmanCart salesmanCart=new SalesmanCart();
                salesmanCart.setSalesmanId(new Integer(10));
                salesmanCart.setDate("2015-11-09");
                SOAPServiceClient soapServiceClient=new SOAPServiceClient();
                //ServiceParams modalParam = new ServiceParams(userModel,"userModel", UserModel.class);
                 ServiceParams primitiveParam = new ServiceParams(salesmanCart, "salesmanCart", SalesmanCart.class);
                {
                    try {
                            status = (ResponseStatus) soapServiceClient.callService(SOAPServices.getServices("testConnectionService"), primitiveParam);
                        if (status.getStatusCode() == 200) {
                            JSONObject object = new JSONObject(status.getStatusResponse());


                            SettingsActivity.this.runOnUiThread(new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(SettingsActivity.this, "Connection Established", Toast.LENGTH_SHORT).show();
                                }
                            }));

                        }
                        else if(status.getStatusCode()==500){
                            SettingsActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(SettingsActivity.this, "" + status.getStatusResponse(), Toast.LENGTH_SHORT).show();
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
}
