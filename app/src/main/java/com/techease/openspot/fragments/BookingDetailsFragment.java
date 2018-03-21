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


public class BookingDetailsFragment extends Fragment {

    Button btnChange;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_booking_details, container, false);

        customActionBar();
        btnChange=(Button)view.findViewById(R.id.btnChange);
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment=new BookingInformationFragment();
                getFragmentManager().beginTransaction().replace(R.id.containerMain,fragment).addToBackStack("abc").commit();
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
        textView.setText(" ");

    }
}
