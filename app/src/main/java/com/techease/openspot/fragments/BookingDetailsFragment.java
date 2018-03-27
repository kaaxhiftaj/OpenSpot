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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BookingDetailsFragment extends Fragment {

    Button btnChange,btnFindSpot;
    ImageView ivClose;
    String groundName,groundId,groundInfo;
    TextView tvName,tvInfo,tvLocation;
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_booking_details, container, false);

        //hiding action bar
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        sharedPreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        ivClose=(ImageView)view.findViewById(R.id.ivCloseBookingDetail);
        btnFindSpot=(Button)view.findViewById(R.id.findSpotBookingDetail);
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
                Log.d("ZmaGroundDetail", response);
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
                    JSONArray jsonArray=jsonObject.getJSONArray("times");
                    for (int i=0; i<jsonArray.length(); i++)
                    {
                        Log.d("zmaTimes",String.valueOf(jsonArray));
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        GroundDetailTimesModel model1=new GroundDetailTimesModel();
                        model1.setTimeId(jsonObject1.getString("time_id"));
                        model1.setTimeTo(jsonObject1.getString("time_to"));
                        model1.setPrice(jsonObject1.getString("price"));
                        model1.setTimeFrom(jsonObject1.getString("time_from"));
                        model1.setIsBooked(jsonObject1.getString("is_booked"));
                        timesModels.add(model1);
                        timesAdapter.notifyDataSetChanged();
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

}
