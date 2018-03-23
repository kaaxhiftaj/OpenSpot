package com.techease.openspot.Adapters;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.techease.openspot.R;
import com.techease.openspot.controllers.UserBookingsModel;
import com.techease.openspot.fragments.BookingDetailsFragment;

import java.util.List;

/**
 * Created by Adamnoor on 3/22/2018.
 */

public class UserBookingsAdapter extends RecyclerView.Adapter<UserBookingsAdapter.ViewHolder>{
    Context context;
    List<UserBookingsModel> model;
    public UserBookingsAdapter(Context context, List<UserBookingsModel> list) {
        this.context=context;
        this.model=list;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_club_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
       final UserBookingsModel model1=model.get(position);
        holder.tvClubInfo.setText(model1.getInformation());
        holder.tvClubName.setText(model1.getName());
        Glide.with(context).load(model1.getImage()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvClubName,tvClubInfo;
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        public ViewHolder(View itemView) {
            super(itemView);
            sharedPreferences = context.getSharedPreferences("abc", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            imageView=(ImageView)itemView.findViewById(R.id.ivClubImage);
            tvClubName=(TextView)itemView.findViewById(R.id.tvClubName);
            tvClubInfo=(TextView)itemView.findViewById(R.id.tvClubInfo);
        }
    }
}
