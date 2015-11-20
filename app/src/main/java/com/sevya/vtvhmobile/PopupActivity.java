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
        viewIdentifier.put(1, "primaryAcc");
        viewIdentifier.put(2, "name");
        viewIdentifier.put(3, "number");
        viewIdentifier.put(4, "address");

        int count = cursor.getCount();
        for (int i = 0; i < count; i++) {

            tbrow = new TableRow(this);
            checkBox = new CheckBox(this);
            checkBox.setId(0);
            tbrow.addView(checkBox);


            TextView t1v = new TextView(this);
            t1v.setText("" + i);
            t1v.setId(1);
            t1v.setTextColor(Color.BLACK);
            t1v.setGravity(Gravity.CENTER);
            tbrow.addView(t1v);

            TextView t2v = new TextView(this);
            t2v.setText("Product " + i);
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
        for(int i = 0; i < tl.getChildCount(); i++) {
            TableRow row = (TableRow) tl.getChildAt(i);

                CheckBox c = (CheckBox) row.getChildAt(0);
                if (c.isChecked()) {
                    mergeMap = new HashMap<String, String>();
                    int[] a = new int[]{row.getId()};
                    Log.d("id", "" + a.toString());
                    for(int j=1;j<row.getChildCount();j++)
                    {
                        TextView view = ((TextView)row.getChildAt(j));
                        mergeMap.put(viewIdentifier.get(view.getId()), view.getText().toString());

                    }
                    selectedList.add(mergeMap);

                }
        }


        Dialog dialog=new Dialog(PopupActivity.this);
        dialog.setContentView(R.layout.popupdialoglayout);
        TableLayout tableLayout = (TableLayout)dialog.findViewById(R.id.dialogTable);


        for(int l=0;l<selectedList.size();l++) {

            TableRow tbrow2 = new TableRow(this);
            mergeMap = selectedList.get(l);
            checkBox = new CheckBox(this);
            tbrow2.addView(checkBox);


            TextView t1v = new TextView(this);
            t1v.setText(mergeMap.get("primaryAcc"));
            t1v.setTextColor(Color.BLACK);
            t1v.setGravity(Gravity.CENTER);
            tbrow2.addView(t1v);

            TextView t2v = new TextView(this);
            t2v.setText(mergeMap.get("name"));
            t2v.setTextColor(Color.BLACK);
            t2v.setGravity(Gravity.CENTER);
            tbrow2.addView(t2v);

            TextView t3v = new TextView(this);
            t3v.setText(mergeMap.get("number"));
            t3v.setTextColor(Color.BLACK);
            t3v.setGravity(Gravity.CENTER);
            tbrow2.addView(t3v);

            TextView t4v = new TextView(this);
            t4v.setText(mergeMap.get("address"));
            t4v.setTextColor(Color.BLACK);
            t4v.setGravity(Gravity.CENTER);
            tbrow2.addView(t4v);
            tableLayout.addView(tbrow2);
        }

        dialog.setTitle("List");
        dialog.show();




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
    protected void onPause() {
        super.onPause();
        dataBaseHelper.deleteMergeTable();
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