package com.techease.openspot.Adapters;

import android.app.Activity;
import android.content.SharedPreferences;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.techease.openspot.R;
import com.techease.openspot.controllers.AllGroundsModel;
import com.techease.openspot.fragments.BookingDetailsFragment;
import com.techease.openspot.utils.SharedPrefUtil;

import java.util.List;

/**
 * Created by Adamnoor on 3/21/2018.
 */

public class AllGroundsAdapter extends RecyclerView.Adapter<AllGroundsAdapter.ViewHolder> {
    Context context;
    List<AllGroundsModel> model;
    public AllGroundsAdapter(Context context, List<AllGroundsModel> list) {
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
        final AllGroundsModel model1=model.get(position);
        holder.tvClubInfo.setText(model1.getInformation());
        holder.tvClubName.setText(model1.getName());
        Glide.with(context).load(model1.getImage()).into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String groundId=model1.getId();
                String groundName=model1.getName();
                String information=model1.getInformation();
                holder.editor.putString("type",model1.getType()).commit();
                holder.editor.putString("groundName",model1.getName()).commit();
                holder.editor.putString("ground_id",model1.getId()).commit();
                Fragment fragment=new BookingDetailsFragment();
                Bundle bundle=new Bundle();
                bundle.putString("groundId",groundId);
                bundle.putString("info",information);
                bundle.putString("groundName",groundName);
                fragment.setArguments(bundle);
                ((AppCompatActivity)context).getFragmentManager().beginTransaction().replace(R.id.containerMain,fragment).addToBackStack("abc").commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvClubName,tvClubInfo;
        CardView cardView;
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        public ViewHolder(View itemView) {
            super(itemView);
            sharedPreferences = context.getSharedPreferences("abc", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            imageView=(ImageView)itemView.findViewById(R.id.ivClubImage);
            tvClubName=(TextView)itemView.findViewById(R.id.tvClubName);
            tvClubInfo=(TextView)itemView.findViewById(R.id.tvClubInfo);
            cardView=(CardView)itemView.findViewById(R.id.card_view);
        }
    }
}
