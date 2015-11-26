package com.sevya.vtvhmobile;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import com.sevya.vtvhmobile.db.DataBaseAdapter;
import com.sevya.vtvhmobile.models.ResponseStatus;
import com.sevya.vtvhmobile.models.UserModel;
import com.sevya.vtvhmobile.util.SOAPServices;
import com.sevya.vtvhmobile.webservices.SOAPServiceClient;
import com.sevya.vtvhmobile.webservices.ServiceParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PopupActivity extends AppCompatActivity {

    Toolbar mToolbar;
    DataBaseAdapter dataBaseHelper;
    String num;
    TableLayout tl;
    TableRow tbrow;
    TableRow tbrow0;
    TableRow dtbrow;
    CheckBox checkBox;
    String name;
    private Map<Integer, String> viewIdentifier = null;
    private  List<Map<String, String>> selectedList = null;
    Map<String, String> mergeMap = null;

    private  List<Map<String, String>> selectedListMain = null;
    Map<String, String> mergeMapMain = null;
    List<String> actList;
    UserModel userModel;
    String duplicateid;
    StringBuffer buffer;
    Thread thread;
    ResponseStatus status;
    Cursor mergecusor;
    String mergeActId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        dataBaseHelper = new DataBaseAdapter(this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backarrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent i = getIntent();

        num = i.getStringExtra("cnum");
      tl = (TableLayout) findViewById(R.id.mergetable);

        populateSameCustomersList();
    }


    @SuppressWarnings("deprecation")
    public void populateSameCustomersList() {
        Cursor cursor = dataBaseHelper.getPerson(num);
      //  startManagingCursor(cursor);
        cursor.moveToFirst();


        viewIdentifier = new HashMap<Integer, String>();
        viewIdentifier.put(0, "checkbox");
        viewIdentifier.put(1, "actId");
        viewIdentifier.put(2, "name");
        viewIdentifier.put(3, "number");
        viewIdentifier.put(4, "address");
        viewIdentifier.put(5, "primaryact");

        tbrow0=new TableRow(this);

        TextView t0v0 = new TextView(this);
        t0v0.setText("Select");
        t0v0.setTextColor(Color.BLUE);
        t0v0.setGravity(Gravity.LEFT);
        t0v0.setPadding(0, 0, 10, 0);
        tbrow0.addView(t0v0);


        TextView t1v0 = new TextView(this);
        t1v0.setText("Act Id");
        t1v0.setTextColor(Color.BLUE);
        t1v0.setGravity(Gravity.LEFT);
        t1v0.setPadding(0, 0, 10, 0);
        tbrow0.addView(t1v0);

        TextView t2v0 = new TextView(this);
        t2v0.setText("Name");
        t2v0.setTextColor(Color.BLUE);
        t2v0.setGravity(Gravity.LEFT);
        t2v0.setPadding(0, 0, 10, 0);
        tbrow0.addView(t2v0);

        TextView t3v0 = new TextView(this);
        t3v0.setText("Mobile");
        t3v0.setTextColor(Color.BLUE);
        t3v0.setPadding(0, 0, 10, 0);
        t3v0.setGravity(Gravity.LEFT);
        tbrow0.addView(t3v0);

        TextView t4v0 = new TextView(this);
        t4v0.setText("Address");
        t4v0.setTextColor(Color.BLUE);
        t4v0.setPadding(0, 0, 10, 0);
        t4v0.setGravity(Gravity.LEFT);
        tbrow0.addView(t4v0);

        TextView t5v0 = new TextView(this);
        t5v0.setText("Act Type");
        t5v0.setTextColor(Color.BLUE);
        t5v0.setPadding(0, 0, 10, 0);
        t5v0.setGravity(Gravity.LEFT);
        tbrow0.addView(t5v0);

        tl.addView(tbrow0);

        int count = cursor.getCount();
        for (int i = 0; i < count; i++) {

            tbrow = new TableRow(this);
            checkBox = new CheckBox(this);
            checkBox.setId(0);
            tbrow.addView(checkBox);



            TextView t1v = new TextView(this);
            t1v.setText(cursor.getString(cursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.ACTID)));
            t1v.setId(1);
            t1v.setTextColor(Color.BLACK);
            t1v.setPadding(0, 0,10, 0);
            t1v.setGravity(Gravity.LEFT);
            tbrow.addView(t1v);

            TextView t2v = new TextView(this);
            t2v.setId(2);
            t2v.setText(cursor.getString(cursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.NAME)));
            t2v.setTextColor(Color.BLACK);
            t2v.setPadding(0, 0,10, 0);
            t2v.setGravity(Gravity.LEFT);
            tbrow.addView(t2v);

            TextView t3v = new TextView(this);
            t3v.setId(3);
            t3v.setText(cursor.getString(cursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.MOBILE_NUMBER)));
            t3v.setTextColor(Color.BLACK);
            t3v.setPadding(0, 0,10, 0);
            t3v.setGravity(Gravity.LEFT);
            tbrow.addView(t3v);

            TextView t4v = new TextView(this);
            t4v.setId(4);
            t4v.setText(cursor.getString(cursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.ADDRESS)));
            t4v.setTextColor(Color.BLACK);
            t4v.setPadding(0, 0,10, 0);
            t4v.setGravity(Gravity.LEFT);
            tbrow.addView(t4v);

            TextView t5v = new TextView(this);
            t5v.setId(5);
            t5v.setText(cursor.getString(cursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.PRIMARYACT)));
            t5v.setTextColor(Color.BLACK);
            t5v.setPadding(0, 0, 10, 0);
            t5v.setGravity(Gravity.LEFT);
            tbrow.addView(t5v);

            tl.addView(tbrow);

            cursor.moveToNext();
        }


    }

    public void merge(View v) {
        ButtonAnimation.animation(v);


        selectedList = new ArrayList<Map<String, String>>();

            for (int i = 1; i < tl.getChildCount(); i++) {
                TableRow row = (TableRow) tl.getChildAt(i);

                CheckBox c = (CheckBox) row.getChildAt(0);
                if (c.isChecked()) {
                    mergeMap = new HashMap<String, String>();
                    int[] a = new int[]{row.getId()};
                    Log.d("id", "" + a.toString());
                    for (int j = 1; j < row.getChildCount(); j++) {
                        TextView view = ((TextView) row.getChildAt(j));
                        mergeMap.put(viewIdentifier.get(view.getId()), view.getText().toString());

                    }
                    selectedList.add(mergeMap);

                }
            }
        if (selectedList.size() >1 ) {
            Dialog dialog = new Dialog(PopupActivity.this);
            dialog.setContentView(R.layout.popupdialoglayout);
            final TableLayout tableLayout1 = (TableLayout) dialog.findViewById(R.id.dialogTable);

            Button submit = (Button) dialog.findViewById(R.id.mergesubmit);

            TableRow tbrow1 = new TableRow(this);


            TextView t0v0 = new TextView(this);
            t0v0.setText("SelectOne");
            t0v0.setTextColor(Color.BLUE);
            t0v0.setGravity(Gravity.LEFT);
            t0v0.setPadding(10, 5, 10, 0);
            tbrow1.addView(t0v0);


            TextView t1v0 = new TextView(this);
            t1v0.setText("Act Id");
            t1v0.setTextColor(Color.BLUE);
            t1v0.setGravity(Gravity.LEFT);
            t1v0.setPadding(0, 0, 10, 0);
            tbrow1.addView(t1v0);

            TextView t2v0 = new TextView(this);
            t2v0.setText("Name");
            t2v0.setTextColor(Color.BLUE);
            t2v0.setGravity(Gravity.LEFT);
            t2v0.setPadding(0, 0, 10, 0);
            tbrow1.addView(t2v0);

            TextView t3v0 = new TextView(this);
            t3v0.setText("Mobile");
            t3v0.setTextColor(Color.BLUE);
            t3v0.setPadding(0, 0, 10, 0);
            t3v0.setGravity(Gravity.LEFT);
            tbrow1.addView(t3v0);

            TextView t4v0 = new TextView(this);
            t4v0.setText("Address");
            t4v0.setTextColor(Color.BLUE);
            t4v0.setPadding(0, 0, 10, 0);
            t4v0.setGravity(Gravity.LEFT);
            tbrow1.addView(t4v0);

            TextView t5v0 = new TextView(this);
            t5v0.setText("Act Type");
            t5v0.setTextColor(Color.BLUE);
            t5v0.setPadding(0, 0, 10, 0);
            t5v0.setGravity(Gravity.LEFT);
            tbrow1.addView(t5v0);

            tableLayout1.addView(tbrow1);


            for (int l = 0; l < selectedList.size(); l++) {

                mergeMap = selectedList.get(l);


                TableRow tbrow2 = new TableRow(this);
                checkBox = new CheckBox(this);
                checkBox.setPadding(10, 0, 10, 0);
                tbrow2.addView(checkBox);


                TextView t1v = new TextView(this);
                t1v.setId(1);
                t1v.setText(mergeMap.get("actId"));
                t1v.setTextColor(Color.BLACK);
                t1v.setPadding(0, 0, 10, 0);
                t1v.setGravity(Gravity.LEFT);
                tbrow2.addView(t1v);

                TextView t2v = new TextView(this);
                t2v.setId(2);
                t2v.setText(mergeMap.get("name"));
                t2v.setTextColor(Color.BLACK);
                t2v.setPadding(0, 0, 10, 0);
                t2v.setGravity(Gravity.LEFT);
                tbrow2.addView(t2v);

                TextView t3v = new TextView(this);
                t3v.setId(3);
                t3v.setText(mergeMap.get("number"));
                t3v.setTextColor(Color.BLACK);
                t3v.setPadding(0, 0, 10, 0);
                t3v.setGravity(Gravity.LEFT);
                tbrow2.addView(t3v);

                TextView t4v = new TextView(this);
                t4v.setId(4);
                t4v.setText(mergeMap.get("address"));
                t4v.setTextColor(Color.BLACK);
                t4v.setPadding(0, 0, 10, 0);
                t4v.setGravity(Gravity.LEFT);
                tbrow2.addView(t4v);

                TextView t5v = new TextView(this);
                t5v.setId(5);
                t5v.setText(mergeMap.get("primaryact"));
                t5v.setTextColor(Color.BLACK);
                t5v.setPadding(0, 0, 10, 0);
                t5v.setGravity(Gravity.LEFT);
                tbrow2.addView(t5v);


                tableLayout1.addView(tbrow2);
            }

            dialog.setTitle("Select Main Account");
            dialog.show();

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    selectedListMain = new ArrayList<Map<String, String>>();


                    actList = new ArrayList<String>();
                    for (int i = 1; i < tableLayout1.getChildCount(); i++) {
                        TableRow row1 = (TableRow) tableLayout1.getChildAt(i);
                        TextView view1 = ((TextView) row1.getChildAt(1));

                        actList.add(view1.getText().toString());

                        CheckBox c = (CheckBox) row1.getChildAt(0);
                        if (c.isChecked()) {

                            mergeMapMain = new HashMap<String, String>();
                            int[] a = new int[]{row1.getId()};

                            for (int j = 1; j < row1.getChildCount(); j++) {
                                TextView view = ((TextView) row1.getChildAt(j));
                                mergeMapMain.put(viewIdentifier.get(view.getId()), view.getText().toString());

                            }
                            selectedListMain.add(mergeMapMain);

                        }

                    }
                    Log.d("size",""+selectedList.size());
                    if (selectedList.size() == 1) {
                        String actId = mergeMapMain.get("actId");
                        final Cursor datacursor = dataBaseHelper.getAllData(actId);
                        datacursor.moveToFirst();
                        userModel = new UserModel();
                        String name = datacursor.getString(datacursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.NAME));
                        userModel.setActName(name);
                        userModel.setPrimaryActID(new Integer(1));
                        userModel.setSurName("");
                        userModel.setCompanyName(datacursor.getString(datacursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.COMPANY_NAME)));
                        userModel.setAddress1(datacursor.getString(datacursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.ADDRESS)));
                        userModel.setStreet(datacursor.getString(datacursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.STREET)));
                        userModel.setGender(datacursor.getString(datacursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.GENDER)));
                        userModel.setMobileNo(datacursor.getString(datacursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.MOBILE_NUMBER)));
                        userModel.setPhone(datacursor.getString(datacursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.LANDLINE_NUMBER)));
                        userModel.setCity(datacursor.getString(datacursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.CITY)));
                        userModel.setState(datacursor.getString(datacursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.STATE)));
                        userModel.setDistrict(datacursor.getString(datacursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.DISTRICT)));
                        userModel.setMandal(datacursor.getString(datacursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.MANDAL)));
                        userModel.setCountry(datacursor.getString(datacursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.COUNTRY)));
                        userModel.setPin(datacursor.getString(datacursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.PIN)));
                        userModel.setTinNo(datacursor.getString(datacursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.TINNO)));
                        userModel.setIsPrimaryAct(datacursor.getString(datacursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.PRIMARYACT)));
                        userModel.setEmail(datacursor.getString(datacursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.EMAIL)));
                        userModel.setFlatNo(datacursor.getString(datacursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.FLATNO)));
                        userModel.setUserId(new Integer(76));
                        userModel.setProfession(datacursor.getString(datacursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.PROFESSION)));

                        buffer = new StringBuffer(",");
                        for (int i = 0; i < actList.size(); i++) {
                            buffer.append(actList.get(i));
                            buffer.append(",");
                        }

                        duplicateid = buffer.toString();
                        Log.d("duplicateId", "" + duplicateid);

                        userModel.setDuplicateIds(duplicateid);

                        Log.d("name from database", "" + name);
                        Log.d("list", "" + actList.toString());


                        thread = new Thread() {
                            public void run() {
                                SOAPServiceClient soapServiceClient = new SOAPServiceClient();
                                ServiceParams modalParam = new ServiceParams(userModel, "userModel", UserModel.class);
                                {
                                    try {
                                        status = (ResponseStatus) soapServiceClient.callService(SOAPServices.getServices("insertCustomerDetailsService"), modalParam);
                                        if (status.getStatusCode() == 200) {
                                            JSONObject object = new JSONObject(status.getStatusResponse());

                                            mergeActId = object.getString("Actid");


                                            PopupActivity.this.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    mergecusor = dataBaseHelper.getAllData(mergeActId);
                                                    mergecusor.moveToFirst();
                                                    Intent i = new Intent(PopupActivity.this, ReceiveDetails.class);
                                                    i.putExtra("cname", mergecusor.getString(mergecusor.getColumnIndex(DataBaseAdapter.DataBaseHelper.NAME)));
                                                    i.putExtra("cnum", mergecusor.getString(mergecusor.getColumnIndex(DataBaseAdapter.DataBaseHelper.MOBILE_NUMBER)));
                                                    i.putExtra("compName", mergecusor.getString(mergecusor.getColumnIndex(DataBaseAdapter.DataBaseHelper.COMPANY_NAME)));
                                                    i.putExtra("rb", mergecusor.getString(mergecusor.getColumnIndex(DataBaseAdapter.DataBaseHelper.GENDER)));
                                                    i.putExtra("cpro", mergecusor.getString(mergecusor.getColumnIndex(DataBaseAdapter.DataBaseHelper.PROFESSION)));
                                                    i.putExtra("cmail", mergecusor.getString(mergecusor.getColumnIndex(DataBaseAdapter.DataBaseHelper.EMAIL)));
                                                    i.putExtra("cadd", mergecusor.getString(mergecusor.getColumnIndex(DataBaseAdapter.DataBaseHelper.ADDRESS)));
                                                    i.putExtra("cln", mergecusor.getString(mergecusor.getColumnIndex(DataBaseAdapter.DataBaseHelper.LANDLINE_NUMBER)));
                                                    i.putExtra("cadd1", mergecusor.getString(mergecusor.getColumnIndex(DataBaseAdapter.DataBaseHelper.DISTRICT)));
                                                    i.putExtra("cadd2", mergecusor.getString(mergecusor.getColumnIndex(DataBaseAdapter.DataBaseHelper.CITY)));
                                                    i.putExtra("cadd3", mergecusor.getString(mergecusor.getColumnIndex(DataBaseAdapter.DataBaseHelper.STREET)));
                                                    i.putExtra("actId", mergeActId);
                                                    startActivity(i);
                                                }
                                            });

                                        } else if (status.getStatusCode() == 500) {
                                            PopupActivity.this.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(PopupActivity.this, "" + status.getStatusResponse(), Toast.LENGTH_SHORT).show();
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
                    else {
                        Toast.makeText(PopupActivity.this, "Please select any 1 account", Toast.LENGTH_SHORT).show();
                    }
                }

            });

        }else {
            Toast.makeText(PopupActivity.this, " select more than 1 account to merge", Toast.LENGTH_SHORT).show();
        }
    }

    public void noMerge(View v)
    {
        ButtonAnimation.animation(v);




            selectedList = new ArrayList<Map<String, String>>();

            for (int i = 1; i < tl.getChildCount(); i++) {
                TableRow row = (TableRow) tl.getChildAt(i);

                CheckBox c = (CheckBox) row.getChildAt(0);
                if (c.isChecked()) {
                    mergeMap = new HashMap<String, String>();
                    int[] a = new int[]{row.getId()};
                    Log.d("id", "" + a.toString());
                    for (int j = 1; j < row.getChildCount(); j++) {
                        TextView view = ((TextView) row.getChildAt(j));
                        mergeMap.put(viewIdentifier.get(view.getId()), view.getText().toString());

                    }
                    selectedList.add(mergeMap);

                }
            }
        if(selectedList.size()!=0) {
            if (selectedList.size() == 1) {
                Log.d("actid",""+mergeMap.get("actId"));
                String accountId = mergeMap.get("actId");
                Cursor nomergecursor = dataBaseHelper.getAllData(accountId);
                Log.d("cursor count",""+nomergecursor.getCount());
                startManagingCursor(nomergecursor);
                nomergecursor.moveToFirst();
                String name=nomergecursor.getString(nomergecursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.NAME));
                Intent i = new Intent(PopupActivity.this, ReceiveDetails.class);
                i.putExtra("cname", name);
                i.putExtra("cnum", nomergecursor.getString(nomergecursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.MOBILE_NUMBER)));
                i.putExtra("compName", nomergecursor.getString(nomergecursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.COMPANY_NAME)));
                i.putExtra("rb", nomergecursor.getString(nomergecursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.GENDER)));
                i.putExtra("cpro", nomergecursor.getString(nomergecursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.PROFESSION)));
                i.putExtra("cmail", nomergecursor.getString(nomergecursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.EMAIL)));
                i.putExtra("cadd", nomergecursor.getString(nomergecursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.ADDRESS)));
                i.putExtra("cln", nomergecursor.getString(nomergecursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.LANDLINE_NUMBER)));
                i.putExtra("cadd1", nomergecursor.getString(nomergecursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.DISTRICT)));
                i.putExtra("cadd2", nomergecursor.getString(nomergecursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.CITY)));
                i.putExtra("cadd3", nomergecursor.getString(nomergecursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.STREET)));
                i.putExtra("actId", accountId);
                startActivity(i);
            } else {
                Toast.makeText(PopupActivity.this, "Please select only 1 account", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(PopupActivity.this, "Please select any 1 account", Toast.LENGTH_SHORT).show();
        }
    }
    public void cancel(View v)
    {
        ButtonAnimation.animation(v);

        Intent i=new Intent(PopupActivity.this,MainActivity.class);
        startActivity(i);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_popup, menu);
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
    public void onBackPressed() {
        Intent i=new Intent(PopupActivity.this,MainActivity.class);
        startActivity(i);

    }


}
