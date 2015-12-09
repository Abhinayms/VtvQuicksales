package com.sevya.vtvhmobile.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.sevya.vtvhmobile.R;
import com.sevya.vtvhmobile.db.DataBaseAdapter;

/**
 * Created by abhinaym on 16/11/15.
 */
public class CustomSaleListViewAdapter extends CursorAdapter {
    String name;
    String mobile;
    String status;
    TextView text_name;
    TextView text_mobile;




    public CustomSaleListViewAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, 0);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();

    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.saleitemviewlayout, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        Log.d("cursor length", ""+cursor.getCount());

         text_name=(TextView)view.findViewById(R.id.s_name);
         text_mobile=(TextView)view.findViewById(R.id.s_mobile);



         name=cursor.getString(cursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.NAME));
         mobile=cursor.getString(cursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.MOBILE_NUMBER));
         status=cursor.getString(cursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.STATUS));


        text_name.setText(name);
        text_mobile.setText(mobile);



    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = super.getView(position, convertView, parent);

        if(status.equals("NotPurchased"))
        {
            text_name.setTextColor(Color.parseColor("#F44336"));
            text_mobile.setTextColor(Color.parseColor("#F44336"));
        }
       else
        {
            text_name.setTextColor(Color.parseColor("#4CAF50"));
            text_mobile.setTextColor(Color.parseColor("#4CAF50"));
        }
        return view;
    }


}
