package com.techease.openspot.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.techease.openspot.R;

import static com.facebook.FacebookSdk.getApplicationContext;


public class ProfileFragment extends Fragment {

    Button btnSignOut;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String name,email,imageUrl;
    TextView tvName,tvEmail;
    ImageView imageView;
    LinearLayout linearLayoutChangePass;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);

       // customActionBar();
        FacebookSdk.sdkInitialize(getApplicationContext());
        sharedPreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        btnSignOut=(Button)view.findViewById(R.id.btnSignOut);
        tvEmail=(TextView)view.findViewById(R.id.tvEmail);
        tvName=(TextView)view.findViewById(R.id.tvName);
        imageView=(ImageView)view.findViewById(R.id.profile_image);
        linearLayoutChangePass=(LinearLayout)view.findViewById(R.id.llChangePass);

        name=sharedPreferences.getString("name","");
        email=sharedPreferences.getString("email","");
        imageUrl=sharedPreferences.getString("image","");

        tvEmail.setText(email);
        tvName.setText(name);
        if (imageUrl!=null)
        {
            Glide.with(getActivity()).load(imageUrl).into(imageView);
        }
        else
        {
            imageView.setBackgroundResource(R.drawable.img);
        }


        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                editor.putString("name","").commit();
                editor.putString("token","").commit();
                editor.putString("email","").commit();
                editor.putString("image","").commit();
                Fragment fragment=new ChooseLoginMethodForProfile();
                getFragmentManager().beginTransaction().replace(R.id.containerMain,fragment).commit();
            }
        });

        linearLayoutChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment=new ChangePasswordFragment();
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
        textView.setText("PROFILE");

    }
}
