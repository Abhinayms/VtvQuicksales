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
    Intent i;
    String selectedText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");


        setSupportActionBar(mToolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backarrow);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        i=new Intent(SurveyActivity.this,MainActivity.class);

        spinner1 = (Spinner) findViewById(R.id.survey_spinner);
        List<String> list = new ArrayList<String>();
        list.add("--Select Survey--");
        list.add("Survey 1");
        list.add("Survey 2");
        list.add("Survey 3");
        list.add("Survey 4");
        list.add("Survey 5");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);


    }


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

    public void submit(View v)
    {
        ButtonAnimation.animation(v);

        if(spinner1.getSelectedItem().toString().equals("--Select Survey--")){
            Toast.makeText(SurveyActivity.this, "Please Select one Survey", Toast.LENGTH_SHORT).show();
        }
        else{
            selectedText=spinner1.getSelectedItem().toString();
        }
        startActivity(i);

    }
   /* public void survey2(View v)
    {
        ButtonAnimation.animation(v);

        startActivity(i);

    }
    public void survey3(View v)
    {
        ButtonAnimation.animation(v);

        startActivity(i);

    }
    public void survey4(View v)
    {
        ButtonAnimation.animation(v);

        startActivity(i);

    }
*/
}
