package com.sevya.vtvhmobile;

/**
 * Created by abhinaym on 24/10/15.
 */
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sevya.vtvhmobile.db.DataBaseAdapter;

public class EditCustomer extends AppCompatActivity implements View.OnTouchListener {

    DataBaseAdapter dataBaseHelper;
    Toolbar mToolbar;
    Cursor cursor;
    Context context;

    EditText phnnum;
    EditText cname;
    EditText cnum;
    EditText compName;
    EditText cgen;
    EditText cpro;
    EditText cln;
    EditText cadd;
    EditText cmail;

    ImageButton cbname;
    ImageButton cbnum;
    ImageButton cbcpmny;
    ImageButton cbmail;
    ImageButton cbadd;
    ImageButton cbln;
    ImageButton cbpro;
    ImageButton cbgen;



    String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_customer);
        dataBaseHelper=new DataBaseAdapter(this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Edit Customer");
        setSupportActionBar(mToolbar);

        dataBaseHelper=new DataBaseAdapter(this);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backarrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getDetails();

        cname = (EditText) findViewById(R.id.cname);
        cnum = (EditText) findViewById(R.id.cnum);
        compName = (EditText) findViewById(R.id.cpro);
        cgen = (EditText) findViewById(R.id.cgen);
        cpro = (EditText) findViewById(R.id.compName);
        cln = (EditText) findViewById(R.id.cln);
        cadd = (EditText) findViewById(R.id.cadd);
        cmail = (EditText) findViewById(R.id.cmail);

        cbname=(ImageButton)findViewById(R.id.cbname);
        cbnum=(ImageButton)findViewById(R.id.cbnum);
        cbcpmny=(ImageButton)findViewById(R.id.cbcpmny);
        cbmail=(ImageButton)findViewById(R.id.cbmail);
        cbadd=(ImageButton)findViewById(R.id.cbadd);
        cbln=(ImageButton)findViewById(R.id.cbln);
        cbpro=(ImageButton)findViewById(R.id.cbpro);
        cbgen=(ImageButton)findViewById(R.id.cbgen);

        cname.setOnTouchListener(this);
        cnum.setOnTouchListener(this);
        compName.setOnTouchListener(this);
        cadd.setOnTouchListener(this);
        cln.setOnTouchListener(this);
        cmail.setOnTouchListener(this);
        cpro.setOnTouchListener(this);
        cgen.setOnTouchListener(this);

        cbname.setOnTouchListener(this);
        cbnum.setOnTouchListener(this);
        cbcpmny.setOnTouchListener(this);
        cbadd.setOnTouchListener(this);
        cbln.setOnTouchListener(this);
        cbmail.setOnTouchListener(this);
        cbgen.setOnTouchListener(this);
        cbpro.setOnTouchListener(this);
    }


    public void getDetails() {

        //ButtonAnimation.animation(view);

        try {
                Intent i=getIntent();
               String num= i.getStringExtra("cnum");
            //phnnum = (EditText) findViewById(R.id.edit_phone);
           // num = phnnum.getText().toString();

            cursor = dataBaseHelper.getPerson(num);



            if (cursor != null) {

                cursor.moveToFirst();
                name = cursor.getString(1);
                cname.setText(cursor.getString(1));
                cnum.setText(cursor.getString(2));
                compName.setText(cursor.getString(3));
                cgen.setText(cursor.getString(4));
                cpro.setText(cursor.getString(5));
                cln.setText(cursor.getString(6));
                cadd.setText(cursor.getString(7));
                cmail.setText(cursor.getString(8));
            } else {


            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void update(View view)
    {
        ButtonAnimation.animation(view);

        int i=dataBaseHelper.update(cname.getText().toString(),cnum.getText().toString(),compName.getText().toString(),cgen.getText().toString(),cpro.getText().toString(),cln.getText().toString(),cadd.getText().toString(),
                cmail.getText().toString());

        Intent intent=new Intent(EditCustomer.this,ReceiveDetails.class);
        intent.putExtra("cname", cname.getText().toString());
        intent.putExtra("cnum", cnum.getText().toString());
        intent.putExtra("compName", cpro.getText().toString());
        intent.putExtra("cpro", compName.getText().toString());
        intent.putExtra("cln", cln.getText().toString());
        intent.putExtra("cadd", cadd.getText().toString());
        intent.putExtra("cmail", cmail.getText().toString());
        intent.putExtra("rb", cgen.getText().toString());
        startActivity(intent);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_customer, menu);
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

    @Override
    public boolean onTouch(View v,MotionEvent event) {




            switch(v.getId())
            {
                case R.id.cname:
                    cname.setFocusableInTouchMode(true);
                    cbname.setVisibility(View.VISIBLE);
                    cbnum.setVisibility(View.INVISIBLE);
                    cbcpmny.setVisibility(View.INVISIBLE);
                    cbln.setVisibility(View.INVISIBLE);
                    cbadd.setVisibility(View.INVISIBLE);
                    cbmail.setVisibility(View.INVISIBLE);
                    cgen.setVisibility(View.INVISIBLE);
                    cpro.setVisibility(View.INVISIBLE);
                    break;
                case R.id.cbname:
                    cname.setText("");
                    break;
                case R.id.cnum:
                    cnum.setFocusableInTouchMode(true);
                    cbname.setVisibility(View.INVISIBLE);
                    cbnum.setVisibility(View.VISIBLE);
                    cbcpmny.setVisibility(View.INVISIBLE);
                    cbln.setVisibility(View.INVISIBLE);
                    cbadd.setVisibility(View.INVISIBLE);
                    cbmail.setVisibility(View.INVISIBLE);
                    cgen.setVisibility(View.INVISIBLE);
                    cpro.setVisibility(View.INVISIBLE);
                    break;
                case R.id.cbnum:
                    cnum.setText("");
                    break;
                case R.id.compName:
                    compName.setFocusableInTouchMode(true);
                    cbname.setVisibility(View.INVISIBLE);
                    cbnum.setVisibility(View.INVISIBLE);
                    cbcpmny.setVisibility(View.VISIBLE);
                    cbln.setVisibility(View.INVISIBLE);
                    cbadd.setVisibility(View.INVISIBLE);
                    cbmail.setVisibility(View.INVISIBLE);
                    cgen.setVisibility(View.INVISIBLE);
                    cpro.setVisibility(View.INVISIBLE);
                    break;
                case R.id.cbcpmny:
                    compName.setText("");
                    break;
                case R.id.cln:
                    cln.setFocusableInTouchMode(true);
                    cbname.setVisibility(View.INVISIBLE);
                    cbnum.setVisibility(View.INVISIBLE);
                    cbcpmny.setVisibility(View.INVISIBLE);
                    cbln.setVisibility(View.VISIBLE);
                    cbadd.setVisibility(View.INVISIBLE);
                    cbmail.setVisibility(View.INVISIBLE);
                    cgen.setVisibility(View.INVISIBLE);
                    cpro.setVisibility(View.INVISIBLE);
                    break;
                case R.id.cbln:
                    cln.setText("");
                    break;
                case R.id.cadd:
                    cadd.setFocusableInTouchMode(true);
                    cbname.setVisibility(View.INVISIBLE);
                    cbnum.setVisibility(View.INVISIBLE);
                    cbcpmny.setVisibility(View.INVISIBLE);
                    cbln.setVisibility(View.INVISIBLE);
                    cbadd.setVisibility(View.VISIBLE);
                    cbmail.setVisibility(View.INVISIBLE);
                    cgen.setVisibility(View.INVISIBLE);
                    cpro.setVisibility(View.INVISIBLE);
                    break;
                case R.id.cbadd:
                    cadd.setText("");
                    break;
                case R.id.cmail:
                    cmail.setFocusableInTouchMode(true);
                    cbname.setVisibility(View.INVISIBLE);
                    cbnum.setVisibility(View.INVISIBLE);
                    cbcpmny.setVisibility(View.INVISIBLE);
                    cbln.setVisibility(View.INVISIBLE);
                    cbadd.setVisibility(View.INVISIBLE);
                    cbmail.setVisibility(View.VISIBLE);
                    cgen.setVisibility(View.INVISIBLE);
                    cpro.setVisibility(View.INVISIBLE);
                    break;
                case R.id.cbmail:
                    cmail.setText("");
                    break;
                case R.id.cpro:
                    cmail.setFocusableInTouchMode(true);
                    cbname.setVisibility(View.INVISIBLE);
                    cbnum.setVisibility(View.INVISIBLE);
                    cbcpmny.setVisibility(View.INVISIBLE);
                    cbln.setVisibility(View.INVISIBLE);
                    cbadd.setVisibility(View.INVISIBLE);
                    cbmail.setVisibility(View.INVISIBLE);
                    cbpro.setVisibility(View.VISIBLE);
                    cgen.setVisibility(View.INVISIBLE);

                    break;
                case R.id.cbpro:
                    cpro.setText("");
                    break;
                case R.id.cgen:
                    cmail.setFocusableInTouchMode(true);
                    cbname.setVisibility(View.INVISIBLE);
                    cbnum.setVisibility(View.INVISIBLE);
                    cbcpmny.setVisibility(View.INVISIBLE);
                    cbln.setVisibility(View.INVISIBLE);
                    cbadd.setVisibility(View.INVISIBLE);
                    cbmail.setVisibility(View.INVISIBLE);
                    cgen.setVisibility(View.VISIBLE);
                    cpro.setVisibility(View.INVISIBLE);
                    break;
                case R.id.cbgen:
                    cgen.setText("");
                    break;


        }
        return false;
    }
}
