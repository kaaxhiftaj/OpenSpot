package com.techease.openspot.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.techease.openspot.R;
import com.techease.openspot.controllers.GroundDetailTimesModel;
import com.techease.openspot.fragments.BookNowFragment;
import com.techease.openspot.fragments.BookingInformationFragment;

import java.util.List;

/**
 * Created by Adamnoor on 3/21/2018.
 */

public class GroundDetailTimesAdapter extends RecyclerView.Adapter<GroundDetailTimesAdapter.ViewHolder> {
    Context context;
    List<GroundDetailTimesModel> models;
    public GroundDetailTimesAdapter(Context context, List<GroundDetailTimesModel> timesModels) {
        this.context=context;
        this.models=timesModels;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.custom_time_price_listview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final GroundDetailTimesModel model=models.get(position);
        holder.tvTime.setText(model.getTimeTo());
        holder.tvPrice.setText(model.getPrice());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token=holder.sharedPreferences.getString("token","");
                if (!token.equals(""))
                {
                    holder.editor.putInt("time_id",model.getTimeId()).commit();
                    holder.editor.putString("price",model.getPrice()).commit();
                    holder.editor.putString("time",model.getTimeFrom()+" TO "+model.getTimeTo()).commit();
                    Fragment fragment=new BookNowFragment();
                    ((AppCompatActivity)context).getFragmentManager().beginTransaction().replace(R.id.containerMain,fragment).addToBackStack("abc").commit();
                }
                else
                {
                    holder.editor.putInt("time_id",model.getTimeId()).commit();
                    holder.editor.putString("price",model.getPrice()).commit();
                    holder.editor.putString("time",model.getTimeFrom()+" TO "+model.getTimeTo()).commit();
                    Fragment fragment=new BookingInformationFragment();
                    int timeId=model.getTimeId();
                    String time=model.getTimeFrom()+" TO "+model.getTimeTo();
                    String price=model.getPrice();
                    Bundle bundle=new Bundle();
                    bundle.putInt("timeId",timeId);
                    bundle.putString("time",time);
                    bundle.putString("price",price);
                    fragment.setArguments(bundle);
                    ((AppCompatActivity)context).getFragmentManager().beginTransaction().replace(R.id.containerMain,fragment).addToBackStack("abc").commit();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTime,tvPrice;
        LinearLayout relativeLayout;
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        public ViewHolder(View itemView) {
            super(itemView);

            sharedPreferences = context.getSharedPreferences("abc", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            tvTime=(TextView)itemView.findViewById(R.id.tvTime);
            tvPrice=(TextView)itemView.findViewById(R.id.tvPrice);
            relativeLayout=(LinearLayout) itemView.findViewById(R.id.linearLayoutTimeAndPrice);
        }
    }
}
