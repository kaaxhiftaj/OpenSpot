package com.techease.openspot.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.techease.openspot.Adapters.DateAndTimeAdapter;
import com.techease.openspot.R;
import com.techease.openspot.ui.activities.BottomNavigationActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class ListOfAllBooking extends Fragment {
    SearchView searchView;
    Spinner spinner;
    String[] arraySpinner = new String[] {"Popular", "Famous", "Dummy", "Dummy", "Data"};
    RecyclerView recyclerView;
    DateAndTimeAdapter recyclerViewAdapter;
    LinearLayout linearLayoutSpot;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_of_all_booking, container, false);

        //customActionBar();

        spinner=(Spinner)view.findViewById(R.id.spin);
        recyclerView=(RecyclerView)view.findViewById(R.id.dayslistview);
        linearLayoutSpot=(LinearLayout)view.findViewById(R.id.bottomView);

        SimpleDateFormat curFormater = new SimpleDateFormat("MMM dd");
        GregorianCalendar date = new GregorianCalendar();
        String[] dateStringArray = new String[7];
        date.set(GregorianCalendar.DATE, date.get(GregorianCalendar.DATE)-date.get(GregorianCalendar.MONTH));
        for (int day = 1; day < 7; day++) {
            dateStringArray[day] = curFormater.format(date.getTime());
            date.roll(Calendar.DAY_OF_MONTH, true);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerViewAdapter = new DateAndTimeAdapter(dateStringArray);
        recyclerView.setAdapter(recyclerViewAdapter);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        searchView=(SearchView)view.findViewById(R.id.searchView);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            linearLayoutSpot.setVisibility(View.VISIBLE);
//                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
//                LayoutInflater inflater = getActivity().getLayoutInflater();
//                View dialogView = inflater.inflate(R.layout.custom_filter_search_alertdialog, null);
//
//                dialogBuilder.setView(dialogView);
//
//                AlertDialog alertDialog = dialogBuilder.create();
//                alertDialog.show();
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
