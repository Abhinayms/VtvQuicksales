package com.sevya.vtvhmobile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.ListView;



import com.sevya.vtvhmobile.db.DataBaseAdapter;
import com.sevya.vtvhmobile.models.ResponseStatus;
import com.sevya.vtvhmobile.webservices.WebServiceClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private Button button;
    private EditText number;
    private ListView mListView;
    DataBaseAdapter dataBaseHelper;
    public Context context;
    public Cursor cursor;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_appbar);

        dataBaseHelper = new DataBaseAdapter(this);




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
                }
                else {
                    Thread thread = new Thread() {
                        public void run() {
                            WebServiceClass webServiceClass = new WebServiceClass();
                            try

                            {
                                status = (ResponseStatus) webServiceClass.getDetailsByMobileNumber(numberr);
                                if(status.getStatusCode() == 200 ) {
                                    array = new JSONArray(status.getStatusResponse());
                                for (int index = 0; index < array.length(); index++) {
                                    try {
                                        JSONObject eachObject = (JSONObject) array.get(index);

                                        actId=eachObject.getString("ActID");
                                        name=eachObject.getString("ActName");
                                        address1=eachObject.getString("Address1");
                                        mobileNo=eachObject.getString("MobileNo");
                                        companyName=eachObject.getString("CompanyName");
                                        street=eachObject.getString("Street");
                                        city=eachObject.getString("City");
                                        email=eachObject.getString("Email");
                                        gender=eachObject.getString("Gender");
                                        district=eachObject.getString("District");
                                        landline=eachObject.getString("Phone");


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
                                                i.putExtra("actId",actId);
                                                startActivity(i);
                                            }
                                        }));

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                }
                                else if(status.getStatusCode()!=200)  {

                                    MainActivity.this.runOnUiThread(new Runnable() {
                                        public void run() {

                                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                                    MainActivity.this);

                                            // set title
                                            alertDialogBuilder.setTitle("Alert");

                                            // set dialog message
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
                                                            // if this button is clicked, just close
                                                            // the dialog box and do nothing
                                                            dialog.cancel();
                                                        }
                                                    });


                                            // create alert dialog
                                            AlertDialog alertDialog = alertDialogBuilder.create();

                                            // show it
                                            alertDialog.show();

                                        } });

                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (XmlPullParserException e) {
                                e.printStackTrace();
                            } catch (
                                    JSONException e
                                    )

                            {
                                e.printStackTrace();
                            }
                        }
                    };

                    thread.start();
                }
                    /*cursor = dataBaseHelper.getPerson(numberr);
                    int length = cursor.getCount();

                    if (length > 1) {
                        Intent intent = new Intent(MainActivity.this, PopupActivity.class);
                        intent.putExtra("cnum", numberr);
                        startActivity(intent);
                    } else {


                        if (cursor != null && cursor.moveToFirst()) {


                            String name = cursor.getString(1);
                            int mnum = cursor.getInt(2);
                            String companyName = cursor.getString(3);
                            String gender = cursor.getString(4);
                            String prof = cursor.getString(5);
                            String ln = cursor.getString(6);
                            String add = cursor.getString(7);
                            String email = cursor.getString(8);

                            Intent i = new Intent(MainActivity.this, ReceiveDetails.class);
                            i.putExtra("cname", name);
                            i.putExtra("cnum", numberr);
                            i.putExtra("cpro", companyName);
                            i.putExtra("rb", gender);
                            i.putExtra("compName", prof);
                            i.putExtra("cmail", email);
                            i.putExtra("cadd", add);
                            i.putExtra("cln", ln);

                            startActivity(i);


                        }*/





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
        // Inflate the menu; this adds items to the action bar if it is present.
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