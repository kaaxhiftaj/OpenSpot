package com.techease.openspot.fragments;

import android.app.AlertDialog;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Spinner;
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
import com.techease.openspot.Adapters.AllGroundsAdapter;
import com.techease.openspot.Adapters.DateAndTimeAdapter;
import com.techease.openspot.R;
import com.techease.openspot.controllers.AllGroundsModel;
import com.techease.openspot.ui.activities.BottomNavigationActivity;
import com.techease.openspot.utils.AlertsUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.INPUT_METHOD_SERVICE;


public class ListOfAllBooking extends Fragment implements View.OnClickListener {
    EditText searchView;
    Button btnFindspot;
    ImageView ivClose;
    TextView btnDuration30,btnDuration60, btnDuration90, btnSportFootball, btnSportBasketBall, btnSportCricket,
    tvTime1,tvTime2,tvTime3,tvNoGroundFound;
    RecyclerView daysRecycler, groundsRecycler;
    DateAndTimeAdapter recyclerViewAdapter;
    LinearLayout linearLayoutSpot;
    List<AllGroundsModel> list;
    AllGroundsAdapter allGroundsAdapter;
    android.support.v7.app.AlertDialog alertDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    SimpleDateFormat year;
    String  filterSport="football",  filterDuration = "30", filterTimeTo, filterTimeFrom,formatYear;
    public static String filterDate;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_of_all_booking, container, false);

        //hiding action bar
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        sharedPreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        daysRecycler = (RecyclerView) view.findViewById(R.id.dayslistview);
        linearLayoutSpot = (LinearLayout) view.findViewById(R.id.bottomView);
        searchView = (EditText) view.findViewById(R.id.searchView);
        tvNoGroundFound=(TextView)view.findViewById(R.id.tvNoGroundFound);
        groundsRecycler = (RecyclerView) view.findViewById(R.id.rvAllBookings);
        ivClose = (ImageView) view.findViewById(R.id.ivClose);
        btnFindspot = (Button) view.findViewById(R.id.findSpot);
        tvTime1 = (TextView) view.findViewById(R.id.tvTime1);
        tvTime2 = (TextView) view.findViewById(R.id.tvTime2);
        tvTime3 = (TextView) view.findViewById(R.id.tvTime3);
        btnDuration30 = (TextView) view.findViewById(R.id.btnDuration1);
        btnDuration60 = (TextView) view.findViewById(R.id.btnDuration2);
        btnDuration90 = (TextView) view.findViewById(R.id.btnDuration3);
        btnSportFootball = (TextView) view.findViewById(R.id.btnSports1);
        btnSportBasketBall = (TextView) view.findViewById(R.id.btnSports2);
        btnSportCricket = (TextView) view.findViewById(R.id.btnSports3);

        tvTime1.setOnClickListener(this);
        tvTime2.setOnClickListener(this);
        tvTime3.setOnClickListener(this);
        btnSportCricket.setOnClickListener(this);
        btnSportBasketBall.setOnClickListener(this);
        btnSportFootball.setOnClickListener(this);
        btnDuration30.setOnClickListener(this);
        btnDuration60.setOnClickListener(this);
        btnDuration90.setOnClickListener(this);

        groundsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        list = new ArrayList<>();

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c);
        editor.putString("date",formattedDate).commit();

        //for getting year
        SimpleDateFormat formatYears = new SimpleDateFormat("yyyy");
        formatYear= formatYears.format(c);

        //for next 7 days
        SimpleDateFormat format = new SimpleDateFormat("MMM-dd");
        Calendar date2 = Calendar.getInstance();
        String[] dateStringArray2 = new String[7];
        for(int i = 0; i < 7;i++)
        {
            dateStringArray2[i] = format.format(date2.getTime());
            date2.add(Calendar.DATE  , 1);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        daysRecycler.setLayoutManager(linearLayoutManager);
        recyclerViewAdapter = new DateAndTimeAdapter(getActivity(),dateStringArray2);
        daysRecycler.setAdapter(recyclerViewAdapter);


        if (alertDialog == null) {
            alertDialog = AlertsUtils.createProgressDialog(getActivity());
            alertDialog.show();
        }
        //calling api for retriving list of grounds
        apicall();
        allGroundsAdapter = new AllGroundsAdapter(getActivity(), list);
        groundsRecycler.setAdapter(allGroundsAdapter);


        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutSpot.setVisibility(View.VISIBLE);

            }
        });

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutSpot.setVisibility(View.GONE);
            }
        });


        btnFindspot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alertDialog == null)
                    alertDialog = AlertsUtils.createProgressDialog(getActivity());
                    alertDialog.show();
                linearLayoutSpot.setVisibility(View.GONE);
                apiCallForSearch();


            }
        });

        return view;
    }

    private void apiCallForSearch() {
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://openspot.qa/openspot/filterGround"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("ZmaFilter", response);

                try {
                    list.clear();
                  JSONObject  jsonObject = new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("data");
                    for(int i=0; i<jsonArray.length(); i++)
                    {
                        JSONObject object=jsonArray.getJSONObject(i);
                        AllGroundsModel model=new AllGroundsModel();
                        model.setId(object.getString("id"));
                        model.setName(object.getString("name"));
                        model.setLocation(object.getString("location"));
                        model.setType(object.getString("type"));
                        model.setImage(object.getString("image"));
                        model.setInformation(object.getString("information"));
                        list.add(model);

                    }
                    if (list.size()<1)
                    {
                        tvNoGroundFound.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        tvNoGroundFound.setVisibility(View.GONE);
                    }
                    if (alertDialog != null)
                        alertDialog.dismiss();
                    allGroundsAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (alertDialog != null)
                    alertDialog.dismiss();
                Log.d("error", String.valueOf(error.getCause()));
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded;charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("date",formatYear+"-"+filterDate);
                editor.putString("date",filterDate+"/"+formatYear).commit();
                params.put("duration",filterDuration);
                params.put("sport",filterSport);
                params.put("slot","Morning");
                Log.d("zmaParm",params.toString());
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
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://openspot.qa/openspot/grounds"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("ZmaGrounds", response);
                try {
                    list.clear();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArr = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject temp = jsonArr.getJSONObject(i);
                        AllGroundsModel model = new AllGroundsModel();
                        model.setId(temp.getString("id"));
                        model.setImage(temp.getString("image"));
                        model.setName(temp.getString("name"));
                        model.setLocation(temp.getString("location"));
                        model.setType(temp.getString("type"));
                        model.setInformation(temp.getString("information"));
                        list.add(model);
                    }
                    if (alertDialog != null)
                        alertDialog.dismiss();
                    allGroundsAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                    if (alertDialog != null)
                        alertDialog.dismiss();

                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (alertDialog != null)
                    alertDialog.dismiss();

                Log.d("error", String.valueOf(error.getCause()));

            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded;charset=UTF-8";
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
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
    public void onResume() {
        super.onResume();
        apicall();
        allGroundsAdapter = new AllGroundsAdapter(getActivity(), list);
        groundsRecycler.setAdapter(allGroundsAdapter);

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tvTime1:
                tvTime1.setBackgroundResource(R.drawable.custom_rounded_shape);
                tvTime2.setBackgroundResource(0);
                tvTime3.setBackgroundResource(0);
                tvTime1.setTextColor(Color.WHITE);
                tvTime2.setTextColor(Color.GRAY);
                tvTime3.setTextColor(Color.GRAY);
                filterTimeTo = "7AM";
                filterTimeFrom = "11AM";
                break;
            case R.id.tvTime2:
                tvTime2.setBackgroundResource(R.drawable.custom_rounded_shape);
                tvTime1.setBackgroundResource(0);
                tvTime3.setBackgroundResource(0);
                tvTime2.setTextColor(Color.WHITE);
                tvTime1.setTextColor(Color.GRAY);
                tvTime3.setTextColor(Color.GRAY);
                filterTimeTo = "12AM";
                filterTimeFrom = "2AM";
                break;
            case R.id.tvTime3:
                Toast.makeText(getActivity(), "clicked", Toast.LENGTH_SHORT).show();
                tvTime3.setBackgroundResource(R.drawable.custom_rounded_shape);
                tvTime1.setBackgroundResource(0);
                tvTime2.setBackgroundResource(0);
                tvTime3.setTextColor(Color.WHITE);
                tvTime2.setTextColor(Color.GRAY);
                tvTime1.setTextColor(Color.GRAY);
                filterTimeTo = "3PM";
                filterTimeFrom = "5PM";
                break;
            case R.id.btnSports1:
                btnSportFootball.setBackgroundResource(R.drawable.custom_rounded_shape);
                btnSportFootball.setTextColor(Color.WHITE);
                btnSportBasketBall.setTextColor(Color.GRAY);
                btnSportCricket.setTextColor(Color.GRAY);
                btnSportBasketBall.setBackgroundResource(0);
                btnSportCricket.setBackgroundResource(0);
                filterSport = "Football";
                break;
            case R.id.btnSports2:
                btnSportBasketBall.setBackgroundResource(R.drawable.custom_rounded_shape);
                btnSportBasketBall.setTextColor(Color.WHITE);
                btnSportFootball.setTextColor(Color.GRAY);
                btnSportCricket.setTextColor(Color.GRAY);
                btnSportFootball.setBackgroundResource(0);
                btnSportCricket.setBackgroundResource(0);
                filterSport = "BasketBall";
                break;
            case R.id.btnSports3:
                btnSportCricket.setBackgroundResource(R.drawable.custom_rounded_shape);
                btnSportCricket.setTextColor(Color.WHITE);
                btnSportBasketBall.setTextColor(Color.GRAY);
                btnSportFootball.setTextColor(Color.GRAY);
                btnSportBasketBall.setBackgroundResource(0);
                btnSportFootball.setBackgroundResource(0);
                filterSport = "Cricket";
                break;
            case R.id.btnDuration1:
                btnDuration30.setBackgroundResource(R.drawable.custom_rounded_shape);
                btnDuration30.setTextColor(Color.WHITE);
                btnDuration90.setTextColor(Color.GRAY);
                btnDuration60.setTextColor(Color.GRAY);
                btnDuration60.setBackgroundResource(0);
                btnDuration90.setBackgroundResource(0);
                filterDuration = "30";
                break;
            case R.id.btnDuration2:
                btnDuration60.setBackgroundResource(R.drawable.custom_rounded_shape);
                btnDuration60.setTextColor(Color.WHITE);
                btnDuration90.setTextColor(Color.GRAY);
                btnDuration30.setTextColor(Color.GRAY);
                btnDuration30.setBackgroundResource(0);
                btnDuration90.setBackgroundResource(0);
                filterDuration = "60";
                break;
            case R.id.btnDuration3:
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
