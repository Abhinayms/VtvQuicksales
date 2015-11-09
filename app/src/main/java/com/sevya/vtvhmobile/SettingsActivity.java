package com.sevya.vtvhmobile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.sevya.vtvhmobile.models.ResponseStatus;
import com.sevya.vtvhmobile.models.SalesmanCart;
import com.sevya.vtvhmobile.util.SOAPServices;
import com.sevya.vtvhmobile.webservices.SOAPServiceClient;
import com.sevya.vtvhmobile.webservices.ServiceParams;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by abhinaym on 8/10/15.
 */
public class SettingsActivity extends AppCompatActivity{
    CheckBox sslcheckbox,nonsslcheckbox;
    Toolbar mToolbar;
    TextView textView;
    EditText portNum;
    ResponseStatus status;
    Thread thread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.settings);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        /*getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu);*/
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backarrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        sslcheckbox = (CheckBox) findViewById(R.id.sslcheckbox);
        textView = (TextView) findViewById(R.id.textView19);
        textView.setVisibility(View.INVISIBLE);
        portNum = (EditText) findViewById(R.id.portno);
        portNum.setVisibility(View.INVISIBLE);
        sslcheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckBox nonsslcheckbox;
                TextView textView = (TextView) findViewById(R.id.textView19);
                nonsslcheckbox = (CheckBox) findViewById(R.id.checkBox);
                EditText editText = (EditText) findViewById(R.id.portno);

                if (nonsslcheckbox.isChecked()) {
                    textView.setVisibility(View.INVISIBLE);
                    nonsslcheckbox.setChecked(false);
                    editText.setVisibility(View.INVISIBLE);
                    editText=(EditText)findViewById(R.id.hostname);
                    editText.setText("");

                } else {
                    CheckBox checkBox = (CheckBox)findViewById(R.id.checkBox);
                    checkBox.setChecked(true);
                    textView.setVisibility(View.VISIBLE);
                    editText.setVisibility(View.VISIBLE);
                    editText=(EditText)findViewById(R.id.hostname);
                    editText.setText("");
                    editText=(EditText)findViewById(R.id.portno);
                    editText.setText("");
                }
            }
        });

        nonsslcheckbox = (CheckBox) findViewById(R.id.checkBox);

        nonsslcheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox sslcheckbox1,sslCb;
                sslcheckbox1 = (CheckBox) findViewById(R.id.checkBox);
                sslCb = (CheckBox) findViewById(R.id.sslcheckbox);
                EditText editText = (EditText) findViewById(R.id.portno);
                TextView textView = (TextView) findViewById(R.id.textView19);
                editText.setText("");
                if (!(sslcheckbox1.isChecked())) {
                    sslCb.setChecked(true);
                    editText.setVisibility(View.INVISIBLE);
                    editText=(EditText)findViewById(R.id.hostname);
                    editText.setText("");
                    textView.setVisibility(View.INVISIBLE);

                } else {
                    sslCb.setChecked(false);
                    editText.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.VISIBLE);
                    editText=(EditText)findViewById(R.id.hostname);
                    editText.setText("");
                }

            }

        });
    }
    public void authenticate(View v)
    {
        ButtonAnimation.animation(v);

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

                                }
                            }));

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
