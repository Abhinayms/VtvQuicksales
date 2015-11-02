package com.sevya.vtvhmobile;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GetDetailsByDate extends AppCompatActivity {
    Toolbar mToolbar;
    DatePicker datePicker;
    int  mYear;
    int mMonth;
    int mDay;
    String date;
    String month;
    String day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_details_by_date);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        /*getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu);*/
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backarrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowHomeEnabled(true);


        datePicker=(DatePicker) findViewById(R.id.datepicker);



    }



    public void getSalesList(View v)
    {
        ButtonAnimation.animation(v);
        mYear = datePicker.getYear();

        mMonth = datePicker.getMonth()+1;
        mDay = datePicker.getDayOfMonth();

        if(mMonth < 10){

            month = "0" + mMonth;
        }else{month=""+mMonth;}
        if(mDay < 10){

            day  = "0" + mDay ;
        }
        date ="" + mYear + "-" + "" + month + "-" + ""+ day;


        Intent intent=new Intent(GetDetailsByDate.this,ExpandableListActivity.class);
        intent.putExtra("Date",date);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_get_details_by_date, menu);
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
