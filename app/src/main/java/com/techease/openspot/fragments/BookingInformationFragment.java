package com.techease.openspot.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.techease.openspot.R;


public class BookingInformationFragment extends Fragment {

    Button btnBookNow,btnEmail,btnFb;
    TextView tvConnect;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_booking_information, container, false);

        btnBookNow=(Button)view.findViewById(R.id.btnBookNow);
        btnEmail=(Button)view.findViewById(R.id.btnEmailBookingInfo);
        btnFb=(Button)view.findViewById(R.id.btnFbBookingInfo);
        tvConnect=(TextView)view.findViewById(R.id.tvConnect);
        btnFb.setVisibility(View.VISIBLE);
        btnEmail.setVisibility(View.VISIBLE);
        tvConnect.setVisibility(View.VISIBLE);
        btnBookNow.setVisibility(View.GONE);

        btnFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnFb.setVisibility(View.INVISIBLE);
                btnEmail.setVisibility(View.INVISIBLE);
                tvConnect.setVisibility(View.INVISIBLE);
                btnBookNow.setVisibility(View.VISIBLE);
            }
        });
        btnBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment=new BookingPlacedFragment();
                getFragmentManager().beginTransaction().replace(R.id.containerMain,fragment).addToBackStack("abc").commit();
            }
        });
        return view;
    }

}
