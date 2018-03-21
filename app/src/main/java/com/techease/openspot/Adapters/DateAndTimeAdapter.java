package com.techease.openspot.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.techease.openspot.R;

/**
 * Created by Adamnoor on 3/20/2018.
 */

public class DateAndTimeAdapter extends RecyclerView.Adapter<DateAndTimeAdapter.viewHolder>  {
    String[] names;
    public DateAndTimeAdapter(String[] dateStringArray) {
        this.names=dateStringArray;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_date_and_time,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(DateAndTimeAdapter.viewHolder holder, int position) {
        holder.days.setText(names[position]);
    }

    @Override
    public int getItemCount() {
        return names.length;
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView days;
        public viewHolder(View itemView) {
            super(itemView);
            days =(TextView)itemView.findViewById(R.id.todayDate);
        }
    }
}
