package com.sevya.vtvhmobile.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sevya.vtvhmobile.R;
import com.sevya.vtvhmobile.models.Information;

import java.util.Collections;
import java.util.List;

/**
 * Created by abhinaym on 22/9/15.
 */
public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder> {

    private  LayoutInflater inflater;
    public List<Information> data=Collections.emptyList();

    public RecycleAdapter(Context context,List<Information> data)
    {
        inflater=LayoutInflater.from(context);
        this.data=data;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view=inflater.inflate(R.layout.custom_row,parent,false);

        MyViewHolder holder=new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Information current=data.get(position);
        holder.title.setText(current.title);
        holder.icon.setImageResource(current.iconId);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        ImageView icon;

        public MyViewHolder(View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.listText);
            icon=(ImageView)itemView.findViewById(R.id.listIcon);
        }
    }

}
