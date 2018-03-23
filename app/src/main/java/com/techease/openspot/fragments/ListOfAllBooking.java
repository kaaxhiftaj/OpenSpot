package com.techease.openspot.fragments;

import android.app.AlertDialog;
import android.content.Context;
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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ListOfAllBooking extends Fragment {
    SearchView searchView;
    RecyclerView daysRecycler,groundsRecycler;
    DateAndTimeAdapter recyclerViewAdapter;
    LinearLayout linearLayoutSpot;
    List<AllGroundsModel> list;
    AllGroundsAdapter allGroundsAdapter;
    android.support.v7.app.AlertDialog alertDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_of_all_booking, container, false);

        //customActionBar();
        daysRecycler=(RecyclerView)view.findViewById(R.id.dayslistview);
        linearLayoutSpot=(LinearLayout)view.findViewById(R.id.bottomView);
        searchView=(SearchView)view.findViewById(R.id.searchView);
        groundsRecycler=(RecyclerView)view.findViewById(R.id.rvAllBookings);
        groundsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        list=new ArrayList<>();

        SimpleDateFormat curFormater = new SimpleDateFormat("MMM dd");
        GregorianCalendar date = new GregorianCalendar();
        String[] dateStringArray = new String[7];
        date.set(GregorianCalendar.DATE, date.get(GregorianCalendar.DATE)-date.get(GregorianCalendar.MONTH));
        for (int day = 1; day < 7; day++) {
            dateStringArray[day] = curFormater.format(date.getTime());
            date.roll(Calendar.DAY_OF_MONTH, true);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false);
        daysRecycler.setLayoutManager(linearLayoutManager);
        recyclerViewAdapter = new DateAndTimeAdapter(dateStringArray);
        daysRecycler.setAdapter(recyclerViewAdapter);

        if (alertDialog==null)
        {
            alertDialog= AlertsUtils.createProgressDialog(getActivity());
            alertDialog.show();
        }
        apicall();
        allGroundsAdapter=new AllGroundsAdapter(getActivity(),list);
        groundsRecycler.setAdapter(allGroundsAdapter);

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            linearLayoutSpot.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }

    private void apicall() {
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://openspot.qa/openspot/grounds"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("ZmaGrounds", response);
                try {
                    list.clear();
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArr=jsonObject.getJSONArray("data");
                    for (int i=0; i<jsonArr.length(); i++)
                    {
                        JSONObject temp = jsonArr.getJSONObject(i);
                        AllGroundsModel model=new AllGroundsModel();
                        model.setId(temp.getString("id"));
                        model.setImage(temp.getString("image"));
                        model.setName(temp.getString("name"));
                        model.setLocation(temp.getString("location"));
                        model.setType(temp.getString("type"));
                        model.setInformation(temp.getString("information"));
                        list.add(model);

                    }
                    if (alertDialog!=null)
                        alertDialog.dismiss();
                    allGroundsAdapter.notifyDataSetChanged();

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
                return params;
            }

        };
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(stringRequest);
    }

    public void customActionBar(){
    android.support.v7.app.ActionBar mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayHomeAsUpEnabled(true);
    LayoutInflater mInflater = LayoutInflater.from(getActivity());
    View mCustomView = mInflater.inflate(R.layout.custom_actionabr, null);
        TextView textView=(TextView)mCustomView.findViewById(R.id.tvActoinBarTitle);
        textView.setText(" ");

}
}
