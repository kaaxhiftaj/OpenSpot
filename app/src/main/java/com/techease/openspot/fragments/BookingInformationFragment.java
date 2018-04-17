package com.techease.openspot.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.techease.openspot.Adapters.GroundDetailTimesAdapter;
import com.techease.openspot.R;
import com.techease.openspot.ui.activities.BottomNavigationActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;


public class BookingInformationFragment extends Fragment {

    Button btnEmail,btnFb;
    TextView tvConnect,tvPrice,tvType,tvTime,tvName;
    String type,time,price,groundName,strEmail,fullName;
    int timeId;
    String provider;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    LoginButton loginButton;
    CallbackManager callbackManager;
    android.support.v7.app.AlertDialog alertDialog;
    ArrayList<String> timeArray=new ArrayList<String>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_booking_information, container, false);

        //hiding action bar
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        sharedPreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        type=sharedPreferences.getString("type","");
        groundName=sharedPreferences.getString("groundName","");
        time=sharedPreferences.getString("time","");
        timeId=sharedPreferences.getInt("timeId",1);
        price=sharedPreferences.getString("price","");
        tvName=(TextView)view.findViewById(R.id.tvGroundNameBookingInfo);
        tvPrice=(TextView)view.findViewById(R.id.tvPriceBookingInfo);
        tvTime=(TextView)view.findViewById(R.id.tvTimeBookingInfo);
        tvType=(TextView)view.findViewById(R.id.tvType);

        btnEmail=(Button)view.findViewById(R.id.btnEmailBookingInfo);
        btnFb=(Button)view.findViewById(R.id.btnFbBookingInfo);
        tvConnect=(TextView)view.findViewById(R.id.tvConnect);
        loginButton = (LoginButton)view.findViewById(R.id.login_button);

        timeArray= GroundDetailTimesAdapter.timeArray;

        tvType.setText(type);
        tvPrice.setText(String.valueOf(GroundDetailTimesAdapter.price));
        tvTime.setText(timeArray.toString().replace("[","").replace("]","").replace(","," "));
        tvName.setText(groundName);

        provider = "facebook";

        btnFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginButton.performClick();
                ((BottomNavigationActivity) getActivity()).facebook();
                strEmail=sharedPreferences.getString("email","");
                fullName=sharedPreferences.getString("name","");
                Thread timer = new Thread() {
                    public void run() {
                        try {
                            sleep(3000);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            Fragment fragment=new BookNowFragment();
                            getFragmentManager().beginTransaction().replace(R.id.containerMain,fragment).commit();
                        }
                    }
                };
                timer.start();
            }

        });

        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new SignUpWithEmailFragment();
                getFragmentManager().beginTransaction().replace(R.id.containerMain,fragment).addToBackStack("abc").commit();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }
}
