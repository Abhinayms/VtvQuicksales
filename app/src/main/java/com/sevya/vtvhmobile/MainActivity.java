package com.sevya.vtvhmobile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;



import com.sevya.vtvhmobile.db.DataBaseAdapter;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private Button button;
    private EditText number;
    private ListView mListView;
    DataBaseAdapter dataBaseHelper;
    public Context context;
    public Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_appbar);

        dataBaseHelper = new DataBaseAdapter(this);




        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("\t\tCustomer Search");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawerlayout), mToolbar);


        onSearchClick();

    }


    private void onSearchClick() {

        number = (EditText) findViewById(R.id.mnumber);
        button = (Button) findViewById(R.id.imageButton);

        // add button listener
        button.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                ButtonAnimation.animation(view);

                final String numberr = number.getText().toString();

                if (!isValidNumber(numberr)) {
                    number.setError("Please enter 10 digit Mobile Number");

                } else {


                    cursor = dataBaseHelper.getPerson(numberr);
                    int length = cursor.getCount();

                    if (length > 1) {
                        Intent intent = new Intent(MainActivity.this, PopupActivity.class);
                        intent.putExtra("cnum", numberr);
                        startActivity(intent);
                    }
                    else {

                        if (cursor != null && cursor.moveToFirst()) {


                            String name = cursor.getString(1);
                            int mnum = cursor.getInt(2);
                            String companyName = cursor.getString(3);
                            String gender = cursor.getString(4);
                            String prof = cursor.getString(5);
                            String ln = cursor.getString(6);
                            String add = cursor.getString(7);
                            String email = cursor.getString(8);

                            Intent i = new Intent(MainActivity.this, ReceiveDetails.class);
                            i.putExtra("cname", name);
                            i.putExtra("cnum", numberr);
                            i.putExtra("cpro", companyName);
                            i.putExtra("rb", gender);
                            i.putExtra("compName", prof);
                            i.putExtra("cmail", email);
                            i.putExtra("cadd", add);
                            i.putExtra("cln", ln);

                            startActivity(i);


                        }
                        else {

                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                    MainActivity.this);

                            // set title
                            alertDialogBuilder.setTitle("Alert");

                            // set dialog message
                            alertDialogBuilder
                                    .setMessage("Did not find any matches with this Number. \nCreate new?")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            Intent i = new Intent(MainActivity.this, AddCustomer.class);
                                            i.putExtra("cnum", numberr);
                                            startActivity(i);

                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // if this button is clicked, just close
                                            // the dialog box and do nothing
                                            dialog.cancel();
                                        }
                                    });


                            // create alert dialog
                            AlertDialog alertDialog = alertDialogBuilder.create();

                            // show it
                            alertDialog.show();


                        }

                    }


                }

            }

        });

    }

    private boolean isValidNumber(String numberr) {
        if (numberr != null && numberr.length() == 10) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        return true;
    }






}