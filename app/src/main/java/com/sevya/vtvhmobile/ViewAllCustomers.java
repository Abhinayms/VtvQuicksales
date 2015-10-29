package com.sevya.vtvhmobile;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ListView;

import com.sevya.vtvhmobile.db.DataBaseAdapter;

public class ViewAllCustomers extends AppCompatActivity {

    DataBaseAdapter dataBaseHelper;
    Toolbar mToolbar;
    int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_customers);
        dataBaseHelper=new DataBaseAdapter(this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backarrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Button viewAllCust=(Button)findViewById(R.id.viewallcust);
        viewAllCust.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view)

            {
                ButtonAnimation.animation(view);

                populateListViewFromDB();

            }

        } );


    }



    public void viewAllSales(View view)
    {

        ButtonAnimation.animation(view);

        Intent intent=new Intent(ViewAllCustomers.this,GetDetailsByDate.class);
        startActivity(intent);

    }

    @SuppressWarnings("deprecation")
    private void populateListViewFromDB()
    {
        count++;
        Cursor cursor = dataBaseHelper.getAllData();
        startManagingCursor(cursor);
        String[] fromFieldNames = new String[]
                {DataBaseAdapter.DataBaseHelper.UID, DataBaseAdapter.DataBaseHelper.NAME, DataBaseAdapter.DataBaseHelper.MOBILE_NUMBER,
                        DataBaseAdapter.DataBaseHelper.COMPANY_NAME, DataBaseAdapter.DataBaseHelper.GENDER, DataBaseAdapter.DataBaseHelper.PROFESSION,
                        DataBaseAdapter.DataBaseHelper.LANDLINE_NUMBER, DataBaseAdapter.DataBaseHelper.ADDRESS, DataBaseAdapter.DataBaseHelper.EMAIL, DataBaseAdapter.DataBaseHelper.CREATED_DATE};

        int[] toViewIDs = new int[]
                {R.id.c_id,R.id.c_name,R.id.c_number,R.id.c_age,R.id.c_gender,R.id.c_profession,R.id.c_landnumber,R.id.c_address,R.id.c_email,R.id.c_date};



        SimpleCursorAdapter myCursorAdapter =
                new SimpleCursorAdapter(
                        this,		// Context
                        R.layout.listviewlayout,	// Row layout template
                        cursor,					// cursor (set of DB records to map)
                        fromFieldNames,			// DB Column names
                        toViewIDs,
                        0				// View IDs to put information in
                );

        // Set the adapter for the list view
        ListView myList = (ListView) findViewById(R.id.listViewFromDB);

        if(count==1) {
            myList.addHeaderView(getLayoutInflater().inflate(R.layout.customerheader, null, false));
        }

        myList.setAdapter(myCursorAdapter);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_all_customers, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
