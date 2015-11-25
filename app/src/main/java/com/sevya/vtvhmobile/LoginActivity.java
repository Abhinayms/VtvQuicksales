package com.sevya.vtvhmobile;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.sevya.vtvhmobile.models.ResponseStatus;
import com.sevya.vtvhmobile.models.SalesmanCart;
import com.sevya.vtvhmobile.util.SOAPServices;
import com.sevya.vtvhmobile.webservices.SOAPServiceClient;
import com.sevya.vtvhmobile.webservices.ServiceParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;


public class LoginActivity extends AppCompatActivity {
    Toolbar mToolbar;
    String IMEI = "";
    String MAC = "";
    ResponseStatus status;
    Thread thread;
    String connectionResponse;
    String response;
    String date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("VTVH  Mobile");
        setSupportActionBar(mToolbar);

        Date pdate=new Date();
        date = new SimpleDateFormat("yyyy-MM-dd").format(pdate);

     //  testConnection();

    }

    public void signIn(View view) {
        ButtonAnimation.animation(view);


       /* if (testConnection().equals("True")) {
            try {
                IMEI = ((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
            } catch (Exception ex) {
                if (IMEI == null)
                    IMEI = "";
            }
            //Get MAC
            try {

                MAC = ((WifiManager) this.getSystemService(Context.WIFI_SERVICE)).getConnectionInfo().getMacAddress();
            } catch (Exception ex) {
                if (MAC == null)
                    MAC = "";
            }
            Log.d("Mac", "" + MAC);
            Log.d("IMEI", "" + IMEI);


            thread = new Thread() {
                public void run() {
                    SOAPServiceClient soapServiceClient = new SOAPServiceClient();
                    {
                        try {
                            status = (ResponseStatus) soapServiceClient.callServiceUsingPrimitives(SOAPServices.getServices("getSalesmanIdByIMEIMACService"), new ServiceParams(IMEI, "IMEI", String.class), new ServiceParams(MAC, "MAC", String.class));
                            if (status.getStatusCode() == 200) {
                                JSONObject object = new JSONObject(status.getStatusResponse());

                                if (object.getString("SalesmanId").equals("null")) {
                                    LoginActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(LoginActivity.this, "Invalid Mac/Imei", Toast.LENGTH_LONG).show();
                                        }
                                    });

                                } else {

                                    LoginActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent i = new Intent(LoginActivity.this, MainActivity.class);

                                            startActivity(i);
                                        }
                                    });

                                }


                            }
                            else if(status.getStatusCode()==500){
                                LoginActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(LoginActivity.this, "" + status.getStatusResponse(), Toast.LENGTH_SHORT).show();
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
        }else {
            Toast.makeText(LoginActivity.this, "Host Unreachable", Toast.LENGTH_LONG).show();

        }*/
        Intent i = new Intent(LoginActivity.this, MainActivity.class);

        startActivity(i);

    }

    public String testConnection() {
        thread = new Thread() {
            public void run() {
                SalesmanCart salesmanCart = new SalesmanCart();
                salesmanCart.setSalesmanId(new Integer(10));
                salesmanCart.setDate(date);
                Log.d("date",""+date);

                SOAPServiceClient soapServiceClient = new SOAPServiceClient();
                ServiceParams primitiveParam = new ServiceParams(salesmanCart, "salesmanCart", SalesmanCart.class);
                {
                    try {
                        status = (ResponseStatus) soapServiceClient.callService(SOAPServices.getServices("testConnectionService"), primitiveParam);
                        if (status.getStatusCode() == 200) {
                            JSONObject object = new JSONObject(status.getStatusResponse());

                            connectionResponse=object.getString("Message");
                        }
                        else if(status.getStatusCode()==500){
                            LoginActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this, "" + status.getStatusResponse(), Toast.LENGTH_SHORT).show();
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
        return connectionResponse;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.settings) {
            Intent intent=new Intent(LoginActivity.this,SettingsActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
