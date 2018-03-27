package com.techease.openspot.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.techease.openspot.R;

/**
 * Created by Adamnoor on 3/20/2018.
 */

public class DateAndTimeAdapter extends RecyclerView.Adapter<DateAndTimeAdapter.viewHolder>  {
    String[] names;
    Context context;
    public DateAndTimeAdapter(Context context, String[] dateStringArray) {
        this.context=context;
        this.names=dateStringArray;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_date_and_time,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DateAndTimeAdapter.viewHolder holder, final int position) {
        holder.days.setText(names[position]);
        holder.days.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getDate=names[position];
                Toast.makeText(context, getDate, Toast.LENGTH_SHORT).show();
                holder.editor.putString("filterDate",getDate).commit();

            }
        });
    }

    @Override
    public int getItemCount() {
        return names.length;
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView days;
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        public viewHolder(View itemView) {
            super(itemView);
            days =(TextView)itemView.findViewById(R.id.todayDate);
            sharedPreferences = context.getSharedPreferences("abc", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }
    }
}
