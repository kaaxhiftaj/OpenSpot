package com.techease.openspot.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
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
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.techease.openspot.R;
import com.techease.openspot.ui.activities.BottomNavigationActivity;
import com.techease.openspot.ui.activities.FullscreenActivity;
import com.techease.openspot.ui.activities.SplashActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ChooseLoginMethodForProfile extends Fragment {

    TextView textView;
    Button btnEmail,btnFb;
    LoginButton loginButton;
    CallbackManager callbackManager;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    android.support.v7.app.AlertDialog alertDialog;
    String strEmail,fullName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_choose_login_method_for_profile, container, false);

        //hiding action bar
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        sharedPreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        btnEmail=(Button)view.findViewById(R.id.btnEmailProfile);
        btnFb=(Button)view.findViewById(R.id.btnFbProfile);
        loginButton=(LoginButton) view.findViewById(R.id.btnLoginProfile);
        btnFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginButton.performClick();
                ((BottomNavigationActivity) getActivity()).facebook();

                Thread timer = new Thread() {
                    public void run() {
                        try {
                            sleep(4000);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            Fragment fragment=new ProfileFragment();
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
                Fragment fragment=new SignUpWithEmailFragment();
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
