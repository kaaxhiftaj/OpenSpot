package com.techease.openspot.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.techease.openspot.R;
import com.techease.openspot.fragments.BookingDetailsFragment;
import com.techease.openspot.fragments.ListOfAllBooking;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Adamnoor on 3/20/2018.
 */

public class DateAndTimeAdapter extends RecyclerView.Adapter<DateAndTimeAdapter.viewHolder>  {
    String[] names;
    Context context;
    public static String getDate;
    boolean isChecked=false;
    int rowID = -1;
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
        holder.days.setText(names[position].replace("-"," "));
        holder.days.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isChecked=true;
                getDate=names[position];
                 rowID = position;
                       notifyDataSetChanged();
                SimpleDateFormat input = new SimpleDateFormat("MMM-dd-yyyy");
                SimpleDateFormat outputDate=new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date oneWayTripDate = input.parse(getDate);
                    getDate= String.valueOf(outputDate.format(oneWayTripDate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                ListOfAllBooking.filterDate=getDate;
                if (BookingDetailsFragment.check==true)
                {
                    BookingDetailsFragment.filterDate=getDate;
                }
                holder.editor.putString("date",getDate).commit();
            }
        });
        if (rowID == position){
            holder.days.setBackgroundResource(R.drawable.custom_btn_green);
            holder.days.setTextColor(Color.WHITE);
        }else {
            holder.days.setBackgroundResource(0);
            holder.days.setTextColor(Color.GRAY);
        }


    }

    @Override
    public int getItemCount() {
        return names.length;
    }

    public class viewHolder extends RecyclerView.ViewHolder {
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
