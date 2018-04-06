package com.techease.openspot.fragments;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.techease.openspot.Adapters.UserBookingsAdapter;
import com.techease.openspot.R;
import com.techease.openspot.controllers.AllGroundsModel;
import com.techease.openspot.controllers.UserBookingsModel;
import com.techease.openspot.utils.AlertsUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import belka.us.androidtoggleswitch.widgets.ToggleSwitch;


public class UserBookingFragment extends Fragment {
    android.support.v7.app.AlertDialog alertDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String strUserId;
    List<UserBookingsModel> list;
    UserBookingsAdapter adapter;
    RecyclerView recyclerView;
    TextView btnFavourtie;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_user_booking, container, false);

        //hiding action bar
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        sharedPreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        btnFavourtie=(TextView) view.findViewById(R.id.btnFavourite);
        recyclerView=(RecyclerView)view.findViewById(R.id.rvUserBooking);
        strUserId=sharedPreferences.getString("user_id","");
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        list=new ArrayList<>();
        if (alertDialog==null)
        {
            alertDialog= AlertsUtils.createProgressDialog(getActivity());
            alertDialog.show();
        }
        apicall();
        adapter=new UserBookingsAdapter(getActivity(),list);
        recyclerView.setAdapter(adapter);

        btnFavourtie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment=new UserFavoritesFragment();
               getFragmentManager().beginTransaction().replace(R.id.containerMain,fragment).addToBackStack("abc").commit();
            }
        });
        return view;
    }

    private void apicall() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://openspot.qa/openspot/userBookings", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (alertDialog!=null)
                    alertDialog.dismiss();
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("data");
                    for (int i=0; i<jsonArray.length(); i++)
                    {
                        JSONObject object=jsonArray.getJSONObject(i);
                        UserBookingsModel model=new UserBookingsModel();
                        model.setId(object.getString("booking_id"));
                        model.setImage(object.getString("image"));
                        model.setName(object.getString("name"));
                        model.setLocation(object.getString("location"));
                        model.setType(object.getString("type"));
                        model.setInformation(object.getString("information"));
                        list.add(model);
                    }
                    adapter.notifyDataSetChanged();
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
                params.put("user_id",strUserId);
                params.put("Accept", "application/json");
                return checkParams(params);
            }
        };

        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
        stringRequest.setRetryPolicy(new
                DefaultRetryPolicy(200000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(stringRequest);
    }
    private Map<String, String> checkParams(Map<String, String> map){
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> pairs = (Map.Entry<String, String>)it.next();
            if(pairs.getValue()==null){
                map.put(pairs.getKey(), "");
            }
        }
        return map;
    }

}
