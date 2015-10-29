package com.sevya.vtvhmobile;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SimpleCursorTreeAdapter;
import android.widget.TextView;

import com.sevya.vtvhmobile.db.DataBaseAdapter;


public class ExpandableListActivity extends AppCompatActivity{
    DataBaseAdapter dataBaseHelper;
    MyExpandableListAdapter mAdapter;
    TextView textView;
    Toolbar mToolbar;
    Cursor mGroupsCursor;

    private ExpandableListView expandableListView;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        expandableListView = new ExpandableListView(this);
        setContentView(R.layout.activity_expandable_list);
        dataBaseHelper=new DataBaseAdapter(this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mToolbar.setTitle("\t\tSales Report");

       /* AppCompatActivity appCompatActivity=new AppCompatActivity();
        Log.d("Object", "onCreate "+appCompatActivity);*/

        setSupportActionBar(mToolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backarrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        fillData();
    }

    private void fillData() {

        i=getIntent();

        String mDate=i.getStringExtra("Date");

        mGroupsCursor = dataBaseHelper.getALLItems(mDate);
        this.startManagingCursor(mGroupsCursor);
        mGroupsCursor.moveToFirst();

        if(mGroupsCursor.getCount()==0)
        {
            textView=(TextView)findViewById(R.id.saletextview);
            textView.setVisibility(View.VISIBLE);

        }
        else {

            textView=(TextView)findViewById(R.id.saletextview);
            textView.setVisibility(View.INVISIBLE);

            ExpandableListView elv = (ExpandableListView)this.findViewById(android.R.id.list);

            mAdapter = new MyExpandableListAdapter(mGroupsCursor, this,
                    R.layout.rowlayout_expgroup,                     // Your row layout for a group
                    R.layout.rowlayout_itemlist_exp,                 // Your row layout for a child
                    new String[]{DataBaseAdapter.DataBaseHelper.NAME, DataBaseAdapter.DataBaseHelper.MOBILE_NUMBER},          // Field(s) to use from group cursor
                    new int[]{R.id.p_name, R.id.p_number},                 // Widget ids to put group data into
                    new String[]{DataBaseAdapter.DataBaseHelper.MODEL_ID, DataBaseAdapter.DataBaseHelper.QUANTITY, DataBaseAdapter.DataBaseHelper.PRICE},  // Field(s) to use from child cursors
                    new int[]{R.id.p_model, R.id.p_qty, R.id.p_price});          // Widget ids to put child data into


            elv.addHeaderView(getLayoutInflater().inflate(R.layout.saleslistheader, null, false));
            elv.setAdapter(mAdapter);                         // set the list adapter.

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_expandable_list, menu);
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

    public class MyExpandableListAdapter extends SimpleCursorTreeAdapter {
        public MyExpandableListAdapter(Cursor cursor, Context context,int groupLayout,
                                       int childLayout, String[] groupFrom, int[] groupTo, String[] childrenFrom,
                                       int[] childrenTo) {
            super(context, cursor, groupLayout, groupFrom, groupTo,
                    childLayout, childrenFrom, childrenTo);
        }



        @SuppressWarnings("deprecation")
        @Override
        protected Cursor getChildrenCursor(Cursor groupCursor) {
            String number=groupCursor.getString(groupCursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.MOBILE_NUMBER));
            String date=groupCursor.getString(groupCursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.CREATED_DATE));
            Cursor childCursor = dataBaseHelper.getItem(number,date);
            ExpandableListActivity.this.startManagingCursor(childCursor);
            childCursor.moveToFirst();
            return childCursor;
        }

    }

}
