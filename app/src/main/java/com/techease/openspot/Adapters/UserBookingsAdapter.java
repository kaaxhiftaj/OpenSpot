package com.techease.openspot.Adapters;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.techease.openspot.R;
import com.techease.openspot.controllers.UserBookingsModel;
import com.techease.openspot.fragments.BookingDetailsFragment;
import com.techease.openspot.fragments.UserBookingFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_view_for_userbookings,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
       final UserBookingsModel model1=model.get(position);
        holder.tvClubInfo.setText(model1.getInformation());
        holder.tvClubName.setText(model1.getName());
        holder.tvBookingTime.setText(model1.getTime());
        Glide.with(context).load(model1.getImage()).into(holder.imageView);
        holder.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String bookingId=model1.getId();
                Toast.makeText(context, bookingId, Toast.LENGTH_SHORT).show();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://openspot.qa/openspot/cancelBooking", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (holder.alertDialog!=null)
                            holder.alertDialog.dismiss();
                        Fragment fragment=new UserBookingFragment();
                        ((AppCompatActivity)context).getFragmentManager().beginTransaction().replace(R.id.containerMain,fragment).commit();
                        Toast.makeText(context, "Booking Canceled", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (holder.alertDialog!=null)
                            holder.alertDialog.dismiss();
                        Log.d("zma error", String.valueOf(error.getCause()));
                    }
                }) {
                    @Override
                    public String getBodyContentType() {
                        return "application/x-www-form-urlencoded;charset=UTF-8";
                    }

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("booking_id",bookingId);
                        params.put("Accept", "application/json");
                        return params;
                    }
                };

                RequestQueue mRequestQueue = Volley.newRequestQueue(context);
                stringRequest.setRetryPolicy(new
                        DefaultRetryPolicy(200000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                mRequestQueue.add(stringRequest);
            }
        });
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvClubName,tvClubInfo,tvBookingTime;
        Button btnCancel;
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        android.support.v7.app.AlertDialog alertDialog;
        public ViewHolder(View itemView) {
            super(itemView);
            sharedPreferences = context.getSharedPreferences("abc", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            imageView=(ImageView)itemView.findViewById(R.id.ivClubImageUserBooking);
            tvClubName=(TextView)itemView.findViewById(R.id.tvClubNameUserBooking);
            tvClubInfo=(TextView)itemView.findViewById(R.id.tvClubInfoUserBooking);
            tvBookingTime=(TextView)itemView.findViewById(R.id.tvBookingTiming);
            btnCancel=(Button)itemView.findViewById(R.id.btnCancelUserBooking);
        }
    }
}
