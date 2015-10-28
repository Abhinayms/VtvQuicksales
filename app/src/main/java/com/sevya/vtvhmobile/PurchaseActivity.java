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

public class PurchaseActivity extends AppCompatActivity {

    Toolbar mToolbar;
    Button sbmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purchase_details);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Purchase Details");
        setSupportActionBar(mToolbar);
        /*getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu);*/
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backarrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        onSubmitButtonClick();
    }

    public void onSubmitButtonClick()
    {
        sbmit=(Button)findViewById(R.id.purchase_submit);
        sbmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ButtonAnimation.animation(view);

                Intent intent=new Intent(PurchaseActivity.this,SurveyActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_purchase, menu);
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
