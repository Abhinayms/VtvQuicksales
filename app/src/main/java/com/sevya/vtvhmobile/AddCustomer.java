package com.sevya.vtvhmobile;

/**
 * Created by abhinaym on 24/10/15.
 */
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.sevya.vtvhmobile.db.DataBaseAdapter;
import com.sevya.vtvhmobile.models.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddCustomer extends AppCompatActivity implements View.OnTouchListener {

    EditText cname;
    EditText cnum;
    EditText compName;
    EditText cln;
    EditText cadd;
    EditText cmail;

    ImageButton cbname;
    ImageButton cbnum;
    ImageButton cbcpmny;
    ImageButton cbmail;
    ImageButton cbadd;
    ImageButton cbln;

    Spinner spinner1;

    public RadioGroup rdg;
    public  RadioButton male;
    public RadioButton female;
    public String selectedType="";
    Button done;
    Button cancel;

    DataBaseAdapter dataBaseHelper;
    Customer customer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcustomer);

        cname = (EditText) findViewById(R.id.cname);
        cnum = (EditText) findViewById(R.id.cnum);
        compName = (EditText) findViewById(R.id.compName);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        cln = (EditText) findViewById(R.id.cln);
        cadd = (EditText) findViewById(R.id.cadd);
        cmail=(EditText)findViewById(R.id.cmail);

        cbname=(ImageButton)findViewById(R.id.cbname);
        cbnum=(ImageButton)findViewById(R.id.cbnum);
        cbcpmny=(ImageButton)findViewById(R.id.cbcpmny);
        cbmail=(ImageButton)findViewById(R.id.cbmail);
        cbadd=(ImageButton)findViewById(R.id.cbadd);
        cbln=(ImageButton)findViewById(R.id.cbln);





        //clearText();

        dataBaseHelper=new DataBaseAdapter(this);

        Intent i=getIntent();
        String num=i.getStringExtra("cnum");

        cnum.setText(num);

        addItemsOnSpinner1();
        onButtonClickDone();
        onButtonClickCancel();

        cname.setOnTouchListener(this);
        cnum.setOnTouchListener(this);
        compName.setOnTouchListener(this);
        cadd.setOnTouchListener(this);
        cln.setOnTouchListener(this);
        cmail.setOnTouchListener(this);

        cbname.setOnTouchListener(this);
        cbnum.setOnTouchListener(this);
        cbcpmny.setOnTouchListener(this);
        cbadd.setOnTouchListener(this);
        cbln.setOnTouchListener(this);
        cbmail.setOnTouchListener(this);
    }


    public void addItemsOnSpinner1() {

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        List<String> list = new ArrayList<String>();
        list.add("Select Category");
        list.add("Business");
        list.add("Agriculture");
        list.add("Govt Employee");
        list.add("Private Employee");
        list.add("Others");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);
    }

    public void onButtonClickDone()
    {
        rdg = (RadioGroup) findViewById(R.id.radioSex);
        male = (RadioButton) findViewById(R.id.radioMale);
        female = (RadioButton) findViewById(R.id.radioFemale);


        male.setSelected(true);
        rdg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.radioMale) {
                    selectedType = male.getText().toString();
                } else if (i == R.id.radioFemale) {
                    selectedType = female.getText().toString();
                }
            }
        });

        try {
            done = (Button) findViewById(R.id.button_done);
            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ButtonAnimation.animation(v);
                    customer=new Customer();
                    String custName = cname.getText().toString();
                    customer.setName(custName);
                    String custNo = cnum.getText().toString();
                    customer.setMobileNumber(custNo);
                    String custCompName = compName.getText().toString();
                    customer.setAge(custCompName);
                    String gender = selectedType;
                    customer.setGender(gender);
                    String cPro = spinner1.getSelectedItem().toString();
                    customer.setProfession(cPro);
                    String cLn = cln.getText().toString();
                    customer.setLandlineNumber(cLn);
                    String cAdd = cadd.getText().toString();
                    customer.setAddress(cAdd);
                    String cMail = cmail.getText().toString();

                    customer.setEmail(cMail);



                    if (custName.length() == 0) {

                        cname.setError("Please enter the required details");

                    }
                    else if (custNo.length() != 10) {
                        cnum.setError("please enter 10 digit Mobile number");
                    }
                    else if(cAdd.length() <=0 )
                    {
                        cadd.setError("Please enter Valid Address");
                    }
                    else if(cMail.length()!=0) {
                        if(!isEmailValid(cMail))
                        cmail.setError("please enter Valid email");
                        else {
                            long id=dataBaseHelper.insertCustomer(customer);
                            Intent i = new Intent(AddCustomer.this, ReceiveDetails.class);
                            i.putExtra("cname", custName);
                            i.putExtra("cnum", custNo);
                            i.putExtra("compName", custCompName);
                            i.putExtra("cpro", cPro);
                            i.putExtra("cln", cLn);
                            i.putExtra("cadd", cAdd);
                            i.putExtra("cmail", cMail);
                            i.putExtra("rb", gender);
                            startActivity(i);
                        }
                    }
                    else {

                        long id=dataBaseHelper.insertCustomer(customer);

                        Intent i = new Intent(AddCustomer.this, ReceiveDetails.class);
                        i.putExtra("cname", custName);
                        i.putExtra("cnum", custNo);
                        i.putExtra("compName", custCompName);
                        i.putExtra("cpro", cPro);
                        i.putExtra("cln", cLn);
                        i.putExtra("cadd", cAdd);
                        i.putExtra("cmail", cMail);
                        i.putExtra("rb", gender);
                        startActivity(i);
                    }


                }
            });
        }catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{1,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }


    public void onButtonClickCancel()
    {   try {

        cancel = (Button) findViewById(R.id.button_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonAnimation.animation(v);
                Intent intent = new Intent(AddCustomer.this, MainActivity.class);
                startActivity(intent);
            }

        });
    }catch (Exception e)
    {
        e.printStackTrace();
    }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);

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

                cbname.setVisibility(View.VISIBLE);
                cbnum.setVisibility(View.INVISIBLE);
                cbcpmny.setVisibility(View.INVISIBLE);
                cbln.setVisibility(View.INVISIBLE);
                cbadd.setVisibility(View.INVISIBLE);
                cbmail.setVisibility(View.INVISIBLE);
                break;
            case R.id.cbname:
                cname.setText("");
                break;
            case R.id.cnum:
                cbname.setVisibility(View.INVISIBLE);
                cbnum.setVisibility(View.VISIBLE);
                cbcpmny.setVisibility(View.INVISIBLE);
                cbln.setVisibility(View.INVISIBLE);
                cbadd.setVisibility(View.INVISIBLE);
                cbmail.setVisibility(View.INVISIBLE);

                break;
            case R.id.cbnum:
                cnum.setText("");
                break;
            case R.id.compName:

                cbname.setVisibility(View.INVISIBLE);
                cbnum.setVisibility(View.INVISIBLE);
                cbcpmny.setVisibility(View.VISIBLE);
                cbln.setVisibility(View.INVISIBLE);
                cbadd.setVisibility(View.INVISIBLE);
                cbmail.setVisibility(View.INVISIBLE);
                break;
            case R.id.cbcpmny:
                compName.setText("");
                break;
            case R.id.cln:

                cbname.setVisibility(View.INVISIBLE);
                cbnum.setVisibility(View.INVISIBLE);
                cbcpmny.setVisibility(View.INVISIBLE);
                cbln.setVisibility(View.VISIBLE);
                cbadd.setVisibility(View.INVISIBLE);
                cbmail.setVisibility(View.INVISIBLE);
                break;
            case R.id.cbln:
                cln.setText("");
                break;
            case R.id.cadd:

                cbname.setVisibility(View.INVISIBLE);
                cbnum.setVisibility(View.INVISIBLE);
                cbcpmny.setVisibility(View.INVISIBLE);
                cbln.setVisibility(View.INVISIBLE);
                cbadd.setVisibility(View.VISIBLE);
                cbmail.setVisibility(View.INVISIBLE);
                break;
            case R.id.cbadd:
                cadd.setText("");
                break;
            case R.id.cmail:

                cbname.setVisibility(View.INVISIBLE);
                cbnum.setVisibility(View.INVISIBLE);
                cbcpmny.setVisibility(View.INVISIBLE);
                cbln.setVisibility(View.INVISIBLE);
                cbadd.setVisibility(View.INVISIBLE);
                cbmail.setVisibility(View.VISIBLE);
                break;
            case R.id.cbmail:
                cmail.setText("");
                break;

        }
        return false;
    }
}
