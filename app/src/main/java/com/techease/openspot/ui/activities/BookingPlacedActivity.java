package com.techease.openspot.ui.activities;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.techease.openspot.R;
import com.techease.openspot.fragments.UserBookingFragment;
import com.techease.openspot.utils.AlertsUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BookingPlacedActivity extends AppCompatActivity {
    TextView tvName,tvDuration,tvTime,tvDate,tvPrice;
    Button btnDone;
    String groundId,strUserId,strPrice;
    int time_id;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    android.support.v7.app.AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_placed);

        sharedPreferences = this.getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        strUserId=sharedPreferences.getString("user_id","");
        groundId=sharedPreferences.getString("ground_id","");
        time_id=sharedPreferences.getInt("time_id",1);
        strPrice=sharedPreferences.getString("price","");


        tvDate=(TextView)findViewById(R.id.tvDate);
        tvDuration=(TextView)findViewById(R.id.tvDuration);
        tvPrice=(TextView)findViewById(R.id.tvPriceBooked);
        tvName=(TextView)findViewById(R.id.tvUserName);
        tvTime=(TextView)findViewById(R.id.tvTimeBooked);
        btnDone=(Button)findViewById(R.id.btnDone);


        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BookingPlacedActivity.this,BottomNavigationActivity.class);
                intent.putExtra("aaa","true");
                startActivity(intent);

            }
        });
        if (alertDialog==null)
        {
            alertDialog= AlertsUtils.createProgressDialog(this);
            alertDialog.show();
        }
        apicall();
    }
    private void apicall() {
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://openspot.qa/openspot/bookground"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("ZmaBooking", response);
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONObject object=jsonObject.getJSONObject("data");
                    tvDate.setText(String.valueOf(object.getString("date")));
                    tvDuration.setText(String.valueOf(object.getString("duration")));
                    tvTime.setText(String.valueOf(object.getString("time")));
                    tvPrice.setText(String.valueOf(object.getString("price")));
                    tvName.setText(String.valueOf(object.getString("name")));

                    if (alertDialog!=null)
                        alertDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (alertDialog!=null)
                        alertDialog.dismiss();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (alertDialog!=null)
                    alertDialog.dismiss();

                Log.d("error" , String.valueOf(error.getCause()));

            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded;charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ground_id",groundId);
                params.put("user_id",strUserId);
                params.put("time_id", String.valueOf(time_id));
                params.put("price",strPrice);
                return params;
            }

        };
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(stringRequest);
    }
}
