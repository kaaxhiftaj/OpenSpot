package com.techease.openspot.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.view.menu.ExpandedMenuView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.anthonyfdev.dropdownview.DropDownView;
import com.techease.openspot.R;

public class ListOfAllBooking extends Fragment {

    SearchView searchView;
    DropDownView downView;
    LinearLayout linearLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_search, container, false);

        searchView=(SearchView)view.findViewById(R.id.searchView);
        searchView.setQueryHint("Search here");
        linearLayout=(LinearLayout)view.findViewById(R.id.bottomView);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.setVisibility(View.VISIBLE);
            }
        });


        return view;
    }
}
