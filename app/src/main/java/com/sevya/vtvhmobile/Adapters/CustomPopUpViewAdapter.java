package com.sevya.vtvhmobile.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.sevya.vtvhmobile.R;
import com.sevya.vtvhmobile.db.DataBaseAdapter;

/**
 * Created by abhinaym on 4/11/15.
 */
public class CustomPopUpViewAdapter extends CursorAdapter {

    TextView totalprice;
    String tPrice;


    public CustomPopUpViewAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, 0);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.custwithsamemobile, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView tname = (TextView) view.findViewById(R.id.name);
        TextView tmobile = (TextView) view.findViewById(R.id.number);
        TextView tadd = (TextView) view.findViewById(R.id.address);
        TextView tactId = (TextView) view.findViewById(R.id.acttype);

        String name = cursor.getString(cursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.NAME));
        String mobile = cursor.getString(cursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.MOBILE_NUMBER));
        String add = cursor.getString(cursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.ADDRESS));
        // tPrice = cursor.getString(cursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.PRIMARYACT));

        tname.setText(name);
        tmobile.setText(mobile);
        tadd.setText(add);

        //totalprice.setText(tPrice);


    }

    private class ViewHolder {
        TextView code;
        CheckBox name;
    }




}