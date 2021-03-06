package com.sevya.vtvhmobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.TextView;

import com.sevya.vtvhmobile.models.Customer;

public class ReceiveDetails extends AppCompatActivity {


    TextView name;
    TextView numm;
    TextView CompName;
    TextView pro;
    TextView email;
    TextView add;
    TextView gen;
    TextView ln;
    Button rcontinue;

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer);


        name=(TextView)findViewById(R.id.ename);
        numm=(TextView)findViewById(R.id.enumm);
        CompName=(TextView)findViewById(R.id.custCompName);
        pro=(TextView)findViewById(R.id.epro);
        email=(TextView)findViewById(R.id.email);
        add=(TextView)findViewById(R.id.eadd);
        gen=(TextView) findViewById(R.id.egen);
        ln=(TextView) findViewById(R.id.eln);

        Intent i=getIntent();

        String cname=i.getStringExtra("cname");
        String cage=i.getStringExtra("compName");
        String cnum=i.getStringExtra("cnum");
        String cpro=i.getStringExtra("cpro");
        String cmail=i.getStringExtra("cmail");
        String cadd=i.getStringExtra("cadd");
        String cgen=i.getStringExtra("rb");
        String cln=i.getStringExtra("cln");

        name.setText(cname);
        numm.setText(cnum);
        CompName.setText(cage);
        pro.setText(cpro);
        email.setText(cmail);
        add.setText(cadd);
        gen.setText(cgen);
        ln.setText(cln);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Customer");
        setSupportActionBar(mToolbar);
        /*getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu);*/
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backarrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        onButtonClick();

    }

    public void onButtonClick()
    {

        rcontinue=(Button) findViewById(R.id.Rcontinue);
        rcontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ButtonAnimation.animation(v);

                Intent in =new Intent(ReceiveDetails.this,BuyProducts.class);
                in.putExtra("cname",name.getText().toString());
                in.putExtra("cnum",numm.getText().toString());
                startActivity(in);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_receive_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id==R.id.editcustomer)
        {
            Intent intent=new Intent(this,EditCustomer.class);
            startActivity(intent);
        }
        if(id==R.id.action_cart)
        {
            Intent intent=new Intent(this,CartActivity.class);
            intent.putExtra("cname",name.getText().toString());
            intent.putExtra("cnum",numm.getText().toString());
            startActivity(intent);

        }
        if(id==R.id.action_edit)
        {
            Intent intent=new Intent(this,EditCustomer.class);
            intent.putExtra("cnum",numm.getText().toString());
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
