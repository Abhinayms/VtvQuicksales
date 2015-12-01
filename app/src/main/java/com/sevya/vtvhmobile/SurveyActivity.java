package com.sevya.vtvhmobile;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.sevya.vtvhmobile.db.DataBaseAdapter;


public class SurveyActivity extends AppCompatActivity {

    Toolbar mToolbar;
    Button sgs1;
    Button sgs2;
    Button sgs3;
    Button sgs4;
    Intent i;
    int j;

    DataBaseAdapter dataBaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");

        sgs1=(Button)findViewById(R.id.survey1);
        sgs2=(Button)findViewById(R.id.survey2);
        sgs3=(Button)findViewById(R.id.survey3);
        sgs4=(Button)findViewById(R.id.survey4);

        setSupportActionBar(mToolbar);
        dataBaseHelper=new DataBaseAdapter(this);

        i=new Intent(SurveyActivity.this,MainActivity.class);

    }

    public int survey(View v)
    {
       int id= v.getId();
        if(id==R.id.survey1)
        {

            sgs1.setBackgroundColor(Color.parseColor("#9e9e9e"));
            sgs2.setBackgroundColor(Color.parseColor("#bdbdbd"));
            sgs3.setBackgroundColor(Color.parseColor("#bdbdbd"));
            sgs4.setBackgroundColor(Color.parseColor("#bdbdbd"));

            j=1;
        }
       else if(id==R.id.survey2)
        {
            sgs1.setBackgroundColor(Color.parseColor("#bdbdbd"));
            sgs2.setBackgroundColor(Color.parseColor("#9e9e9e"));
            sgs3.setBackgroundColor(Color.parseColor("#bdbdbd"));
            sgs4.setBackgroundColor(Color.parseColor("#bdbdbd"));
            j=2;
        }
        else if(id==R.id.survey3)
        {
            sgs1.setBackgroundColor(Color.parseColor("#bdbdbd"));
            sgs2.setBackgroundColor(Color.parseColor("#bdbdbd"));
            sgs3.setBackgroundColor(Color.parseColor("#9e9e9e"));
            sgs4.setBackgroundColor(Color.parseColor("#bdbdbd"));
            j=3;
        }
       else if(id==R.id.survey4)
        {
            sgs1.setBackgroundColor(Color.parseColor("#bdbdbd"));
            sgs2.setBackgroundColor(Color.parseColor("#bdbdbd"));
            sgs3.setBackgroundColor(Color.parseColor("#bdbdbd"));
            sgs4.setBackgroundColor(Color.parseColor("#9e9e9e"));
            j=4;
        }
         return j;
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
        dataBaseHelper.deleteCartTable();
        ButtonAnimation.animation(v);

            startActivity(i);



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(SurveyActivity.this,CartActivity.class);
        startActivity(i);
    }
}
