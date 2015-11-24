package com.sevya.vtvhmobile;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
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


public class PurchaseInfo extends AppCompatActivity {
    Toolbar mToolbar;
    String name;
    String number;
    String totalPrice;
    String unitPrice;
    String qty;
    String modelName;
    String modelId;
    String status;
    TextView txtname;
    TextView txtnumber;
    TextView txttotalprice;
    TextView txtqty;
    TextView txtmodelName;
    TextView txtmodelId;
    TextView txtstatus;
    String date;
    ListView saleList;
    Cursor cursor;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_info);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backarrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mToolbar.setTitle("");


        setSupportActionBar(mToolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backarrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        txtname= (TextView) findViewById(R.id.txt_name);
        txtnumber= (TextView) findViewById(R.id.txt_mobile);
        txtstatus= (TextView) findViewById(R.id.txt_status);




        Intent i=getIntent();

        name=i.getStringExtra("name");
        number=i.getStringExtra("mobile");
        totalPrice=i.getStringExtra("totalprice");
        unitPrice=i.getStringExtra("unitPrice");
        modelId=i.getStringExtra("modelId");
        modelName=i.getStringExtra("modelName");
        qty=i.getStringExtra("qty");
        status=i.getStringExtra("status");
        date=i.getStringExtra("date");

        txtname.setText(name);
        txtnumber.setText(number);
        txtstatus.setText(status);

        DataBaseAdapter dataBaseHelper=new DataBaseAdapter(this);

         cursor=dataBaseHelper.getItemSales(number,date);
        saleList=(ListView)findViewById(R.id.salelist);

        String[] from=new String[]{DataBaseAdapter.DataBaseHelper.MODEL_NAME, DataBaseAdapter.DataBaseHelper.QUANTITY, DataBaseAdapter.DataBaseHelper.PRICE, DataBaseAdapter.DataBaseHelper.TOTAL_PRICE};
        int[] to=new int[]{R.id.p_model,R.id.p_qty,R.id.p_price,R.id.p_totalprice};

        SimpleCursorAdapter saleListadapter=new SimpleCursorAdapter(PurchaseInfo.this,R.layout.cartitemlayout,cursor,from,to,0);
        saleList.setAdapter(saleListadapter);








    }
    public void callButton(View v)
    {
        ButtonAnimation.animation(v);

            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:"+number));
            startActivity(callIntent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_purchase_info, menu);
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
}
