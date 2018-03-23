package com.techease.openspot.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
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
import com.techease.openspot.controllers.UserFavoriteModel;

import java.util.List;

/**
 * Created by Adamnoor on 3/23/2018.
 */

public class UserFavoriteAdapter extends RecyclerView.Adapter<UserFavoriteAdapter.ViewHolder>{
    Context context;
    List<UserFavoriteModel> modelList;
    public UserFavoriteAdapter(Context context, List<UserFavoriteModel> list) {
        this.context=context;
        this.modelList=list;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_user_favorite,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserFavoriteModel model=modelList.get(position);
        holder.tvClubInfo.setText(model.getInformation());
        holder.tvClubName.setText(model.getName());
        Glide.with(context).load(model.getImage()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return modelList.size();
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
            imageView=(ImageView)itemView.findViewById(R.id.ivClubImageFavorite);
            tvClubName=(TextView)itemView.findViewById(R.id.tvClubNameFavorite);
            tvClubInfo=(TextView)itemView.findViewById(R.id.tvClubInfoFavorite);
        }
    }
}
