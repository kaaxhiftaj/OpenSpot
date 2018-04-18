package com.techease.openspot.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.techease.openspot.Adapters.DateAndTimeAdapter;
import com.techease.openspot.Adapters.GroundDetailImageAdapter;
import com.techease.openspot.Adapters.GroundDetailTimesAdapter;
import com.techease.openspot.R;
import com.techease.openspot.controllers.AllGroundsModel;
import com.techease.openspot.controllers.GroundDetailImgeModel;
import com.techease.openspot.controllers.GroundDetailTimesModel;
import com.techease.openspot.utils.AlertsUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BookingDetailsFragment extends Fragment implements View.OnClickListener {

    Button btnChange,btnFindSpot;
    public static Button btnBookNow;
    ImageView ivClose;
    String groundName,groundId,groundInfo;
    TextView tvName,tvInfo,tvLocation;
    TextView btnDuration30,btnDuration60, btnDuration90, btnSportFootball, btnSportBasketBall, btnSportCricket,
            tvTime1,tvTime2,tvTime3;
    List<GroundDetailTimesModel> timesModels;
    GroundDetailTimesAdapter timesAdapter;
    RecyclerView recyclerViewImage,recyclerViewTimes;
    List<GroundDetailImgeModel> list;
    GroundDetailImageAdapter adapter;
    android.support.v7.app.AlertDialog alertDialog;
    LinearLayout linearLayoutFavorite,linearLayoutFilter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String token,UserId;
    RecyclerView recyclerViewDate;
    DateAndTimeAdapter recyclerViewAdapter;
    SimpleDateFormat year;
    String filterDate,filterSport, filterDuration, filterTimeTo, filterTimeFrom,formatYear;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_booking_details, container, false);

        //hiding action bar
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        sharedPreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        recyclerViewDate = (RecyclerView) view.findViewById(R.id.dayslistview2);
        ivClose=(ImageView)view.findViewById(R.id.ivCloseBookingDetail);
        btnFindSpot=(Button)view.findViewById(R.id.findSpotBookingDetail);
        btnBookNow=(Button)view.findViewById(R.id.btnBookNowBookingDetail);
        token=sharedPreferences.getString("token","");
        UserId=sharedPreferences.getString("user_id","");
        tvName=(TextView)view.findViewById(R.id.tvGroundDetailName);
        tvInfo=(TextView)view.findViewById(R.id.tvInfoGroundDetail);
        btnChange=(Button)view.findViewById(R.id.btnChange);
        tvLocation=(TextView)view.findViewById(R.id.tvLocation);
        recyclerViewImage=(RecyclerView)view.findViewById(R.id.rvGroundDetailImage);
        recyclerViewTimes=(RecyclerView)view.findViewById(R.id.rvGroundDetailTimes);
        linearLayoutFavorite=(LinearLayout)view.findViewById(R.id.layoutFavourite);
        linearLayoutFilter=(LinearLayout)view.findViewById(R.id.bottomViewBookingDetail);

        tvTime1 = (TextView) view.findViewById(R.id.tvTime1BookingDetail);
        tvTime2 = (TextView) view.findViewById(R.id.tvTime2BookingDetail);
        tvTime3 = (TextView) view.findViewById(R.id.tvTime3BookingDetail);
        btnDuration30 = (TextView) view.findViewById(R.id.btnDuration1BookingDetail);
        btnDuration60 = (TextView) view.findViewById(R.id.btnDuration2BookingDetail);
        btnDuration90 = (TextView) view.findViewById(R.id.btnDuration3BookingDetail);
        btnSportFootball = (TextView) view.findViewById(R.id.btnSports1BookingDetail);
        btnSportBasketBall = (TextView) view.findViewById(R.id.btnSports2BookingDetail);
        btnSportCricket = (TextView) view.findViewById(R.id.btnSports3BookingDetail);

        tvTime1.setOnClickListener(this);
        tvTime2.setOnClickListener(this);
        tvTime3.setOnClickListener(this);
        btnSportCricket.setOnClickListener(this);
        btnSportBasketBall.setOnClickListener(this);
        btnSportFootball.setOnClickListener(this);
        btnDuration30.setOnClickListener(this);
        btnDuration60.setOnClickListener(this);
        btnDuration90.setOnClickListener(this);

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c);
        editor.putString("date",formattedDate).commit();

        //for next 7 days
        SimpleDateFormat format = new SimpleDateFormat("MMM dd");
        Calendar date2 = Calendar.getInstance();
        String[] dateStringArray2 = new String[7];
        for(int i = 0; i < 7;i++)
        {
            dateStringArray2[i] = format.format(date2.getTime());
            date2.add(Calendar.DATE  , 1);
            Toast.makeText(getActivity(), dateStringArray2[i], Toast.LENGTH_SHORT).show();
        }
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewDate.setLayoutManager(linearLayoutManager2);
        recyclerViewAdapter = new DateAndTimeAdapter(getActivity(),dateStringArray2);
        recyclerViewDate.setAdapter(recyclerViewAdapter);
        filterDate=sharedPreferences.getString("filterDate","");

        groundName=getArguments().getString("groundName");
        groundId=getArguments().getString("groundId");
        groundInfo=getArguments().getString("info");

        tvName.setText(groundName);
        tvInfo.setText(groundInfo);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViewImage.setLayoutManager(linearLayoutManager);
        recyclerViewTimes.setLayoutManager(new LinearLayoutManager(getActivity()));
        timesModels=new ArrayList<>();
        list=new ArrayList<>();
        if (alertDialog==null)
        {
            alertDialog= AlertsUtils.createProgressDialog(getActivity());
            alertDialog.show();
        }
        apicall();
        adapter=new GroundDetailImageAdapter(getActivity(),list);
        recyclerViewImage.setAdapter(adapter);
        timesAdapter=new GroundDetailTimesAdapter(getActivity(),timesModels);
        recyclerViewTimes.setAdapter(timesAdapter);

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             linearLayoutFilter.setVisibility(View.VISIBLE);
            }
        });
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutFilter.setVisibility(View.GONE);
            }
        });

        linearLayoutFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (token!=null)
                {
                    apiCallForFavorite();
                }
                else
                {
                    Fragment fragment=new LoginMethodForUserFavorite();
                    getFragmentManager().beginTransaction().replace(R.id.containerMain,fragment).addToBackStack("abc").commit();
                }

            }
        });
        return view;
    }

    private void apiCallForFavorite() {
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://openspot.qa/openspot/userliked"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("ZmaGroundDetail", response);

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String respo=jsonObject.getString("message");
                   // JSONObject object=jsonObject.getJSONObject("message");
                    Toast.makeText(getActivity(), respo, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
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
                params.put("user_id",UserId);
                return params;
            }

        };
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(stringRequest);
    }

    private void apicall() {
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://openspot.qa/openspot/groundDetail"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("zma timedetail", response);
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray getJson=jsonObject.getJSONArray("images");
                    for(int z=0; z<getJson.length(); z++)
                    {
                        JSONObject object=getJson.getJSONObject(z);
                        GroundDetailImgeModel model=new GroundDetailImgeModel();
                        model.setImageUrl(object.getString("image"));
                        list.add(model);
                        adapter.notifyDataSetChanged();
                    }
                    JSONObject time_Object=jsonObject.getJSONObject("times");
                    JSONArray jsonArray=time_Object.getJSONArray("Morning");
                    for (int i=0; i<jsonArray.length(); i++)
                    {
                        Log.d("zmaTimes",String.valueOf(jsonArray));
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        GroundDetailTimesModel model1=new GroundDetailTimesModel();
                        model1.setTimeId(jsonObject1.getInt("time_id"));
                        model1.setTimeTo(jsonObject1.getString("time_to"));
                        model1.setPrice(jsonObject1.getString("price"));
                        model1.setTimeFrom(jsonObject1.getString("time_from"));
                        model1.setIsBooked(jsonObject1.getString("is_booked"));
                        timesModels.add(model1);
                    }
                    timesAdapter.notifyDataSetChanged();
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
                return params;
            }

        };
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tvTime1BookingDetail:
                tvTime1.setBackgroundResource(R.drawable.custom_rounded_shape);
                tvTime2.setBackgroundResource(0);
                tvTime3.setBackgroundResource(0);
                tvTime1.setTextColor(Color.WHITE);
                tvTime2.setTextColor(Color.GRAY);
                tvTime3.setTextColor(Color.GRAY);
                filterTimeTo = "7AM";
                filterTimeFrom = "11AM";
                break;
            case R.id.tvTime2BookingDetail:
                tvTime2.setBackgroundResource(R.drawable.custom_rounded_shape);
                tvTime1.setBackgroundResource(0);
                tvTime3.setBackgroundResource(0);
                tvTime2.setTextColor(Color.WHITE);
                tvTime1.setTextColor(Color.GRAY);
                tvTime3.setTextColor(Color.GRAY);
                filterTimeTo = "12AM";
                filterTimeFrom = "2AM";
                break;
            case R.id.tvTime3BookingDetail:
                tvTime3.setBackgroundResource(R.drawable.custom_rounded_shape);
                tvTime1.setBackgroundResource(0);
                tvTime2.setBackgroundResource(0);
                tvTime3.setTextColor(Color.WHITE);
                tvTime2.setTextColor(Color.GRAY);
                tvTime1.setTextColor(Color.GRAY);
                filterTimeTo = "3PM";
                filterTimeFrom = "5PM";
                break;
            case R.id.btnSports1BookingDetail:
                btnSportFootball.setBackgroundResource(R.drawable.custom_rounded_shape);
                btnSportFootball.setTextColor(Color.WHITE);
                btnSportBasketBall.setTextColor(Color.GRAY);
                btnSportCricket.setTextColor(Color.GRAY);
                btnSportBasketBall.setBackgroundResource(0);
                btnSportCricket.setBackgroundResource(0);
                filterSport = "Football";
                break;
            case R.id.btnSports2BookingDetail:
                btnSportBasketBall.setBackgroundResource(R.drawable.custom_rounded_shape);
                btnSportBasketBall.setTextColor(Color.WHITE);
                btnSportFootball.setTextColor(Color.GRAY);
                btnSportCricket.setTextColor(Color.GRAY);
                btnSportFootball.setBackgroundResource(0);
                btnSportCricket.setBackgroundResource(0);
                filterSport = "BasketBall";
                break;
            case R.id.btnSports3BookingDetail:
                btnSportCricket.setBackgroundResource(R.drawable.custom_rounded_shape);
                btnSportCricket.setTextColor(Color.WHITE);
                btnSportBasketBall.setTextColor(Color.GRAY);
                btnSportFootball.setTextColor(Color.GRAY);
                btnSportBasketBall.setBackgroundResource(0);
                btnSportFootball.setBackgroundResource(0);
                filterSport = "Cricket";
                break;
            case R.id.btnDuration1BookingDetail:
                btnDuration30.setBackgroundResource(R.drawable.custom_rounded_shape);
                btnDuration30.setTextColor(Color.WHITE);
                btnDuration90.setTextColor(Color.GRAY);
                btnDuration60.setTextColor(Color.GRAY);
                btnDuration60.setBackgroundResource(0);
                btnDuration90.setBackgroundResource(0);
                filterDuration = "30";
                break;
            case R.id.btnDuration2BookingDetail:
                btnDuration60.setBackgroundResource(R.drawable.custom_rounded_shape);
                btnDuration60.setTextColor(Color.WHITE);
                btnDuration90.setTextColor(Color.GRAY);
                btnDuration30.setTextColor(Color.GRAY);
                btnDuration30.setBackgroundResource(0);
                btnDuration90.setBackgroundResource(0);
                filterDuration = "60";
                break;
            case R.id.btnDuration3BookingDetail:
                btnDuration90.setBackgroundResource(R.drawable.custom_rounded_shape);
                btnDuration90.setTextColor(Color.WHITE);
                btnDuration30.setTextColor(Color.GRAY);
                btnDuration60.setTextColor(Color.GRAY);
                btnDuration60.setBackgroundResource(0);
                btnDuration30.setBackgroundResource(0);
                filterDuration = "90";
                break;
            default:
                break;
        }
    }
}
