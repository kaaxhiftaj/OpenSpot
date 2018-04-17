package com.techease.openspot.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.techease.openspot.Adapters.GroundDetailTimesAdapter;
import com.techease.openspot.R;
import com.techease.openspot.ui.activities.BookingPlacedActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class BookNowFragment extends Fragment {

    Button btnBookNow;
    TextView tvPrice,tvTime,tvType,tvGroundName;
    String strType,strTime,strPrice,strGroundName,strUserId;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ArrayList<String> timeArray=new ArrayList<String>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_book_now, container, false);

        //hiding action bar
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        sharedPreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        strUserId=sharedPreferences.getString("user_id","");
        strPrice=sharedPreferences.getString("price","");
        strTime=sharedPreferences.getString("time","");
        strType=sharedPreferences.getString("type","");
        strGroundName=sharedPreferences.getString("name","");

        tvGroundName=(TextView)view.findViewById(R.id.tvGroundNameBookNow);
        tvPrice=(TextView)view.findViewById(R.id.tvPriceBookNow);
        tvTime=(TextView)view.findViewById(R.id.tvTimeBookNow);
        tvType=(TextView)view.findViewById(R.id.tvPriceBookNow);
        btnBookNow=(Button)view.findViewById(R.id.btnBookNow);

        tvType.setText(strType);
        timeArray=GroundDetailTimesAdapter.timeArray;
        tvTime.setText(timeArray.toString().replace("[","").replace("]","").replace(","," "));
        tvPrice.setText(String.valueOf(GroundDetailTimesAdapter.price));
        tvGroundName.setText(strGroundName);

        btnBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(),BookingPlacedActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
