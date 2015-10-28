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
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;

import com.sevya.vtvhmobile.db.DataBaseAdapter;

public class PopupActivity extends AppCompatActivity {

    Toolbar mToolbar;
    DataBaseAdapter dataBaseHelper;
    String num;
    private RadioButton listRadioButton = null;
    int listIndex = -1;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        dataBaseHelper=new DataBaseAdapter(this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Customers List");
        setSupportActionBar(mToolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backarrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent i=getIntent();

        num=i.getStringExtra("cnum");

        populateSameCustomersList();
    }



    public void populateSameCustomersList()

    {
        Cursor cursor =dataBaseHelper.getPerson(num);
        startManagingCursor(cursor);

        String[] fromFieldsNames = new String[]{ DataBaseAdapter.DataBaseHelper.NAME,  DataBaseAdapter.DataBaseHelper.MOBILE_NUMBER,DataBaseAdapter.DataBaseHelper.ADDRESS,};
        int[] toViewIDs = new int[]
                {R.id.name,R.id.number,R.id.address };

        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(

                this,
                R.layout.custwithsamemobile,
                cursor,
                fromFieldsNames,
                toViewIDs,
                0
        );

        ListView listView = (ListView) findViewById(R.id.listViewPersons);
        /*listView.addHeaderView(getLayoutInflater().inflate(R.layout.header, null, false));*/
        listView.setAdapter(simpleCursorAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

    }





    public void onClickRadioButton(View v) {
        View vMain = ((View) v.getParent());
        // getParent() must be added 'n' times,
        // where 'n' is the number of RadioButtons' nested parents
        // in your case is one.

        // uncheck previous checked button.
        if (listRadioButton != null)
            listRadioButton.setChecked(false);
        // assign to the variable the new one
        listRadioButton = (RadioButton) v;
        // find if the new one is checked or not, and set "listIndex"
        if (listRadioButton.isChecked()) {
            listIndex = ((ViewGroup) vMain.getParent()).indexOfChild(vMain);
        } else {
            listRadioButton = null;
            listIndex = -1;
        }

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
}
