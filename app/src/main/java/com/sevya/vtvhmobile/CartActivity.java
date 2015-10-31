package com.sevya.vtvhmobile;

/**
 * Created by abhinaym on 24/10/15.
 */
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.sevya.vtvhmobile.db.DataBaseAdapter;

import java.text.NumberFormat;
import java.util.Locale;

public class CartActivity extends AppCompatActivity {

    TextView cname;
    TextView cnum;
    Intent intent;
    Toolbar mToolbar;
    Button tickButton;
    ImageButton plusButton;
    TextView textView;
    Button continueshopping;
    TextView totalPrice;

    DataBaseAdapter dataBaseHelper;
    SimpleCursorAdapter simpleCursorAdapter;
    Cursor cursor;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        /*getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu);*/
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backarrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        dataBaseHelper=new DataBaseAdapter(this);



        cname=(TextView)findViewById(R.id.cname);
        cnum=(TextView)findViewById(R.id.cnum);
        totalPrice=(TextView)findViewById(R.id.totalprice);

        intent=getIntent();

        String name=intent.getStringExtra("cname");
        String num=intent.getStringExtra("cnum");

        cname.setText(name);
        cnum.setText(num);


        onTickButtonClick();
        onPlusButtonClick();

        populateItemsListFromDB();


    }
    @SuppressWarnings("deprecation")
    private void populateItemsListFromDB()
    {   int sum=0;
        String number=cnum.getText().toString();

        String date=intent.getStringExtra("Date");


        cursor=dataBaseHelper.getItem(number, date);
        startManagingCursor(cursor);
        if(cursor.getCount()==0)
        {
            textView=(TextView)findViewById(R.id.cartitemtextview);
            textView.setVisibility(View.VISIBLE);
            continueshopping=(Button)findViewById(R.id.continueshopping);
            continueshopping.setVisibility(View.VISIBLE);
            tickButton.setVisibility(View.INVISIBLE);
            plusButton.setVisibility(View.INVISIBLE);

            continueshopping.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        ButtonAnimation.animation(v);
                                                        Intent intent=new Intent(CartActivity.this,BuyProducts.class);
                                                        intent.putExtra("cname",cname.getText().toString());
                                                        intent.putExtra("cnum",cnum.getText().toString());
                                                                startActivity(intent);
                                                    }
                                                }

            );


        }
        else {
            textView=(TextView)findViewById(R.id.cartitemtextview);
            continueshopping=(Button)findViewById(R.id.continueshopping);
            textView.setVisibility(View.INVISIBLE);
            continueshopping.setVisibility(View.INVISIBLE);



            String[] fromFieldsNames = new String[]{DataBaseAdapter.DataBaseHelper.MODEL_ID, DataBaseAdapter.DataBaseHelper.QUANTITY, DataBaseAdapter.DataBaseHelper.PRICE,};
            int[] toViewIDs = new int[]
                    {R.id.p_model, R.id.p_qty, R.id.p_price,};

            simpleCursorAdapter = new SimpleCursorAdapter(

                    this,
                    R.layout.cartitemlayout,
                    cursor,
                    fromFieldsNames,
                    toViewIDs,
                    0
            );

            listView = (ListView) findViewById(R.id.cartitemview);
            /*ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) listView.getLayoutParams();
            lp.height = 300;
            listView.setLayoutParams(lp);*/
            listView.addHeaderView(getLayoutInflater().inflate(R.layout.header, null, false));
            listView.setAdapter(simpleCursorAdapter);


            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                // setting onItemLongClickListener and passing the position to the function
                @Override
                public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                               int position, long arg3) {
                    removeItemFromList(position);

                    return true;
                }
            });
            cursor.moveToFirst();
            for(int i=0;i<cursor.getCount();i++) {
                int index = cursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.PRICE);
                String price = cursor.getString(index);
                int convertedPrice=Integer.parseInt(price);
                sum+=convertedPrice;
                cursor.moveToNext();
            }
            String tcost=NumberFormat.getNumberInstance(Locale.US).format(sum);
            totalPrice.setText(tcost);
        }

    }
    // method to remove list item
    protected void removeItemFromList(final int position) {
        final int deletePosition = position;

        AlertDialog.Builder alert = new AlertDialog.Builder(
                CartActivity.this);

        alert.setTitle("Delete");
        alert.setMessage("Do you want delete this item?");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TOD O Auto-generated method stub

                // main code on after clicking yes
                //dataBaseHelper.deleteItem(position);
                simpleCursorAdapter.notifyDataSetChanged();
                simpleCursorAdapter.notifyDataSetInvalidated();

            }
        });
        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });

        alert.show();

    }

    public void onTickButtonClick()
    {
        tickButton=(Button) findViewById(R.id.float_button_tick);
        tickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ButtonAnimation.animation(v);
                Intent intent =new Intent(CartActivity.this,SurveyActivity.class);
                startActivity(intent);


            }
        });
    }

    public void onPlusButtonClick()
    {
        plusButton=(ImageButton) findViewById(R.id.float_button_plus);
        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ButtonAnimation.animation(v);

                Intent intent=new Intent(CartActivity.this,BuyProducts.class);
                intent.putExtra("cname",cname.getText().toString());
                intent.putExtra("cnum",cnum.getText().toString());
                //intent.putExtra("listsize",((Integer)listView.getScrollBarSize()).toString());
                intent.putExtra("listsize",simpleCursorAdapter.getCount());
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cart, menu);
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
        if(id==R.id.home)
        {
            Intent intent=new Intent(CartActivity.this,BuyProducts.class);
            intent.putExtra("cname",cname.getText().toString());
            intent.putExtra("cnum",cnum.getText().toString());
            startActivity(intent);
        }
        if(id==R.id.homeicon)
        {
            Intent intent=new Intent(CartActivity.this,MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}

