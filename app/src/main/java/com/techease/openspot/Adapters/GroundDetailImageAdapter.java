package com.techease.openspot.Adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.techease.openspot.R;
import com.techease.openspot.controllers.GroundDetailImgeModel;

import java.util.List;

/**
 * Created by Adamnoor on 3/21/2018.
 */

public class GroundDetailImageAdapter extends RecyclerView.Adapter<GroundDetailImageAdapter.ViewHolder> {
    Context context;
    List<GroundDetailImgeModel> models;
    public GroundDetailImageAdapter(Context context, List<GroundDetailImgeModel> list) {
        this.context=context;
        this.models=list;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_image_ground_details,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GroundDetailImgeModel model=models.get(position);
        Glide.with(context).load(model.getImageUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.iv);
        }
    }
}
