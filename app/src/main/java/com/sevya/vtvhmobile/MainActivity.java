package com.sevya.vtvhmobile;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.sevya.vtvhmobile.db.DataBaseAdapter;
import com.sevya.vtvhmobile.models.ResponseStatus;
import com.sevya.vtvhmobile.util.SOAPServices;
import com.sevya.vtvhmobile.webservices.SOAPServiceClient;
import com.sevya.vtvhmobile.webservices.ServiceParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;




public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private Button button;
    private EditText number;
    DataBaseAdapter dataBaseHelper;
    public Context context;
    private ResponseStatus status;
    public JSONArray array;

    private String actId;
    private String companyName;
    private String name;
    private String address1;
    private String gender;
    private String mobileNo;
    private String email;
    private String street;
    private String city;
    private String district;
    private String landline;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_appbar);

        dataBaseHelper = new DataBaseAdapter(this);


        number = (EditText) findViewById(R.id.mnumber);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Customer Search");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawerlayout), mToolbar);


        onSearchClick();

    }

    private void onSearchClick() {

        number = (EditText) findViewById(R.id.mnumber);
        button = (Button) findViewById(R.id.imageButton);

        // add button listener
        button.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                ButtonAnimation.animation(view);


                final String numberr = number.getText().toString();

                if (!isValidNumber(numberr)) {
                    number.setError("Please enter 10 digit Mobile Number");
                } else {
                    final ProgressDialog progress;

                    progress = new ProgressDialog(MainActivity.this);
                    progress.setTitle("Please Wait");
                    progress.setMessage("Searching....");
                    progress.setCancelable(true);
                    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progress.show();
                    Thread thread = new Thread() {
                        public void run() {
                            SOAPServiceClient soapServiceClient = new SOAPServiceClient();
                            try

                            {
                                status = (ResponseStatus) soapServiceClient.callService(SOAPServices.getServices("getAccountDetailsService"), new ServiceParams(numberr, "MobileNo", Integer.class));
                                if (status.getStatusCode() == 200) {
                                    array = new JSONArray(status.getStatusResponse());
                                    if (array.length() > 1) {
                                        Intent i = new Intent(MainActivity.this, PopupActivity.class);
                                        startActivity(i);
                                    } else if (array.length() == 1) {
                                        for (int index = 0; index < array.length(); index++) {
                                            try {

                                                JSONObject eachObject = (JSONObject) array.get(index);

                                                actId = eachObject.getString("PrimaryActID");
                                                Log.d("mac", "" + actId);

                                                name = eachObject.getString("ActName");
                                                address1 = eachObject.getString("Address1");
                                                mobileNo = eachObject.getString("MobileNo");
                                                companyName = eachObject.getString("CompanyName");
                                                street = eachObject.getString("Street");
                                                city = eachObject.getString("City");
                                                email = eachObject.getString("Email");
                                                gender = eachObject.getString("Gender");
                                                district = eachObject.getString("District");
                                                landline = eachObject.getString("Phone");


                                                MainActivity.this.runOnUiThread(new Thread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Intent i = new Intent(MainActivity.this, ReceiveDetails.class);
                                                        i.putExtra("cname", name);
                                                        i.putExtra("cnum", mobileNo);
                                                        i.putExtra("cpro", companyName);
                                                        i.putExtra("rb", gender);
                                                        //   i.putExtra("compName", prof);
                                                        i.putExtra("cmail", email);
                                                        i.putExtra("cadd", address1);
                                                        i.putExtra("cln", landline);
                                                        i.putExtra("cadd1", street);
                                                        i.putExtra("cadd2", city);
                                                        i.putExtra("cadd3", district);
                                                        i.putExtra("actId", actId);
                                                        startActivity(i);
                                                    }
                                                }));

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                }else if (status.getStatusCode() != 200) {

                                        progress.dismiss();

                                        MainActivity.this.runOnUiThread(new Runnable() {
                                            public void run() {

                                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                                        MainActivity.this);

                                                alertDialogBuilder.setTitle("Alert");

                                                alertDialogBuilder
                                                        .setMessage("Did not find any matches with this Number. \nCreate new?")
                                                        .setCancelable(false)
                                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int id) {
                                                                Intent i = new Intent(MainActivity.this, AddCustomer.class);
                                                                i.putExtra("cnum", numberr);
                                                                startActivity(i);

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
                                        });



                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    thread.start();
                }

            }


        });

    }

    private boolean isValidNumber(String numberr) {
        if (numberr != null && numberr.length() == 10) {
            return true;
        }
        return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        return true;
    }






}