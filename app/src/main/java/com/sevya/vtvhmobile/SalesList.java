package com.sevya.vtvhmobile;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.sevya.vtvhmobile.db.DataBaseAdapter;

public class SalesList extends AppCompatActivity {

    DataBaseAdapter dataBaseHelper;
    Toolbar mToolbar;
    Intent i;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_list);

        dataBaseHelper=new DataBaseAdapter(this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        /*getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu);*/
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backarrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowHomeEnabled(true);



        populateItemsListFromDB();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sales_list, menu);
        return true;


    }

    public void populateItemsListFromDB()
    {
        i=getIntent();
        String mDate=i.getStringExtra("Date");

        Cursor cursor=dataBaseHelper.getALLItems(mDate);

        if(cursor.getCount()==0)
        {
            textView=(TextView)findViewById(R.id.saletextview);
            textView.setVisibility(View.VISIBLE);


        }
        else {


            textView=(TextView)findViewById(R.id.saletextview);
            textView.setVisibility(View.INVISIBLE);

            startManagingCursor(cursor);
            String[] fromFieldsNames = new String[]{DataBaseAdapter.DataBaseHelper.CART_ID, DataBaseAdapter.DataBaseHelper.NAME, DataBaseAdapter.DataBaseHelper.MOBILE_NUMBER,
                    DataBaseAdapter.DataBaseHelper.MODEL_ID, DataBaseAdapter.DataBaseHelper.QUANTITY, DataBaseAdapter.DataBaseHelper.PRICE};
            int[] toViewIDs = new int[]
                    {R.id.p_id, R.id.p_name, R.id.p_number, R.id.p_model, R.id.p_qty, R.id.p_price};

            SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(

                    this,
                    R.layout.salesviewlayout,
                    cursor,
                    fromFieldsNames,
                    toViewIDs,
                    0
            );

            ListView listView = (ListView) findViewById(R.id.saleslist);
            /*listView.addHeaderView(getLayoutInflater().inflate(R.layout.salesheader, null, false));*/
            listView.setAdapter(simpleCursorAdapter);
        }
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

}
