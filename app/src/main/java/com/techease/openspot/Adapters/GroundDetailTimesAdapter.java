package com.techease.openspot.Adapters;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.techease.openspot.R;
import com.techease.openspot.controllers.GroundDetailTimesModel;
import com.techease.openspot.fragments.BookNowFragment;
import com.techease.openspot.fragments.BookingDetailsFragment;
import com.techease.openspot.fragments.BookingInformationFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adamnoor on 3/21/2018.
 */

public class GroundDetailTimesAdapter extends RecyclerView.Adapter<GroundDetailTimesAdapter.ViewHolder> {
    Context context;
    List<GroundDetailTimesModel> models;
    public static  ArrayList<String> timeArray = new ArrayList<String>();
    public static ArrayList<String> priceArray=new ArrayList<String>();

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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final GroundDetailTimesModel model=models.get(position);
        holder.tvTime.setText(model.getTimeTo());
        holder.tvPrice.setText(model.getPrice());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    timeArray.add(model.getTimeTo());
                    priceArray.add(model.getPrice());
                    holder.editor.putInt("time_id",model.getTimeId()).commit();
                    holder.editor.putString("price",model.getPrice()).commit();
                    holder.editor.putString("time",model.getTimeFrom()+" TO "+model.getTimeTo()).commit();
                    BookingDetailsFragment.btnBookNow.setVisibility(View.VISIBLE);
                    BookingDetailsFragment.btnBookNow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String token=holder.sharedPreferences.getString("token","");
                            if (!token.equals(""))
                            {
                                Fragment fragment=new BookNowFragment();
                                ((AppCompatActivity) context).getFragmentManager().beginTransaction().replace(R.id.containerMain,fragment).addToBackStack("abc").commit();
                            }
                            else
                            {
                                Fragment fragment=new BookingInformationFragment();
                                ((AppCompatActivity) context).getFragmentManager().beginTransaction().replace(R.id.containerMain,fragment).addToBackStack("abc").commit();

                            }
                        }
                    });
                }
                else
                {
                    BookingDetailsFragment.btnBookNow.setVisibility(View.VISIBLE);
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
        CheckBox checkBox;
        public ViewHolder(View itemView) {
            super(itemView);

            sharedPreferences = context.getSharedPreferences("abc", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            tvTime=(TextView)itemView.findViewById(R.id.tvTime);
            tvPrice=(TextView)itemView.findViewById(R.id.tvPrice);
            checkBox=(CheckBox)itemView.findViewById(R.id.cbx);
            relativeLayout=(LinearLayout) itemView.findViewById(R.id.linearLayoutTimeAndPrice);
        }
    }
}
