package com.sevya.vtvhmobile.Adapters;

/**
 * Created by abhinaym on 24/10/15.
 */
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sevya.vtvhmobile.models.Information;
import com.sevya.vtvhmobile.R;

import java.util.Collections;
import java.util.List;


public class Adapter extends RecyclerView.Adapter< Adapter.myViewHolder> {

    List<Information> data = Collections.emptyList();
    private Context context;
    private ClickListener clickListener;
    private LayoutInflater inflator;


    public Adapter(Context context, List<Information> data) {
        inflator = LayoutInflater.from(context);
        this.context = context;
        this.data = data;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflator.inflate(R.layout.custom_row, parent, false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        Information current = data.get(position);

        holder.title.setText(current.title);
        holder.icon.setImageResource(current.iconId);


    }
    public void setClickListener(ClickListener clickListener){
        this.clickListener=clickListener;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    //Image + Text von dem Drawer
    class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        ImageView icon;

        public myViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.listText);
            icon = (ImageView) itemView.findViewById(R.id.listIcon);
        }

        @Override
        public void onClick(View v) {

            Log.d("position",""+this.getPosition());
            clickListener.itemClicked(v, this.getPosition());

        }
    }
    public interface ClickListener{
        public void itemClicked(View view,int position);
    }
}