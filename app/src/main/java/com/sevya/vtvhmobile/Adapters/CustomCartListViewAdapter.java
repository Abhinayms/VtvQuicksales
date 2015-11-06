package com.sevya.vtvhmobile.Adapters;

/**
 * Created by abhinaym on 4/11/15.
 */
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.sevya.vtvhmobile.R;
import com.sevya.vtvhmobile.db.DataBaseAdapter;

/**
 * Created by abhinaym on 3/11/15.
 */
public class CustomCartListViewAdapter extends CursorAdapter {

    public CustomCartListViewAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, 0);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();

    }

    private int[] colors = new int[] { 0x15527921, 0x6323595 };
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.cartitemlayout, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView modelno=(TextView)view.findViewById(R.id.p_model);
        TextView qty=(TextView)view.findViewById(R.id.p_qty);
        TextView price=(TextView)view.findViewById(R.id.p_price);
        TextView totalprice=(TextView)view.findViewById(R.id.p_totalprice);

        String modelNo=cursor.getString(cursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.MODEL_ID));
        String quty=cursor.getString(cursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.QUANTITY));
        String uPrice=cursor.getString(cursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.PRICE));
        String tPrice=cursor.getString(cursor.getColumnIndex(DataBaseAdapter.DataBaseHelper.TOTAL_PRICE));

        modelno.setText(modelNo);
        qty.setText(quty);
        price.setText(uPrice);

        totalprice.setText(tPrice);


    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = super.getView(position, convertView, parent);
        int colorPos = position % colors.length;
        view.setBackgroundColor(colors[colorPos]);
        return view;
    }
}
