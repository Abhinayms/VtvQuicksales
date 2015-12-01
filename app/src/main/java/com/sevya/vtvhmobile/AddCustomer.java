package com.sevya.vtvhmobile;

/**
 * Created by abhinaym on 24/10/15.
 */
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sevya.vtvhmobile.db.DataBaseAdapter;
import com.sevya.vtvhmobile.models.Customer;
import com.sevya.vtvhmobile.models.ResponseStatus;
import com.sevya.vtvhmobile.models.UserModel;
import com.sevya.vtvhmobile.util.SOAPServices;
import com.sevya.vtvhmobile.webservices.SOAPServiceClient;
import com.sevya.vtvhmobile.webservices.ServiceParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
    EditText cadd1;
    EditText cadd2;
    EditText cadd3;
    EditText cmail;

    ImageButton cbname;
    ImageButton cbnum;
    ImageButton cbcpmny;
    ImageButton cbmail;
    ImageButton cbadd;
    ImageButton cbadd1;
    ImageButton cbadd2;
    ImageButton cbadd3;
    ImageButton cbln;

    Spinner spinner1;

    private String custName;
    private String custNo;
    private String cMail;
    private String cLn;
    private String cAdd;
    private String cAdd1;
    private String cAdd2;
    private String cAdd3;
    private String custCompName;

    private String cPro;
    private String actId;



    public RadioGroup rdg;
    public SwitchCompat compGroup;
    public  RadioButton male;
    public RadioButton female;
    public  RadioButton yes;
    public RadioButton no;
    public String selectedType="Male";
    Button done;
    Button cancel;
    private ResponseStatus status;
    RelativeLayout genderlayout;
    DataBaseAdapter dataBaseHelper;
    Thread thread;
    UserModel userModel;
    String selectedText;
    AlertDialog levelDialog;
    TextView profession;
    TextView selectedProfession;
    LinearLayout profLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcustomer);

        cname = (EditText) findViewById(R.id.cname);
        cnum = (EditText) findViewById(R.id.cnum);
        compName = (EditText) findViewById(R.id.compName);
      //  spinner1 = (Spinner) findViewById(R.id.spinner1);
        cln = (EditText) findViewById(R.id.cln);
        cadd = (EditText) findViewById(R.id.cadd);
        cadd1=(EditText)findViewById(R.id.cadd1);
        cadd2=(EditText)findViewById(R.id.cadd2);
        cadd3=(EditText)findViewById(R.id.cadd3);
        profession=(TextView)findViewById(R.id.textprofession);
         genderlayout=(RelativeLayout)findViewById(R.id.genderlayout);
        selectedProfession=(TextView)findViewById(R.id.selectedProfession);
        profLayout=(LinearLayout)findViewById(R.id.professionlayout);

        cmail=(EditText)findViewById(R.id.cmail);

        cbname=(ImageButton)findViewById(R.id.cbname);
        cbnum=(ImageButton)findViewById(R.id.cbnum);
        cbcpmny=(ImageButton)findViewById(R.id.cbcpmny);
        cbmail=(ImageButton)findViewById(R.id.cbmail);
        cbadd=(ImageButton)findViewById(R.id.cbadd);
        cbadd1=(ImageButton)findViewById(R.id.cbadd1);
        cbadd2=(ImageButton)findViewById(R.id.cbadd2);
        cbadd3=(ImageButton)findViewById(R.id.cbadd3);
        cbln=(ImageButton)findViewById(R.id.cbln);


        dataBaseHelper=new DataBaseAdapter(this);

        Intent i=getIntent();
        String num=i.getStringExtra("cnum");

        cnum.setText(num);
        cname.setOnTouchListener(this);
        cnum.setOnTouchListener(this);
        compName.setOnTouchListener(this);
        cadd.setOnTouchListener(this);
        cadd1.setOnTouchListener(this);
        cadd2.setOnTouchListener(this);
        cadd3.setOnTouchListener(this);
        cln.setOnTouchListener(this);
        cmail.setOnTouchListener(this);

        cbname.setOnTouchListener(this);
        cbnum.setOnTouchListener(this);
        cbcpmny.setOnTouchListener(this);
        cbadd.setOnTouchListener(this);
        cbadd1.setOnTouchListener(this);
        cbadd2.setOnTouchListener(this);
        cbadd3.setOnTouchListener(this);
        cbln.setOnTouchListener(this);
        cbmail.setOnTouchListener(this);

        addItemsOnSpinner1();
        onButtonClickDone();
        onButtonClickCancel();
    }


    public void addItemsOnSpinner1() {

       /* spinner1 = (Spinner) findViewById(R.id.spinner1);
        List<String> list = new ArrayList<String>();
        list.add("Select Category");
        list.add("Business");
        list.add("Agriculture");
        list.add("Govt. Employee");
        list.add("Private Employee");
        list.add("Others");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ScrollView sv = (ScrollView) findViewById(R.id.scrollview);
                sv.fullScroll(view.getTop());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
        profession.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(final View v) {

        final CharSequence[] items = {" Business ", " Agriculture ", " Govt. Employee ", "Private Employee ","Others"};
        AlertDialog.Builder builder = new AlertDialog.Builder(AddCustomer.this);
        builder.setTitle("Profession");
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {


                switch (item) {
                    case 0:
                        selectedProfession.setText("Business");
                        selectedProfession.setTextColor(Color.parseColor("#000000"));
                        selectedText="Business";

                        break;
                    case 1:
                        selectedProfession.setText("Agriculture");
                        selectedProfession.setTextColor(Color.parseColor("#000000"));
                        selectedText="Agriculture";
                        break;
                    case 2:
                        selectedProfession.setText("Govt. Employee");
                        selectedProfession.setTextColor(Color.parseColor("#000000"));
                        selectedText="Govt. Employee";
                        break;
                    case 3:
                        selectedProfession.setText("Private Employee");
                        selectedProfession.setTextColor(Color.parseColor("#000000"));
                        selectedText="Private Employee";
                        break;
                    case 4:
                        selectedProfession.setText("Others");
                        selectedProfession.setTextColor(Color.parseColor("#000000"));
                        selectedText="Others";
                        break;


                        }
                         levelDialog.dismiss();
                ScrollView sv = (ScrollView) findViewById(R.id.scrollview);
                sv.fullScroll(v.getTop());
                 }
             });
        levelDialog = builder.create();
        levelDialog.show();
             }
        });

    }

    public void onButtonClickDone()
    {

        compGroup=(SwitchCompat)findViewById(R.id.company);
        male = (RadioButton) findViewById(R.id.radioMale);
        male.setChecked(true);
        compGroup.setChecked(false);
        genderlayout.setVisibility(View.VISIBLE);
        compGroup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

               if(isChecked)
               {
                   genderlayout.setVisibility(View.GONE);
                   selectedType="";
                   profLayout.setVisibility(View.GONE);
                   selectedText="";


               }else{
                   genderlayout.setVisibility(View.VISIBLE);
                   rdg = (RadioGroup) findViewById(R.id.radioSex);
                   male = (RadioButton) findViewById(R.id.radioMale);
                   female = (RadioButton) findViewById(R.id.radioFemale);
                   profLayout.setVisibility(View.VISIBLE);

                   male.setChecked(true);

                   rdg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                       @Override
                       public void onCheckedChanged(RadioGroup radioGroup, int i) {

                           if (i == R.id.radioMale) {
                               selectedType = "Male";


                           } else if (i == R.id.radioFemale) {
                               selectedType = "Female";

                           }
                       }
                   });
               }

            }
        });


        try {
            done = (Button) findViewById(R.id.button_done);
            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ButtonAnimation.animation(v);

                   /* if(spinner1.getSelectedItem().toString().equals("Select Category")){
                        selectedText="";
                    }
                    else{
                        selectedText=spinner1.getSelectedItem().toString();
                    }
*/
                     custName = cname.getText().toString();
                     custNo = cnum.getText().toString();
                     custCompName = compName.getText().toString();

                     cPro = selectedText;
                     cLn = cln.getText().toString();
                     cAdd = cadd.getText().toString();
                     cAdd1 = cadd1.getText().toString();
                     cAdd2 = cadd2.getText().toString();
                     cAdd3 = cadd3.getText().toString();
                     cMail = cmail.getText().toString();





                    userModel=new UserModel();
                    userModel.setPrimaryActID(new Integer(0));
                    userModel.setActName(custName);
                    userModel.setMobileNo(custNo);
                    userModel.setAddress1(cAdd1);
                    userModel.setPhone(cLn);
                    userModel.setFlatNo(cAdd);
                    userModel.setGender(selectedType);
                    userModel.setCity(cAdd2);
                    userModel.setCountry(cAdd3);
                    userModel.setState("");
                    userModel.setStreet("");
                    userModel.setSurName("");
                    userModel.setTinNo("");
                    userModel.setDistrict("");
                    userModel.setEmail(cMail);
                    userModel.setIsPrimaryAct("");
                    userModel.setPin("");
                    userModel.setCompanyName(custCompName);
                    userModel.setMandal("");
                    userModel.setDuplicateIds("");
                    userModel.setUserId(new Integer(76));
                    userModel.setProfession(cPro);


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

                      else if(!isEmailValid(cMail))
                        cmail.setError("please enter Valid email");
                        else  {
                            thread=new Thread() {
                                public void run() {
                                    SOAPServiceClient soapServiceClient=new SOAPServiceClient();
                                    ServiceParams modalParam = new ServiceParams(userModel,"userModel", UserModel.class);
                                    {
                                        try {
                                            status = (ResponseStatus) soapServiceClient.callService(SOAPServices.getServices("insertCustomerDetailsService"), modalParam);
                                            if (status.getStatusCode() == 200) {
                                                JSONObject object = new JSONObject(status.getStatusResponse());
                                                actId=object.getString("Actid");

                                                AddCustomer.this.runOnUiThread(new Thread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Intent i = new Intent(AddCustomer.this, ReceiveDetails.class);
                                                        i.putExtra("cname", custName);
                                                        i.putExtra("cnum", custNo);
                                                        i.putExtra("compName", custCompName);
                                                        i.putExtra("rb", selectedType);
                                                        i.putExtra("cpro", selectedText);
                                                        i.putExtra("cmail", cMail);
                                                        i.putExtra("cadd", cAdd);
                                                        i.putExtra("cln", cLn);
                                                        i.putExtra("cadd1", cAdd1);
                                                        i.putExtra("cadd2", cAdd2);
                                                        i.putExtra("cadd3", cAdd3);
                                                        i.putExtra("actId", actId);

                                                        startActivity(i);
                                                    }
                                                }));

                                            }
                                            else if(status.getStatusCode()==500){
                                                AddCustomer.this.runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Toast.makeText(AddCustomer.this, "" + status.getStatusResponse(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }


                                    }
                                }

                                         };
                            thread.start();
                                     }
                            }

     });
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public  boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{1,4}$";
        CharSequence inputStr = email;
        if(email.length()>0) {
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(inputStr);
            if (matcher.matches()) {
                isValid = true;
            }

        }else{
            isValid=true;
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
                cbadd1.setVisibility(View.INVISIBLE);
                cbadd2.setVisibility(View.INVISIBLE);
                cbadd3.setVisibility(View.INVISIBLE);
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
                cbadd1.setVisibility(View.INVISIBLE);
                cbadd2.setVisibility(View.INVISIBLE);
                cbadd3.setVisibility(View.INVISIBLE);
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
                cbadd1.setVisibility(View.INVISIBLE);
                cbadd2.setVisibility(View.INVISIBLE);
                cbadd3.setVisibility(View.INVISIBLE);
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
                cbadd1.setVisibility(View.INVISIBLE);
                cbadd2.setVisibility(View.INVISIBLE);
                cbadd3.setVisibility(View.INVISIBLE);
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
                cbadd1.setVisibility(View.INVISIBLE);
                cbadd2.setVisibility(View.INVISIBLE);
                cbadd3.setVisibility(View.INVISIBLE);
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
                cbadd1.setVisibility(View.INVISIBLE);
                cbadd2.setVisibility(View.INVISIBLE);
                cbadd3.setVisibility(View.INVISIBLE);
                cbmail.setVisibility(View.VISIBLE);
                break;
            case R.id.cbmail:
                cmail.setText("");
                break;
            case R.id.cadd1:

                cbname.setVisibility(View.INVISIBLE);
                cbnum.setVisibility(View.INVISIBLE);
                cbcpmny.setVisibility(View.INVISIBLE);
                cbln.setVisibility(View.INVISIBLE);
                cbadd.setVisibility(View.INVISIBLE);
                cbadd1.setVisibility(View.VISIBLE);
                cbadd2.setVisibility(View.INVISIBLE);
                cbadd3.setVisibility(View.INVISIBLE);
                cbmail.setVisibility(View.INVISIBLE);
                break;
            case R.id.cbadd1:
                cadd1.setText("");
                break;
            case R.id.cadd2:

                cbname.setVisibility(View.INVISIBLE);
                cbnum.setVisibility(View.INVISIBLE);
                cbcpmny.setVisibility(View.INVISIBLE);
                cbln.setVisibility(View.INVISIBLE);
                cbadd.setVisibility(View.INVISIBLE);
                cbadd1.setVisibility(View.INVISIBLE);
                cbadd2.setVisibility(View.VISIBLE);
                cbadd3.setVisibility(View.INVISIBLE);
                cbmail.setVisibility(View.INVISIBLE);
                break;
            case R.id.cbadd2:
                cadd2.setText("");
                break;
            case R.id.cadd3:

                cbname.setVisibility(View.INVISIBLE);
                cbnum.setVisibility(View.INVISIBLE);
                cbcpmny.setVisibility(View.INVISIBLE);
                cbln.setVisibility(View.INVISIBLE);
                cbadd.setVisibility(View.INVISIBLE);
                cbadd1.setVisibility(View.INVISIBLE);
                cbadd2.setVisibility(View.INVISIBLE);
                cbadd3.setVisibility(View.VISIBLE);
                cbmail.setVisibility(View.INVISIBLE);
                break;
            case R.id.cbadd3:
                cadd3.setText("");
                break;


        }
        return false;
    }
}
