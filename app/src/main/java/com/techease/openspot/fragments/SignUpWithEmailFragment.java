package com.techease.openspot.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.techease.openspot.R;
import com.techease.openspot.ui.activities.BottomNavigationActivity;


public class SignUpWithEmailFragment extends Fragment {
    Button btnRegister;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sign_up_with_email, container, false);

        btnRegister=(Button)view.findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),BottomNavigationActivity.class));
                getActivity().finish();
            }
        });

        return view;
    }
}
