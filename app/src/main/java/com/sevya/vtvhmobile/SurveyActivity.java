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

    public void survey1(View v)
    {
        ButtonAnimation.animation(v);

        startActivity(i);

    }
    public void survey2(View v)
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

}
