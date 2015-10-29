package com.sevya.vtvhmobile;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SurveyActivity extends AppCompatActivity {

    Toolbar mToolbar;
    Spinner spinner1;
    Button sgs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("\t\tSend Survey");


        setSupportActionBar(mToolbar);
        /*getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu);*/
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backarrow);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

       // addItemsOnSpinner1();
        //addListenerOnButton();
    }

   /* public void addItemsOnSpinner1() {

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        List<String> list = new ArrayList<String>();
        list.add("Select Category");
        list.add("category 1");
        list.add("category 2");
        list.add("category 3");
        list.add("category 4");
        list.add("category 5");
        list.add("category 6");
        list.add("category 7");
        list.add("category 8");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);
    }
*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_survey, menu);
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

    public void survey1(View v)
    {
        ButtonAnimation.animation(v);

    }
    public void survey2(View v)
    {
        ButtonAnimation.animation(v);

    }
    public void survey3(View v)
    {
        ButtonAnimation.animation(v);

    }
    public void survey4(View v)
    {
        ButtonAnimation.animation(v);

    }
    /*public void addListenerOnButton() {

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        sgs= (Button) findViewById(R.id.sgs);

        sgs.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ButtonAnimation.animation(v);

                Toast.makeText(SurveyActivity.this,
                         String.valueOf(spinner1.getSelectedItem())+"  Survey sent",
                        Toast.LENGTH_SHORT).show();

                Intent i=new Intent(SurveyActivity.this,MainActivity.class);
                startActivity(i);
            }

        });
    }*/
}
