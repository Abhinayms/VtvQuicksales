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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.sevya.vtvhmobile.db.DataBaseAdapter;

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
    CheckBox checkBox;
    String name;
    private Map<Integer, String> viewIdentifier = null;
    private  List<Map<String, String>> selectedList = null;
    Map<String, String> mergeMap = null;

    private  List<Map<String, String>> selectedListMain = null;
    Map<String, String> mergeMapMain = null;
    List<String> actList;

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
        startManagingCursor(cursor);
        cursor.moveToFirst();


        viewIdentifier = new HashMap<Integer, String>();
        viewIdentifier.put(0, "checkbox");
        viewIdentifier.put(1, "actId");
        viewIdentifier.put(2, "name");
        viewIdentifier.put(3, "number");
        viewIdentifier.put(4, "address");
        /*viewIdentifier.put(5, "salesmanid");
        viewIdentifier.put(6, "gender");
        viewIdentifier.put(7, "city");
        viewIdentifier.put(8, "state");
        viewIdentifier.put(9, "country");
        viewIdentifier.put(10, "district");
        viewIdentifier.put(11, "primaryact");
        viewIdentifier.put(12, "duplicateid");
        viewIdentifier.put(13, "pin");
        viewIdentifier.put(14, "tinno");
        viewIdentifier.put(15, "mandal");
        viewIdentifier.put(16, "street");
        viewIdentifier.put(17, "company");
        viewIdentifier.put(18, "profession");
        viewIdentifier.put(19, "landline");
        viewIdentifier.put(20, "email");*/











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
            t1v.setGravity(Gravity.CENTER);
            tbrow.addView(t1v);

            TextView t2v = new TextView(this);
            t2v.setId(2);
            t2v.setText(cursor.getString(cursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.NAME)));
            t2v.setTextColor(Color.BLACK);
            t2v.setGravity(Gravity.CENTER);
            tbrow.addView(t2v);

            TextView t3v = new TextView(this);
            t3v.setId(3);
            t3v.setText(cursor.getString(cursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.MOBILE_NUMBER)));
            t3v.setTextColor(Color.BLACK);
            t3v.setGravity(Gravity.CENTER);
            tbrow.addView(t3v);

            TextView t4v = new TextView(this);
            t4v.setId(4);
            t4v.setText(cursor.getString(cursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.ADDRESS)));
            t4v.setTextColor(Color.BLACK);
            t4v.setGravity(Gravity.CENTER);
            tbrow.addView(t4v);

            tl.addView(tbrow);

            cursor.moveToNext();
        }


    }
    public void merge(View v) {

        selectedList = new ArrayList<Map<String, String>>();
        for (int i = 0; i < tl.getChildCount(); i++) {
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

        Dialog dialog=new Dialog(PopupActivity.this);
        dialog.setContentView(R.layout.popupdialoglayout);
        final TableLayout tableLayout1 = (TableLayout)dialog.findViewById(R.id.dialogTable);

        Button submit=(Button)dialog.findViewById(R.id.mergesubmit);

        for(int l=0;l<selectedList.size();l++) {

            TableRow tbrow2 = new TableRow(this);
            mergeMap = selectedList.get(l);



            checkBox = new CheckBox(this);
            tbrow2.addView(checkBox);


            TextView t1v = new TextView(this);
            t1v.setId(1);
            t1v.setText(mergeMap.get("actId"));
            t1v.setTextColor(Color.BLACK);
            t1v.setGravity(Gravity.CENTER);
            tbrow2.addView(t1v);

            TextView t2v = new TextView(this);
            t2v.setId(2);
            t2v.setText(mergeMap.get("name"));
            t2v.setTextColor(Color.BLACK);
            t2v.setGravity(Gravity.CENTER);
            tbrow2.addView(t2v);

            TextView t3v = new TextView(this);
            t3v.setId(3);
            t3v.setText(mergeMap.get("number"));
            t3v.setTextColor(Color.BLACK);
            t3v.setGravity(Gravity.CENTER);
            tbrow2.addView(t3v);

            TextView t4v = new TextView(this);
            t4v.setId(4);
            t4v.setText(mergeMap.get("address"));
            t4v.setTextColor(Color.BLACK);
            t4v.setGravity(Gravity.CENTER);
            tbrow2.addView(t4v);


            tableLayout1.addView(tbrow2);
        }

        dialog.setTitle("Select Main Account");
        dialog.show();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedListMain = new ArrayList<Map<String, String>>();
                actList=new ArrayList<String>();
                for (int i = 0; i < tableLayout1.getChildCount(); i++) {
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
                String actId=mergeMapMain.get("actId");
                Cursor datacursor=dataBaseHelper.getAllData(actId);
                datacursor.moveToFirst();
                String name=datacursor.getString(datacursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.NAME));
                Log.d("name from database",""+name);
                Log.d("list",""+actList.toString());


            }
        });




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
    protected void onStop() {
        super.onStop();
        dataBaseHelper.deleteMergeTable();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataBaseHelper.deleteMergeTable();
    }



}