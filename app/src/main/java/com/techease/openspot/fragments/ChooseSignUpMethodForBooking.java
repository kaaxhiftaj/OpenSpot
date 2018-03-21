package com.techease.openspot.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.techease.openspot.R;


public class ChooseSignUpMethodForBooking extends Fragment {

    TextView textView;
    Button btnEmail,btnFb;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_choose_sign_up_method_for_booking_method, container, false);

        getActivity().setTitle("BOOKINGS");
        //customActionBar();
        btnEmail=(Button)view.findViewById(R.id.btnEmail);
        btnFb=(Button)view.findViewById(R.id.btnFb);


        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment=new SignUpWithEmailFragment();
                getFragmentManager().beginTransaction().replace(R.id.containerMain,fragment).commit();
            }
        });
        btnFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment=new ProfileFragment();
                getFragmentManager().beginTransaction().replace(R.id.containerMain,fragment).commit();
            }
        });
        return view;
    }

    public void customActionBar(){
        android.support.v7.app.ActionBar mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        LayoutInflater mInflater = LayoutInflater.from(getActivity());
        View mCustomView = mInflater.inflate(R.layout.custom_actionabr, null);
        TextView textView=(TextView)mCustomView.findViewById(R.id.tvActoinBarTitle);
        textView.setText("BOOKING");

    }
}
